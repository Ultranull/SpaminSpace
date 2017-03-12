
package ModelUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import org.lwjgl.util.vector.Vector3f;
import Utils.Material;

public class OBJLoader {
    public static Model loadModel(Model m,File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fac=line.split(" ");
                float x, y, z;
                switch (fac[0]) {
                    case "mtllib":
                        m.VBOTexID=loadmtl(new File(f.getParentFile().getAbsolutePath()+"\\"+fac[1]));
                        m.hasTexture=m.VBOTexID!=-0xdead;

                        break;
                    case "v":
                        x = Float.valueOf(fac[1]);
                        y = Float.valueOf(fac[2]);
                        z = Float.valueOf(fac[3]);
                        m.vertices.add(new Vector3f(x, y, z));
                        break;

                    case "vn":
                        x = Float.valueOf(fac[1]);
                        y = Float.valueOf(fac[2]);
                        z = Float.valueOf(fac[3]);
                        m.normals.add(new Vector3f(x, y, z));
                        break;

                    case "vt":
                        x = Float.valueOf(fac[1]);
                        y = Float.valueOf(fac[2]);
                        m.textures.add(new Vector3f(x, y,0));
                        break;

                    case "f":
                        String[] ff= Arrays.copyOfRange(fac,1,fac.length);
                        int length = ff.length;
                        int[] verts = new int[length];
                        int[] norms = new int[length];
                        int[] texts = new int[length];
                        for (int i = 0; i < length; i++) {
                            String v=ff[i].split("/")[0];
                            String t=ff[i].split("/")[1];
                            String n=ff[i].split("/")[2];
                            verts[i] = Integer.parseInt(v);
                            texts[i] = Integer.parseInt(t.isEmpty()?"0":t);
                            norms[i] = Integer.parseInt(n);
                        }
                        m.faces.add(new Faceindex(verts, norms,texts));
                        break;
                }
            }
            reader.close();
        }catch (Exception ignored){
            ignored.printStackTrace();
            System.exit(-1);
        }
        return m;
    }
    public static int loadmtl(File f) {
        int id=-0xdead;
        String name="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fac = line.split(" ");
                switch (fac[0]) {
                    case "newmtl":
                        name=fac[1];
                        break;
                    case "map_Kd":
                        id= Material.addMat(name,"images\\"+fac[1]);
                        break;

                }
            }
            reader.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return id;
    }
}