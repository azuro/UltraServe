package com.azuro.ultraserve.util;

public class Util {
	
	public static int getInt(String num){
		if(num.startsWith("0x"))//Hex
			return Integer.parseInt(num.substring(2),16);
		else
			return Integer.parseInt(num);
	}
}
