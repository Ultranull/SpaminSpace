import Utils.Point;
import Utils.Polygon;
import Utils.Sprite;

/**
 * Created by usr on 3/4/2017.
 *
 */
public class explosion {
    Sprite sprite;
    int life=10;
    public boolean dead=false;
    public explosion(int[] tex, Point p){
        sprite=new Sprite(tex,p,0,2);
    }
    public void draw(int rx,int ry,int rz,int ticks){
        sprite.draw(rx,ry,rx,ticks);
        life--;
        if(life<=0)
            dead=true;
    }
}
