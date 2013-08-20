package com.azuro.ultraserve.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
	
	public static int getInt(String num){
		if(num.startsWith("0x"))//Hex
			return Integer.parseInt(num.substring(2),16);
		else
			return Integer.parseInt(num);
	}

	public static short[] getFromCommaSepString16(String from) {
		String frm[] = from.split(",");
		short[] ret = new short[frm.length];
		for(int i=0;i<ret.length;i++){
			ret[i]=(short)Integer.parseInt(frm[i].trim());
		}
		return ret;
	}
	
	public static int[] getFromCommaSepString32(String from) {
		String frm[] = from.split(",");
		int[] ret = new int[frm.length];
		for(int i=0;i<ret.length;i++){
			ret[i]=Integer.parseInt(frm[i].trim());
		}
		return ret;
	}
	
	public static List<Integer> StringListToIntList(List<String> strList){
		List<Integer> ret = new ArrayList<Integer>();
		for(String str:strList)
			try{
			ret.add(Integer.parseInt(str.trim()));
			}catch(Exception e){}
		return ret;
	}
	public static String toCommaSeparatedString(Object[] from){
		StringBuilder buildString = new StringBuilder();
		for(int i=0;i<from.length;i++){
			if(i!=0)
				buildString.append(", ");
			buildString.append(from[i].toString());
		}
		return buildString.toString();
	}
	public static String toCommaSeparatedString(short[] from){
		StringBuilder buildString = new StringBuilder();
		for(int i=0;i<from.length;i++){
			if(i!=0)
				buildString.append(", ");
			buildString.append(from[i]);
		}
		return buildString.toString();
	}
	
	public static <T> List<T> Shuffle(List<T> initial){
		Collections.shuffle(initial);
		return initial;
	}
}
