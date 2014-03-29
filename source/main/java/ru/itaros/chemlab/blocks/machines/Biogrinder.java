package ru.itaros.chemlab.blocks.machines;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.GUIBiogrinder;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class Biogrinder extends IOMachineBlock {


	public Biogrinder() {
		super(Material.iron);
		this.setBlockNameRaw("chemlab:machinebiogrinder");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}

	
	
	@Override
	public void updateTick(World world, int x, int y,
			int z, Random random) {
		
		TileEntity te  =world.getTileEntity(x, y, z);
		if(te instanceof MachineTileEntity){
			MachineTileEntity me = (MachineTileEntity)te;
			me.pullFromHOE();
			me.pushToHOE();
			me.sync();
		}
		
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
            par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }	
    
	@Override
	public int tickRate(World p_149738_1_) {
		return 20*5;
	}



	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new BiogrinderTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer entplayer,
			int unknown, float px, float py,
			float pz) {
		
		entplayer.openGui(ChemLab.getInstance(), GUIBiogrinder.ID, world, x, y, z);
		
		if(world.isRemote){
			//client
			
		}else{
			//server
			world.markBlockForUpdate(x, y, z);
		}
		
		
		return true;
	}
	
	
	
	
	

}
