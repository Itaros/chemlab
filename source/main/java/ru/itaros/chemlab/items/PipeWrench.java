package ru.itaros.chemlab.items;

import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.hoe.blocks.IRotatableBlock;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PipeWrench extends Item {
	public PipeWrench(){
		super();
		this.setUnlocalizedName(getClass().getSimpleName().toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:wrench");
		
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
			
			ItemStack result = io.setPortToNothing(realside);
			if(result!=null){
				if(!world.isRemote){
					ForgeDirection dir = ForgeDirection.getOrientation(side);
					world.spawnEntityInWorld(new EntityItem(world,(double)x+0.5D+(dir.offsetX*0.45D),(double)y+0.5D+(dir.offsetY*0.45D),(double)z+0.5D+(dir.offsetZ*0.45D),result));
				}
			}
			return !world.isRemote;
		}else{
			return false;
		}		
		
	}

	
	
	
	
	
}
