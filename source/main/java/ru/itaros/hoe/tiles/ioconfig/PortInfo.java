package ru.itaros.hoe.tiles.ioconfig;

import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.utils.EnumUtility;
import ru.itaros.hoe.utils.StackUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

/*
 * Port Info classes represent internal inventories AND information
 * Be careful!
 */
public final class PortInfo {

	public final static int amountOfPorts(){
		return 6;
	}
	
	private PortType type;
	private boolean isOutput;
	private boolean isEnabled=true;
	
	private Object stack;
	
	/*
	 * Stack Object must be ItemStack or FluidStack
	 */
	public PortInfo(PortType type, Object stack, boolean isOutput){
		//Validating
		if(stack!=null && !(stack instanceof ItemStack | stack instanceof FluidStack)){
			throw new IllegalArgumentException("Fluid or Item Stacks expected!");
		}
		//Assigning
		this.type=type;
		this.stack=stack;
		this.isOutput=isOutput;
	}
	
	//Queries
	public boolean isNothing(){
		return type==PortType.NOTHING;
	}
	public boolean isFluidSocket(){
		return type==PortType.FLUID;
	}
	public boolean isItemSocket(){
		return type==PortType.ITEM;
	}
	public boolean isPowerSocket(){
		return type==PortType.POWER;
	}
	public boolean isClosedSocket(){
		return type==PortType.CLOSED;
	}
	
	public boolean isOutput(){
		return isOutput;
	}
	public boolean isInput(){
		return !isOutput;
	}
	
	//Switches
	public void disable(){
		isEnabled=false;
	}
	public void enable(){
		isEnabled=true;
	}
	public void switchDirection(){
		isOutput=!isOutput;
	}

	
	public IIcon getIcon(IconsLibrary library) {
		if(library==null){return null;}
		
		if(isNothing()){
			return library.getNothing();
		}else if(isItemSocket()){
			return isInput()?library.getInputItem():library.getOutputItem();
		}else if(isFluidSocket()){
			return isInput()?library.getInputFluid():library.getOutputFluid();
		}else if(isPowerSocket()){
			return isInput()?library.getInputPower():library.getOutputPower();
		}else{
			return null;
		}
	}
	
	//Misc
	public PortType getType(){
		return type;
	}

	public static NBTTagCompound writeNBT(PortInfo pi) {
		NBTTagCompound item = new NBTTagCompound();
		if(pi!=null){
			item.setBoolean("isEnabled", pi.isEnabled);
			item.setBoolean("isOutput", pi.isOutput);
			EnumUtility.writeEnumValueImplicitly(item, pi.type, "type");
			if(pi.stack!=null){
				if(pi.stack instanceof ItemStack){
					item.setByte("st", (byte) 0x01);
					((ItemStack)pi.stack).writeToNBT(item);
				}else if(pi.stack instanceof FluidStack){
					item.setByte("st", (byte) 0x02);
					((FluidStack)pi.stack).writeToNBT(item);
				}
			}else{
				item.setByte("st", (byte) 0x00);
			}
		}
		return item;
	}

	public static PortInfo readNBT(NBTTagCompound nbt) {
		PortType type = EnumUtility.readEnumValueImplicitly(PortType.class, nbt, "type");
		
		if(type==PortType.NOIDENTITY){return null;}
		
		byte dataid = nbt.getByte("st");
		Object stack=null;
		switch(dataid){
		case 0x00:
			//No data
			break;
		case 0x01:
			stack=ItemStack.loadItemStackFromNBT(nbt);
			break;
		case 0x02:
			stack=FluidStack.loadFluidStackFromNBT(nbt);
			break;
		}
		
		
		boolean isEnabled = nbt.getBoolean("isEnabled");
		boolean isOutput = nbt.getBoolean("isOutput");
		
		PortInfo retr = new PortInfo(type,stack,isOutput);
		retr.isEnabled=isEnabled;
		
		return retr;
	}

	public void setStack(ItemStack stack) {
		this.stack=stack;
	}
	public void setStack(FluidStack stack) {
		this.stack=stack;
	}
	
	
	public Object getStack() {
		return stack;
	}


	
}
