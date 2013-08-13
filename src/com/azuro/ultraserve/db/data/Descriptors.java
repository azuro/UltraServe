package com.azuro.ultraserve.db.data;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.azuro.ultraserve.util.Util;

public class Descriptors {
	/*
	 *  Stat Buff/Debuff Data Descriptor
	 */
	//Stores 'Actual' Condition Effects
	public enum ConditionEffects{
		 			Dead (1 << 0),
				    Quiet (1 << 1),
				    Weak (1 << 2),
				    Slowed (1 << 3),
				    Sick (1 << 4),
				    Dazed (1 << 5),
				    Stunned (1 << 6),
				    Blind (1 << 7),
				    Hallucinating (1 << 8),
				    Drunk (1 << 9),
				    Confused (1 << 10),
				    StunImmume (1 << 11),
				    Invisible (1 << 12),
				    Paralyzed (1 << 13),
				    Speedy (1 << 14),
				    Bleeding (1 << 15),
				    NotUsed (1 << 16),
				    Healing (1 << 17),
				    Damaging (1 << 18),
				    Berserk (1 << 19),
				    Paused (1 << 20),
				    Stasis (1 << 21),
				    StasisImmune (1 << 22),
				    Invincible (1 << 23),
				    Invulnerable (1 << 24),
				    Armored (1 << 25),
				    ArmorBroken (1 << 26);
		 			private int Effect;
				    ConditionEffects(int Effect){
				    	this.Effect = Effect;
				    }
				    public int Effect(){
				    	return Effect;
				    }
	}
	//Stores Indices of Condition Effects
	public enum ConditionEffectIndex
	{
	    Dead (0),
	    Quiet (1),
	    Weak (2),
	    Slowed (3),
	    Sick (4),
	    Dazed (5),
	    Stunned (6),
	    Blind (7),
	    Hallucinating (8),
	    Drunk (9),
	    Confused (10),
	    StunImmume (11),
	    Invisible (12),
	    Paralyzed (13),
	    Speedy (14),
	    Bleeding (15),
	    NotUsed (16),
	    Healing (17),
	    Damaging (18),
	    Berserk (19),
	    Paused (20),
	    Stasis (21),
	    StasisImmune (22),
	    Invincible (23),
	    Invulnerable (24),
	    Armored (25),
	    ArmorBroken (26),
	    Hexed (27);
	    
	    private int EffectIndex;
	    ConditionEffectIndex(int Effect){
	    	this.EffectIndex = Effect;
	    }
	    public int EffectIndex(){
	    	return EffectIndex;
	    }
	    public static ConditionEffectIndex Parse(String args){
	    	for(ConditionEffectIndex Eff:ConditionEffectIndex.values()){
	    		if(args.equalsIgnoreCase(Eff.name()))
	    			return Eff;
	    	}
	    	return NotUsed;
	    }
	}
	//Buff or Debuff Data
	public class ConditionEffect{
		//Store Effect Type, Duration(ms) and Range of Effect(RoE)
		public ConditionEffectIndex Effect;
		public int DurationMS;
		public float Range;
		public ConditionEffect(Element elem){
			//XML Structure:
			//<ConditionEffect duration = "{duration}" range="{range}">{ConditionEffect}</ConditionEffect>
			Effect = ConditionEffectIndex.valueOf(elem.getNodeValue().replace(" ", ""));
			if(elem.hasAttribute("duration"))
				DurationMS = (int)(Float.parseFloat(elem.getAttribute("duration")));
			if(elem.hasAttribute("range"))
				Range = Float.parseFloat(elem.getAttribute("range"));
		}
	}
	//Projectile Data
	public class ProjectileDesc{
		//General:
		public int BulletType;
	    public String ObjectId;
	    public int LifetimeMS;
	    public float Speed;
	    public int Size;
	    public int MinDamage;
	    public int MaxDamage;
	    //Buff/Debuffs:
	    public ConditionEffect[] Effects;
	    //Shot Character:
	    public boolean MultiHit;
	    public boolean PassesCover;
	    public boolean ArmorPiercing;
	    public boolean ParticleTrail;
	    public boolean Wavy;
	    public boolean Parametric;
	    public boolean Boomerang;
	    //Shot Description:
	    public float Amplitude;
	    public float Frequency;
	    public float Magnitude;
	    
	    public ProjectileDesc(Element elem){
	    	//XML Structure:
	    	//
	    	//<Projectile id="{id}">
	    	//<ObjectId>{ObjectId(Name)}</ObjectId>
	    	//<Speed>{Speed}</Speed>
	    	//<MinDamage>{MinDamage}</MinDamage>
			//<MaxDamage>{MaxDamage}</MaxDamage>
			//<Size>80</Size>
	    	//<LifetimeMS>{LifetimeMS}</LifeTimeMS>
	    	//<ConditionEffect duration="{duration}">{ConditionEffect}</ConditionEffect> 
	    	//<{Type Of Shot}/>
	    	//</Projectile>
	    	
	    	if(elem.hasAttribute("id")){
	    		BulletType = Integer.parseInt(elem.getAttribute("id"));
	    	}
	    	ObjectId = elem.getElementsByTagName("ObjectId").item(0).getTextContent();
	    	LifetimeMS = Integer.parseInt(elem.getElementsByTagName("LifetimeMS").item(0).getTextContent());
	    	Speed = Float.parseFloat(elem.getElementsByTagName("Speed").item(0).getTextContent());
	    	
	    	//Projectile may have either a Single Value or a Range of values for damage, handle accordingly
	    	if(elem.getElementsByTagName("Speed").getLength()!=0)
	    		MinDamage = MaxDamage =  Integer.parseInt(elem.getElementsByTagName("Damage").item(0).getTextContent());
	    	else{
	    		MinDamage = Integer.parseInt(elem.getElementsByTagName("MinDamage").item(0).getTextContent());
	    		MaxDamage = Integer.parseInt(elem.getElementsByTagName("MaxDamage").item(0).getTextContent());
	    	}
	    	NodeList CEffectElements = elem.getElementsByTagName("ConditionEffect");
	    	//Add Condition Effects
	    	if(CEffectElements.getLength()!=0){
	    	List<ConditionEffect> effects = new ArrayList<ConditionEffect>(CEffectElements.getLength());
	    		for(int i=0;i<effects.size();i++){
	    			effects.add(new ConditionEffect((Element)CEffectElements.item(i)));
	    		}
	    		Effects = (ConditionEffect[]) effects.toArray();
	    	}
	    	//Shot Character:
	    	MultiHit = elem.getElementsByTagName("MultiHit").getLength()!=0;
	    	PassesCover = elem.getElementsByTagName("PassesCover").getLength()!=0;
	    	ArmorPiercing = elem.getElementsByTagName("ArmorPiercing").getLength()!=0;
	    	ParticleTrail = elem.getElementsByTagName("ParticleTrail").getLength()!=0;
	        Wavy = elem.getElementsByTagName("Wavy").getLength()!=0;
	        Parametric = elem.getElementsByTagName("Parametric").getLength()!=0;
	        Boomerang = elem.getElementsByTagName("Boomerang").getLength()!=0;
	    	
	        NodeList DescrList = elem.getElementsByTagName("Amplitude");
	        if(DescrList.getLength()!=0){
	        	Amplitude = Float.parseFloat(DescrList.item(0).getNodeValue());
	        }
	        else{
	        	Amplitude = 0;
	        }
	        DescrList = elem.getElementsByTagName("Frequency");
	        if(DescrList.getLength()!=0){
	        	Frequency = Float.parseFloat(DescrList.item(0).getNodeValue());
	        }
	        else{
	        	Frequency = 1;
	        }
	        DescrList = elem.getElementsByTagName("Magnitude");
	        if(DescrList.getLength()!=0){
	        	Magnitude = Float.parseFloat(DescrList.item(0).getNodeValue());
	        }
	        else{
	        	Magnitude = 3;
	        }
	    }
	}
	
	/*
	 * Special Abilities Descriptor
	 */
	public enum ActivateEffects
	{
	    Shoot,
	    StatBoostSelf,
	    StatBoostAura,
	    BulletNova,
	    ConditionEffectAura,
	    ConditionEffectSelf,
	    Heal,
	    HealNova,
	    Magic,
	    MagicNova,
	    Teleport,
	    VampireBlast,
	    Trap,
	    StasisBlast,
	    Decoy,
	    Lightning,
	    PoisonGrenade,
	    RemoveNegativeConditions,
	    RemoveNegativeConditionsSelf,
	    IncrementStat,
	    Pet,
	    PermaPet,
	    Create,
	    UnlockPortal,
	    DazeBlast,
	    ClearConditionEffectAura,
	    ClearConditionEffectSelf,
	    Dye,
	    ShurikenAbility,
	    TomeDamage,
	    MultiDecoy,
	    Mushroom
	}
	public class ActivateEffect
	{
	    public ActivateEffects Effect;
	    public int Stats;
	    public int Amount;
	    public float Range;
	    public int DurationMS;
	    public ConditionEffectIndex ConditionEffect;
	    public float EffectDuration;
	    public int MaximumDistance;
	    public float Radius;
	    public int TotalDamage;
	    public String ObjectId;
	    public int AngleOffset;
	    public int MaxTargets;
	    public String Id;
	    public String DungeonName;
	    public String LockedName;
	    public long Color;
	    
	    public ActivateEffect(Element elem){
	    	//XML Structure:
	    	//
	    	//<Activate effect="{ActivateEffect}" duration="{duration}" range="{range}">{Effect}</Activate>
	    	Effect = ActivateEffects.valueOf(elem.getNodeValue());
	    	if(elem.hasAttribute("stat"))
	    		Stats = Integer.parseInt(elem.getAttribute("stat"));
	    	
	    	if(elem.hasAttribute("amount"))
	    		Amount = Integer.parseInt(elem.getAttribute("amount"));
	    	
	    	if(elem.hasAttribute("range"))
	    		Range = Integer.parseInt(elem.getAttribute("range"));
	    	if(elem.hasAttribute("duration"))
	    		DurationMS = Integer.parseInt(elem.getAttribute("duration"));
	    	
	    	if(elem.hasAttribute("effect"))
	    		ConditionEffect = ConditionEffectIndex.valueOf(elem.getAttribute("effect"));
	    	if(elem.hasAttribute("condEffect"))
	    		ConditionEffect = ConditionEffectIndex.valueOf(elem.getAttribute("condEffect"));
	    	
	    	if(elem.hasAttribute("condDuration"))
	    		EffectDuration = Float.parseFloat(elem.getAttribute("condDuration"));
	    	
	    	if(elem.hasAttribute("maxDistance"))
	    		MaximumDistance = Integer.parseInt(elem.getAttribute("maxDistance"));
	    	
	    	if(elem.hasAttribute("totalDamage"))
	    		TotalDamage = Integer.parseInt(elem.getAttribute("totalDamage"));
	    	
	    	if (elem.hasAttribute("objectId"))
	            ObjectId = elem.getAttribute("objectId");

	        if (elem.hasAttribute("angleOffset"))
	            AngleOffset = Integer.parseInt(elem.getAttribute("angleOffset"));

	        if (elem.hasAttribute("maxTargets"))
	            MaxTargets = Integer.parseInt(elem.getAttribute("maxTargets"));

	        if (elem.hasAttribute("id"))
	            Id = elem.getAttribute("id");

	        if (elem.hasAttribute("dungeonName"))
	            DungeonName = elem.getAttribute("dungeonName");

	        if (elem.hasAttribute("lockedName"))
	            LockedName = elem.getAttribute("lockedName");

	        //TODO Check if int Works
	        if (elem.hasAttribute("color"))
	            Color = Integer.parseInt(elem.getAttribute("color").substring(2));
	    	
	    }
	}
	
	/*
	 * Portal Type/Data Descriptor
	 */
	public class PortalDesc{
		
		public short ObjectType;
		public String ObjectId;
		public String DungeonName;
	    public int TimeoutTime;
	    public boolean NexusPortal;
	    
	    public PortalDesc(Element elem){
	    	//XML Structure:
	    	//
	    	//<Object type="{ObjectType}" id="{ObjectId(Name)}">
			//<Class>Portal</Class>
			//<Size>{Size}</Size>
			//<IntergamePortal/>
			//<NexusPortal/>
			//</Object>
	    	
	    	ObjectType = (short)Util.getInt(elem.getAttribute("type"));
	    	ObjectId = elem.getAttribute("id");
	    	if(elem.getElementsByTagName("DungeonName").getLength()!=0)//Dungeon Portal
	    		DungeonName = elem.getElementsByTagName("DungeonName").item(0).getNodeValue();
	    	
	    	else if(elem.getElementsByTagName("NexusPortal").getLength()!=0)//Nexus Portal
	    		NexusPortal = true;
	    	
	    	TimeoutTime = 30;
	    }
	}
	
	/*
	 * Item Descriptor(All Weapons/Armor/Potions/Abilities/Pets)
	 */
	public class Item{
		public short ObjectType;
	    public String ObjectId;
	    public int SlotType;
	    public int Tier;
	    public String Description;
	    public float RateOfFire;
	    public boolean Usable;
	    public int BagType;
	    public int MpCost;
	    public int FameBonus;
	    public int NumProjectiles;
	    public float ArcGap;
	    public boolean Consumable;
	    public boolean Potion;
	    public String DisplayId;
	    public String SuccessorId;
	    public boolean Soulbound;
	    public boolean Undead;
	    public boolean PUndead;
	    public boolean SUndead;
	    public float Cooldown;
	    public boolean Resurrects;
	    public int Texture1;
	    public int Texture2;
	    public boolean Secret;

	    public int Doses;

	    public SimpleEntry<Integer,Integer>[] StatsBoost;
	    public ActivateEffect[] ActivateEffects;
	    public ProjectileDesc[] Projectiles;
	    
	    @SuppressWarnings("unchecked")
		public Item(Element elem){
	    	//XML Structure:
	    	//
	    	//<Object type="0xa00" id="Short Sword">
			//<Class>Equipment</Class>
			//<Item/>
			//<Texture><File>lofiObj5</File><Index>0x30</Index></Texture>
			//<SlotType>1</SlotType>
			//<Tier>0</Tier>
			//<Description>A steel short sword.</Description>
			//<RateOfFire>1</RateOfFire>
			//<Sound>weapon/blunt_sword</Sound>
			//<Projectile>
				//<ObjectId>Blade</ObjectId>
				//<Speed>100</Speed>
				//<MinDamage>45</MinDamage>
				//<MaxDamage>90</MaxDamage>
				//<LifetimeMS>350</LifetimeMS>
			//</Projectile>
			//<BagType>0</BagType>
			//<OldSound>bladeSwing</OldSound>
	    	//</Object>
	    	NodeList sub;
	    	
	    	ObjectType = (short)Util.getInt(elem.getAttribute("type"));
	    	ObjectId = elem.getAttribute("id");
	    	
	    	SlotType = Util.getInt(elem.getElementsByTagName("SlotType").item(0).getNodeValue());
	    	
	    	if((sub = elem.getElementsByTagName("Tier")).getLength()!=0){
	    		Tier = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		Tier = -1;
	    	}
	    	
	    	Description = elem.getElementsByTagName("Description").item(0).getNodeValue();
	    	
	    	if((sub = elem.getElementsByTagName("RateOfFire")).getLength()!=0){
	    		RateOfFire = Float.parseFloat(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		RateOfFire = 1;
	    	}
	    	
	    	Usable = elem.getElementsByTagName("Usable").getLength()!=0;
	    	
	    	if((sub = elem.getElementsByTagName("BagType")).getLength()!=0){
	    		BagType = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		BagType = 0;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("MpCost")).getLength()!=0){
	    		MpCost = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		MpCost = 0;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("FameBonus")).getLength()!=0){
	    		FameBonus = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		FameBonus = 0;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("NumProjectiles")).getLength()!=0){
	    		NumProjectiles = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		NumProjectiles = 1;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("ArcGap")).getLength()!=0){
	    		ArcGap = Float.parseFloat(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		ArcGap = 11.25f;
	    	}
	    	
	    	Consumable = elem.getElementsByTagName("Consumable").getLength()!=0;
	    	Potion = elem.getElementsByTagName("Potion").getLength()!=0;
	    	
	    	if((sub = elem.getElementsByTagName("DisplayId")).getLength()!=0){
	    		DisplayId = sub.item(0).getNodeValue();
	    	}
	    	else{
	    		DisplayId=null;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("Doses")).getLength()!=0){
	    		Doses = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		Doses = 0;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("SuccessorId")).getLength()!=0){
	    		SuccessorId = sub.item(0).getNodeValue();
	    	}
	    	else{
	    		SuccessorId=null;
	    	}
	    	
	    	Soulbound = elem.getElementsByTagName("Soulbound").getLength()!=0;
	    	Undead = elem.getElementsByTagName("Undead").getLength()!=0;
	    	PUndead = elem.getElementsByTagName("PUndead").getLength()!=0;
	    	SUndead = elem.getElementsByTagName("SUndead").getLength()!=0;
	    	Secret = elem.getElementsByTagName("Secret").getLength()!=0;
	    	
	    	if((sub = elem.getElementsByTagName("Cooldown")).getLength()!=0){
	    		Cooldown = Float.parseFloat(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		Cooldown = 0f;
	    	}
	    	
	    	Resurrects = elem.getElementsByTagName("Resurrects").getLength()!=0;
	    	
	    	if((sub = elem.getElementsByTagName("Tex1")).getLength()!=0){
	    		Texture1 = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		Texture1 = 0;
	    	}
	    	
	    	if((sub = elem.getElementsByTagName("Tex2")).getLength()!=0){
	    		Texture2 = Util.getInt(sub.item(0).getNodeValue());
	    	}
	    	else{
	    		Texture2 = 0;
	    	}
	    	
	    	List<SimpleEntry<Integer, Integer>> stats = new ArrayList<SimpleEntry<Integer, Integer>>();
	    	sub=elem.getElementsByTagName("ActivateOnEquip");
	    	for(int i=0;i<sub.getLength();i++){
	    		Element subElement = (Element)sub.item(i);
	    		stats.add(new SimpleEntry<Integer, Integer>(Util.getInt(subElement.getAttribute("stat")), Util.getInt(subElement.getAttribute("amount"))));
	    	}
	    	StatsBoost = (SimpleEntry<Integer,Integer>[])stats.toArray();
	    	
	    	List<ActivateEffect> activate = new ArrayList<ActivateEffect>();
	    	sub=elem.getElementsByTagName("Activate");
	    	for(int i=0;i<sub.getLength();i++){
	    		activate.add(new ActivateEffect((Element)sub.item(i)));
	    	}
	    	ActivateEffects = (ActivateEffect[])activate.toArray();
	    	
	    	List<ProjectileDesc> prj = new ArrayList<ProjectileDesc>();
	    	sub=elem.getElementsByTagName("Projectile");
	    	for(int i=0;i<sub.getLength();i++){
	    		prj.add(new ProjectileDesc((Element)sub.item(i)));
	    	}
	    	Projectiles = (ProjectileDesc[])prj.toArray();
	    	
	    }
	}
	
	public class SpawnCount{
		public int Mean;
	    public int StdDev;
	    public int Min;
	    public int Max;
	    
	    public SpawnCount(Element elem){
	    	Mean = Util.getInt(elem.getElementsByTagName("Mean").item(0).getNodeValue());
	    	StdDev = Util.getInt(elem.getElementsByTagName("StdDev").item(0).getNodeValue());
	    	Min = Util.getInt(elem.getElementsByTagName("Min").item(0).getNodeValue());
	    	Max = Util.getInt(elem.getElementsByTagName("Max").item(0).getNodeValue());
	    }
	}

	public class ObjectDesc{
		public short ObjectType;
	    public String ObjectId;
	    public String DisplayId;
	    public String Group;
	    public String Class;
	    public boolean Player;
	    public boolean Enemy;
	    public boolean OccupySquare;
	    public boolean FullOccupy;
	    public boolean EnemyOccupySquare ;
	    public boolean Static ;
	    public boolean NoMiniMap ;
	    public boolean ProtectFromGroundDamage ;
	    public boolean ProtectFromSink ;
	    public boolean Flying ;
	    public boolean ShowName ;
	    public boolean DontFaceAttacks ;
	    public int MinSize ;
	    public int MaxSize ;
	    public int SizeStep ;
	    public ProjectileDesc[] Projectiles;
	    
	    public int MaxHP ;
	    public int Defense ;
	    public String Terrain ;
	    public float SpawnProbability ;
	    public SpawnCount Spawn ;
	    public boolean Cube ;
	    public boolean God ;
	    public boolean Quest ;
	    public int Level ;
	    public boolean StasisImmune ;
	    public boolean Oryx ;
	    public boolean Hero ;
	    public int PerRealmMax ;
	    public float ExpMultiplier ;    //Exp gained = level total / 10 * multi
	    
	    

	}
}
