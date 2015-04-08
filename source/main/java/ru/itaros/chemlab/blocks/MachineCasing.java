package ru.itaros.chemlab.blocks;

import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.hoe.utils.MetaIconFolder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class MachineCasing extends Block {

	public MachineCasing() {
		super(Material.iron);
		this.setBlockName("component.machinecasing");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	
	private static final int METADATA_VARIATIONS = 1;	
	protected MetaIconFolder icons;
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_case","machine_case","machine_case","machine_case","machine_case","machine_case"}, reg);
	}	
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons.GetIcon(side, 0);
	}	
	
}
