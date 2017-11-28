import Utils.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by usr on 2/26/2017.
 *
 */
public class Spam extends HasSprite{
    Path path;
    Ship player;
    int moverate=1;
    public Spam(Ship p,int tid){
        player=p;
        path=new Path(new Point[]{
                new Point(0,0,0),
                new Point(0,0,-20)
        },false);
        path.translate(player.origin);
        path.normalize(1/4f);
        origin=p.origin;
        sprite=new Sprite(new int[]{tid},origin,0,0);
    }
    int rot=1;
    public void draw(int ticks){
        origin = path.get();
        if (ticks % moverate == 0) {
            path.next();
        }
        sprite.setOr(origin);

        isdead=path.isDone();
        if(!isdead)
            sprite.draw(0,0,rot+=2,ticks);
    }
}
