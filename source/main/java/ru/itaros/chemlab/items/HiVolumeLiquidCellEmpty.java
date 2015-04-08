package ru.itaros.chemlab.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import ru.itaros.chemlab.ChemLabCreativeTab;

public class HiVolumeLiquidCellEmpty extends Item {
	public static final String EMPTY_TOKEN = "empty";

	public HiVolumeLiquidCellEmpty(){
		super();
		this.setUnlocalizedName("hvlc."+EMPTY_TOKEN);
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:hvlc-"+EMPTY_TOKEN);
	}

	@Override
	  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	    {
		boolean flag = true;//LOLWUT?
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, flag);
		if(movingobjectposition==null){return stack;}
		int x = movingobjectposition.blockX;
		int y = movingobjectposition.blockY;
		int z = movingobjectposition.blockZ;
		
		//TODO: should precache water block for performance
		Block target = world.getBlock(x, y, z);
		if(target==Block.getBlockFromName("water")){

			
//			HOEFluid water = HOEFluidLoader.water_natural;
//			//TODO: This needed to be cached too
//			HiVolumeLiquidCell cell = HiVolumeLiquidCell.getByFluid(water);
//
//			
//			if(--stack.stackSize<=0){
//				return new ItemStack(cell,1);
//			}else{
//				ItemStack rslt = new ItemStack(cell,1);
//				player.inventory.addItemStackToInventory(rslt);				
//				return stack;
//			}			
			
			
		}else if(target instanceof BlockFluidFinite){
			BlockFluidFinite f = (BlockFluidFinite)target;
			//HOEFluid hoefl = FluidToHOE.get(f.getFluid());
			//There is nothing to do. BFF is fragmented thingie :(
		}
		
		return stack;
	    }
	
	
	
	
}
