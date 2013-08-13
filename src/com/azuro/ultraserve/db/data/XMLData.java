package com.azuro.ultraserve.db.data;

import org.w3c.dom.Element;

import com.google.common.collect.HashBiMap;

public class XMLData {
	
	final byte XML_COUNT = 36;
	
	public static HashBiMap<Short,String> TypeToId = HashBiMap.create();
	public static HashBiMap<Short,String> IdToDungeon = HashBiMap.create();
	public static HashBiMap<Short,Element> TypeToElement = HashBiMap.create();
	// TODO public static HashBiMap<Short,TileDesc> TileDescs = HashBiMap.create();
	
	static{
		ReadXMLs();
	}
	public static void ReadXMLs(){
		
	}
}
