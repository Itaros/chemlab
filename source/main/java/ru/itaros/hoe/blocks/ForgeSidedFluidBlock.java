package ru.itaros.hoe.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import ru.itaros.hoe.fluid.FluidToHOE;
import ru.itaros.hoe.fluid.HOEFluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ForgeSidedFluidBlock extends BlockFluidFinite {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;	
	
	private HOEFluid fnative;
	
	public ForgeSidedFluidBlock(Fluid fluid) {
		super(fluid, Material.water);
		
		fnative = FluidToHOE.get(fluid);
		
		this.setTickRate(20);
		
		this.setRenderPass(1);//Transparent
		
		this.setBlockName(fluid.getUnlocalizedName());
	}

	@Override
	public void registerBlockIcons(IIconRegister registry) {
		String texname = fnative.getUnlocalizedName();
        stillIcon = registry.registerIcon("chemlab:"+texname);
        flowingIcon = registry.registerIcon("chemlab:"+texname);
        
        getFluid().setIcons(stillIcon, flowingIcon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return flowingIcon;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(getFluid().isGaseous()){
			int quantaRemaining = this.getQuantaValue(world, x, y, z);
			quantaRemaining-=quantaPerBlock/3;
			if(quantaRemaining>0){
				world.setBlockMetadataWithNotify(x, y, z, quantaRemaining-1, 2);
			}else{
				world.setBlockToAir(x, y, z);
				return;
			}
		}
		super.updateTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x,
			int y, int z, Random r) {
		if(getFluid().isGaseous()){
			if(world.isRemote){
				for(int i = 0; i<3;i++){
					net.minecraft.client.particle.EntityFX effect = new net.minecraft.client.particle.EntityBubbleFX(world, x+r.nextDouble(), y+0.1D, z+r.nextDouble(), 0.0D, 0.1D+r.nextDouble(), 0.0D);
					Minecraft.getMinecraft().effectRenderer.addEffect(effect);
				}
			}			
		}
	}
	
}
