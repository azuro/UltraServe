package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlAttribute;

public class GuildStruct
{
    @XmlAttribute(name="id")
    public int Id;
    public String Name;
    public int Level;
    public String[] Members;
    public int GuildFame;
    public int TotalGuildFame;
}
