package com.azuro.ultraserve.db.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.azuro.ultraserve.db.data.Descriptors.*;
import com.azuro.ultraserve.util.Util;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;

public class XMLData {

	final static byte XML_COUNT = 36;

	public static HashBiMap<Short, String> TypeToId = HashBiMap.create();
	public static HashMap<Short, String> IdToDungeon = new HashMap<Short,String>();
	public static HashMap<Short, Element> TypeToElement = new HashMap<Short,Element>();
	public static HashMap<Short, TileDesc> TileDescs = new HashMap<Short,TileDesc>();
	public static HashMap<Short, Item> ItemDescs = new HashMap<Short,Item>();
	public static HashMap<Short, ObjectDesc> ObjectDescs = new HashMap<Short,ObjectDesc>();
	public static HashMap<Short, PortalDesc> PortalDescs = new HashMap<Short, PortalDesc>();
	public static HashMap<String, DungeonDesc> DungeonDescs = new HashMap<String, DungeonDesc>();
	public static HashMap<Short, Integer> KeyPrices = new HashMap<Short, Integer>(); 

	// Merch
	public static HashMap<Short, Integer> ItemPrices = new HashMap<Short, Integer>();
	public static HashMap<Integer, String> ItemShops = new HashMap<Integer, String>();
	

	public static ArrayList<Short> Keys = new ArrayList<Short>();

	// XML Data:
	public static String AdditionXml;
	public static String RemoteXml;
	public static String ItemXml;
	
	public static Descriptors Desc = new Descriptors();
	static {
		try {
			docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ReadXMLs();
	}

	public static void ReadXMLs() {
		try {
			InputStream Stream;

			// Essential
			for (int i = 0; i < XML_COUNT; i++) {
				Stream = XMLData.class.getResourceAsStream("dat" + i + ".xml");
				ProcessXml(Stream);
			}

			// Items
			Stream = XMLData.class.getResourceAsStream("item.xml");
			ProcessXml(Stream);
			Stream = XMLData.class.getResourceAsStream("item.xml");
			ItemXml = CharStreams.toString(new InputStreamReader(Stream,
					"UTF-8"));
			Stream.close();
			
			// Additional(Local Textures)
			Stream = XMLData.class.getResourceAsStream("addition.xml");
			ProcessXml(Stream);
			Stream = XMLData.class.getResourceAsStream("addition.xml");
			AdditionXml = CharStreams.toString(new InputStreamReader(Stream,
					"UTF-8"));
			Stream.close();
						
			// Additional(Remote Textures)
			Stream = XMLData.class.getResourceAsStream("addition2.xml");
			ProcessXml(Stream);
			Stream = XMLData.class.getResourceAsStream("addition2.xml");
			RemoteXml = CharStreams.toString(new InputStreamReader(Stream,
					"UTF-8"));
			Stream.close();

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void ProcessXml(InputStream stream) {
		try {
			Document doc = docBuilder.parse(stream);
			doc.getDocumentElement().normalize();

			//Root Node
			Element root = doc.getDocumentElement();
			NodeList eList;//Entity List
			
			//Ground
			eList = root.getElementsByTagName("Ground");
			for(int i = 0, len = eList.getLength();i<len;i++){
				Element elem = (Element)eList.item(i);
				short type = (short)Util.getInt(elem.getAttribute("type"));
				String id = elem.getAttribute("id");
				
				TypeToId.forcePut(type, id);
				TypeToElement.put(type, elem);
				TileDescs.put(type, Desc.new TileDesc(elem));
			}
			//Equips/Dyes/Pets
			eList = root.getElementsByTagName("Object");
			for(int i = 0, len = eList.getLength();i<len;i++){
				Element elem = (Element)eList.item(i);
				Element sub=(Element)elem.getElementsByTagName("Class").item(0);
				
				//Doesn't Contain Element 'Class'? No Entry!
				if(sub==null)
					continue;
				
				//Get Object Attributes
				String Class = sub.getTextContent();
				short type = (short)Util.getInt(elem.getAttribute("type"));
				String id = elem.getAttribute("id");
				
				//Categorizing
				//Sellable
				if(Class.equalsIgnoreCase("Equipment")||Class.equalsIgnoreCase("Dye")||Class.equalsIgnoreCase("Pet")){
					ItemDescs.put(type, Desc.new Item(elem));
					//Set Up Shop Locations and Prices
					if((sub = (Element)elem.getElementsByTagName("Shop").item(0))!=null){
						ItemShops.put((int)type, sub.getElementsByTagName("Name").item(0).getTextContent());
						ItemPrices.put(type, Util.getInt(sub.getElementsByTagName("Price").item(0).getTextContent()));
					}
				}
				
				//Game Pieces
				if(Class.equalsIgnoreCase("Character")||Class.equalsIgnoreCase("GameObject")||Class.equalsIgnoreCase("Wall")||
						Class.equalsIgnoreCase("ConnectedWall")||Class.equalsIgnoreCase("CaveWall")||Class.equalsIgnoreCase("Portal")){
					Descriptors.ObjectDesc Piece = Desc.new ObjectDesc(elem); 
					ObjectDescs.put(type, Piece);
				}
				
				
				//Portals
				if(Class.equalsIgnoreCase("Portal")){
					PortalDescs.put(type, Desc.new PortalDesc(elem));
				}
				
				//Keys
				if((sub = (Element)elem.getElementsByTagName("Key").item(0))!=null){
					Keys.add(type);
					KeyPrices.put(type, Util.getInt(sub.getTextContent()));
				}
			}
			
			//Dungeons
			eList = root.getElementsByTagName("Dungeon");
			for(int i = 0, len = eList.getLength();i<len;i++){
				Element elem = (Element)eList.item(i);
				short portalId = (short)Util.getInt(elem.getAttribute("type"));
				String name = elem.getAttribute("name");
				
				IdToDungeon.put(portalId, name);
				DungeonDescs.put(name, Desc.new DungeonDesc(elem));
			}
			
			stream.close();
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Parses the XML
	static DocumentBuilder docBuilder;
	
	
	public static void main(String[] args){
		
	}
}
