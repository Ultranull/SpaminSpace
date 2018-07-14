import Utils.Material;
import Utils.Path;
import Utils.Point;

import java.util.LinkedList;

/**
 * Created by usr on 2/27/2017.
 *
 */
public class Spawner {

    int spawnrate=30;
    LinkedList<Alien> fleet;
    LinkedList<Alien> spawnque;
    Path path;
    public Spawner(LinkedList<Alien> f,Path p){
        spawnque=new LinkedList<>();
        fleet=f;
        path=p;
    }
    public void update(int ticks){
        if(ticks%spawnrate==0&&!spawnque.isEmpty())
            fleet.add(spawnque.removeFirst());
    }
    public void burst(int id,int length){
        for (int i = 0; i < length; i++) {
            Alien s=Spawner.farm(id,path.get(),true);
            s.setPath(path.clone());
            spawnque.add(s);
        }
    }
    public static Alien farm(int id,Point p,boolean dwd){
        int[] t;
        switch(id){
            case 0:
                t=new int[]{Material.get("dopen"), Material.get("dclose")};
                break;
            case 1:
                t=new int[]{Material.get("jopen"), Material.get("jclosed")};
                break;
            case 2:
                t=new int[]{Material.get("mopen"), Material.get("mclosed")};
                break;
            case 3:
                t=new int[]{Material.get("topen"), Material.get("tclosed")};
                break;
            default:
                t=new int[]{Material.get("front")};
        }
        Alien a=new Alien(p,t);
        a.dwd=dwd;
        return a;
    }
}
