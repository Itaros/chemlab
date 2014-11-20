package ru.itaros.chemlab.items;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.hoe.blocks.IRotatableBlock;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.tiles.ioconfig.PortType;

public class ItemPortApplianceItem extends ChemLabItem {

	private PortType type;
	
	public ItemPortApplianceItem(String name, PortType type) {
		super("ipai",name);
		this.type=type;
		if(type!=null){
			type.setRelevantItem(new ItemStack(this,1));
		}
	}

	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!player.isSneaking()){return false;}
		
		TileEntity te = world.getTileEntity(x, y, z);
		Block b = world.getBlock(x, y, z);
		
		if(te instanceof IConfigurableIO & b instanceof IRotatableBlock){
			IConfigurableIO io = (IConfigurableIO)te;
			IRotatableBlock irb = (IRotatableBlock)b;
			
			int realside=irb.getRealSide(side,world.getBlockMetadata(x, y, z));
			
			
			PortInfo cur = io.getPorts()[realside];
			if(cur==null || !cur.isNothing()){return true;}//Succeded interaction, but applciation failure
			
			ItemStack result = io.setPort(realside,type);
			
			
			if(!world.isRemote){
				//stack.stackSize--;
				//int cuid = player.inventory.currentItem;
				//player.inventory.setInventorySlotContents(cuid, stack);
				player.inventory.consumeInventoryItem(stack.getItem());
			}
			
			
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
