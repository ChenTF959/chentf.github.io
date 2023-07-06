package tankgame_.tankgame6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 陈唐发
 * @version 1.0
 * @date 2023/6/29 15:02
 */
public class CTFTankGame06 extends JFrame {

    //定义MyPanel
    private MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        CTFTankGame06 ctfTankGame01 = new CTFTankGame06();
    }
    public CTFTankGame06() {
        System.out.println("请选择：");
        System.out.println("1.新游戏");
        System.out.println("2.继续游戏");
        System.out.println("按任意其他键退出程序");
        String key = scanner.next();

        mp = new MyPanel(key);
        //将mp放入到Thread,并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板加进去
        this.setSize(1300,950);
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中 增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });

    }
}
