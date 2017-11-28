package Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static Utils.Textureloader.*;

/**
 * Created by usr on 2/11/2017.
 *
 */
public class Material {
    public static HashMap<String,Integer> mats;
    public static void init(String[] textures){
        mats=new HashMap<>();
        for(int i=0;i<textures.length;i+=2) {
            System.out.println("--loading: "+textures[i+1]);
            mats.put(textures[i], loadTexture(loadImage(textures[i + 1])));
        }
    }
    public static int addMat(String name,String tex){
        int id;
        id=loadTexture(loadImage(tex));
        mats.put(name,id);
        return id;
    }
    public static int[] addMat(String name,String loc,int ol,int oh,int not,int il,int ih){
        int[] b=loadTexture(loadImage(loc,ol,oh,not,il,ih));
        int ii=0;
        for (Integer i:b)
            mats.put(name+ii++,i);
        return b;
    }
    public static int get(String n){
        return mats.get(n);
    }
    public static int[] get(String... n){
        int[] i=new int[n.length];
        for(int f=0;f<n.length;f++)
            i[f]=mats.get(n[f]);
        return i;
    }

    public static void playsound(String file,int loops){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sounds\\"+file));
            clip.open(ais);
            clip.loop(loops);
        }catch (Exception e){e.printStackTrace();}

    }
}
