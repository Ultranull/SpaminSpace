package ModelUtil;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Model {
    public Vector3f origin;
    private int VBOColorHandle;
    private int VBOVertexHandle;
    private int VBONormalHandle;
    private int VBOTextureHandle;

    public int VBOTexID;

    private float rx = 0;
    private float ry = 0;
    private float rz = 0;

    public boolean isWrieframe = false;
    public boolean hasTexture  = false;
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Vector3f> textures = new ArrayList<>();
    public List<Faceindex> faces = new ArrayList<>();

    public Model(Vector3f p, String mesh){
        OBJLoader.loadModel(this,new File(mesh));
        origin=p;
        oinit();
    }
    private void oinit() {
        if(hasTexture)
            VBOTextureHandle = glGenBuffers();
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBONormalHandle = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, makecolorbuff(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, makevertbuff(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, VBONormalHandle);
        glBufferData(GL_ARRAY_BUFFER, makenormbuff(), GL_STATIC_DRAW);
        if(hasTexture) {
            glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
            glBufferData(GL_ARRAY_BUFFER, maketexbuff(), GL_STATIC_DRAW);
        }
    }
    public void immediaterender(){
        glBegin(GL_TRIANGLES);
        glColor3f(1,1,1);
        for (Faceindex face : faces) {

            for(int i=0;i<face.norms.length;i++){
                Vector3f n1 = normals.get(face.norms[i] - 1);
                glNormal3f(n1.x, n1.y, n1 .z);
                Vector3f v1 = vertices.get(face.verts[i] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
            }

        }
        glEnd();
    }
    public void VBOrender(Vector3f p){

        if(hasTexture) {
            glEnable(GL_TEXTURE_2D);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        }
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_BLEND);

        if(hasTexture) {
            glBindTexture(GL_TEXTURE_2D, VBOTexID);
            glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
            glTexCoordPointer(2, GL_FLOAT, 0, 0);
        }
        glBindBuffer(GL_ARRAY_BUFFER, VBONormalHandle);
        glNormalPointer(GL_FLOAT, 0,0);

        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);



        glPushMatrix();
        glTranslatef(origin.x, origin.y, origin.z);
        float amount = rx != 0 ? rx : ry != 0 ? ry : rz != 0 ? rz : 0;
        float tx = rx != 0 ? 1 : 0, ty = ry != 0 ? 1 : 0, tz = rz != 0 ? 1 : 0;
        glRotatef(amount, tx, ty, tz);
//        glTranslatef(-p.x, -p.y, -p.z);
        if (isWrieframe)
            glDrawArrays(GL_LINE_LOOP, 0, faces.size()*3);
        else
            glDrawArrays(GL_TRIANGLES, 0, faces.size()*3);

        glPopMatrix();

        if(hasTexture) {
            glDisable(GL_TEXTURE_2D);
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        }
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        glDisable(GL_COLOR_MATERIAL);
        glDisable(GL_BLEND);
    }



    public FloatBuffer makecolorbuff() {
        FloatBuffer colors = BufferUtils.createFloatBuffer(faces.size()*3*3);
        for (int i = 0; i < faces.size()*3*3; i++)
            colors.put(1f);
        colors.flip();
        return colors;
    }

    public FloatBuffer makevertbuff() {
        FloatBuffer vertex = BufferUtils.createFloatBuffer(faces.size()*3*3);
        for(Faceindex f: faces)
            for (int i = 0; i < f.verts.length; i++) {
                vertex.put(vertices.get(f.verts[i]-1).x);
                vertex.put(vertices.get(f.verts[i]-1).y);
                vertex.put(vertices.get(f.verts[i]-1).z);
            }
        vertex.flip();
        return vertex;
    }

    public FloatBuffer maketexbuff() {
        FloatBuffer texture = BufferUtils.createFloatBuffer(faces.size()*3*2);
        for(Faceindex f: faces)
            for (int i = 0; i < f.texts.length; i++) {
                texture.put((textures.get(f.texts[i]-1).x));
                texture.put((textures.get(f.texts[i]-1).y));
            }
        texture.flip();
        return texture;
    }

    public FloatBuffer makenormbuff() {
        FloatBuffer normal = BufferUtils.createFloatBuffer(faces.size()*3*3);
        for(Faceindex f: faces)
            for (int i = 0; i < f.norms.length; i++) {
                normal.put(normals.get(f.norms[i]-1).x);
                normal.put(normals.get(f.norms[i]-1).y);
                normal.put(normals.get(f.norms[i]-1).z);
            }
        normal.flip();
        return normal;
    }


    public void rotate(float amount, float tx, float ty, float tz) {
        rx += amount * tx;
        ry += amount * ty;
        rz += amount * tz;
    }

    public String toString(){
        String ans="";
        for (Vector3f v : vertices)
            ans += "v " + v.x + " " + v.y + " " + v.z+"\n";
        for (Vector3f v : normals)
            ans += "vn " + v.x + " " + v.y + " " + v.z+"\n";
        for(Faceindex f : faces)
            ans+=f;
        return ans;
    }
}