package ru.itaros.hoe.utils.euclideanspace;

public class Vector3 {

	private double x,y,z;

	public Vector3(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public final double getX() {
		return x;
	}

	public final void setX(double x) {
		this.x = x;
	}

	public final double getY() {
		return y;
	}

	public final void setY(double y) {
		this.y = y;
	}

	public final double getZ() {
		return z;
	}

	public final void setZ(double z) {
		this.z = z;
	}
	
	
	
}
