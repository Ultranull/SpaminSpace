import Utils.Point;
import Utils.Sprite;

/**
 * Created by usr on 3/8/2017.
 *
 */
public abstract class HasSprite {
     boolean isdead=false;
     Sprite sprite=null;
    Point origin;
     abstract void draw(int ticks);
}
