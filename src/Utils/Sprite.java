package Utils;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by usr on 2/8/2017.
 *
 *
 */
public class Sprite {

    private Polygon im;
    private Point or;
    private int[] texs;
    private int[] animationindex;
    private boolean hasai=false;
    private boolean flippedH=false;
    private boolean flippedV=false;
    private int ts;
    private int te;
    private int index=0;
    public int rate=20;
    public Sprite(int[] t,Point o,int tt,int ttt){
        or=o;
        texs=t;
        ts=tt;
        te=ttt;
        im=new Polygon(new Point[]{
                new Point(-.5f,1,0),
                new Point(-.5f,0,0),
                new Point(.5f,0,0),
                new Point(.5f,1,0)});
        im.HasTex(new Point[]{
                new Point(0,0,0),
                new Point(0,1,0),
                new Point(1,1,0),
                new Point(1,0,0),
        },texs[ts]);

    }
    public void draw(float rx,float ry,float rz,int ticks){
        if(ticks%rate==0) {
            index++;
            if(index>te||index<ts)
                index=ts;
            if(hasai)
                im.setTexid(texs[animationindex[index]]);
            else
                im.setTexid(texs[index]);
        }
        glPushMatrix();
        glTranslatef(or.x,or.y,or.z);
        glRotatef(-rx,1,0,0);
        glRotatef(-ry,0,1,0);
        glRotatef(-rz,0,0,1);
        im.draw();
        glPopMatrix();
    }
    public void setHasai(int[] i,int end,int offset){
        hasai=true;
        animationindex =i;
        ts=offset;
        te=end;
    }
    public void settex(int i,int c) {
        ts=i;
        te=c;
    }
    public void setOr(Point or) {
        this.or = or;
    }
    public void flipH(boolean t){
        flippedH=t;
        if(flippedH)
            im.setTex(new Point[]{
                    new Point(1,0,0),
                    new Point(1,1,0),
                    new Point(0,1,0),
                    new Point(0,0,0),});
        else
            im.setTex(new Point[]{
                    new Point(0,0,0),
                    new Point(0,1,0),
                    new Point(1,1,0),
                    new Point(1,0,0),});
    }

    public boolean isFlippedH() {
        return flippedH;
    }
}
