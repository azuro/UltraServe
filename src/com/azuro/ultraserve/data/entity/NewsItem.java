package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Item")
public class NewsItem {

	String Icon;
	String Title;
	String TagLine;
	String Link;
	public long Date;
}
