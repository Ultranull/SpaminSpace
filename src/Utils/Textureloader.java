package Utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by usr on 2/6/2017.
 *
 */
public class Textureloader {
    private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    public static int loadTexture(BufferedImage image){
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        return textureID;
    }
    public static int[] loadTexture(BufferedImage[] image){
        int[] ans=new int[image.length];
        int i=0;
        for(BufferedImage b:image)
        ans[i++]=loadTexture(b);
        return ans;
    }

    public static BufferedImage loadImage(String loc) {
        try {
            return ImageIO.read(new File(System.getProperty("user.dir")+"/"+loc));
        } catch (IOException e) {
            System.out.println(loc+": IMAGE INCORRECTLY LOADED!!!");
            System.exit(-1);
        }
        return null;
    }
    public static BufferedImage[] loadImage(String loc,int ol,int oh,int not,int il,int ih) {
        BufferedImage image=new BufferedImage(10,10,BufferedImage.TYPE_4BYTE_ABGR);
        try {
            image= ImageIO.read(new File(System.getProperty("user.dir")+"/"+loc));
        } catch (IOException e) {
            System.out.println(loc+": IMAGE INCORRECTLY LOADED!!!");
            System.exit(-1);
        }
        BufferedImage[] ans=new BufferedImage[not];
        int ind=0;
        for(int i=0;i<ih;i++)
            for(int c=0;c<il;c++){
                ans[ind++]=image.getSubimage(c*ol,i*oh,ol,oh);
            }
//        JOptionPane.showConfirmDialog(null,"hello","test",JOptionPane.OK_OPTION,JOptionPane.OK_CANCEL_OPTION,new ImageIcon(ans[0]));
        return ans;
    }

}
