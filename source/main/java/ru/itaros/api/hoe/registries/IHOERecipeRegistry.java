package ru.itaros.api.hoe.registries;

import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;

public interface IHOERecipeRegistry {

	public void add(Recipe r);
	public Recipe get(String mnemonic);
	
}
