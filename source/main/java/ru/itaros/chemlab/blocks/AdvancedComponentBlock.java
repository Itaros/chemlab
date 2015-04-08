package ru.itaros.chemlab.blocks;

import java.util.List;

import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.loader.client.ISBRLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class AdvancedComponentBlock extends Block {
	private AdvancedComponentBlockType[] subblocks;
	private IIcon[] subicons;
	
	public AdvancedComponentBlock(AdvancedComponentBlockType...types) {
		super(Material.iron);
		this.subblocks=types;
		subicons = new IIcon[this.subblocks.length];
		
		this.setBlockName("advcomponent.block");
		
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	public static enum AdvancedComponentBlockType{
		ArcFurnaceElectrodes("arcfurnace.electrodes");
		
		String subname;
		AdvancedComponentBlockType(String subname){
			this.subname=subname;
		}
		public String getSubname(){
			return subname;
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			List list) {
		for(int i = 0 ; i < subblocks.length ; i ++){
			list.add(new ItemStack(this,1,i));
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return subicons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		for(int i = 0 ; i < subicons.length ; i++){
			subicons[i]=reg.registerIcon("chemlab:structural.block."+subblocks[i].getSubname());
		}
	}	
	
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}

	@Override
	public int getRenderType() {
		if(ISBRLoader.advancedComponentBlockModel!=null){
			return ISBRLoader.advancedComponentBlockModel.getRenderId();
		}else{
			return 0;
		}
	}

	public AdvancedComponentBlockType getType(int meta) {
		return subblocks[meta];
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_,
			int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}	
	
	
	
	
}
