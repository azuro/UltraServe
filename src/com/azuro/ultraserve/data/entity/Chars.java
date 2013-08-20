package com.azuro.ultraserve.data.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Chars
{
    
    @XmlElement(name="Char")
    public List<Char> Characters;

    @XmlAttribute(name="nextCharId")
    public int NextCharId;
    @XmlAttribute(name="maxNumChars")
    public int MaxNumChars;

    public Account Account;

    public List<NewsItem> News;

    public List<ServerItem> Servers;

    public Double Lat;
    public Double Long;
}