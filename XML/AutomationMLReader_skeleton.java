package XML;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class AutomationMLReader_skeleton {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
	        File inputFile = new File("testing_station.aml"); //lisätään tiedosto
	        //File inputFile = new File("proteusexample.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("SystemUnitClass"); //haetaan kaikki SystemUnitClass
	        System.out.println(nList.getLength());
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	        	Node nNode = nList.item(temp);
	        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		Element eElement = (Element) nNode;
	        		String componentName = eElement.getAttribute("Name");
	        		
	        		String targetPath = "SystemUnitClassLib/" + componentName;
	        		System.out.println("\nInternal elements with system unit class " + targetPath);
	        		NodeList nList2 = doc.getElementsByTagName("InternalElement"); //isompi haku
	        		int length2 = nList2.getLength();
	        		for (int temp2 = 0; temp2 < length2; temp2++) {
	        			Node nNode2 = nList2.item(temp2);
	        			if (nNode2.getNodeType() == Node.ELEMENT_NODE ) {
	        				Element eElement2 = (Element)nNode2;
	        				String componentName2 = eElement2.getAttribute("Name");
	        				String attribute = eElement2.getAttribute("RefBaseSystemUnitPath");
	        				if(attribute.equals(targetPath)) {
	        					System.out.println(componentName2);
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