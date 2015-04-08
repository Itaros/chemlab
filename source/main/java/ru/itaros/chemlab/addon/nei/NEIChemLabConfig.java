package ru.itaros.chemlab.addon.nei;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.hoe.recipes.Recipe;
import static codechicken.nei.api.API.*;
import codechicken.nei.api.IConfigureNEI;

public class NEIChemLabConfig implements IConfigureNEI {

	@Override
	public String getName() {
		return "ChemLab";
	}

	@Override
	public String getVersion() {
		return ChemLab.getPublicVersionNotation();
	}

	@Override
	public void loadConfig() {
		MachineCrafterRecipes generalCrafting = new MachineCrafterRecipes();
		generalCrafting.initiateCache(Recipe.getRecipeRegistry());
		registerRecipeHandler(generalCrafting);
		registerUsageHandler(generalCrafting);
	}

}
