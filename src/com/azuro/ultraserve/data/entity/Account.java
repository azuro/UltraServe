package com.azuro.ultraserve.data.entity;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.azuro.ultraserve.util.Util;
import com.google.common.base.Joiner;
@XmlRootElement
public class Account
{

    public int AccountId;
    public String Name;

    public String NameChosen;
    
    public void setNameChosen(boolean isNameChosen){
    	NameChosen = isNameChosen?"True":null;
    }
    @XmlTransient
    public boolean getNameChosen(){
    	return NameChosen!=null;
    }

    @XmlElement(name="Converted")
    public String Converted;
    
    public void setConverted(boolean isConverted){
    	Converted = isConverted?"True":null;
    }
    @XmlTransient
    public boolean getConverted(){
    	return Converted!=null;
    }

    @XmlElement(name="Admin")
    public String Admin;
    
    public void setAdmin(boolean isAdmin){
    	Admin = isAdmin?"True":null;
    }
    @XmlTransient
    public boolean getAdmin(){
    	return Admin!=null;
    }

    @XmlElement(name="Banned")
    public String Banned;
    
    public void setBanned(boolean isBanned){
    	Banned = isBanned?"True":null;
    }
    @XmlTransient
    public boolean getBanned(){
    	return Banned!=null;
    }

    @XmlElement(name="VerifiedEmail")
    public String VerifiedEmail;
    
    public void setVerifiedEmail(boolean isVerifiedEmail){
    	VerifiedEmail = isVerifiedEmail?"True":null;
    }
    @XmlTransient
    public boolean getVerifiedEmail(){
    	return VerifiedEmail!=null;
    }

    public String StarredAccounts;
    
    public void setLocked(List<Integer> Locked){
    	StarredAccounts = Joiner.on(',').join(Locked);
    }
    @XmlTransient
    public List<Integer> getLocked(){
    	return Util.StringListToIntList(Arrays.asList(StarredAccounts.split(",")));
    }
    
    @XmlElement(name="IgnoredAccounts")
    public String IgnoredAccounts;
    
    public void setIgnored(List<Integer> Ignored){
    	IgnoredAccounts = Joiner.on(',').join(Ignored);
    }
    @XmlTransient
    public List<Integer> getIgnored(){
    	return Util.StringListToIntList(Arrays.asList(IgnoredAccounts.split(",")));
    }

    public int Rank;
    public int Credits;
    public int NextCharSlotPrice;
    public Integer BeginnerPackageTimeLeft;

    public VaultData Vault;
    public Stats Stats;
    public Guild Guild;


}