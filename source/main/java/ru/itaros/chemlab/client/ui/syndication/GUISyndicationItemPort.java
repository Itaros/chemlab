package ru.itaros.chemlab.client.ui.syndication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationItemPortTileEntity;
import ru.itaros.chemlab.network.packets.ChemLabSyndicationHubCommand;
import ru.itaros.chemlab.network.packets.ChemLabSyndicationHubCommand.SyndicationCommandMode;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUISyndicationItemPort extends GUIHOEClassicalMachine {

	public GUISyndicationItemPort(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(SyndicationItemPortTileEntity)tile);
	}
	private GUISyndicationItemPort(InventoryPlayer playerInv, SyndicationItemPortTileEntity tile) {
		super(new SyndicationItemPortContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Syndication Item Port";
	}
	@Override
	protected String getUITexturePath() {
		return "textures/gui/syndicationitemport.png";
	}
	
	GuiButton setFilter,switchMode;
	@Override
	public void initGui() {
		super.initGui();
		
		setFilter = new GuiButton(0,x+135-2, y+60, (169-131),20, "SET");
		this.buttonList.add(setFilter);
		switchMode = new GuiButton(0,x+135-2, y+10, (169-131),20, "");
		refreshSwitchModeButton();
		this.buttonList.add(switchMode);		
		
	}
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button==setFilter){
			ChemLab.getInstance().SendPacketAsClientToServer(new ChemLabSyndicationHubCommand(tile,SyndicationCommandMode.GRAB_FILTERSETTINGS));
		}else if(button==switchMode){
			localRoundTrip_SOM();
			//if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){
			ChemLab.getInstance().SendPacketAsClientToServer(new ChemLabSyndicationHubCommand(tile,SyndicationCommandMode.GRAB_INBOUNDMODE));
			//}
			refreshSwitchModeButton();
		}
	}

	
	private void localRoundTrip_SOM(){
		((SyndicationItemPortData)tile.getClientData()).cycleSOM();
	}
	private void refreshSwitchModeButton(){
		SyndicationItemPortData data = (SyndicationItemPortData)tile.getClientData();
		switchMode.displayString=data.getSOM().getAlias();
	}
	
	
	
	
}
