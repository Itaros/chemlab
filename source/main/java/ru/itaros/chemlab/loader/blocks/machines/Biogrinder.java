package ru.itaros.chemlab.loader.blocks.machines;

import net.minecraft.block.material.Material;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachine;

public class Biogrinder extends IOMachine {

	protected Biogrinder() {
		super(Material.iron);
		
		this.setBlockName("machine.biogrinder");
	}

}
