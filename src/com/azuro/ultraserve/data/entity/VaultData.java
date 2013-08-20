package com.azuro.ultraserve.data.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class VaultData
{
    @XmlElement(name="Chest")
    public List<VaultChest> Chests;
}
