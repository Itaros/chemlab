package ru.itaros.hoe.utils.euclideanspace;

import net.minecraftforge.common.util.ForgeDirection;

public final class Transformations {

	public static final double BLOCK_CENTER=0.5D;
	
	public static final double RADIAL_SHIFT=(2*Math.PI/4)/2;//MC Block segmentation
	
	public static final ForgeDirection[] dirseqrad={
		ForgeDirection.WEST,
		ForgeDirection.SOUTH,
		ForgeDirection.EAST,
		ForgeDirection.NORTH,
		ForgeDirection.WEST
	};
	public static final double[] rangeLow={
		0,
		(2*Math.PI/4)/2,
		(2*Math.PI/4)/2+((2*Math.PI/4)*1),
		(2*Math.PI/4)/2+((2*Math.PI/4)*2),
		(2*Math.PI/4)/2+((2*Math.PI/4)*3)
	};
	public static final double[] rangeHigh={
		(2*Math.PI/4)/2,
		(2*Math.PI/4)/2+((2*Math.PI/4)*1),
		(2*Math.PI/4)/2+((2*Math.PI/4)*2),
		(2*Math.PI/4)/2+((2*Math.PI/4)*3),
		2*Math.PI
	};
	
	public static Vector3 getVectorFromBlock(int x, int y, int z){
		return new Vector3(x + BLOCK_CENTER, z + BLOCK_CENTER, y + BLOCK_CENTER);
	}
	
	public static Vector3 getRadialPoint(Vector3 center, Vector3 point){
		return new Vector3(
					point.getX()-center.getX(),
					point.getY()-center.getY(),
					point.getZ()-center.getZ()
				);
	}
	
	public static double getRadians(Vector3 point){
		double rads = Math.atan2(point.getY(), point.getX());//Fuck you java library
		if(rads<0){rads+=2*Math.PI;}
		return rads;
	}
	
	public static ForgeDirection getDirectionFromRadialPoint(Vector3 point){
		double rads = getRadians(point);
		
		//getting range
		int range=-1;
		for(int i = 0 ; i < rangeHigh.length; i++){
			if(rads<rangeHigh[i] && rads>rangeLow[i]){
				range=i;break;
			}
		}
		
		if(range!=-1){
			return dirseqrad[range];
		}else{
			return ForgeDirection.UNKNOWN;
		}
	}
	
	
}
