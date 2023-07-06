package tankgame_.tankgame6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author 陈唐发
 * @version 1.0
 * @date 2023/6/29 16:00
 */

//为了监听键盘事件，实现KeyListener
//为了让Panel不停的重绘子弹，需要将MyPanel实现Runnable，当做一个线程使用
public class MyPanel extends JPanel implements KeyListener,Runnable {//坦克大战绘图区
    //定义我的坦克
    MyTank myTank = null;
    //定义敌人坦克,放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    //定义一个Vector，用于存放炸弹
    //当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    int enemyTanksSize = 3;

    public MyPanel(String key) {
        //先判断记录的文件是否存在
        //如果存在，就正常执行，如果文件不存在，提示只能开启新游戏，key = 1
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        //将MyPanel对象的enemyTanks设置给Recorder的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        myTank = new MyTank(200, 500);//初始化自己的坦克
        myTank.setSpeed(2);

        switch (key) {
            case "1": //新游戏
                //初始化击毁坦克数
                Recorder.setAllEnemyTankNum(0);
                //初始化敌人的坦克
                for (int i = 0; i < enemyTanksSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将enemyTanks 设置给enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemyTank).start();

                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot 对象
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //继续上局游戏
                //初始化敌人坦克
                enemyTanksSize = nodes.size();
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks 设置给enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemyTank).start();

                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot 对象
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("程序已退出");
                System.exit(0);
        }

        //初始化图片
//        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));//空指针异常
//        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));//空指针异常
//        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));//空指针异常
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb3.png"));
//        image1 = Toolkit.getDefaultToolkit().getImage("G:\\javaProject\\tankgame\\src\\bomb1.png");
//        image2 = Toolkit.getDefaultToolkit().getImage("G:\\javaProject\\tankgame\\src\\bomb2.png");
//        image3 = Toolkit.getDefaultToolkit().getImage("G:\\javaProject\\tankgame\\src\\bomb3.png");

        //这里播放指定的音乐
        new AePlayWave("src\\111.wav").start();
    }

    //编写方法，显示我方机会敌方坦克的信息
    public void showInfo(Graphics g) {
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁敌方坦克",1020, 30);
        drawTank(1020, 60, g , 0 , 0);//画出敌方坦克
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形
        showInfo(g);

        if (myTank != null && myTank.isLive == true) {
            //画出自己的坦克,调用方法
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }

//        //画出myTank射击的子弹
////        if (myTank.shot != null && myTank.shot.isLive == true)
////            System.out.println("子弹被绘制");
////            g.draw3DRect(myTank.shot.x,myTank.shot.y,1,1,false);
////        }

        //将myTank的子弹集合 shots，遍历取出绘制
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive == true) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {//如果该shot对象已经无效，就从shots集合中remove
                myTank.shots.remove(shot);
            }
        }

        //不休眠则第一次击中无爆炸效果
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //如果bombs集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                 g.drawImage(image1, bomb.x, bomb.y, 60 ,60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60 ,60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60 ,60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为0，就从bombs 的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }


        //画出敌人的坦克,遍历Vector
        for (int i = 0; i < enemyTanksSize; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否存货
            if (enemyTank.isLive) {//当敌人坦克是活的画出坦克

                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);

                //画出enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从Vector移除子弹
                        enemyTank.shots.remove(shot);
                    }
                }
            }


        }
    }

    //画出坦克的方法
    //x,y坦克的坐标，g 画笔， direct 方向， type 类型
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型的坦克设置不同的颜色
        switch (type) {
            case 0: //我们自己坦克
                g.setColor(Color.cyan);
                break;

            case 1: //敌人的坦克
                g.setColor(Color.yellow);
                break;
        }


        //根据坦克的方向绘制对应方向的坦克
        //direct表示方向(0:向上 1:向右 2:向下 3:向左)
        //
        switch (direct) {
            case 0: //向上
                g.fill3DRect(x, y, 10, 60, false);//坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间部分
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间的圆形部分
                g.drawLine(x + 20, y + 30, x + 20, y);//坦克的炮筒
                break;
            case 1: //向右
                g.fill3DRect(x - 10, y + 10, 60, 10, false);//坦克上边的轮子
                g.fill3DRect(x - 10, y + 40, 60, 10, false);//坦克下边的轮子
                g.fill3DRect(x, y + 20, 40, 20, false);//坦克中间部分
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间的圆形部分
                g.drawLine(x + 20, y + 30, x + 50, y + 30);//坦克的炮筒
                break;
            case 2: //向下
                g.fill3DRect(x, y, 10, 60, false);//坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间部分
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间的圆形部分
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//坦克的炮筒
                break;
            case 3: //向左
                g.fill3DRect(x - 10, y + 10, 60, 10, false);//坦克上边的轮子
                g.fill3DRect(x - 10, y + 40, 60, 10, false);//坦克下边的轮子
                g.fill3DRect(x, y + 20, 40, 20, false);//坦克中间部分
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间的圆形部分
                g.drawLine(x + 20, y + 30, x - 10, y + 30);//坦克的炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    //编写方法，判断敌人坦克是否击中我方坦克
    public void hitMyTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历EnemyTank对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (myTank.isLive && shot.isLive){
                    hitTank(shot,myTank);
                }
            }
        }
    }


    //如果坦克可以发射多颗子弹是否击中敌人坦克时，
    //就需要把我们的子弹集合中的子弹全部取出来进行判断
    public void hitEnemyTank() {

        //遍历我们的子弹
        for (int j = 0; j < myTank.shots.size(); j++) {
            Shot shot = myTank.shots.get(j);
            //判断我方子弹是否击中敌人坦克
            if (shot != null && shot.isLive) {//当我方子弹还存活
                //遍历敌人所有的坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }


    //编写方法，判断我方子弹是否击中敌人
    //什么时候判断我方子弹是否击中敌人坦克？ run方法
    public void hitTank(Shot s, Tank tank) {
        //判断s 击中坦克
        if (tank.isLive) {
            switch (tank.getDirect()) {
                case 0:
                case 2:
                    if (s.x > tank.getX() && s.x < tank.getX() + 40
                            && s.y > tank.getY() && s.y < tank.getY() + 60) {
                        s.isLive = false;
                        tank.isLive = false;

                        //当我方子弹击中敌人坦克后，将enemyTank从Vector拿掉,enemyTanks--
                        enemyTanks.remove(tank);
                        enemyTanksSize--;
                        //当我方坦克击毁一个敌方坦克时，就对数据allEnemyTankNum++
                        if (tank instanceof EnemyTank) {
                            Recorder.addAllEnemyTankNum();
                        }

                        //创建Bomb对象，加入到bombs集合
                        Bomb bomb = new Bomb(tank.getX(), tank.getY());
                        bombs.add(bomb);
                    }
                    break;
                case 1:
                case 3:
                    if (s.x > tank.getX() && s.x < tank.getX() + 60
                            && s.y > tank.getY() && s.y < tank.getY() + 40) {
                        s.isLive = false;
                        tank.isLive = false;

//                        //当我方子弹击中敌人坦克后，将enemyTank从Vector拿掉,enemyTanks--
                        enemyTanks.remove(tank);
                        enemyTanksSize--;
                        //当我方坦克击毁一个敌方坦克时，就对数据allEnemyTankNum++
                        if (tank instanceof EnemyTank) {
                            Recorder.addAllEnemyTankNum();
                        }

                        //创建Bomb对象，加入到bombs集合
                        Bomb bomb = new Bomb(tank.getX(), tank.getY());
                        bombs.add(bomb);
                    }
                    break;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理w s a d键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下w键
            //改变坦克的方向
            myTank.setDirect(0);
            if (myTank.getY() > 0) {
                myTank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D键
            //改变坦克的方向
            myTank.setDirect(1);
            if (myTank.getX() + 50 < 1000) {
                myTank.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键
            //改变坦克的方向
            myTank.setDirect(2);
            if (myTank.getY() + 60 < 750) {
                myTank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键
            //改变坦克的方向
            myTank.setDirect(3);
            if (myTank.getX() - 10 > 0) {
                myTank.moveLeft();
            }
        }

        //如果用户按下的是j，就发射
        if (e.getKeyCode() == KeyEvent.VK_J) {

//            //判断myTank的子弹是否销毁发射一颗子弹
//            if (myTank.shot == null || !myTank.shot.isLive) {
//                myTank.shotEnemyTank();
//            }

            //发射多颗子弹
            myTank.shotEnemyTank();
        }
        //让面板重绘
        this.repaint();



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100ms重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断我方子弹是否击中敌人坦克
            hitEnemyTank();

            //判断敌人坦克是否击中我们
            hitMyTank();
            this.repaint();
        }

    }
}
