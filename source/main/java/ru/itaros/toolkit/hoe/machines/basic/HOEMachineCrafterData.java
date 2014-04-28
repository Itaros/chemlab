package ru.itaros.toolkit.hoe.machines.basic;





import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import ru.itaros.api.hoe.exceptions.HOEWrongSyncDirection;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HOEMachineCrafterData extends HOEMachineData{
	private int incoming_slots,outcoming_slots;
	//ALIGNED DATA
	private int[] incoming_depot;
	private int[] outcoming_depot;
	
	private ItemStack[] incoming_stricttype;//HARDBIBNDED
	private ItemStack[] outcoming_stricttype;//HARDBIBNDED	
	
	
	public boolean isRecipeSet=false;	
	protected void setRecipeFlag(boolean flag){
		isRecipeSet=flag;
	}		
	private Recipe recipe;
	//END OF ALIGNED DATA
	
	
	/*
	 * NBT Constructor and generational constructor
	 */
	public HOEMachineCrafterData(){
		super();
		this.isSided=false;
	
	}
	/*
	 * Reflection autocaster
	 */
	public HOEMachineCrafterData(HOEData parent){
		super(parent);
	}
	
	
	public void setDepots(int incoming, int outcoming){
		incoming_slots=incoming;
		outcoming_slots=outcoming;
	}

	@Override
	protected void bindChildToParent(HOEMachineData parent){
		HOEMachineCrafterData hmcd = (HOEMachineCrafterData)parent;
		//Linking strict types. Believed to be Read-Only
		this.incoming_stricttype=hmcd.incoming_stricttype;
		this.outcoming_stricttype=hmcd.outcoming_stricttype;
		//Mimicking structure
		this.incoming_slots=hmcd.incoming_slots;
		this.outcoming_slots=hmcd.outcoming_slots;
		this.incoming_depot=new int[incoming_slots];
		this.outcoming_depot=new int[outcoming_slots];
		//Mimicking recipe
		this.recipe=hmcd.recipe;
	}
	
	
	@Override
	public void sync() {
		super.sync();
		HOEMachineCrafterData childd=(HOEMachineCrafterData) child;
		childd.incoming_depot=incoming_depot;
		childd.outcoming_depot=outcoming_depot;
		
		childd.incoming_stricttype=incoming_stricttype.clone();
		childd.outcoming_stricttype=outcoming_stricttype.clone();
		
	}

	@Override
	protected void init(){
		incoming_depot=new int[incoming_slots];
		outcoming_depot=new int[outcoming_slots];
		incoming_stricttype=new ItemStack[incoming_slots];
		outcoming_stricttype=new ItemStack[outcoming_slots];
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
	@Override
	public void writeNBT(NBTTagCompound nbt) {
		super.writeNBT(nbt);
		//Slot allocators cfg
		nbt.setInteger("incoming_slots", incoming_slots);	
		nbt.setInteger("outcoming_slots", outcoming_slots);	

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
	@Override
	public void readNBT(NBTTagCompound nbt) {
		super.readNBT(nbt);
		//Slot allocators cfg
		incoming_slots=nbt.getInteger("incoming_slots");	
		outcoming_slots=nbt.getInteger("outcoming_slots");	
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
	public void incrementProduction() {
		if(recipe==null){return;}
		recipe.incrementProduction(this);
	}
	public boolean decrementResources() {
		if(recipe==null){return false;}
		//Here we check if it is possible to produce something with those available resources
		if(recipe.checkResources(this) && HOEDataStateCheck()){
			recipe.consumeResources(this);
			HOEDataUpdateState();
			return true;//There are enough resources and run is completed
		}else{
			return false;//Not enough resources
		}
	}
	/*
	 * Override it to provide special requirements to run production process
	 */
	protected boolean HOEDataStateCheck(){
		return true;
	}
	/*
	 * Override it to provide requirements change after each production cycle
	 */
	protected void HOEDataUpdateState(){
		;
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
			if(incoming_depot[x]<=temp.getMaxStackSize()){
				Item originType=incoming_stricttype[x].getItem();
				Item sourceType=temp.getItem();
				int originMeta = incoming_stricttype[x].getItemDamage();
				int sourceMeta = temp.getItemDamage();
				if(originType==sourceType && originMeta==sourceMeta){
				//if(incoming_stricttype[x].getUnlocalizedName().equals(temp.getItem().getUnlocalizedName())){
					
					int diff=temp.getMaxStackSize()-incoming_depot[x];
					if(diff==0){return false;}
					incoming_depot[x]+=diff;
					temp.stackSize-=diff;
					return true;
				}
			}
		}
		
		
		return false;
	}	
	public ItemStack pullProduct(ItemStack outbound_synchro) {
		Item reqtype=null;
		int reqmeta=0;
		int max=64;
		if(outbound_synchro!=null){
			reqtype=outbound_synchro.getItem();
			reqmeta=outbound_synchro.getItemDamage();
			max = 64-outbound_synchro.stackSize;
			if(max<=0){return outbound_synchro;}
		}
		for(int x = 0; x < outcoming_stricttype.length; x++){
			if(outcoming_stricttype[x]==null){continue;}
			
			if(outcoming_depot[x]>0){
				ItemStack product = outbound_synchro;
				if(product==null){
					product = outcoming_stricttype[x].copy();product.stackSize=outcoming_depot[x];
					outcoming_depot[x]-=product.stackSize;//Allowing RaceConditions!
				}else{
					
					if((outcoming_stricttype[x].getItem()!=reqtype & outcoming_stricttype[x].getItemDamage()==reqmeta)){continue;}//Do not try to shift physical form of items, lol
					
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
			ItemStack rslt = incoming_stricttype[0].copy();rslt.stackSize=incoming_depot[0];
			return rslt;
		}else{
			return null;
		}
	}
	public ItemStack getOutboundRO() {
		if(outcoming_stricttype!=null && outcoming_stricttype.length>0 && outcoming_stricttype[0]!=null){
			ItemStack rslt = outcoming_stricttype[0].copy();rslt.stackSize=outcoming_depot[0];
			return rslt;
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
	
	public ItemStack getStricttypeByIndex(int injectorTypeOffset) {
		if(injectorTypeOffset>=incoming_stricttype.length){
			return null;
		}else{
			return incoming_stricttype[injectorTypeOffset];
		}
	}	


	
}
