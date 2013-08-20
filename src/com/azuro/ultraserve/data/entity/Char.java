package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.azuro.ultraserve.util.Util;

@XmlRootElement(name="Char")
public class Char {
	
	@XmlAttribute
	public int CharacterId;
	
	public int ObjectType;
    public int Level;
    public int Exp;
    public int CurrentFame;
    
    public String Equipment;
    
    public short[] getEquipment(){
    	return Util.getFromCommaSepString16(Equipment);
    }
    
    public void setEquipment(short[] Equips){
    	Equipment = Util.toCommaSeparatedString(Equips);
    }
    
    public int MaxHitPoints;
    public int HitPoints;
    public int MaxMagicPoints;
    public int MagicPoints;
    public int Attack;
    public int Defense;
    public int Speed;
    public int Dexterity;
    public int HpRegen;
    public int MpRegen;
    public int Tex1;
    public int Tex2;
    public String PCStats;
    @XmlTransient
    public FameStats FameStats;
    
    public boolean Dead;
    public int Pet;

}
