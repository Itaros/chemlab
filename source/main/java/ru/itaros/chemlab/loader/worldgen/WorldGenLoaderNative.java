package ru.itaros.chemlab.loader.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import ru.itaros.chemlab.Config;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.signatures.IAllowedConfigArgument;
import ru.itaros.hoe.toolkit.worldgen.GenDistributionProvider;
import ru.itaros.hoe.toolkit.worldgen.GenDistributionProviderScattered;
import ru.itaros.hoe.toolkit.worldgen.GenDistributionProviderSurface;
import ru.itaros.hoe.toolkit.worldgen.WorldGenOverlay;
import ru.itaros.hoe.toolkit.worldgen.WorldGenOverlayBiome;
import ru.itaros.hoe.toolkit.worldgen.WorldGenOverlayRange;
import ru.itaros.hoe.toolkit.worldgen.WorldGenOverlayRangeLimestone;
import ru.itaros.hoe.vanilla.registries.WorldGenRegistry;

public class WorldGenLoaderNative implements IAllowedConfigArgument {
	
	
	private static final float NOISEDOWNSAMPLE_HALITE = 0.5F;
	private static final float NOISEDOWNSAMPLE_ASBESTOS = 0.04F;
	private static final float NOISEDOWNSAMPLE_LIMESTONE = 0.04F;
	private static final float NOISEDOWNSAMPLE_HEMATITE = 0.1F;
	private static final float NOISEDOWNSAMPLE_PERICLASE = 0.2F;
	private static final float NOISEDOWNSAMPLE_PLATINUM = 0.3F;
	private static final float NOISEDOWNSAMPLE_METAANTHRACITE = 0.7F;
	private static final float NOISEDOWNSAMPLE_PYRITE = 0.9F;
	
	private static WorldGenRegistry registry;
	
	
	private static GenDistributionProvider haliteDisProvider;
	private static GenDistributionProvider platinumDisProvider;
	private static GenDistributionProvider metaAnthraciteDisProvider;
	private static GenDistributionProvider pyriteProvider;
	private static GenDistributionProvider periclaseProvider;
	
	private static WorldGenOverlay asbestos_serpentite;
	private static WorldGenOverlay hematite;
	private static WorldGenOverlay limestone;
	private static WorldGenOverlay halite;
	private static WorldGenOverlay platinum;
	private static WorldGenOverlay metaAnthracite;
	private static WorldGenOverlay pyrite;
	private static WorldGenOverlay periclase;
	
	
	public void execute(){
		registry=HOE.getInstance().getRegistryWorldGen();
		
		HOE.getInstance().getHOEWorldGenerator().setAllowedDims(Config.worldgenerator_allowedDims);
		
		
		asbestos_serpentite=new WorldGenOverlay("serpentite", Blocks.stone , BlockLoader.asbestos_serpentite, 18, NOISEDOWNSAMPLE_ASBESTOS, 425L);
		registry.registerOverlay(asbestos_serpentite);
		
		hematite=new WorldGenOverlay("hematite", Blocks.stone ,BlockLoader.oreHematite, 18.5F ,NOISEDOWNSAMPLE_HEMATITE,657L);
		registry.registerOverlay(hematite);
		
		limestone = new WorldGenOverlayRangeLimestone("limestone", Blocks.stone, BlockLoader.oreLimestone,5,8,NOISEDOWNSAMPLE_LIMESTONE,789L);
		registry.registerOverlay(limestone);
		
		haliteDisProvider = new GenDistributionProviderSurface(3, 1F, 3F,false);
		halite = new WorldGenOverlay("halite",Blocks.sand,BlockLoader.oreHalite,0,NOISEDOWNSAMPLE_HALITE,348L);
		halite.setDestributorProvider(haliteDisProvider);
		registry.registerOverlay(halite);
		
		platinumDisProvider = new GenDistributionProviderSurface(5, 1F, 3F,true);
		platinumDisProvider.setOptionalBiomeRestriction(BiomeGenBase.river);
		platinum = new WorldGenOverlay("platinum",Blocks.stone,BlockLoader.orePlatinum,5F,NOISEDOWNSAMPLE_PLATINUM,627L);
		platinum.setDestributorProvider(platinumDisProvider);
		registry.registerOverlay(platinum);
		
		metaAnthraciteDisProvider = new GenDistributionProviderScattered(20, 20, 1F, 14, 7389L, 6);
		metaAnthracite = new WorldGenOverlay("metaAnthracite", Blocks.stone, BlockLoader.oreMetaAnthracite,10F,NOISEDOWNSAMPLE_METAANTHRACITE,399L);
		metaAnthracite.setDestributorProvider(metaAnthraciteDisProvider);
		registry.registerOverlay(metaAnthracite);
		
		pyriteProvider = new GenDistributionProviderScattered(20, 5, 1F, 6, 9135L, 17);
		pyrite = new WorldGenOverlay("pyrite", Blocks.stone, BlockLoader.orePyrite,10F,NOISEDOWNSAMPLE_PYRITE,216L);
		pyrite.setDestributorProvider(pyriteProvider);
		registry.registerOverlay(pyrite);
		
		periclaseProvider = new GenDistributionProviderScattered(20, 5, 1F, 6, 9135L, 3);
		periclase = new WorldGenOverlay("periclase", Blocks.stone, BlockLoader.orePericlase,10F,NOISEDOWNSAMPLE_PERICLASE,994L);
		periclase.setDestributorProvider(periclaseProvider);
		registry.registerOverlay(periclase);		
		
		
	}
	
	
}
