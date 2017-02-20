import Utils.Point;
import Utils.Polygon;
import Utils.Textureloader;

/**
 * Created by usr on 2/7/2017.
 *
 */
public class Ship {
    Point origin;
    Polygon left;
    Polygon right;
    Polygon back;
    int[] texid;
    public Ship(Point p,int[] t){
        origin=p;
        texid=t;
        polyinit();
    }
    private void polyinit(){
        right=new Polygon(new Point[]{
                new Point(0,.125f,0,  1,1,1).sum(origin),
                new Point(0,0,-1,     1,1,1).sum(origin),
                new Point(.5f,0,.5f,  1,1,1).sum(origin),
        });
        right.HasTex(new Point[]{
                new Point(1,0,0),
                new Point(0,1,0),
                new Point(1,1,0),
        }, texid[1]);
        left=new Polygon(new Point[]{
                new Point(0,.125f,0,    1,1,1).sum(origin),
                new Point(0,0,-1,     1,1,1).sum(origin),
                new Point(-.5f,0,.5f,  1,1,1).sum(origin),
        });
        left.HasTex(new Point[]{
                new Point(1,0,0),
                new Point(0,1,0),
                new Point(1,1,0),
        }, texid[1]);
        back=new Polygon(new Point[]{
                new Point(0,.125f,0,    1,1,1).sum(origin),
                new Point(-.5f,0,.5f,  1,1,1).sum(origin),
                new Point(.5f,0,.5f,  1,1,1).sum(origin),
        });

        back.HasTex(new Point[]{
                new Point(0.5f,0,0),
                new Point(0,1,0),
                new Point(1,1,0),
        }, texid[0]);
    }
public void move(float x,float y,float z,float max,float min){
    origin=origin.sum(new Point(x,y,z));
    origin.x=origin.x>max?min:origin.x<min?max:origin.x;
    origin.y=origin.y>max?min:origin.y<min?max:origin.y;
    origin.z=origin.z>max?min:origin.z<min?max:origin.z;
    polyinit();
}
    public void draw(){
        left.draw();
        right.draw();
        back.draw();
    }
}
