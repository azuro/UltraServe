package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlTransient;

import com.azuro.ultraserve.util.Util;

public class VaultChest
{
    @XmlTransient
    public int ChestId;

    public String Items;
    @XmlTransient
    public short[] getItems(){
    	return Util.getFromCommaSepString16(Items);
    }
    
    public void setItems(short[] ItemArray){
    	Items = Util.toCommaSeparatedString(ItemArray);
    }
}