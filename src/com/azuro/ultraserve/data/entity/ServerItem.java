package com.azuro.ultraserve.data.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Server")
public class ServerItem
{
    public String Name;
    public String DNS;
    public double Lat;
    public double Long;
    public double Usage;
    public int RankRequired;

    String AdminOnly;
    
    public void setAdminOnly(boolean isAdminOnly){
    	AdminOnly = isAdminOnly?"True":null;
    }
    @XmlTransient
    public boolean getAdminOnly(){
    	return AdminOnly!=null;
    }
}