package mygame;
import java.util.ArrayList;

import com.jme3.math.Vector3f;
public class Trajectory {
	 ArrayList<Vector3f> points;
	 
	 int index = 0; // ‘points’ listan indeksi
	 int size = 0; // kuinka monta waypointtia ‘points’ listassa on

	 // alustaa yllämainitut points ja index muuttujat
	 public Trajectory() {
		 points = new ArrayList<Vector3f>(10);
		 index = 0;
		  
	 }

	 // lisää pisteen listan hännille
	 public void addPoint(Vector3f v) {
		 points.add(v);
	 }
	 // nollaa indeksin ja asettaa size muuttujalle oikean arvon
	 public void initTrajectory() {
		 index = 0;
		 size = points.size();
		 
	 }

	 // palauttaa indexin kohdalla olevan pisteen tai null jos ei enää pisteitä
	 public Vector3f nextPoint() {
		 if (index >= points.size()) {
			 return null;
		 }
		 index++;
		 return points.get(index-1);
		 
	 }

}
