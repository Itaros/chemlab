package ru.itaros.chemlab.blocks.multiblock;

//TODO: Move to HOE

/*
 * Interface to define composition\decomposition event triggers for MBTE
 */
public interface IMultiblockController {

	void notifyDecomposition();
	void notifyComposition();
}
