package Utils;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

/**
 * Created by usr on 2/7/2017.
 *
 */
public class Entity {

    private Point origin;
    private Polygon[] faces;

    private int VBOColorHandle;
    private int VBOVertexHandle;
    private int VBONormalHandle;
    private int VBOTextureHandle;
    private int VBOTexID;

    private float l, w, h;

    private float rx = 0;
    private float ry = 0;
    private float rz = 0;

    public boolean isWrieframe = false;

    public Entity(){
        oinit();

    }


    private void oinit() {
        VBOTextureHandle = glGenBuffers();
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBONormalHandle = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, makecolorbuff(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, makevertbuff(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, maketexbuff(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, VBONormalHandle);
        glBufferData(GL_ARRAY_BUFFER, makenormbuff(), GL_STATIC_DRAW);
    }

    public void draw() {

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_BLEND);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

//        glBindTexture(GL_TEXTURE_2D, VBOTexID);
//        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
//        glTexCoordPointer(2, GL_FLOAT, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

//        glBindBuffer(GL_ARRAY_BUFFER, VBONormalHandle);
//        glNormalPointer(GL_FLOAT, 0, 0);

        glPushMatrix();
        glTranslatef(origin.x, origin.y, origin.z);
        float amount = rx != 0 ? rx : ry != 0 ? ry : rz != 0 ? rz : 0;
        float tx = rx != 0 ? 1 : 0, ty = ry != 0 ? 1 : 0, tz = rz != 0 ? 1 : 0;
        glRotatef(amount, tx, ty, tz);
        glTranslatef(-origin.x, -origin.y, -origin.z);
        if (isWrieframe)
            glDrawArrays(GL_LINE_LOOP, 0, 4 * 6);
        else
            glDrawArrays(GL_QUADS, 0, 4 * 6);
        glPopMatrix();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_COLOR_MATERIAL);
        glDisable(GL_BLEND);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);

    }

    public FloatBuffer makecolorbuff() {
        FloatBuffer colors = BufferUtils.createFloatBuffer(24 * 3);
        for (int i = 0; i < faces.length; i++)
            colors.put(faces[i].getColors());
        colors.flip();
        return colors;
    }

    public FloatBuffer makevertbuff() {
        FloatBuffer vertexes = BufferUtils.createFloatBuffer(24 * 3);
        for (int i = 0; i < faces.length; i++)
            vertexes.put(faces[i].getVerts());
        vertexes.flip();
        return vertexes;
    }

    public FloatBuffer maketexbuff() {
        FloatBuffer texture = BufferUtils.createFloatBuffer(24 * 2);
        texture.flip();
        return texture;
    }

    public FloatBuffer makenormbuff() {
        FloatBuffer normal = BufferUtils.createFloatBuffer(12 * 3);
        for (int i = 0; i < faces.length; i++)
            normal.put(faces[i].getNormals());
        normal.flip();
        return normal;
    }


    public void rotate(float amount, float tx, float ty, float tz) {
        rx += amount * tx;
        ry += amount * ty;
        rz += amount * tz;
    }

    public Point getOrigin() {
        return origin;
    }

    public void translate(float x, float y, float z) {
        translate(new Point(x, y, z));
    }

    public void translate(Point de) {
        this.origin.x += de.x;
        this.origin.y += de.y;
        this.origin.z += de.z;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }
}
