package ru.itaros.chemlab.blocks;

import java.util.List;

import ru.itaros.chemlab.ChemLabCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

//Those blocks are multiblock building components
public class StructuralBlock extends Block {

	private StructuralBlockType[] subblocks;
	private IIcon[] subicons;
	
	public StructuralBlock(StructuralBlockType...types) {
		super(Material.iron);
		this.subblocks=types;
		subicons = new IIcon[this.subblocks.length];
		
		this.setBlockName("structural.block");
		
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	public static enum StructuralBlockType{
		HeatResistantBricks("heatresist.bricks"),
		HeatResistantConcrete("heatresist.concrete");
		
		String subname;
		StructuralBlockType(String subname){
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
	
	
}
