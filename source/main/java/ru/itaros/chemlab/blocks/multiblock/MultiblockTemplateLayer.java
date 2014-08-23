package ru.itaros.chemlab.blocks.multiblock;

//TODO: Move To HOE

/*
 * Layer determines one level of structure encoded by resolution IDs
 * Resolution ID is used to check against blocksets and calculations
 * (like max t-resistance or t-gradient distribution steepness)
 */
public class MultiblockTemplateLayer {
	short[] template;
	
	public MultiblockTemplateLayer(short[] matrix){
		template = matrix;
	}

	short[] getRaw() {
		return template;
	}
	
}
