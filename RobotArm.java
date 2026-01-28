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
	Geometry mast;
	Geometry zArm;
	Geometry xarm;
	Geometry yArm;
	Geometry tooltip;
	
	Box mastShape;
	Box zArmShape;
	Box xArmShape;
	Box yArmShape;
	Box tooltipShape;
	private Vector3f targetLocation; // välietappi
	float step = 0.1f; // etäisyys akselia kohden mikä liikutaan yhden syklin aikana
	
	private Vector3f targetLocation; // välietappi
	float step = 0.1f; // etäisyys akselia kohden mikä liikutaan yhden syklin aikana
	
	public RobotArm(AssetManager assetManager, Node node) {
		
		 Material mat = new Material(assetManager,
				 "Common/MatDefs/Light/Lighting.j3md");
				  mat.setBoolean("UseMaterialColors",true);
		ColorRGBA c = ColorRGBA.Orange;
		mat.setColor("Diffuse", c);
					
		Node tooltipNode = new Node();
		
		Box mastShape = new Box(0.2f,6f,0.2f);
		Box zArmShape = new Box(0.2f,0.2f,20f);
		Box xArmShape = new Box(18f,0.2f,0.2f);
		Box yArmShape = new Box(0.2f,6f,0.2f);
		Box tooltipShape = new Box(0.14f,0.4f,0.14f);
		
		
		Geometry mast = new Geometry("Box",mastShape);
		Geometry zArm = new Geometry("Box",zArmShape);
		Geometry xArm = new Geometry("Box",xArmShape);
		Geometry yArm = new Geometry("Box",yArmShape);
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
	// target on välietappi johon kuuluu ajaa
	public void initMove(Vector3f target) {
	targetLocation = target;
	}
	// palauttaa tooltipin alapinnan keskipisteen koordinaatit maailma-koordinaateissa
	// käytä Geometry luokan getWorldTranslation()
	public Vector3f getToolTipLocation() {
		
		return tooltip.getWorldTranslation();
	}
	// moves towards target location and returns false when it reached the location
	public boolean move() {
		Vector3f location = getToolTipLocation();
		// lasketaan etäisyys määränpäähän maailma-koordinaateissa
		float xDistance = targetLocation.getX() - location.getX();
		float zDistance = ???
		float yDistance = ???
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
			 ???
			 }

			 if (yDistance > step ) {
			 ???
			 }
			 // siirretään mastossa kiinni oleva zArm, joka liikkuu siis z-suuntaan
			 // 0.5f siitä syystä että robotti ulottuu paremmin (xArm liikuu zArmia pitkin)
			 Vector3f v = new Vector3f(0, 0, 0.5f*z);
			 zArmGeom.setLocalTranslation(zArmGeom.getLocalTranslation().add(v));
			 // xArm on zArmin varassa minkä lisäksi se liikkuu sitä pitkin, joten nyt
			 // käytetä 0.5f kerrointa kuten äsken
			 Vector3f v1 = new Vector3f(0, 0, z);
			 xArmGeom.setLocalTranslation(xArmGeom.getLocalTranslation().add(v1));
			 // yArm liikkuu xArm Pitkin x suuntaan ja tekee myös y-suuntaisen liikkeen,
			 // minkä lisäksi zArmin liike siirtää myös yArmia
			 Vector3f v2 = new Vector3f(x, y, z);
			 yArmGeom.setLocalTranslation(yArmGeom.getLocalTranslation().add(v2));
			 // nodetoolTip paikaksi on määritelty yArm alapinta, mutta nodetoolTipin parent
			 // noodi ei liiku, joten nodetoolTip pitää siirtää kuten yArm
			 // samalla liikkuu nodetoolTippiin liitetty tooltipin geometria
			 nodetoolTip.setLocalTranslation(nodetoolTip.getLocalTranslation().add(???));
			 if((yReady && xReady) && zReady) {
			 return false; //i.e. not moving anymore
			 } else {
			 return true;
			 }
			}
}
