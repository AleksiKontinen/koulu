package mygame;
import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.light.PointLight;
/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */

public class Main extends SimpleApplication {
 public static void main(String[] args) {
 Main app = new Main();
 app.start();
 }
 
 public static boolean stacktype = true;
 public static boolean dualArm = true;
 public static float floorHeight = -15;
 public static boolean firstReady = false;
 AssemblyStation assemblyStation;
 LegoBuffer legoBuffer;
 LegoBuffer legoBuffer2;
 Trajectory trajectory;
 boolean freeze = false; // debug tarkoituksiin – laita true siinä kohdassa koodia
 // mihin haluat robotin pysähtyvän
 boolean moving = false; // true kun robotti liikkuu
 boolean goingToLego = false; // true kun mennään hakemaan legoa bufferista
 
 Lego lego;
 int slotIndex = 0; // kokoonpanoaseman slot
 final int numColors = 4; // final on sama kuin C-kielen cons
 int colorIndex = 0; // “colors” (kts alla) listan indeksi
//listan perusteella tiedetään missä järjestyksessä lajitellaan värin mukaan
ArrayList<String> colors = new ArrayList(numColors);

 @Override
 public void simpleInitApp() {
	 flyCam.setMoveSpeed(10);
	 PointLight lamp_light = new PointLight();
	 lamp_light.setColor(ColorRGBA.White);
	 lamp_light.setRadius(400f);
	 lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
	 rootNode.addLight(lamp_light);
	 legoBuffer = new LegoBuffer(assetManager, rootNode, 5f,-29f,10,6,true);
	 if(dualArm) {
		 legoBuffer2 = new LegoBuffer(assetManager,rootNode,5f,15,10,6,false);
	 }
	 
	 
	 assemblyStation = new AssemblyStation(assetManager,rootNode,5, -11);
	 rootNode.attachChild(assemblyStation.node);
	 //RobotArm robotArm = new RobotArm(assetManager, rootNode);
	 //rootNode.attachChild(robotArm.node);
	 assemblyStation.initTestMove(new Vector3f(0,0,-5));
	 colors.add("yellow");
	 colors.add("blue");
	 colors.add("pink");
	 colors.add("green");
	 
	 
	 
	 
 }
 @Override
 public void simpleUpdate(float tpf) {
 //TODO: add update code
	
	 assemblyStation.move();
	 if(!freeze && moving) {
		 moving = assemblyStation.move();
	 }
	 if(!moving && !freeze) {
	// moving=false tarkoittaa että saavuttiin reitin päähän, joten on 2 tapausta:
	// otetaan lego mukaan tai jätetään se
	 	if(goingToLego) { // otetaan lego mukaan
	 		// nyt ollaan bufferilla sen legon kohdalla mikä otetaan mukaan
	 		// v:hen laitetaan kokoonpanoaseman slot numero ”slotIndex” koordinaatit
	 		Vector3f v = assemblyStation.slotPosition(slotIndex);
	 		slotIndex++;
	 		// suoritetaan APP kohteeseen v
	 		assemblyStation.initMoveToStation(lego,v);
	 		goingToLego = false;
	 		moving = true;
		 } else { // jätetään lego tähän
			 if(lego != null) { // käynnistyksen yhteydessä tätä koodia ei suoriteta
				 // lego on nyt toimitettu oikeaan paikkaan kokoonpanoasemalle
				 // otetaan paikka talteen ennen kuin irrotetaan noodi
				 Vector3f loc = lego.node.getWorldTranslation();
				 
				 // irrota legon node tooltipin nodesta
				 // (tämä on pitkä rimpsu jossa käytetään monen olion nimeä
				 assemblyStation.assemblyArm.tooltipNode.detachChild(lego.node);
				 lego.node.setLocalTranslation(loc);
				 // legon node ei ole nyt kiinni missään nodessa, joten se ei tule
				 // näkyviin ennen kuin korjaat asian
				 assemblyStation.node.attachChild(lego.node);
			 }
			 // haetaan bufferista seuraava lego, jonka väri on: colors.get(colorIndex)
			 // eli päivitä muuttujan ’lego’ arvo
			 lego = legoBuffer.giveLego(colors.get(colorIndex));
			 moving = true;
			 if (lego == null) {
				 // bufferissa ei ole enempää tämänvärisiä legoja
				 colorIndex++;
				 	if(colorIndex >= numColors) {
				 		// kaikki legot on siirretty
				 		freeze = true; // tämän jälkeen mitään ei tapahdu
				 	} else {
				 		// haetaan bufferista seuraava lego, jonka väri on:
				 		// colors.get(colorIndex)
				 		lego = legoBuffer.giveLego(colors.get(colorIndex));
				 	}
			 }
			 if(!freeze) {
				 assemblyStation.initMoveToLego(lego);
			 }
			 goingToLego = true;
		 }
	}

	 
 }
 @Override
 public void simpleRender(RenderManager rm) {
 //TODO: add render code
 }
} 