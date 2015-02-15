package ru.itaros.chemlab.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.addon.cl3.userspace.CL3AddonLoader;
import ru.itaros.chemlab.addon.cl3.userspace.UserspaceTooltip;
import ru.itaros.hoe.client.TextTooltip;

public class ChemLabItem extends Item {

	
	private String name;
	
	public String getInternalName(){
		return name;
	}
	
	public ChemLabItem(String groupname, String name){
		super();
		this.name=name;
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setUnlocalizedName(groupname+"."+name);
		this.setTextureName("chemlab:"+groupname+"."+name);
	}
	@Override
	public void onCreated(ItemStack stack, World world,
			EntityPlayer player) {
		//ChemLabAchievements.onCrafting(player,stack);
		super.onCreated(stack, world, player);
	}
	
	/*
	 * Tries to load texture from CL3 addons
	 */
	@SideOnly(Side.CLIENT)
	private TextureAtlasSprite overridenExternalIcon;
	@SideOnly(Side.CLIENT)
	public void setIcon(CL3AddonLoader invoker) {
		TextureAtlasSprite i = invoker.getTexture(this.iconString);
		if(i!=null){
			overridenExternalIcon=i;
			this.itemIcon=i;
		}
	}
	@Override
	public void registerIcons(IIconRegister reg) {
		//System.out.println("IS: "+this.iconString);
		if(overridenExternalIcon!=null){
			if(reg instanceof TextureMap){
				TextureMap map = (TextureMap)reg;
				map.setTextureEntry(overridenExternalIcon.getIconName(), overridenExternalIcon);
			}
		}else{
			super.registerIcons(reg);
		}
	}
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return super.getIcon(stack, pass);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_,
			List list, boolean p_77624_4_) {
		
		if(tooltip!=null){
			list.add(tooltip.getText());
		}
		
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
	}

	@SideOnly(Side.CLIENT)
	TextTooltip tooltip;
	
	@SideOnly(Side.CLIENT)
	public void trySetUserspaceTooltip(UserspaceTooltip tooltip) {
		if(tooltip==null){return;}
		this.tooltip = tooltip.getTooltip();
	}

	
	
	
	
}
