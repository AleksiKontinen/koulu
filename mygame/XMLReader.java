package mygame;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;
public class XMLReader {

float X;
float Z;
public ArrayList<Float> xVal = new ArrayList<>(10);
public ArrayList<Float> zVal = new ArrayList<>(10);
	public XMLReader(String target) {
		// TODO Auto-generated method stub
		try {
	        File inputFile = new File("xmlManyfac.aml"); //lisätään tiedosto
	        //File inputFile = new File("proteusexample.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("InternalElement"); //haetaan kaikki InternalElement
	        System.out.println(nList.getLength());
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	        	Node nNode = nList.item(temp);
	        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		Element eElement = (Element) nNode;
	        		
	        		if(eElement.getAttribute("RefBaseSystemUnitPath").equals("SystemUnitClassLib/Cell")) {
	        		    // Haetaan InternalElementit VAIN tämän Cellin sisältä (eElement)
	        		    NodeList children = eElement.getElementsByTagName("InternalElement");
	        		    
	        		    for(int i = 0; i < children.getLength(); i++) {
	        		        Element subElement = (Element) children.item(i);
	        		        String subName = subElement.getAttribute("Name");
	        		        
	        		        // Jos alielementin nimi on se mitä etsitään (target)
	        		        if(subName.equals(target)) {
	        		            String xStr = "0";
	        		            String yStr = "0";

	        		            // Haetaan Attribute-elementit subElementin sisältä
	        		            NodeList attrList = subElement.getElementsByTagName("Attribute");
	        		            for (int k = 0; k < attrList.getLength(); k++) {
	        		                Element attr = (Element) attrList.item(k);
	        		                String attrName = attr.getAttribute("Name");
	        		                
	        		                // Haetaan Value-elementin sisältö
	        		                if (attrName.equals("X")) {
	        		                    xStr = attr.getElementsByTagName("Value").item(0).getTextContent();
	        		                } else if (attrName.equals("Z")) {
	        		                    yStr = attr.getElementsByTagName("Value").item(0).getTextContent();
	        		                }
	        		            }

	        		            System.out.println(subName + ": X: " + xStr + " Z: " + yStr);
	        		            
	        		            // Käytetään Doublea siltä varalta että luku on esim "5.0"
	        		            X = (float) Float.parseFloat(xStr);
	        		            Z = (float) Float.parseFloat(yStr);
	        		            xVal.add(X);
	        		            zVal.add(Z);
	        		        }
	        		    }
	        		}
	        	
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
