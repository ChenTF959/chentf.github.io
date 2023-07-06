package tankgame_.tankgame6;

import java.util.Vector;

/**
 * @author 陈唐发
 * @version 1.0
 * @date 2023/6/29 16:26
 */

@SuppressWarnings({"all"})
public class  EnemyTank extends Tank implements Runnable{

    //在敌人坦克类，使用Vector保存多个Shot
    Vector<Shot> shots = new Vector<>();

    //增加成员，EnemyTank可以得到敌人坦克的Vector
    //1.Vector<EnemyTank>
    Vector<EnemyTank> enemyTanks = new Vector<>();

    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法，可以将MyPanel 的成员Vector<EnemyTank> enemyTanks = new Vector<>();
    //设置到EnemyTank 的成员 enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前的这个敌人坦克，是否和 enemyTanks中的其他坦克是否发生重叠或者碰撞
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克(this)的方向
        switch(this.getDirect()) {
            case 0: //向上
                //让当前的敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vectory中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人的坦克是上/下方向
                        //1.判断敌人坦克的x范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //              y范围[enemyTank.getY(),enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的左上角坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的右上角坐标[this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        //1.判断敌人坦克的x范围[enemyTank.getX() - 10,enemyTank.getX() + 50]
                        //              y范围[enemyTank.getY() + 10,enemyTank.getY() + 50]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左上角坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX() - 10
                                    && this.getX() <= enemyTank.getX() + 50
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 50) {
                                return true;
                            }
                            //3.当前坦克的右上角坐标[this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX() - 10
                                    && this.getX() + 40 <= enemyTank.getX() + 50
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: //向右
                //让当前的敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vectory中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人的坦克是上/下方向
                        //1.判断敌人坦克的x范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //              y范围[enemyTank.getY(),enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的右上角坐标[this.getX() + 50,this.getY() + 10]
                            if (this.getX() + 50 >= enemyTank.getX()
                                    && this.getX() + 50 <= enemyTank.getX() + 40
                                    && this.getY() + 10 >= enemyTank.getY()
                                    && this.getY() + 10 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标[this.getX() + 50,this.getY() + 50]
                            if (this.getX() + 50 >= enemyTank.getX()
                                    && this.getX() + 50 <= enemyTank.getX() + 40
                                    && this.getY() + 50 >= enemyTank.getY()
                                    && this.getY() + 50 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        //1.判断敌人坦克的x范围[enemyTank.getX() - 10,enemyTank.getX() + 50]
                        //              y范围[enemyTank.getY() + 10,enemyTank.getY() + 50]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的右上角坐标[this.getX() + 50,this.getY() + 10]
                            if (this.getX() + 50 >= enemyTank.getX() - 10
                                    && this.getX() + 50 <= enemyTank.getX() + 50
                                    && this.getY() + 10 >= enemyTank.getY() + 10
                                    && this.getY() + 10 <= enemyTank.getY() + 50) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标[this.getX() + 50,this.getY() + 50]
                            if (this.getX() + 50 >= enemyTank.getX() - 10
                                    && this.getX() + 50 <= enemyTank.getX() + 50
                                    && this.getY() + 50 >= enemyTank.getY() + 10
                                    && this.getY() + 50 <= enemyTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //向下
                //让当前的敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vectory中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人的坦克是上/下方向
                        //1.判断敌人坦克的x范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //              y范围[enemyTank.getY(),enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的左下角坐标[this.getX(),this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标[this.getX() + 40,this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        //1.判断敌人坦克的x范围[enemyTank.getX() - 10,enemyTank.getX() + 50]
                        //              y范围[enemyTank.getY() + 10,enemyTank.getY() + 50]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左下角坐标[this.getX(),this.getY() + 60]
                            if (this.getX() >= enemyTank.getX() - 10
                                    && this.getX() <= enemyTank.getX() + 50
                                    && this.getY() + 60 >= enemyTank.getY() + 10
                                    && this.getY() + 60 <= enemyTank.getY() + 50) {
                                return true;
                            }
                            //3.当前坦克的右下角坐标[this.getX() + 40,this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX() - 10
                                    && this.getX() + 40 <= enemyTank.getX() + 50
                                    && this.getY() + 60 >= enemyTank.getY() + 10
                                    && this.getY() + 60 <= enemyTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //向左
                //让当前的敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vectory中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人的坦克是上/下方向
                        //1.判断敌人坦克的x范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //              y范围[enemyTank.getY(),enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2.当前坦克的左上角坐标[this.getX() - 10,this.getY() + 10]
                            if (this.getX() - 10 >= enemyTank.getX()
                                    && this.getX() - 10 <= enemyTank.getX() + 40
                                    && this.getY() + 10 >= enemyTank.getY()
                                    && this.getY() + 10 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3.当前坦克的左下角坐标[this.getX() - 10,this.getY() + 50]
                            if (this.getX() - 10 >= enemyTank.getX()
                                    && this.getX() - 10 <= enemyTank.getX() + 40
                                    && this.getY() + 50 >= enemyTank.getY()
                                    && this.getY() + 50 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是 右/左
                        //1.判断敌人坦克的x范围[enemyTank.getX() - 10,enemyTank.getX() + 50]
                        //              y范围[enemyTank.getY() + 10,enemyTank.getY() + 50]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2.当前坦克的左上角坐标[this.getX() - 10,this.getY() + 10]
                            if (this.getX() - 10 >= enemyTank.getX() - 10
                                    && this.getX() - 10 <= enemyTank.getX() + 50
                                    && this.getY() + 10 >= enemyTank.getY() + 10
                                    && this.getY() + 10 <= enemyTank.getY() + 50) {
                                return true;
                            }
                            //3.当前坦克的左下角坐标[this.getX() - 10,this.getY() + 50]
                            if (this.getX() - 10 >= enemyTank.getX() - 10
                                    && this.getX() - 10 <= enemyTank.getX() + 50
                                    && this.getY() + 50 >= enemyTank.getY() + 10
                                    && this.getY() + 50 <= enemyTank.getY() + 50) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {

        while (true) {

            //这里我们判断如果shots size() < 2 ，创建一颗子弹放入到shots集合并启动
            if (isLive && shots.size() < 2) {
                Shot s = null;
                //判断坦克方向，绘制对应的子弹
                switch (getDirect()){
                    case 0://向上
                        s = new Shot(getX() + 20,getY(),0);
                        break;
                    case 1://向右
                        s = new Shot(getX() + 50,getY() + 30,1);
                        break;
                    case 2://向下
                        s = new Shot(getX() + 20,getY() + 60,2);
                        break;
                    case 3://向左
                        s = new Shot(getX() - 10,getY() + 30,3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            //根据坦克的方向继续移动
            switch (getDirect()) {
                case 0: //向上
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 1: //向右
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 50 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 2: //向下
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 3: //向左
                    for (int i = 0; i < 30; i++) {
                        if (getX() - 10 > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }

            //休眠50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //然后随机的改变坦克方向
            setDirect((int) (Math.random() * 4));

            //写并发程序，必须考虑清楚，该线程什么时候结束

            if (!isLive) {
                break;//退出线程
            }
        }
    }
}
