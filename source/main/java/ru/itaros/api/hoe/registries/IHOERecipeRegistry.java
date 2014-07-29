package ru.itaros.api.hoe.registries;

import ru.itaros.hoe.recipes.Recipe;

public interface IHOERecipeRegistry {

	public void add(Recipe r);
	public Recipe get(String mnemonic);
	public Recipe[] dumpAll();
}
