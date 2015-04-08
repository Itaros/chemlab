package ru.itaros.chemlab.items;

import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.hoe.blocks.IRotatableBlock;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CIOWrench extends Item {
	public CIOWrench(){
		super();
		this.setUnlocalizedName(getClass().getSimpleName().toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:ciowrench");
		
		this.setMaxStackSize(1);
	}

	
	
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		Block b = world.getBlock(x, y, z);
		
		if(te instanceof IConfigurableIO & b instanceof IRotatableBlock){
			IConfigurableIO io = (IConfigurableIO)te;
			IRotatableBlock irb = (IRotatableBlock)b;
			
			int realside=irb.getRealSide(side,world.getBlockMetadata(x, y, z));
			
			PortInfo port = io.getPorts()[realside];
			
			if(port!=null){
				port.switchDirection();
				world.markBlockForUpdate(x, y, z);
			}
			
			return !world.isRemote;
		}else{
			return false;
		}		
		
	}

}
