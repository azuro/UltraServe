package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlAttribute;

public class Guild
{
    @XmlAttribute(name="id")
    public int Id;
    public String Name;
    public int Rank;
}