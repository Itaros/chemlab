package ru.itaros.chemlab.achievements;

import java.util.ArrayList;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.blocks.ore.OreMetal;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.MachineSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class ChemLabAchievements {

	static Achievement beinnefficient;
	static Achievement ceo;
	static Achievement syndicate;
	static Achievement doublefun;
	static Achievement molten;
	static Achievement ironmaster;
	static Achievement hematite,pyrite,limestone,metaa;
	static Achievement oxygenic,backinwild;
	
	static AchievementPage page;
	
	public static void load(){
		if(TierLoader.L0_WroughtIron.isEnabled()){
			beinnefficient = registerAchievement("beinnefficient",0,0,ItemLoader.wroughtiron,null);
		}
		ceo = registerAchievement("ceo",0,-2,ItemLoader.programmer,beinnefficient);
		syndicate = registerAchievement("syndicate",0,-2,BlockLoader.syndicationhub,ceo).setSpecial();
		doublefun = registerAchievement("doublefun", 2,0,OreDictionary.getOres("crushedIron").get(0),beinnefficient);
		molten = registerAchievement("molten",2,0,BlockLoader.hiresistmixer,doublefun);
		
		ironmaster = registerAchievement("ironmaster",3,0,Items.iron_ingot,molten);
		hematite = registerAchievement("hematite",-1,2,BlockLoader.oreHematite,ironmaster);
		pyrite = registerAchievement("pyrite",-1,-2,BlockLoader.orePyrite,ironmaster);
		limestone = registerAchievement("limestone",1,-2,BlockLoader.oreLimestone,ironmaster);
		metaa = registerAchievement("metaa",1,2,BlockLoader.oreMetaAnthracite,ironmaster);
		
		oxygenic = registerAchievement("oxygenic",0,2,HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas),molten);
		backinwild = registerAchievement("backinwild",0,2,BlockLoader.gaschimney,oxygenic);
		
		
		Achievement[] aa = new Achievement[registered.size()];
		aa=registered.toArray(aa);
		page = new AchievementPage("ChemLab", aa);
		registered=null;
		AchievementPage.registerAchievementPage(page);
		
		
	}
	
	private static ArrayList<Achievement> registered = new ArrayList<Achievement>();
	
	private static Achievement registerAchievement(String name, int x, int y, Object image, Achievement dep){
		if(dep!=null){
			x+=dep.displayColumn;
			y+=dep.displayRow;
		}
		
		Achievement rslt;
		if(image instanceof Item){
			rslt = new Achievement("chemlab:"+name,name,x,y,(Item) image,dep);
		}else if(image instanceof ItemStack){
			rslt = new Achievement("chemlab:"+name,name,x,y,(ItemStack) image,dep);
		}else if(image instanceof Block){
			rslt =  new Achievement("chemlab:"+name,name,x,y,(Block) image,dep);
		}else{
			throw new RuntimeException("Wrong Image Type");
		}
		rslt.registerStat();
		registered.add(rslt);
		return rslt;
	}

	private static final int COMPLETED = 1;
	
	public static void testForItem(EntityPlayer player, ItemStack stack, Slot slot) {
		if(stack==null || player==null || slot==null){return;}
		Item item = stack.getItem();
		
		if(slot instanceof MachineSlot){
			MachineSlot ms = (MachineSlot)slot;
			if(ms.getType()==HOESlotType.OUTPUT){
				//Take From Output Tasks
				if(item==OreDictionary.getOres("crushedIron").get(0).getItem()){
					player.addStat(doublefun, COMPLETED);
				}else if(item==Items.iron_ingot){
					player.addStat(ironmaster, COMPLETED);
				}else if(item==HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas)){
					player.addStat(oxygenic, COMPLETED);
				}
			}
		}
		
		
		

		
	}

	public static void checkOre(EntityPlayer player, OreMetal ore) {
		if(player==null){return;}
		
		if(ore==BlockLoader.oreHematite){
			player.addStat(hematite,COMPLETED);
		}else if(ore==BlockLoader.orePyrite){
			player.addStat(pyrite, COMPLETED);
		}else if(ore==BlockLoader.oreMetaAnthracite){
			player.addStat(metaa, COMPLETED);
		}else if(ore==BlockLoader.oreLimestone){
			player.addStat(limestone, COMPLETED);
		}
		
	}

	public static void onCrafting(EntityPlayer player, ItemStack stack) {
		if(stack==null || player==null){return;}
		
		Item item = stack.getItem();
		
		if(item==ItemLoader.wroughtiron){
			player.addStat(beinnefficient, COMPLETED);
		}else if(item==ItemLoader.programmer){
			player.addStat(ceo, COMPLETED);
		}else if(item==Item.getItemFromBlock(BlockLoader.syndicationhub)){
			player.addStat(syndicate, COMPLETED);
		}else if(item==Item.getItemFromBlock(BlockLoader.hiresistmixer)){
			player.addStat(molten, COMPLETED);
		}else if(item==Item.getItemFromBlock(BlockLoader.gaschimney)){
			player.addStat(backinwild, COMPLETED);
		}
		
		
	}
}
