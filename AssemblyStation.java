package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;


public class AssemblyStation {
	Node node = new Node();
	Geometry geom;
	Box box;
	int yExtent = 6;
	public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset) {
		box = new Box (20, yExtent, 10);
		geom = new Geometry("Box", box);
		node.attachChild(geom);
		Material mat = new Material(assetManager,
		"Common/MatDefs/Light/Lighting.j3md");
		mat.setBoolean("UseMaterialColors",true);
		ColorRGBA c = ColorRGBA.LightGray;
		mat.setColor("Diffuse", c);
		geom.setMaterial(mat);
		geom.setLocalTranslation(xOffset, Main.floorHeight + yExtent, zOffset);
		
		
		
	}
}
