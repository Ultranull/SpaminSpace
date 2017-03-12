package ModelUtil;

/**
 * Created by usr on 3/9/2017.
 *
 */
public class Faceindex {
    public int[] verts;
    public int[] norms;
    public int[] texts;
    public Faceindex(int[] vi,int[] ni,int[] ti){
        verts=vi;norms=ni;texts=ti;
    }

    public String toString(){
        String ans="f ";
        for(int i=0;i<verts.length;i++)
            ans+=verts[i]+"/"+texts[i]+"/"+norms[i]+" ";
        return ans+"\n";
    }
}
