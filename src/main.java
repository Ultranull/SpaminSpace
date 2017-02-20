

import Utils.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import sun.awt.image.ImageWatched;


import java.nio.FloatBuffer;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public class main {

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
    Path enter=new Path(new Point[]{
            new Point(20,20,0),
            new Point(10,10,0),
            new Point(0,5,0),
    },false);
    private void init(){

        Material.init(new String[]{
                "back","images\\blueHatBack.png",
                "front","images\\blueHatFront.png",
                "dopen","images\\dopen.jpg",
                "dclose","images\\dclose.jpg",
        });
        player=new Ship(new Point(0,0,0),new int[]{Material.get("back"),Material.get("front")});
        Alien derek=new Alien(new Point(0,100,0),new int[]{
                Material.get("dopen"),
                Material.get("dclose"),
        });
        fleet=new LinkedList<>();
        fleet.add(derek);
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
    private void update(int delta) {
        initGL();
        updateFPS();
        getInput();

        glClear(GL_COLOR_BUFFER_BIT
                | GL_DEPTH_BUFFER_BIT);

        for (Alien a :fleet) {
            a.draw(0,0,0,ticks);
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
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            for(Alien a:fleet) {
                a.setPath(enter);
            }
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
