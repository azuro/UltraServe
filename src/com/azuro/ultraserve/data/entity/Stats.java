package com.azuro.ultraserve.data.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Stats
{
    public int BestCharFame;
    public int TotalFame;
    public int Fame;
    @XmlElement(name="ClassStats")
    public List<ClassStats> ClassStates;
}