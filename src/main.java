

import Utils.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;


import java.security.Key;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public class main {//todo make spawner with different paths and stuff yeh

    long lastFrame;
    int fps;
    long lastFPS;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
            Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        initGL();
        init();
        getDelta();
        lastFPS = getTime();
        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            Display.update();
            Display.sync(60); // cap fps to 60fps
            if (Display.wasResized()) {
                glViewport(0, 0, Display.getWidth(), Display.getHeight());
                initGL();
            }
        }

        Display.destroy();
    }
    Point ms=new Point(0,0,-30);
    Path enterm=new Path(new Point[]{
            ms,
            new Point(0,0,-5),
    },false);
    private void init(){

        Material.init(new String[]{
                "back","images\\blueHatBack.png",
                "front","images\\blueHatFront.png",
                "dopen","images\\dopen.png",
                "dclose","images\\dclose.png",
                "sky","images\\space.jpg",
                "spam","images\\Spam_can.png"
        });
        player=new Ship(new Point(0,0,0),new int[]{Material.get("back"),Material.get("front")});
        fleet=new LinkedList<>();
        pew=new LinkedList<>();

        for(int i=0;i<10;i++)
            fleet.add(new Alien(new Point(5-i,0,100),new int[]{
                    Material.get("dopen"),
                    Material.get("dclose"),
            }));

    }
    private void initGL() {

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.0);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        GLU.gluPerspective(45f, (float) Display.getWidth()
                / (float) Display.getHeight(), 0.1f, 800f);
        glTranslatef(0,0,-21);
        glRotatef(30,1,0,0);
        glMatrixMode(GL_MODELVIEW);

    }



    int ticks =0;
    Ship player;
    LinkedList<Alien> fleet;
    LinkedList<Spam> pew;
    private void update(int delta) {
        initGL();
        updateFPS();
        getInput();

        glClear(GL_COLOR_BUFFER_BIT
                | GL_DEPTH_BUFFER_BIT);
        drawback();
        for (int i = 0; i < fleet.size(); i++) {
            Alien a = fleet.get(i);
            if(a.isdead) {
                fleet.remove(a);
                i--;
            }else
                a.draw(30,0,0,ticks,pew);
        }
        for (int i = 0; i < pew.size(); i++) {
            Spam s = pew.get(i);
            if(s.isdone) {
                pew.remove(s);
                i--;
            }else
            s.draw(ticks);
        }

        player.draw();
        int ii=10;
        if(true)
            for (int i = -ii; i < ii; i++) {
//            drawLine(new Point(i, ii, 0, 0, 0, 1), new Point(i, -ii, 0, 0, 0, 1));//y blue
//            drawLine(new Point(0, i, ii, 0, 1, 0), new Point(0, i, -ii, 0, 1, 0));//z green
                drawLine(new Point(ii, 0, i, 1, 0, 0), new Point(-ii, 0, i, 1, 0, 0));//x red
                glColor3f(1,1,1);
            }
        ticks +=1;
    }
    private void drawLine(Point point, Point point2) {
        glBegin(GL_LINE_STRIP);
        pointset(point);
        pointset(point2);
        glEnd();
    }
    private void pointset(Point point){
        glColor3f(point.r,point.g,point.b);
        glVertex3f(point.x, point.y,point.z);
    }
    private void drawback(){
        glPushMatrix();
        glRotatef(-30,1,0,0);
        glTranslatef(0,0,-10);
        Polygon.draw(new Point[]{
                new Point(-30,30,0),
                new Point(-30,-30,0),
                new Point(30,-30,0),
                new Point(30,30,0),
        },new Point[]{
                new Point(0,0,0),
                new Point(0,1,0),
                new Point(1,1,0),
                new Point(1,0,0),
        },Material.get("sky"));
        glPopMatrix();
    }
    private void getInput(){

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            player.move(-.125f,0,0,10,-10);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            player.move(.125f,0,0,10,-10);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
            player.move(0,0,-.125f,10,-10);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            player.move(0,0,.125f,10,-10);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_P)){

        }
        if(Keyboard.next()&&!Keyboard.getEventKeyState())
            switch (Keyboard.getEventKey()){
                case Keyboard.KEY_SPACE:
                    pew.add(new Spam(player,Material.get("spam")));
                    break;
                case Keyboard.KEY_P:
                    for(Alien a:fleet) {
                        enterm.translate(a.origin.getX(),0,0);
                        a.setPath(enterm.clone());
                        enterm.translate(-a.origin.getX(),0,0);
                    }
                    break;
            }
    }
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    double sin(double a){return Math.sin(Math.toRadians(a));}
    double cos(double a){return Math.cos(Math.toRadians(a));}





}
