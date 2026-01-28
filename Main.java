package mygame;
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
 
 public static float floorHeight = -15;
 AssemblyStation assemblyStation;
 //Trajectory trajectory;
 
 @Override
 public void simpleInitApp() {
	 flyCam.setMoveSpeed(10);
	 PointLight lamp_light = new PointLight();
	 lamp_light.setColor(ColorRGBA.White);
	 lamp_light.setRadius(400f);
	 lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
	 rootNode.addLight(lamp_light);
	 
	 assemblyStation = new AssemblyStation(assetManager,rootNode,5, -11);
	 rootNode.attachChild(assemblyStation.node);
	 //RobotArm robotArm = new RobotArm(assetManager, rootNode);
	 //rootNode.attachChild(robotArm.node);
	 assemblyStation.initTestMove(new Vector3f(0,0,-5));
	 //assemblyStation.move();
 }
 @Override
 public void simpleUpdate(float tpf) {
 //TODO: add update code
	/* Lego legoGreen = new Lego(assetManager,"green");
	 Lego legoRed = new Lego(assetManager,"red");
	 Lego legoPink = new Lego(assetManager,"pink");
	 Lego legoYellow = new Lego(assetManager,"yellow");
	 Lego legoBlue = new Lego(assetManager,"blue");
	 rootNode.attachChild(legoGreen.node);
	 rootNode.attachChild(legoRed.node);
	 rootNode.attachChild(legoPink.node);
	 rootNode.attachChild(legoYellow.node);
	 rootNode.attachChild(legoBlue.node);
	 legoGreen.node.setLocalTranslation(8f, 0, 0); 
	 legoRed.node.setLocalTranslation(4f, 0, 0); 
	 legoYellow.node.setLocalTranslation(-4f, 0, 0); 
	 legoBlue.node.setLocalTranslation(-8f, 0, 0); */
	 assemblyStation.move();
	 
	 
 }
 @Override
 public void simpleRender(RenderManager rm) {
 //TODO: add render code
 }
} 