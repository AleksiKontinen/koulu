package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class RobotArm {
	Node node = new Node();
	Node tooltipNode = new Node();
	Geometry mast;
	Geometry zArm;
	Geometry xArm;
	Geometry yArm;
	Geometry tooltip;
	
	Box mastShape;
	Box zArmShape;
	Box xArmShape;
	Box yArmShape;
	Box tooltipShape;
	private Vector3f targetLocation; // välietappi
	float step = 1f; // etäisyys akselia kohden mikä liikutaan yhden syklin aikana
	ColorRGBA c;

	
	public RobotArm(AssetManager assetManager,Node node,boolean mirror) {
		
		 Material mat = new Material(assetManager,
				 "Common/MatDefs/Light/Lighting.j3md");
				  mat.setBoolean("UseMaterialColors",true);
		if (mirror) {
			 c= ColorRGBA.Pink;
		}else {
			c = ColorRGBA.Orange;
		}
		
		
		mat.setColor("Diffuse", c);
					
		mastShape = new Box(0.2f,6f,0.2f);
		zArmShape = new Box(0.2f,0.2f,20f);
		xArmShape = new Box(18f,0.2f,0.2f);
		yArmShape = new Box(0.2f,6f,0.2f);
		tooltipShape = new Box(0.14f,0.4f,0.14f);
		
		
		mast = new Geometry("Box",mastShape);
		zArm = new Geometry("Box",zArmShape);
		xArm = new Geometry("Box",xArmShape);
		yArm = new Geometry("Box",yArmShape);
		tooltip = new Geometry("Box",tooltipShape);
		
		mast.setMaterial(mat);
		zArm.setMaterial(mat);
		xArm.setMaterial(mat);
		yArm.setMaterial(mat);
		tooltip.setMaterial(mat);
		
		if (mirror) {
			mast.setLocalTranslation(8f,0f,-10f);
			zArm.setLocalTranslation(8f,6f,-8f);
			xArm.setLocalTranslation(-6f,6f,0f);
			yArm.setLocalTranslation(7f,6f,0f);
			tooltip.setLocalTranslation(7f,-0.4f, 0f);
		}else {
			mast.setLocalTranslation(-8f,0f,-10f);
			zArm.setLocalTranslation(-8f,6f,-8f);
			xArm.setLocalTranslation(6f,6f,0f);
			yArm.setLocalTranslation(-7f,6f,0f);
			tooltip.setLocalTranslation(-7f,-0.4f, 0f);
		}

		
		node.attachChild(mast);
		node.attachChild(zArm);
		node.attachChild(xArm);
		node.attachChild(yArm);
		node.attachChild(tooltipNode);
		tooltipNode.attachChild(tooltip);
	}
	
	// target on välietappi johon kuuluu ajaa
	public void initMove(Vector3f target) {
	targetLocation = target;
	}
	// palauttaa tooltipin alapinnan keskipisteen koordinaatit maailma-koordinaateissa
	// käytä Geometry luokan getWorldTranslation()
	public Vector3f getToolTipLocation() {
		return tooltip.getWorldTranslation().add(0f,-0.2f,0f);
	}
	
	// moves towards target location and returns false when it reached the location
	public boolean move() { // HERE
		if(targetLocation == null) return false;
		Vector3f location = getToolTipLocation();
		// lasketaan etäisyys määränpäähän maailma-koordinaateissa
		float xDistance = targetLocation.getX() - location.getX();
		float zDistance = targetLocation.getZ() - location.getZ();
		float yDistance = targetLocation.getY() - location.getY();
		// booleanit ilmaisee että onko kyseisen akselin suuntainen liike valmis
		boolean xReady = false;
		boolean yReady = false;
		boolean zReady = false;
		float x; // x-akselin suuntainen liike tämän syklin aikana
		float y; // y-akselin suuntainen liike tämän syklin aikana
		float z; // z-akselin suuntainen liike tämän syklin aikana
			 // siirrytään stepin verran oikeaan suuntaan jos matkaa on yli stepin verran
			 // muuten siirrytään targetLocationin x koordinaattiit
		if (xDistance > step ) {
			x = step;
		} else if ((-1 * xDistance) > step) {
			x = -1 * step;
		} else {
			xReady = true;
			x = xDistance;
		}
		
		if (zDistance > step ) {
			z = step;
		}else if ((-1 * zDistance) > step) {
				z = -1 * step;
		} else {
			zReady = true;
			z = zDistance;
		}

		if (yDistance > step ) {
			 y = step;
		} else if ((-1 * yDistance) > step) {
			y = -1 * step;
		} else {
			yReady = true;
			y = yDistance;
		}
			 // siirretään mastossa kiinni oleva zArm, joka liikkuu siis z-suuntaan
			 // 0.5f siitä syystä että robotti ulottuu paremmin (xArm liikuu zArmia pitkin)
			 Vector3f v = new Vector3f(0, 0, 0.5f*z);
			 zArm.setLocalTranslation(zArm.getLocalTranslation().add(v));
			 // xArm on zArmin varassa minkä lisäksi se liikkuu sitä pitkin, joten nyt
			 // käytetä 0.5f kerrointa kuten äsken
			 Vector3f v1 = new Vector3f(0, 0, z);
			 xArm.setLocalTranslation(xArm.getLocalTranslation().add(v1));
			 // yArm liikkuu xArm Pitkin x suuntaan ja tekee myös y-suuntaisen liikkeen,
			 // minkä lisäksi zArmin liike siirtää myös yArmia
			 Vector3f v2 = new Vector3f(x, y, z);
			 yArm.setLocalTranslation(yArm.getLocalTranslation().add(v2));
			 // nodetoolTip paikaksi on määritelty yArm alapinta, mutta nodetoolTipin parent
			 // noodi ei liiku, joten nodetoolTip pitää siirtää kuten yArm
			 // samalla liikkuu nodetoolTippiin liitetty tooltipin geometria
			 tooltipNode.setLocalTranslation(tooltipNode.getLocalTranslation().add(v2));
			 if((yReady && xReady) && zReady) {
			 return false; //i.e. not moving anymore
			 } else {
			 return true;
			 }
		}
}
