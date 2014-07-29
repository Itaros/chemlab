package ru.itaros.chemlab.client.ui.syndication;

import cpw.mods.fml.relauncher.Side;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.network.packets.ChemLabSyndicationHubCommand;
import ru.itaros.chemlab.network.packets.ChemLabSyndicationHubCommand.SyndicationCommandMode;
import ru.itaros.chemlab.network.packets.SetHOEMachineRecipePacket;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonEngageSyndication extends GuiButton {

	private boolean inversed;
	
	public GuiButtonEngageSyndication(int par1, int par2, int par3, int par4,
			int par5, String par6Str, boolean inverse) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.inversed=inverse;
	}
	public GuiButtonEngageSyndication(int par1, int par2, int par3, String par6Str) {
		super(par1, par2, par3, par6Str);
	}
	
	public GuiButtonEngageSyndication setTile(SyndicationHubTileEntity tile){
		this.tile=tile;
		
		return this;
	}
	
	public GuiButtonEngageSyndication setEnabled(boolean mode){
		this.enabled=mode;
		
		return this;
	}
	
	
	private SyndicationHubTileEntity tile;
	
	@Override
	public boolean mousePressed(Minecraft mc, int x,
			int y) {
		if(super.mousePressed(mc, x, y)){
			action();
			return true;
		}else{
			return false;
		}
	}
	
	
	protected void action(){
		if(enabled){
			System.out.println("SENDING");
			this.enabled=false;
			
			ChemLabSyndicationHubCommand comm;
			if(!inversed){
				comm = new ChemLabSyndicationHubCommand(tile,SyndicationCommandMode.ENGAGE_NETWORK_INSPECTION);
			}else{
				comm = new ChemLabSyndicationHubCommand(tile,SyndicationCommandMode.DISENGAGE_NETWORK);
			}
			ChemLab.getInstance().SendPacketAsClientToServer(comm);
			//tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		}
	}
	
	

}
