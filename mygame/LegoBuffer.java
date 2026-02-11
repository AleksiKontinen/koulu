package mygame;

import java.util.ArrayList;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class LegoBuffer {
    private Box box;
    private Geometry geom;
    float surfaceHeight;
    ArrayList<Lego> legos = new ArrayList<Lego>(500);
    float x;
    float z;
    private float legoSpacingX = 2f; 
    private float legoSpacingZ = 2f;
    int rowSize;
    int columnSize;

    public LegoBuffer(AssetManager assetManager, Node rootNode, float xOffset, float zOffset, int rowSize, int columnSize,boolean spawnLego) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.x = xOffset;
        this.z = zOffset;

        float yExtent = 7f;
        box = new Box(16f, yExtent, 8f);
        geom = new Geometry("BufferBox", box);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);
        geom.setMaterial(mat);
        
        // Asetetaan puskurin paikka: keskipiste on floorHeight + yExtent
        // jolloin pohja koskettaa lattiaa.
        float bufferCenterY = Main.floorHeight + yExtent;
        geom.setLocalTranslation(x, bufferCenterY, z);
        rootNode.attachChild(geom);

        // Pinnan korkeus on keskipiste + puolet laatikon korkeudesta
        surfaceHeight = bufferCenterY + yExtent;

        String[] colors = {"pink", "yellow", "green", "blue"};
        
        // Luodaan täsmälleen haluttu määrä legoja
        int totalLegos = rowSize * columnSize;
        if (spawnLego) {
        	for (int i = 0; i < totalLegos; i++) {
                // Vaihdetaan väriä indeksin mukaan
            	
                String colorLego = colors[i % colors.length];
                
                Lego lego = new Lego(assetManager, colorLego);
                legos.add(lego);
                rootNode.attachChild(lego.node);
                lego.node.setLocalTranslation(getLegoCenterLocation(i));
            }
        		
        }
        
    }

    private float xCoord(int index) {
    	
    	int rowIndex = index % rowSize;
    	return (rowIndex - (rowSize - 1) / 2f) * legoSpacingX;
    	
        
    }

    private float zCoord(int index) {
        int columnIndex = index / rowSize; // Huom: rivin vaihto columnSize:n mukaan
        return (columnIndex - (columnSize - 1) / 2f) * legoSpacingZ;
    }

    public Vector3f getLegoCenterLocation(int index) {
        // Lisätään surfaceHeightiin pieni offset (0.2f), jotta lego on pinnalla
        return new Vector3f(x + xCoord(index), surfaceHeight + 0.4f, z + zCoord(index));
    }
    
    // APP tarvitsee legonyläpinnan koordinaatin, johon robotti tuo työkalunsa alapinnassa
    // olevan ’tooltip’ pisteensä
    /*private Vector3f getLegoTopLocation(int index) {
    	return new Vector3f(x+xCoord(index), surfaceHeight + 0.4f, z+zCoord(index));
    }*/
    // palauttaa Lego olion joka on halutun värinen tai null jos tällaista legoa ei ole
    // päivitä legoColor Lego luokkaan ja konstruktoriin
    // päivitä location Lego luokkaan
    public Lego giveLego(String color) {
    	Lego lego = null;
    	// luupataan läpi kaikki bufferin legoslotit
    	for(int i=0; i<(rowSize*columnSize); i++) {
    		lego = legos.get(i);
    				if(lego != null) { 	
    					if(lego.legoColor.equals(color)) {
    						lego.location = lego.node.getWorldTranslation().add(0,0.2f,0);
    						legos.set(i, null);
    						return lego;
    					}
    				}
    	}
    	return null;
 }
    public boolean LegoAdd(Lego lego) {
    	return legos.add(lego);
    }
    public boolean legoKill(Lego lego) {
    	int index = legos.indexOf(lego);
    	legos.set(index, null);
    	
    	return legos.get(index) == null;
    }
    
}