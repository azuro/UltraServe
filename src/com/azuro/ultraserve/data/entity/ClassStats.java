package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlAttribute;

public class ClassStats
{
    @XmlAttribute(name="objectType")
    public int ObjectType;
    public int BestLevel;
    public int BestFame;
}