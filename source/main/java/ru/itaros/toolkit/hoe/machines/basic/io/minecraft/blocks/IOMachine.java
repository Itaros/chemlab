package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class IOMachine extends Block {

	protected IOMachine(Material material) {
		super(material);
		System.out.println("Registering: "+this.getClass().getName());
	}

}
