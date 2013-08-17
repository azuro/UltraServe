package com.azuro.ultraserve.db.data;

public class IntIntPair {
	 
	  private final int left;
	  private final int right;
	 
	  public IntIntPair(int l,int r) {  
	    left = l;
	    right = r;   
	  }
	 
	  public int getLeft() {
	    return left;
	  }
	 
	  public int getRight() {
	    return right;
	  }
	 
	  public String toString() { 
	    return "(" + left + ", " + right + ")";  
	  }
	}