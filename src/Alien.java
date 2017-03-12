import Utils.Path;
import Utils.Point;
import Utils.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by usr on 2/11/2017.
 *
 */
public class Alien extends HasSprite{
    Path path;
    boolean hasPath=false;
    boolean dwd=false;
    int moverate=3;
    int id;
    public Alien(Point o,int[] texs){
        origin=o;
        sprite=new Sprite(texs,origin,0,1);
        sprite.rate=20;
        id=(int)(Math.random()*100);
    }
    public void setPath(Path p){
        hasPath=true;
        path=p;
        p.normalize(1/4f);
    }
    public void checkcol(LinkedList<Spam> pew){
        for(Spam s:pew)
            if(s.origin.distance(origin)<1) {
                isdead = true;
                s.isdead=true;
            }
    }
    public void draw(int ticks){
        if(hasPath) {
            if(path.isDone()&&!path.isLoops()&&dwd)
                isdead=true;
            origin = path.get();
            if (ticks % moverate == 0) {
                path.next();
            }
        }
        sprite.setOr(origin);
        sprite.draw(30,0,0,ticks);
    }
    public boolean haspath(){
        return hasPath;
    }
    public void display(){
        System.out.println(path.size());
    }
}
