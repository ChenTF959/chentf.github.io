package tankgame_.tankgame6;

import java.util.Vector;

/**
 * @author 陈唐发
 * @version 1.0
 * @date 2023/6/29 15:59
 */
public class MyTank extends Tank {//自己的坦克

    //定义一个shot对象,表示一个射击(线程)
    Shot shot = null;

    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    public MyTank(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {

        //控制一次最多发射5颗子弹
        if (shots.size() ==5) {
            return;
        }

        //创建 Shot 对象,根据我们tank对象的位置和方向来创建Shot
        switch (getDirect()) {//得到我们的tank的方向
            case 0://向上
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1://向右
                shot = new Shot(getX() + 50,getY() + 30,1);
                break;
            case 2://向下
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3://向左
                shot = new Shot(getX() - 10,getY() + 30,3);
                break;
        }

        //把新建的shot放入到shots中
        shots.add(shot);

        //启动我们的Shot线程
        new Thread(shot).start();

    }


}
