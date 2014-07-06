package ru.itaros.hoe.data.utils;

public enum SynchroportOperationMode {

	UNIFORM,
	INPUT_ONLY,
	OUTPUT_ONLY,
	DISABLED;
	
	private String[] aliases={"U","I","O","X"};
	public String getAlias(){
		return aliases[this.ordinal()];
	}
	
	private int[] masks={0b11,0b10,0b01,0b00};
	public int getMask(){
		return masks[this.ordinal()];
	}
	
	
	public static final int INPUT_ENABLED=0b10;
	public static final int OUTPUT_ENABLED=0b01;
	
	
	public boolean canIn() {
		return (getMask()&INPUT_ENABLED)==INPUT_ENABLED;
	}
	public boolean canOut() {
		return (getMask()&OUTPUT_ENABLED)==OUTPUT_ENABLED;
	}	
	
}
