package ru.itaros.hoe.data.machines;





import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.heat.Heat;
import ru.itaros.api.hoe.heat.IHeatContainer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.hoe.adapter.HOEAdapters;
import ru.itaros.hoe.data.IHOEMultiInventoryMachine;
import ru.itaros.hoe.data.ISynchroportFluids;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalFluidStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.Recipe;
import ru.itaros.hoe.utils.FluidStackTransferTuple;
import ru.itaros.hoe.utils.ItemStackTransferTuple;
import ru.itaros.hoe.utils.StackUtility;

public class HOEMachineCrafterData extends HOEMachineData implements IHOEMultiInventoryMachine, IHeatContainer, ISynchroportItems, ISynchroportFluids{
	private int incoming_slots,outcoming_slots;
	//ALIGNED DATA
	private IUniversalStack[] inbound;
	private IUniversalStack[] outbound;
	
	
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
	public boolean sync() {
		if(super.sync()){
			HOEMachineCrafterData childd=(HOEMachineCrafterData) child;
	
			childd.hasWork=hasWork;
			
			heat.syncInto(childd.heat);
			
			childd.inbound = StackUtility.syncUniversalStacks(childd.inbound, inbound);
			childd.outbound = StackUtility.syncUniversalStacks(childd.outbound, outbound);
			
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void init(){
		inbound=new IUniversalStack[incoming_slots];
		outbound=new IUniversalStack[outcoming_slots];
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
		heat=Heat.readNBT(heat,nbt,"heat");
	}
	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
		StackUtility.writeItemStacksToNBT(inbound, nbt, "initem");
		StackUtility.writeItemStacksToNBT(outbound, nbt, "outitem");
		Heat.writeNBT(heat,nbt,"heat");
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
	public IUniversalStack get_in_withRecipe(int i){
		if(!isSided || recipe==null){
			//Normal Operation
			return get_in(i);
		}else{
			IUniversalStack current = inbound[i];
			if(current!=null){return current;}
			//Try to show recipe
			IUniversalStack[] stricts = recipe.getNormalziedIncomingStricttypes();
			if(!(i<stricts.length)){
				return null;
			}else{
				return stricts[i];
			}
		}		
	}
	public IUniversalStack get_out_withRecipe(int i){
		if(!isSided || recipe==null){
			//Normal Operation
			return get_out(i);
		}else{
			IUniversalStack current = outbound[i];
			if(current!=null){return current;}
			//Try to show recipe
			IUniversalStack[] stricts = recipe.getNormalziedOutcomingStricttypes();
			if(!(i<stricts.length)){
				return null;
			}else{
				return stricts[i];
			}
		}		
	}	
	public IUniversalStack get_in(int i){
		return inbound[i];
	}
	public IUniversalStack get_out(int i){
		return outbound[i];
	}	
	public IHOEMultiInventoryMachine set_in(int i, IUniversalStack stack){
		 inbound[i]=stack;
		 return this;
	}
	public IHOEMultiInventoryMachine set_out(int i, IUniversalStack stack){
		outbound[i]=stack;
		return this;
	}
	public IUniversalStack[] get_in(){
		return inbound;
	}
	public IUniversalStack[] get_out(){
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
			getHeat().addEnergy(10000000L);
			
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
			if(inbound[i]!=null && inbound[i].getStackSize()>0){return true;}
		}
		for(int i = 0 ; i<outbound.length;i++){
			if(!UniversalStackUtils.isNull(outbound[i]) && outbound[i].getStackSize()>0){return true;}
		}		
		return false;
	}
	
	protected boolean ignoreInboundMetadata=false;
	public void setIgnoreInboundMetadata(){
		ignoreInboundMetadata=true;
	}
	
	ItemStackTransferTuple transferItemStackTuple = new ItemStackTransferTuple();
	FluidStackTransferTuple transferFluidTuple = new FluidStackTransferTuple();
	private int outboundslotItems=0;
	private int outboundslotFluids=0;
	@Override
	public ItemStack tryToPutItemsIn(ItemStack source) {
		return tryToPutItemsIn(source, null);
	}
	@Override
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
		if(recipe==null){return source;}
		int slot = recipe.getSlotIdFor(source,ignoreInboundMetadata);
		if(slot==-1){return source;}
		//There is no hope if this is not an item. But really, this is a mess...
		if(inbound[slot] instanceof UniversalItemStack || inbound[slot]==null){
			transferItemStackTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound[slot]), source);
			source=StackUtility.tryToPutIn(transferItemStackTuple,ignoreInboundMetadata,filter);
			inbound[slot]=UniversalStackUtils.setSafeProxy(inbound[slot],transferItemStackTuple.retr1());
			this.markDirty();
		}
		return source;
	}
	
	@Override
	public FluidStack tryToPutFluidsIn(FluidStack source) {
		return tryToPutFluidsIn(source, null);
	}
	@Override
	public FluidStack tryToPutFluidsIn(FluidStack source, FluidStack filter) {
		if(recipe==null){return source;}
		int slot = recipe.getSlotIdFor(source);
		if(slot==-1){return source;}
		if(inbound[slot] instanceof UniversalFluidStack || inbound[slot]==null){
			transferFluidTuple.fill((HOEFluidStack) UniversalStackUtils.getSafeProxy(inbound[slot]), source);
			int max = recipe.getIncomingStricttypes()[slot].getMaxStackSize();
			source=StackUtility.tryToPutIn(transferFluidTuple,filter,max);
			inbound[slot]=UniversalStackUtils.setSafeProxy(inbound[slot],transferFluidTuple.retr1());
			this.markDirty();			
		}
		return source;
	}	
	
	@Override
	public ItemStack tryToGetItemsOut(ItemStack target) {
		return tryToGetItemsOut(target, null);
	}
	@Override
	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter) {
		if(recipe==null){return target;}
		if(outboundslotItems>=outbound.length){outboundslotItems=0;}
		if(outbound[outboundslotItems] instanceof UniversalItemStack){
			transferItemStackTuple.fill(target, (ItemStack) outbound[outboundslotItems].getProxy());
			target = StackUtility.tryToGetOut(transferItemStackTuple,filter);
			//FIXME: NPE On line below. Null outbound[outboundslotItems]? No idea
			outbound[outboundslotItems].setProxy(StackUtility.verify(transferItemStackTuple.retr2()));
			this.markDirty();
		}
		outboundslotItems++;
		return target;
	}
	
	@Override
	public FluidStack tryToGetFluidsOut(FluidStack fluid) {
		return tryToGetFluidsOut(fluid,null);
	}
	@Override
	public FluidStack tryToGetFluidsOut(FluidStack target, FluidStack filter) {
		if(recipe==null){return target;}
		if(outboundslotFluids>=outbound.length){outboundslotFluids=0;}
		if(outbound[outboundslotFluids] instanceof UniversalFluidStack){
			HOEFluidStack hflst = (HOEFluidStack) UniversalStackUtils.getSafeProxy(outbound[outboundslotFluids]);
			if(hflst!=null && hflst.stackSize==0){hflst=null;}//TODO: put in more optimized place
			transferFluidTuple.fill(target,  hflst);
			target = StackUtility.tryToGetOut(transferFluidTuple,filter);
			outbound[outboundslotFluids].setProxy(transferFluidTuple.retr2());
			this.markDirty();
		}
		outboundslotFluids++;
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
	
	protected Heat heat=new Heat(1402800000L);
	
	@Override
	public Heat getHeat() {
		return heat;
	}
	@Override
	public void updateDistribution() {
		HOEData[] exchangables = this.getConnectome().getConnected();
		for(HOEData c : exchangables){
			if(c instanceof IHeatContainer){
				IHeatContainer heatContainer = (IHeatContainer)c;
				heatContainer.getHeat().exchange(getHeat());
			}
		}
		getHeat().exchange(HOEAdapters.getInstance().getEnviroLab().getEnviromentalHeat());
	}
	
	@Override
	public long getMeltdownPoint() {
		return io.getMeltdownTemperature();
	}

	
}
