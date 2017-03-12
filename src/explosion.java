import Utils.Point;
import Utils.Polygon;
import Utils.Sprite;

/**
 * Created by usr on 3/4/2017.
 *
 */
public class explosion extends HasSprite{
    int life=10;
    public explosion(int[] tex, Point p){
        sprite=new Sprite(tex,p,0,2);
    }
    public void draw(int ticks){
        sprite.draw(30,0,0,ticks);
        life--;
        if(life<=0)
            isdead=true;
    }
}
