package ru.itaros.toolkit.hoe.facilities.client.textures;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

//TODO: Move to ItarosCore. Every mainstream modder should have it. I am not worse then them, lol

public class MetaIconFolder {

	

	private static final int _NUMBER_OF_BLOCK_SIDES = 6;
	
	private IIcon[] iconIndex;
	private int maxValues;
	
	public MetaIconFolder(int amountOfVariations) {
		int icons = amountOfVariations*_NUMBER_OF_BLOCK_SIDES;
		iconIndex = new IIcon[icons];
		maxValues = icons;
	}

	public void Register(int metaFragment,String modname, String[] strings, IIconRegister iconRegister) {
		//Array:DOWN,UP,LEFT,RIGHT,FRONT,BACK
		if(strings.length!=6){throw new RuntimeException("Bad Amount of sides");}//TODO: New Excpetion
		int offset = metaFragment*_NUMBER_OF_BLOCK_SIDES;
		
		for(int x = 0 ; x < _NUMBER_OF_BLOCK_SIDES;x++){
			int realposition = offset+x;
			
			IIcon i = iconRegister.registerIcon(modname+":"+strings[x]);
			iconIndex[realposition]=i;
		}
		
		
	}

	public IIcon GetIcon(int side, int metadata) {
		if(metadata*side>maxValues){throw new RuntimeException("Out of Range");}//TODO: New Excpetion
		int offset = metadata*_NUMBER_OF_BLOCK_SIDES;
		int realposition = offset+side;
		return iconIndex[realposition];
	}

	public void Reference(int i, int i2) {
		int offset_i = i*_NUMBER_OF_BLOCK_SIDES;
		int offset_i2 = i2*_NUMBER_OF_BLOCK_SIDES;
		for(int x = 0 ; x < _NUMBER_OF_BLOCK_SIDES;x++){
			int realposition_i=offset_i+x;
			int realposition_i2=offset_i2+x;
			iconIndex[realposition_i2]=iconIndex[realposition_i];
		}
		
	}

	public IIcon GetIcon(int metadata, int side, int facing) {
		if(side==0 || side==1){
			return GetIcon(metadata, side);
		}else{
			int rside = side+(facing-3);
			if(rside<2){rside+=4;}
			if(rside>5){rside-=4;}
			return GetIcon(metadata, rside);
		}
	}

}
