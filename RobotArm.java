package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class RobotArm {

	public RobotArm(AssetManager assetManager, Node node) {
		 Material mat = new Material(assetManager,
				 "Common/MatDefs/Light/Lighting.j3md");
				  mat.setBoolean("UseMaterialColors",true);
		ColorRGBA c = ColorRGBA.Orange;
		mat.setColor("Diffuse", c);
					
		Node tooltipNode = new Node();
		
		Box mastShape = new Box(0.2f,0.6f,0.2f);
		Box zArmShape = new Box(0.2f,0.6f,20f);
		Box xArmShape = new Box(18f,0.2f,0.2f);
		Box yArmShape = new Box(0.2f,6f,0.2f);
		Box tooltipShape = new Box(0.14f,0.4f,0.14f);
		
		
		Geometry mast = new Geometry("Box",mastShape);
		Geometry zArm = new Geometry("Box",zArmShape);
		Geometry xArm = new Geometry("Box",xArmShape);
		Geometry yArm = new Geometry("Box",xArmShape);
		Geometry tooltip = new Geometry("Box",tooltipShape);
		
		mast.setMaterial(mat);
		zArm.setMaterial(mat);
		xArm.setMaterial(mat);
		yArm.setMaterial(mat);
		tooltip.setMaterial(mat);
		
		mast.setLocalTranslation(-8f,0f,-10f);
		zArm.setLocalTranslation(-8f,6f,-8f);
		xArm.setLocalTranslation(6f,6f,0f);
		yArm.setLocalTranslation(-7f,6f,0f);
		tooltip.setLocalTranslation(-7f,-0.4f, 0f);
		
		node.attachChild(mast);
		node.attachChild(zArm);
		node.attachChild(xArm);
		node.attachChild(yArm);
		node.attachChild(tooltipNode);
		tooltipNode.attachChild(tooltip);
		
		
		
	}
}
