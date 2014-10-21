package ru.itaros.chemlab.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.achievements.ChemLabAchievements;
import ru.itaros.chemlab.addon.cl3.userspace.CL3AddonLoader;

public class ChemLabItem extends Item {

	
	private String name;
	public ChemLabItem(String name){
		super();
		this.name=name;
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setUnlocalizedName("genericitem."+name);
		this.setTextureName("chemlab:genericitem."+name);
	}
	@Override
	public void onCreated(ItemStack stack, World world,
			EntityPlayer player) {
		ChemLabAchievements.onCrafting(player,stack);
		super.onCreated(stack, world, player);
	}
	
	/*
	 * Tries to load texture from CL3 addons
	 */
	private TextureAtlasSprite overridenExternalIcon;
	public void setIcon(CL3AddonLoader invoker) {
		TextureAtlasSprite i = invoker.getTexture(this.iconString);
		if(i!=null){
			overridenExternalIcon=i;
			this.itemIcon=i;
		}
	}
	@Override
	public void registerIcons(IIconRegister reg) {
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

	
	
	
	
}
