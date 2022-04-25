package com.github.npaetzel.MineSearcher;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Highscore {
	private static File xmlFile = new File("bin/save.xml");
	private static Highscore[] highscores = new Highscore[3];
	String name;
	int time;
	
	public static Highscore getHighscore(Difficulty difficulty) {
		return highscores[difficulty.ordinal()];
	}
    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    public int getTime() {
		return time;
	}
    public void setTime(int time) {
		this.time = time;
	}
    
	public Highscore(String difficulty, String name, int time) {
		setName(name);
		setTime(time);
		highscores[Difficulty.valueOf(difficulty.toUpperCase()).ordinal()] = this;
	}
	
	public static Document getXmlDocument() {
		try {
			File xmlFile = new File("bin/save.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			return dBuilder.parse(xmlFile);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void loadHighscores() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("highscore");
			for(int i=0; i<nList.getLength(); i++) {
				Element element = (Element) nList.item(i);
				new Highscore(element.getAttribute("difficulty"),
						element.getElementsByTagName("name").item(0).getTextContent(),
						Integer.parseInt(element.getElementsByTagName("time").item(0).getTextContent()));
			}			

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean checkHighscore(int newTime, Difficulty difficulty) {
		if(newTime < highscores[difficulty.ordinal()].getTime()) {
			return true;
		}
		return false;
	}
	
	public static void updateHighscore(int newTime, Difficulty difficulty, String name) {
		highscores[difficulty.ordinal()].setTime(newTime);
		highscores[difficulty.ordinal()].setName(name);
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("highscore");
			for(int i=0; i<nList.getLength(); i++) {
				Element element = (Element) nList.item(i);
				if(element.getAttribute("difficulty").equals(difficulty.toString().toLowerCase())) {
					System.out.println("YA");
					element.getElementsByTagName("name").item(0).setTextContent(name);
					element.getElementsByTagName("time").item(0).setTextContent(Integer.toString(newTime));

				}
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(xmlFile);
			Source input = new DOMSource(doc);
			transformer.transform(input, output);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
