import Utils.Path;
import Utils.Point;
import Utils.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by usr on 2/11/2017.
 *
 */
public class Alien {
    Point origin;
    Sprite sprite;
    Path path;
    boolean hasPath=false;
    boolean isdead=false;
    int moverate=1;
    public Alien(Point o,int[] texs){
        origin=o;
        sprite=new Sprite(texs,origin,0,1);
        sprite.rate=20;
    }
    public void setPath(Path p){
        hasPath=true;
        path=p;
        p.normalize(1/4f);
    }
    public void draw(float rx, float ry, float rz, int ticks, LinkedList<Spam> pew){
        if(hasPath) {
            origin = path.get();
            if (ticks % moverate == 0) {
                path.next();
            }
        }
        for(Spam s:pew)
            if(s.origin.distance(origin)<1) {
                isdead = true;
                s.isdone=true;
            }
        sprite.setOr(origin);
        sprite.draw(rx,ry,rz,ticks);
    }
    public boolean haspath(){
        return hasPath;
    }
    public void display(){
        System.out.println(path.size());
    }
}
