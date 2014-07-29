package ru.itaros.toolkit.hoe.machines.basic;





import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.toolkit.data.descriptor.IHOEMultiInventoryMachine;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackTransferTuple;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import ru.itaros.toolkit.hoe.machines.interfaces.ISynchroportItems;

public class HOEMachineCrafterData extends HOEMachineData implements IHOEMultiInventoryMachine, ISynchroportItems{
	private int incoming_slots,outcoming_slots;
	//ALIGNED DATA
	private ItemStack[] inbound;
	private ItemStack[] outbound;
	
	
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
		super.bindChildToParent(parent);
		HOEMachineCrafterData hmcd = (HOEMachineCrafterData)parent;
		//Mimicking recipe
		this.recipe=hmcd.recipe;
		
		//copying slot indexation
		this.incoming_slots=hmcd.incoming_slots;
		this.outcoming_slots=hmcd.outcoming_slots;
	}
	
	
	@Override
	public void sync() {
		super.sync();
		HOEMachineCrafterData childd=(HOEMachineCrafterData) child;

		childd.hasWork=hasWork;
		
		childd.inbound = StackUtility.syncItemStacks(childd.inbound, inbound);
		childd.outbound = StackUtility.syncItemStacks(childd.outbound, outbound);
		
	}

	@Override
	protected void init(){
		inbound=new ItemStack[incoming_slots];
		outbound=new ItemStack[outcoming_slots];
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
		}else{
			//TODO: Null recipe(recipe remover)
		}
	}
	
	//NBT
	@Override
	protected void readConfigurationNBT(NBTTagCompound nbt) {
		//Types are configured by recipe to reduce network congestion		
		IHOERecipeRegistry repreg = Recipe.getRecipeRegistry();
		String reptoken = nbt.getString("reptoken");
		recipe=repreg.get(reptoken);
		unfoldStricttypesByRecipe();		
		super.readConfigurationNBT(nbt);
	}
	@Override
	protected void writeConfigurationNBT(NBTTagCompound nbt) {
		//Types are configured by recipe to reduce network congestion
		String reptoken = "";
		if(recipe!=null){
			reptoken = recipe.getName();
		}
		nbt.setString("reptoken", reptoken);
		super.writeConfigurationNBT(nbt);
	}
	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
		inbound=StackUtility.readItemStacksFromNBT(inbound,nbt, "initem");
		outbound=StackUtility.readItemStacksFromNBT(outbound,nbt, "outitem");
	}
	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
		StackUtility.writeItemStacksToNBT(inbound, nbt, "initem");
		StackUtility.writeItemStacksToNBT(outbound, nbt, "outitem");
	}
	/*
	 * Client method to slightly lower network GUI congestion
	 */
	private void unfoldStricttypesByRecipe() {
		applyRecipeParametrics(recipe);
		if(recipe!=null){isRecipeSet=true;}else{isRecipeSet=false;}
	}
	
	
	public boolean useEnergy() {
		if(recipe==null){return false;}
		return recipe.tryToConsumeEnergy(this);
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
	
	
	
	protected boolean hasWork;
	private int refreshSpin=0;
	private static final int SPIN_THRESHOLD=20-1;
	public boolean hasWork(){
		if(hasWork){
			return true;
		}else{
			refreshSpin++;
			if(refreshSpin>SPIN_THRESHOLD){
				hasWork=checkWork();
				refreshSpin=0;
			}
			return false;
		}
	}	


	public int inSize(){
		return incoming_slots;
	}
	public int outSize(){
		return outcoming_slots;
	}	
	public ItemStack get_in_withRecipe(int i){
		if(!isSided || recipe==null){
			//Normal Operation
			return get_in(i);
		}else{
			ItemStack current = inbound[i];
			if(current!=null){return current;}
			//Try to show recipe
			ItemStack[] stricts = recipe.getNormalziedIncomingStricttypes();
			if(!(i<stricts.length)){
				return null;
			}else{
				return stricts[i];
			}
		}		
	}
	public ItemStack get_out_withRecipe(int i){
		if(!isSided || recipe==null){
			//Normal Operation
			return get_out(i);
		}else{
			ItemStack current = outbound[i];
			if(current!=null){return current;}
			//Try to show recipe
			ItemStack[] stricts = recipe.getNormalziedOutcomingStricttypes();
			if(!(i<stricts.length)){
				return null;
			}else{
				return stricts[i];
			}
		}		
	}	
	public ItemStack get_in(int i){
		return inbound[i];
	}
	public ItemStack get_out(int i){
		return outbound[i];
	}	
	public IHOEMultiInventoryMachine set_in(int i, ItemStack stack){
		 inbound[i]=stack;
		 return this;
	}
	public IHOEMultiInventoryMachine set_out(int i, ItemStack stack){
		outbound[i]=stack;
		return this;
	}
	public ItemStack[] get_in(){
		return inbound;
	}
	public ItemStack[] get_out(){
		return outbound;
	}	
	
	protected void reevaluateWork(){
		hasWork=checkWork();
	}
	private boolean checkWork(){
		if(recipe==null){return false;}//No work without recipe
		
		boolean isStorageFree = recipe.checkStorage(this);
		boolean isResourcesAvail = recipe.checkResources(this);
		
		return isStorageFree & isResourcesAvail;
	}	


	int cycleid=0;
	public void produce(boolean doReal) {
		if(checkWork()){
			
			cycleid++;
			
			recipe.performProduction(this);
			
			reevaluateWork();
			
		}else{
			reevaluateWork();
		}
	}	
	
	
	public boolean checkStorage() {
		//Checking storage capabilities
		if(recipe==null){return false;}//No storage if there is no recipe
		return recipe.checkStorage(this);
	}
	
	//Storage
	
	public boolean evaluateHasItems() {
		for(int i = 0 ; i<inbound.length;i++){
			if(inbound[i]!=null && inbound[i].stackSize>0){return true;}
		}
		for(int i = 0 ; i<outbound.length;i++){
			if(outbound[i]!=null && outbound[i].stackSize>0){return true;}
		}		
		return false;
	}
	
	protected boolean ignoreInboundMetadata=false;
	public void setIgnoreInboundMetadata(){
		ignoreInboundMetadata=true;
	}
	
	StackTransferTuple transferTuple = new StackTransferTuple();
	private int outboundslot=0;
	@Override
	public ItemStack tryToPutIn(ItemStack source) {
		return tryToPutIn(source, null);
	}
	@Override
	public ItemStack tryToPutIn(ItemStack source, ItemStack filter) {
		if(recipe==null){return source;}
		int slot = recipe.getSlotIdFor(source,ignoreInboundMetadata);
		if(slot==-1){return source;}
		transferTuple.fill(inbound[slot], source);
		source=StackUtility.tryToPutIn(transferTuple,ignoreInboundMetadata,filter);
		inbound[slot]=transferTuple.retr1();
		this.markDirty();
		return source;
	}
	@Override
	public ItemStack tryToGetOut(ItemStack target) {
		return tryToGetOut(target, null);
	}
	@Override
	public ItemStack tryToGetOut(ItemStack target, ItemStack filter) {
		if(recipe==null){return target;}
		if(outboundslot>=outbound.length){outboundslot=0;}
		transferTuple.fill(target, outbound[outboundslot]);
		target = StackUtility.tryToGetOut(transferTuple,filter);
		outbound[outboundslot]=StackUtility.verify(transferTuple.retr2());
		outboundslot++;
		this.markDirty();
		return target;
	}

	//Synchromanager(visual inventory sync)
	protected boolean isDirty=false;
	@Override
	public void markDirty() {
		isDirty=true;
	}
	@Override
	public boolean pollDirty() {
		boolean cache = isDirty;
		isDirty=false;
		return cache;
	}

	
}
