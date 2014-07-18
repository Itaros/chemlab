package ru.itaros.chemlab.addon.nei;

import java.util.List;

import org.lwjgl.opengl.GL11;

import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;

public class MachineCrafterRecipes implements ICraftingHandler, IUsageHandler {

	private static RecipeNEIIncapsulator[] recipes;
	private RecipeNEIIncapsulator[] filtered;
	public void initiateCache(IHOERecipeRegistry registry){
		Recipe[] recipes_raw = registry.dumpAll();
		recipes = RecipeNEIIncapsulator.generateFromRecipes(recipes_raw);		
	}
	
	public MachineCrafterRecipes(){}
	
	
	private ResourceLocation recipeTex = new ResourceLocation(ChemLab.MODID,"textures/gui/neiaddon/neiprocessing.png");
	private Gui gui = new Gui();
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(recipeTex);
		gui.drawTexturedModalRect(0, 0, 48, 99, 160, 60);
	}

	@Override
	public void drawForeground(int recipe) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PositionedStack> getIngredientStacks(int recipe) {
		return filtered[recipe].in;
	}

	@Override
	public List<PositionedStack> getOtherStacks(int recipe) {
		return filtered[recipe].out;
	}
	
	@Override
	public PositionedStack getResultStack(int recipe) {
		return filtered[recipe].host;
	}	

	@Override
	public IOverlayHandler getOverlayHandler(GuiContainer arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRecipeName() {
		return "ChemLab Processing";
	}


	@Override
	public List<String> handleItemTooltip(GuiRecipe arg0, ItemStack arg1,
			List<String> current, int arg3) {
		return current;
	}

	@Override
	public List<String> handleTooltip(GuiRecipe arg0, List<String> current,
			int arg2) {
		return current;
	}

	@Override
	public boolean hasOverlay(GuiContainer arg0, Container arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(GuiRecipe arg0, char arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseClicked(GuiRecipe arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numRecipes() {
		return filtered.length;
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	private void fillByPatternOut(Object object) {
		ItemStack result = (ItemStack) object;
		filtered = RecipeNEIIncapsulator.findAllByOutput(recipes,result);
	}
	private void fillByPatternIn(Object object) {
		ItemStack result = (ItemStack) object;
		filtered = RecipeNEIIncapsulator.findAllByInput(recipes,result);
	}	

	private MachineCrafterRecipes newInstance() {
		try {
			return getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ICraftingHandler getRecipeHandler(String outputId, Object... results) {
		MachineCrafterRecipes handler = newInstance();
		handler.fillByPatternOut(results[0]);
		return handler;
	}	
	
	@Override
	public IUsageHandler getUsageHandler(String outputId, Object... results) {
		MachineCrafterRecipes handler = newInstance();
		handler.fillByPatternIn(results[0]);
		return handler;		
	}

}
