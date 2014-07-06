package ru.itaros.chemlab.blocks.machines.syndication;

import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationCapacitor;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationCapacitorTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class SyndicationCapacitor extends Block implements ISyndicationPipeConnectable, ITileEntityProvider {

	public SyndicationCapacitor(){
		super(Material.iron);
		
		this.setBlockName("syndication.utilities.capacitor");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setBlockTextureName("chemlab:syndication.utilities.battery");
	}

	protected MetaIconFolder icons;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons.GetIcon(side, 0);
	}	
	private static final int METADATA_VARIATIONS = 1;	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_synd_util_capacitor_side","machine_synd_util_capacitor_side","machine_synd_util_capacitor_side","machine_synd_util_capacitor_side"}, reg);
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new SyndicationCapacitorTileEntity();
	}
	
	
	
	
	
}
