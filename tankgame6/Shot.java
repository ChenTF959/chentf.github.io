package tankgame_.tankgame6;

/**
 * @author 陈唐发
 * @version 1.0
 * @date 2023/6/29 15:37
 */
public class Shot implements Runnable {
    int x;//子弹的x坐标
    int y;//子弹的y坐标
    int direct = 0;//子弹方向
    int speed = 2;//子弹速度
    boolean isLive = true;//子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//射击
        while (true) {

            //线程休眠50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //根据方向改变x，y坐标
            switch(direct) {
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }

            //测试输出x，y
            System.out.println("x= " + x + " y= " + y);

            //当子弹移动到面板的边界时，就应该销毁(把启动子弹的线程销毁)
            //当子弹碰到敌人坦克时，也应该结束线程
            if ( !(x >=0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                System.out.println("子弹线程退出");
                isLive = false;
                break;
            }

        }

    }
}
