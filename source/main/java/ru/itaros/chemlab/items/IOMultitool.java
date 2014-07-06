package ru.itaros.chemlab.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping.PipingMode;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.NBTUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;

public class IOMultitool extends Item {
	
	public final static int FLAG_SYND_RESETER=0b00000001;
	
	public IOMultitool(){
		super();
		this.setUnlocalizedName(getClass().getSimpleName().toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:tool.iomultitool");
		
		this.setMaxStackSize(1);

	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}
	
	public static void addFlag(ItemStack i, int flag){
		NBTTagCompound tag = StackUtility.getTags(i);
		NBTUtility.addFlag(tag, "flags", flag);
	}
	public static int getFlag(ItemStack i){
		NBTTagCompound nbt = StackUtility.getTags(i);
		return nbt.getInteger("flags");
	}
	public static boolean checkFlag(ItemStack i, int flag){
		NBTTagCompound nbt = StackUtility.getTags(i);
		int flags = nbt.getInteger("flags");
		return ((flags & flag)==flag);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack i,
			EntityPlayer player, List list, boolean par4) {
		super.addInformation(i, player, list, par4);
		int flags = getFlag(i);
		list.add("Installed programs:");
		boolean isAny=false;
		String prefix=" *";
		if((flags & FLAG_SYND_RESETER)==FLAG_SYND_RESETER){
			isAny=true;
			list.add(prefix+"SBR Sequence");
		}
		
		if(!isAny){
			list.add("NONE");
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		
		if(!checkFlag(stack,FLAG_SYND_RESETER) && !player.capabilities.isCreativeMode){return false;}
		
		
		//Block b = world.getBlock(x, y, z);
		//int meta = world.getBlockMetadata(x,y,z);
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te==null){return false;}
		if(te instanceof ISyndicationPiping){
			ISyndicationPiping ste = (ISyndicationPiping)te;
			if(ste.getMode()==PipingMode.SEARCHING || ste.getMode()==PipingMode.ADHOCGATE){
				ste.setClear();
				player.swingItem();
				return !world.isRemote;
			}
			if(ste.getMode()==PipingMode.DISABLED){
				ste.setAdHoc();
				player.swingItem();
				return !world.isRemote;
			}
			if(ste.getMode()==PipingMode.ACTIVE){
				if(ste.askControllerToDie(player)){
					ste.setClear();
					player.swingItem();
					return !world.isRemote;
				}
			}
			return false;
		}else{
			return false;
		}
		
	}
	
	
	
	
	
}
