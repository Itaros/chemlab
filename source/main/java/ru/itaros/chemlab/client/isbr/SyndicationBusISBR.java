package ru.itaros.chemlab.client.isbr;

import org.lwjgl.opengl.GL11;

import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationBus;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class SyndicationBusISBR extends ItarosISBR implements ISimpleBlockRenderingHandler {

	public static final double AXIAL_CENTER = 0.5D;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		
		IIcon icon = block.getIcon(0, 0);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, -0D, POSITIVE_DIAMETER, POSITIVE_DIAMETER, +1D);
		renderer.uvRotateTop=1;
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);		
		
		Tessellator.instance.draw();
		
		
		
	}

	public static final double radius=6;//minecraft px
	public static final double aperture = radius/16D/2D;
	private static final double POSITIVE_DIAMETER = AXIAL_CENTER+aperture;
	private static final double NEGATIVE_DIAMETER = AXIAL_CENTER-aperture;
	private static final double POSITIVE_DIAMETER_CAP = AXIAL_CENTER+aperture+0.025D;
	private static final double NEGATIVE_DIAMETER_CAP = AXIAL_CENTER-aperture-0.025D;	
	
	//										--**EWSNDU
	public static final byte IS_DOWN	=	0b00000001;	
	public static final byte IS_UP		=	0b00000010;
	public static final byte IS_N		=	0b00000100;
	public static final byte IS_S		=	0b00001000;
	public static final byte IS_W		=	0b00010000;
	public static final byte IS_E		=	0b00100000;
	public static final byte IS_NONE	=	0b00000000;
	public static final byte IS_ALL		=	0b00111111;
	
	public static final byte IS_O_UPDOWN=	IS_UP | IS_DOWN;
	public static final byte IS_O_NS	=	IS_N  | IS_S;
	public static final byte IS_O_WE	=	IS_W  | IS_E;

	public static final byte[] SRSLT = {IS_UP,IS_DOWN,IS_N,IS_S,IS_W,IS_E};
	
	
	protected void setVerticalRendering(RenderBlocks renderer){
		renderer.uvRotateEast=1;
		renderer.uvRotateNorth=1;
		renderer.uvRotateSouth=1;
		renderer.uvRotateWest=1;
	}
	protected void setNormalRenderingMode(RenderBlocks renderer){
		renderer.uvRotateEast=0;
		renderer.uvRotateNorth=0;
		renderer.uvRotateSouth=0;
		renderer.uvRotateWest=0;
		
		renderer.uvRotateTop=0;
		renderer.uvRotateBottom=0;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		//renderer.setRenderAllFaces(true);
		
		boolean isNotConnected=true;
		
		boolean hasAngularConnections = false;
		short straightConnections=0;
		
		byte connectionMask = getStraightConnections(world,x,y,z);
		
		SyndicationBus busblock = (SyndicationBus)block;
		
		int meta = world.getBlockMetadata(x, y, z);
		IIcon torender = busblock.getIcon(0, meta);
		
		renderer.setOverrideBlockTexture(torender);
		
		//busblock.setCulling((byte) (IS_ALL & connectionMask));
		
		busblock.setOpaqueHack(true);
		
		busblock.setCulling(IS_O_UPDOWN);
		setVerticalRendering(renderer);
		if((connectionMask & IS_O_UPDOWN)  == IS_O_UPDOWN){
			isNotConnected=false;
			straightConnections++;
			renderer.setRenderBounds(NEGATIVE_DIAMETER, -0D, NEGATIVE_DIAMETER, POSITIVE_DIAMETER,+1D, POSITIVE_DIAMETER);
			renderer.renderStandardBlock(block,x,y,z);	
			
		}else{
			if((connectionMask & IS_UP)  == IS_UP){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_DOWN);
				renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, POSITIVE_DIAMETER,+1D, POSITIVE_DIAMETER);
				renderer.renderStandardBlock(block,x,y,z);				
			}
			if((connectionMask & IS_DOWN)  == IS_DOWN){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_UP);
				renderer.setRenderBounds(NEGATIVE_DIAMETER, -0D, NEGATIVE_DIAMETER, POSITIVE_DIAMETER,POSITIVE_DIAMETER, POSITIVE_DIAMETER);
				renderer.renderStandardBlock(block,x,y,z);					
			}			
		}
		setNormalRenderingMode(renderer);
		busblock.setCulling(IS_O_NS);
		renderer.uvRotateTop=1;
		renderer.uvRotateBottom=1;
		if((connectionMask & IS_O_NS)  == IS_O_NS){
			isNotConnected=false;
			straightConnections++;
			renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, -0D, POSITIVE_DIAMETER, POSITIVE_DIAMETER, +1D);
			renderer.renderStandardBlock(block,x,y,z);	
		}else{
			if((connectionMask & IS_N)  == IS_N){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_S);
				renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, POSITIVE_DIAMETER, POSITIVE_DIAMETER, +1D);
				renderer.renderStandardBlock(block,x,y,z);					
			}
			if((connectionMask & IS_S)  == IS_S){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_N);
				renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, -0D, POSITIVE_DIAMETER, POSITIVE_DIAMETER, POSITIVE_DIAMETER);
				renderer.renderStandardBlock(block,x,y,z);					
			}
		}
		busblock.setCulling(IS_O_WE);
		renderer.uvRotateTop=0;
		renderer.uvRotateBottom=0;
		if((connectionMask & IS_O_WE)  == IS_O_WE){
			isNotConnected=false;
			straightConnections++;
			renderer.setRenderBounds(-0D, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, +1D, POSITIVE_DIAMETER, POSITIVE_DIAMETER);
			renderer.renderStandardBlock(block,x,y,z);	
		}else{
			if((connectionMask & IS_W)  == IS_W){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_E);
				renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, +1D, POSITIVE_DIAMETER, POSITIVE_DIAMETER);
				renderer.renderStandardBlock(block,x,y,z);					
			}
			if((connectionMask & IS_E)  == IS_E){
				isNotConnected=false;
				hasAngularConnections=true;
				busblock.setCulling(IS_W);
				renderer.setRenderBounds(-0D, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER, POSITIVE_DIAMETER, POSITIVE_DIAMETER, POSITIVE_DIAMETER);
				renderer.renderStandardBlock(block,x,y,z);					
			}
		}		
		
		
		busblock.setCulling(IS_NONE);
		setNormalRenderingMode(renderer);
		
		
		if(hasAngularConnections || isNotConnected || straightConnections==2){
			renderer.setRenderBounds(NEGATIVE_DIAMETER_CAP, NEGATIVE_DIAMETER_CAP, NEGATIVE_DIAMETER_CAP, POSITIVE_DIAMETER_CAP, POSITIVE_DIAMETER_CAP, POSITIVE_DIAMETER_CAP);
			renderer.renderStandardBlock(block,x,y,z);
		}
		
		
		busblock.setOpaqueHack(false);
		renderer.clearOverrideBlockTexture();
		//renderer.
		
		return true;
	}

	private byte getStraightConnections(IBlockAccess world, int x, int y, int z) {
		byte flags = IS_NONE;
		//TODO://what if y is 0 or 255(256)?
		//checking UPDOWN
		if(isConnectable(world,x,y-1,z)){
			flags|=IS_DOWN;
		}
		if(isConnectable(world,x,y+1,z)){
			flags|=IS_UP;
		}		
		if(isConnectable(world,x,y,z+1)){
			flags|=IS_N;
		}				
		if(isConnectable(world,x,y,z-1)){
			flags|=IS_S;
		}		
		if(isConnectable(world,x+1,y,z)){
			flags|=IS_W;
		}			
		if(isConnectable(world,x-1,y,z)){
			flags|=IS_E;
		}					
		
		
		return flags;
	}
	
	private boolean isConnectable(IBlockAccess world, int x, int y, int z){
		return world.getBlock(x, y, z) instanceof ISyndicationPipeConnectable;
	}
	

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	

}
