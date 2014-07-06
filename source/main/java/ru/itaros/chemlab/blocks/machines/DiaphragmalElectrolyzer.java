package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.DiaphragmalElectrolyzerContainer;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.minecraft.tileentity.DiaphragmalElectrolyzerTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;

public class DiaphragmalElectrolyzer extends IOMachineBlock {

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return HOEContainer.getID(DiaphragmalElectrolyzerContainer.class);
	}	
	

	public DiaphragmalElectrolyzer() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"diaphelectr");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new DiaphragmalElectrolyzerTileEntity();
	}


	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_diaphragelectrlzr_side","machine_diaphragelectrlzr_face","machine_diaphragelectrlzr_side","machine_diaphragelectrlzr_side"}, reg);
	}
	
	
	
	//SPECIAL
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer entplayer, int unknown, float px, float py, float pz) {
		
		ItemStack current = entplayer.inventory.getCurrentItem();
		if(current!=null ){
			if(current.getItem()==ItemLoader.asbestos_diaphragm || current.getItem()==ItemLoader.graphite_anode ){
				TileEntity te = world.getTileEntity(x, y, z);
				DiaphragmalElectrolyzerTileEntity dete = (DiaphragmalElectrolyzerTileEntity)te;
				ItemStack rslt;
				if(current.getItem()==ItemLoader.asbestos_diaphragm){
					rslt=dete.exchangeDiaphragm(current);
				}else{
					rslt=dete.exchangeAnode(current);
				}
				if(rslt==null){
					current.stackSize=0;
				}else{
					current.setItemDamage(rslt.getItemDamage());
				}
				return true;
			}else{
				//GUI and stuff
				return super.onBlockActivated(world, x, y, z, entplayer, unknown, px, py, pz);
			}
		}else{
			//GUI and stuff
			return super.onBlockActivated(world, x, y, z, entplayer, unknown, px, py, pz);
		}
		
		
		
	}
	
	
	
	

}
