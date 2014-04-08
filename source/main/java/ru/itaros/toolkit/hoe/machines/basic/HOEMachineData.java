package ru.itaros.toolkit.hoe.machines.basic;





import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import ru.itaros.api.hoe.exceptions.HOEWrongSyncDirection;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HOEMachineData extends HOEData{
	private boolean isSided;//Is it residing in MTA?
	
	private int incoming_slots,outcoming_slots;
	//ALIGNED DATA
	private int[] incoming_depot;
	private int[] outcoming_depot;
	
	private Item[] incoming_stricttype;//HARDBIBNDED
	private Item[] outcoming_stricttype;//HARDBIBNDED
	
	private int power;
	private int maxpower;
	public int getPower() {
		return power;
	}
	public int getPowerMax(){
		return maxpower;
	}
	
	public int ticksRequared;
	public boolean isRecipeSet=false;	
	protected void setRecipeFlag(boolean flag){
		isRecipeSet=flag;
	}		
	private int ticksAccumulated=0;
	public int getTicks(){
		return ticksAccumulated;
	}
	public void incrementTick(){
		ticksAccumulated++;
	}
	public void voidTicks(){
		ticksAccumulated=0;
	}	
	
	private HOEMachineIO io;
	
	private Recipe recipe;
	//END OF ALIGNED DATA
	
	
	public boolean isSided(){
		return isSided;
	}
	
	/*
	 * NBT Constructor and generational constructor
	 */
	public HOEMachineData(){
		this.isSided=false;
		

		
	}
	
	
	public void setMaxPower(int maxpower){
		this.maxpower=maxpower;
	}
	public void setDepots(int incoming, int outcoming){
		incoming_slots=incoming;
		outcoming_slots=outcoming;
	}
	public void setMachine(HOEMachineIO io){
		this.io=io;
	}
	

	private boolean isConfigured=false;
	public void setConfigured(){
		init();
		child=spawnChild();
		sync();
		isConfigured=true;
	}
	public boolean isConfigured(){
		return isConfigured;
	}
	
	
	public HOEMachineData(HOEData parent){
		this((HOEMachineData)parent);
		//Reflection autocaster
	}
	protected HOEMachineData(HOEMachineData parent){
		this.isSided=true;
		bindChildToParent(parent);
	}
	private void bindChildToParent(HOEMachineData parent){
		//Linking strict types. Believed to be Read-Only
		this.incoming_stricttype=parent.incoming_stricttype;
		this.outcoming_stricttype=parent.outcoming_stricttype;
		//Mimicking structure
		this.incoming_slots=parent.incoming_slots;
		this.outcoming_slots=parent.outcoming_slots;
		this.incoming_depot=new int[incoming_slots];
		this.outcoming_depot=new int[outcoming_slots];
		//Mimicking recipe
		this.recipe=parent.recipe;
	}
	
	
	public void sync() {
		if(child==null){throw new HOEWrongSyncDirection();}
		HOEMachineData childd=(HOEMachineData) child;
		childd.incoming_depot=incoming_depot;
		childd.outcoming_depot=outcoming_depot;
		
		childd.incoming_stricttype=incoming_stricttype.clone();
		childd.outcoming_stricttype=outcoming_stricttype.clone();
		
	}

	private void init(){
		incoming_depot=new int[incoming_slots];
		outcoming_depot=new int[outcoming_slots];
		incoming_stricttype=new Item[incoming_slots];
		outcoming_stricttype=new Item[outcoming_slots];
	}

	public HOEMachineIO getIO() {
		return io;
	}
	public int incrementPower(int i) {
		power+=i;
		if(power>maxpower){
			int overflow = power-maxpower;
			return overflow;
		}
		return 0;
	}
	
	
	public void setRecipe(Recipe recipe){
		if(recipe!=null){
			applyRecipeParametrics(recipe);
			this.recipe=recipe;
			this.isRecipeSet=true;
			sync();
		}else{
			//TODO: Null recipe(recipe remover)
		}
	}
	
	private void applyRecipeParametrics(Recipe recipe){
		if(recipe!=null){
					//TODO: recipe should do that on its own. Polymorphism, fuck that
			if(recipe instanceof FixedConversionRecipe){
				FixedConversionRecipe fcr = (FixedConversionRecipe)recipe;
				ticksRequared=fcr.getTicksRequared();
			}
			
			incoming_stricttype=recipe.getIncomingStricttypes();
			outcoming_stricttype=recipe.getOutcomingStricttypes();
		}else{
			//TODO: Null recipe(recipe remover)
		}
	}
	
	
	
	//NBT
	public void writeNBT(NBTTagCompound nbt) {
		//Slot allocators cfg
		nbt.setInteger("incoming_slots", incoming_slots);	
		nbt.setInteger("outcoming_slots", outcoming_slots);	
		//Power
		nbt.setInteger("power", power);
		nbt.setInteger("maxpower", maxpower);
		//Ticks
		nbt.setInteger("ticksRequared", ticksRequared);
		nbt.setInteger("ticksAccumulated", ticksAccumulated);
		//IO recognition Signature
		if(io!=null){
			nbt.setString("io_sign", io.getClass().getName());
		}
		//Slots Itself
		nbt.setIntArray("incoming_depot", incoming_depot);
		nbt.setIntArray("outcoming_depot", outcoming_depot);
		//Types are configured by recipe to reduce network congestion
		String reptoken = "";
		if(recipe!=null){
			reptoken = recipe.getName();
		}
		nbt.setString("reptoken", reptoken);
	}
	public void readNBT(NBTTagCompound nbt) {
		//Slot allocators cfg
		incoming_slots=nbt.getInteger("incoming_slots");	
		outcoming_slots=nbt.getInteger("outcoming_slots");	
		//Power
		power=nbt.getInteger("power");
		maxpower=nbt.getInteger("maxpower");
		//FMLLog.log(Level.INFO, "Maxpower was:"+maxpower);
		//Ticks
		ticksRequared=nbt.getInteger("ticksRequared");
		ticksAccumulated=nbt.getInteger("ticksAccumulated");
		//IO recognition Signature
		String ioname = nbt.getString("io_sign");
		io = (HOEMachineIO) HOEIO.getIORegistry().get(ioname);
		//Slots Itself
		incoming_depot=nbt.getIntArray("incoming_depot");
		outcoming_depot=nbt.getIntArray("outcoming_depot");
		//Types are configured by recipe to reduce network congestion		
		IHOERecipeRegistry repreg = Recipe.getRecipeRegistry();
		String reptoken = nbt.getString("reptoken");
		recipe=repreg.get(reptoken);
		unfoldStricttypesByRecipe();
	}	
	/*
	 * Client method to slightly lower network GUI congestion
	 */
	private void unfoldStricttypesByRecipe() {
		applyRecipeParametrics(recipe);
		if(recipe!=null){isRecipeSet=true;}else{isRecipeSet=false;}
	}
	public static HOEMachineData generateFromNBT(NBTTagCompound nbt){
		HOEMachineData data = new HOEMachineData();
		data.readNBT(nbt);
		return data;
	}
	public void incrementProduction() {
		if(recipe==null){return;}
		recipe.incrementProduction(this);
	}
	public boolean decrementResources() {
		if(recipe==null){return false;}
		//Here we check if it is possible to produce something with those available resources
		if(recipe.checkResources(this)){
			recipe.consumeResources(this);
			return true;//There are enough resources and run is completed
		}else{
			return false;//Not enough resources
		}
	}
	public boolean checkStorage() {
		//Checking storage capabilities
		if(recipe==null){return false;}//No storage if there is no recipe
		return recipe.checkStorage(this);
	}

	public boolean pushResource(ItemStack temp) {
		if(temp==null){return false;}
		for(int x = 0; x < incoming_stricttype.length; x++){
			if(incoming_stricttype[x]==null){continue;}
			if(incoming_depot[x]<64){
				if(incoming_stricttype[x].getUnlocalizedName().equals(temp.getItem().getUnlocalizedName())){
					incoming_depot[x]+=temp.stackSize;
					return true;
				}
			}
		}
		
		
		return false;
	}	
	public ItemStack pullProduct(ItemStack outbound_synchro) {
		Item reqtype=null;
		int max=64;
		if(outbound_synchro!=null){
			reqtype=outbound_synchro.getItem();
			max = 64-outbound_synchro.stackSize;
			if(max<=0){return outbound_synchro;}
		}
		for(int x = 0; x < outcoming_stricttype.length; x++){
			if(outcoming_stricttype[x]==null){continue;}
			
			if(outcoming_depot[x]>0){
				ItemStack product = outbound_synchro;
				if(product==null){
					product = new ItemStack(outcoming_stricttype[x],outcoming_depot[x]);
					outcoming_depot[x]-=product.stackSize;//Allowing RaceConditions!
				}else{
					
					if(outcoming_stricttype[x]!=reqtype){continue;}//Do not try to shift physical form of items, lol
					
					if(outcoming_depot[x]<=max){
						max=outcoming_depot[x];
					}else{
						max=max;//LOL, dummy to remind me of something I already forgot
					}
					outcoming_depot[x]-=max;//Allowing RaceConditions!
					product.stackSize+=max;
				}
				
				return product;
			}else{
				//Nothing to pull
				continue;
				//return outbound_synchro;
			}
		}
		//Nothing to pull from full assortment
		return outbound_synchro;
	}
	
	
	//TODO: Pass originals to modify them for performance
	public ItemStack getInboundRO() {
		if(incoming_stricttype!=null && incoming_stricttype.length>0 && incoming_stricttype[0]!=null){
			return new ItemStack(incoming_stricttype[0],incoming_depot[0]);
		}else{
			return null;
		}
	}
	public ItemStack getOutboundRO() {
		if(outcoming_stricttype!=null && outcoming_stricttype.length>0 && outcoming_stricttype[0]!=null){
			return new ItemStack(outcoming_stricttype[0],outcoming_depot[0]);
		}else{
			return null;
		}
	}
	public String getInboundAmount() {
		if(incoming_depot!=null && incoming_depot.length>0){
			return String.valueOf(incoming_depot[0]);
		}else{
			return "N/A";
		}
	}
	public String getOutboundAmount() {
		if(outcoming_depot!=null && outcoming_depot.length>0){
			return String.valueOf(outcoming_depot[0]);
		}else{
			return "N/A";
		}		
	}
	
	//Storage
	
	public int getOutboundAmountByIndex(int index) {
		if(index<outcoming_depot.length){
			return outcoming_depot[index];
		}else{
			return 500;//INVALID INDEX TOCKEN
		}
	}
	public int getInboundAmountByIndex(int index) {
		if(index<incoming_depot.length){
			return incoming_depot[index];
		}else{
			return -500;//INVALID INDEX TOCKEN
		}		
	}
	public void decrementInboundAmountByIndex(int index, int amount) {
		if(index<incoming_depot.length){
			incoming_depot[index]-=amount;
		}else{
			//TODO: Exception?
		}	
	}
	public void incrementOutboundAmountByIndex(int index, int amount) {
		if(index<outcoming_depot.length){
			outcoming_depot[index]+=amount;
		}else{
			//TODO: Exception?
		}
		
	}	


	
}
