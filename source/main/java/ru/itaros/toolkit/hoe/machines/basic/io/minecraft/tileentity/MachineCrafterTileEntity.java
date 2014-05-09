package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import org.apache.logging.log4j.Level;

import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ProgrammerSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import cpw.mods.fml.common.FMLLog;


public abstract class MachineCrafterTileEntity extends MachineTileEntity implements ISidedInventory{

	ItemStack programmerStack=null;
	
	
	@Override
	public Packet getDescriptionPacket() {
		//this.xCoord, this.yCoord, this.zCoord, 1, nbtTag
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,nbt);
		return pkt;
	}
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net,pkt);
		this.readFromNBT(pkt.func_148857_g());//Meh. This gets NBT
	}


	public MachineCrafterTileEntity(){
		super();

	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines){
		return machines.generateMachineCrafterData();
	}


	//INVENTORY	

	ItemStack inbound_synchro;//0
	ItemStack outbound_synchro;//1
	
	
	ItemStack inbound_ro;
	ItemStack outbound_ro;
	//-1 = client.inbound
	//-2 = client.outbound
	
	private void updateRO(){
		if(client!=null){
			inbound_ro=getCrafterClient().getInboundRO();
			outbound_ro=getCrafterClient().getOutboundRO();
		}
	}
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagCompound in = nbt.getCompoundTag("inbound");
		if(in!=null){
			inbound_synchro = ItemStack.loadItemStackFromNBT(in);
		}
		NBTTagCompound out = nbt.getCompoundTag("outbound");
		if(out!=null){
			outbound_synchro = ItemStack.loadItemStackFromNBT(out);		
		}
		
		NBTTagCompound pr = nbt.getCompoundTag("prog");
		if(pr!=null){
			programmerStack = ItemStack.loadItemStackFromNBT(pr);		
		}		
		
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		//TODO: Helper needed for that
		if(inbound_synchro!=null){
			NBTTagCompound in = new NBTTagCompound();
			inbound_synchro.writeToNBT(in);
			nbt.setTag("inbound", in);
		}
		if(outbound_synchro!=null){
			NBTTagCompound out = new NBTTagCompound();
			outbound_synchro.writeToNBT(out);
			nbt.setTag("outbound", out);	
		}
		if(programmerStack!=null){
			NBTTagCompound pr = new NBTTagCompound();
			programmerStack.writeToNBT(pr);
			nbt.setTag("prog", pr);	
		}
	}
	
	
	
	@Override
	public int getSizeInventory() {
		return 1+1;//in and out synchrobound! %_%
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		switch(slot){
		case 0:
			return inbound_synchro;
		case 1:
			return outbound_synchro;
			
		case -1:
			return inbound_ro;
		case -2:
			return outbound_ro;		
			
		case ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT:
			return programmerStack;
			
		}
		return null;
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		switch(slot){
		case 0:
			inbound_synchro=stack;
			break;
		case 1:
			outbound_synchro=stack;
			break;

			
			
		case ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT:
			programmerStack=stack;
			if(programmerStack==null){programmerWasOpened=false;break;}
			if(!programmerWasOpened){
				programmatorScreenLauncher();
				programmerWasOpened=true;
			}
			//programmerStack=null;//TODO: DEBUG!!!
			break;
			
		}		
	}
	
	private boolean programmerWasOpened=false;
	
	private void programmatorScreenLauncher() {
		if(programmerStack!=null && programmerStack.getItem()==ItemLoader.programmer){
			
			ChemLab.proxy.openProgrammerGUI(this);
		}
		
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot<0){return false;}//There is no way to push something into chamber
		if(slot==1){return false;}
		
		if(slot==0){
			//Injector slot
			ItemStack possible = getStricttypeByOffsetIndex();
			if(possible!=null && (possible.getItem()==stack.getItem() & possible.getItemDamage()==stack.getItemDamage())){
				return true;
			}
			if(possible==null){
				return true;
			}
			return false;
		}
		
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			if(stack.getItem()==ItemLoader.programmer){
				return true;
			}else{
				return false;
			}
		}
		
		
		return true;
	}
	
	

	@Override
    public ItemStack decrStackSize(int slot, int amt) {
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			ItemStack ret = this.getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return ret;
		}
		
    	if(slot<0){return (ItemStack)null;}//You can't take from client inbound
            ItemStack stack = getStackInSlot(slot);
            if (stack != null) {
                    if (stack.stackSize <= amt) {
                            setInventorySlotContents(slot, null);
                            
                    } else {
                            stack = stack.splitStack(amt);
                            if (stack.stackSize == 0) {
                                    setInventorySlotContents(slot, null);
                                   
                            }
                    }
            }
            return stack;
    }	

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;//Do not drops
	}
	@Override
	public String getInventoryName() {
		return "HOEMachineInventory";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}
	@Override
	public void openInventory() {
	}
	@Override
	public void closeInventory() {
	}
	
	
	private static final int[] synchroportAccessIndices = {0,1};
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return synchroportAccessIndices;
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if(slot==0){
			return !lastPushFailed;//Cant insert if failed
		}else{
			return false;
		}
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if(slot==0){
			return lastPushFailed;
		}else{
			//1
			return true;
		}
	}
	
	
	//Sync
	private boolean doReal = (ContextDetector.getInstance().getContext()==FMLContext.CLIENT);
	@Override
	public void updateEntity() {
		if(client!=null){
			HOEMachineIO io = client.getIO();
			if(io!=null){
				//CLIENTSIDE TICK in MTA
				io.tick(client, doReal);
				updateRO();
			}
		}		

	}
	private ItemStack getStricttypeByOffsetIndex() {
    	if(server!=null){
    		ItemStack result = getCrafterServer().getStricttypeByIndex(injectorTypeOffset);
    		if(result==null){
    			injectorTypeOffset=0;
    		}
    		return result;
    	}
    	return null;
	}	
	
	public void pullFromHOE() {
		if(server!=null){

			outbound_synchro = getCrafterServer().pullProduct(outbound_synchro);

		}
		
	}
	
	private int injectorTypeOffset=0;
	private boolean lastPushFailed=false;
	public void pushToHOE() {
		if(server!=null){
			if(inbound_synchro!=null){
				if(inbound_synchro.stackSize<=0){inbound_synchro=null;return;}
				ItemStack temp = inbound_synchro;
				if(!getCrafterServer().pushResource(temp)){
					//FAILED!
					if(!lastPushFailed){
						FMLLog.log(Level.INFO, "Last Push failed! by"+this.getClass().getName());
						injectorTypeOffset++;
						lastPushFailed=true;
					}
				}else{
					//lastPushFailed=false;//Push succeeded. There is no need in that flag
					if(inbound_synchro.stackSize<=0){
						inbound_synchro=null;//Kill unused stack
						
					}
					setInventorySlotContents(0, inbound_synchro);//Sync backtrip
				}
			}else{
				lastPushFailed=false;
			}
		}
		
	}

	
	
	
	
	public boolean trySetRecipe(Recipe r){
		if(server!=null){
			HOEMachineCrafterData d = getCrafterServer();
			if(!d.evaluateHasItems()){
				d.setRecipe(r);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	
	
	//Wrappers
	private HOEMachineCrafterData getCrafterClient(){
		return (HOEMachineCrafterData)client;
	}
	private HOEMachineCrafterData getCrafterServer(){
		return (HOEMachineCrafterData)server;
	}	
	
	
}
