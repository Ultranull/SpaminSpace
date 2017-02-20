package Utils;

import java.util.ArrayList;

/**
 * Created by usr on 2/11/2017.
 *
 */
public class Alien {
    Point origin;
    Sprite sprite;
    public Alien(Point o,int[] texs){
        origin=o;
        sprite=new Sprite(texs,origin,0,1);
        sprite.moverate=1;
        sprite.rate=20;
    }
    public void setPath(Path p){
        sprite.setPath(p);
    }
    public void draw(float rx,float ry,float rz,int ticks){
        sprite.draw(rx,ry,rz,ticks);
    }
    public boolean haspath(){
        return sprite.hasPath;
    }
    public void display(){
       System.out.println(sprite.getPath().size());
    }
}
