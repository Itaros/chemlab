package ru.itaros.chemlab.items;

import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.hoe.physics.IMatterState;

public class IronScraps extends ChemLabItem implements IMatterState{

	public IronScraps(String name) {
		super(name);
		
	}

	@Override
	public int lowerPoint() {
		return -1;//C
	}

	@Override
	public int upperPoint() {
		return 1538+274;//K
	}

	@Override
	public float heatCapacity() {
		//Per item = 3.539F/9F;
		return 3.539F;//mJ/K
	}

	@Override
	public long resistance() {
		return 97L;//nOhm
	}

	@Override
	public IMatterState lowerForm() {
		return null;
	}

	@Override
	public IMatterState upperForm() {
		return HOEFluidLoader.moltenIron;
	}

	
	
}
