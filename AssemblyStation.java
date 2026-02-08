package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;


public class AssemblyStation {
	
	Node node = new Node();
	Geometry geom;
	Box box;
	Trajectory trajectory;
	RobotArm assemblyArm;
	RobotArm assemblyArm2;
	
	float x;
    float z;
    
	int yExtent = 6;
	float maxHeight = 4; // max korkeus reitin välietapeille
	boolean moving = false; // true jos matkalla seuraavaan välietappiin
	float stackh = 0;
	
	
	
	public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset) {
		this.x = xOffset;
	    this.z = zOffset;
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
		
		
		assemblyArm = new RobotArm(assetManager,node,false);
		node.attachChild(assemblyArm.node);
		if(Main.dualArm) {
			assemblyArm2 = new RobotArm(assetManager,node,true);
			node.attachChild(assemblyArm2.node);
		}

		
	}
	// tehdään APP eli reitinsuunnittelu destination koordinaatteihin
	public void initTestMove(Vector3f destination) {
		trajectory = new Trajectory();
		// eka välietappi suoraan ylös max korkeuteen
		Vector3f v1;
		if(Main.firstReady) {
			v1 = assemblyArm2.getToolTipLocation().clone();
			System.out.print(v1);
		}else {
			v1 = assemblyArm.getToolTipLocation().clone();
		}
		
		v1.setY(maxHeight);
		trajectory.addPoint(v1);
		
		// toka välietappi max korkeuteen destination ylle
		Vector3f v2 = destination.clone();
		v2.setY(maxHeight);
		trajectory.addPoint(v2);
		
		trajectory.addPoint(destination);
		trajectory.initTrajectory();
	}
	
	// käskyttää robottia ajamaan reitin, joka on määritelty trajectory-attribuuttiin
	// palauttaa false jos saavutettiin trajectory viimeinen (väli)etappi, eli
    // initTestMove() saama destination. Muuten palauttaa true.
	// tätä tulee kutsua syklisesti kunnes se palauttaa false
	public boolean move() { 
		if (moving) {
			if(Main.firstReady) {
				System.out.print("now moving");
				moving = assemblyArm2.move();
			}else {
				moving = assemblyArm.move();
			}
		
		return true;
		} else {
			// tänne tullaan jos edellinen välietappi saavutettiin
			Vector3f nextPoint = trajectory.nextPoint();
			if (nextPoint == null) {
				return false;
			} else {
				// debug printit tulee konsoliin näkyviin kun suljet ohjelman
				System.out.println(nextPoint.toString());
				// annetaan robotille seuraava välietappi ja alustetaan moving seuraavaa
				// move() kutsua silmälläpitäen
				if(Main.firstReady) {
					
					assemblyArm2.initMove(nextPoint);
				}else {
					assemblyArm.initMove(nextPoint);
				}
				
				moving = true;
				return true;
			}
					
					
		}
	}
	
	float legoSpacingX = 2; // legojen slottipaikkojen etäisyys x-suuntaan
	float legoSpacingZ = 2; // legojen slottipaikkojen etäisyys z-suuntaan
	// kokoonpanoasemalla on slotteja, joiden indeksi on kokonaisluku
	// tämä palauttaa slotin 3D koordinaatit
	public Vector3f slotPosition(int slot) { // MUOKKAA kolmas stäkkäystapa eli station 2
	 // vain osa asemasta on varattu tähän tarkoitukseen. Sen koko on 16x12
		int rowSize = (int)((16)/legoSpacingX);
		//int columnSize = (int)((12)/legoSpacingZ);
		int rowIndex = slot % rowSize;
		float xOffset = (rowIndex-1) * legoSpacingX;
		int columnIndex = slot / rowSize;
		float zOffset = (columnIndex + 2) * legoSpacingZ;
		float yOffset = 0.8f; // legonyExtent
		// ’x’ ja ’z’ on float muuttujia, joihin on tallennettu konstruktorin xOffset/zOffset
		// laske ’surfaceHeight’ konstruktorissa
		float surfaceHeight = Main.floorHeight + 2*yExtent;
		
		int stackYindex = slot % 15;
		int stackXindex = (int)(slot / 15);
		float legoh = 0.4f;
		float stackXoffset = legoSpacingX * stackXindex;
		float stackYoffset = yOffset + (stackYindex * legoh);
		if (Main.stacktype) {
			if (Main.firstReady) {
				return new Vector3f(x + xOffset, surfaceHeight+yOffset, z + zOffset - 22);
			}else{
				return new Vector3f(x + stackXoffset, surfaceHeight + stackYoffset, z - 6);
			}
			
		}else {
			return new Vector3f(x + xOffset, surfaceHeight+yOffset, z + zOffset - 12);
		}
		
	}
	// APP kohteeseen lego.location
	// sama idea kuin edellisen harjoituksen initTestMove()
	public void initMoveToLego(Lego lego) {
		initTestMove(lego.location.clone().addLocal(0,0.2f,0));
		this.moving = true;
		
	}
	// APP kohteeseen destination
	public void initMoveToStation(Lego lego, Vector3f destination) {
	Vector3f loc = lego.node.getWorldTranslation().clone();
	Vector3f localPos;
	if(Main.firstReady) {
		assemblyArm2.tooltipNode.attachChild(lego.node);
		localPos = assemblyArm2.tooltipNode.worldToLocal(loc, null);
	}else {
		assemblyArm.tooltipNode.attachChild(lego.node); // muuten lego ei lähde mukaan
		localPos = assemblyArm.tooltipNode.worldToLocal(loc, null);
	}
	
	
	lego.node.setLocalTranslation(localPos.add(0,-0.2f,0));
	 // sitten tehdään APP kohteeseen ”destination”
	 initTestMove(destination);
	 this.moving = true;
	}

	
}
