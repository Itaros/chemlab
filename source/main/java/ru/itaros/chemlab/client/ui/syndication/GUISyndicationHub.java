package ru.itaros.chemlab.client.ui.syndication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationHubData;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationHubTileEntity;
import ru.itaros.hoe.toolkit.ui.Tab;
import ru.itaros.hoe.toolkit.ui.TabManager;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;

public class GUISyndicationHub extends GUIHOEClassicalMachine {

	public GUISyndicationHub(InventoryPlayer playerInv,MachineTileEntity tile){
		this(playerInv,(SyndicationHubTileEntity)tile);
	}
	private GUISyndicationHub(InventoryPlayer playerInv, SyndicationHubTileEntity tile) {
		super(new SyndicationHubContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	private TabManager tabs;
	private Tab tab_status,tab_list,tab_adhoc,tab_engage;
	
	@Override
	public void initGui() {
		super.initGui();
		
		tab_status = new Tab(-17, 0, 190,2,19,20,193,53);
		tab_list = new Tab(-17, 0, 190,2,19,20,208,53);
		tab_adhoc = new Tab(-17, 0, 190,2,19,20,208,68);
		tab_engage = new Tab(-17, 0, 190,2,19,20,193,83);
		
		tabs = new TabManager(tab_status,tab_list,tab_adhoc,tab_engage);		
		refreshView();
	}
	
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Syndication Hub";
	}
	@Override
	protected String getUITexturePath() {
		return "textures/gui/syndicationhub.png";
	}
	
	
	
	private static int MIDDLEBUTTON_WIDTH = 150;
	
	@Override
	protected void mouseClicked(int mousex, int mousey, int button) {
		super.mouseClicked(mousex, mousey, button);
		if(tabs.clickTabs(this, mousex, mousey, x, y, button)){refreshView();}
	}
	private void refreshView() {
		if(tile instanceof SyndicationHubTileEntity){
			SyndicationHubTileEntity shte = (SyndicationHubTileEntity)tile;
			
			
			
			Tab active = tabs.getActiveTab();
			buttonList.clear();
			if(active==tab_engage){
				if(shte.isReadyForConfiguration()){
					buttonList.add(new GuiButtonEngageSyndication(1, width / 2 - MIDDLEBUTTON_WIDTH/2, height / 2 - 40, MIDDLEBUTTON_WIDTH, 20, "Engage Network Inspection",false).setTile(shte).setEnabled(shte.isReadyForConfiguration()));
				}else if(shte.isConfigured()){
					buttonList.add(new GuiButtonEngageSyndication(1, width / 2 - MIDDLEBUTTON_WIDTH/2, height / 2 - 40, MIDDLEBUTTON_WIDTH, 20, "Disengage Network",true).setTile(shte).setEnabled(true));
				}
			}
			if(active==tab_status){
				syndicationStatus=getSyndicationStatusString();
			}
			
			
		}else{
			//TODO: Exception
		}
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float unk1, int unk2,
			int unk3) {
		super.drawGuiContainerBackgroundLayer(unk1, unk2, unk3);
		setUnderlyingContext();
		tabs.drawTabs(this, x, y);
		
		SyndicationHubTileEntity shte = (SyndicationHubTileEntity)tile;
		SyndicationHubData shdata = (SyndicationHubData) shte.getServerData();
		
		
		Tab active = tabs.getActiveTab();
		if(active==tab_status){
			this.fontRendererObj.drawString(syndicationStatus, x+15, y+20, 0x000000);
			
		}
		
		if(active==tab_engage){
			this.fontRendererObj.drawString("Steps: "+shte.getSyndicationSteps(), x+15, y+20, 0x000000);
		}
		
		if(active==tab_list){
			this.fontRendererObj.drawString("EMFCs: "+shdata.getCapacitorsListAmount()+" ("+shdata.getStoredAmount()+"/"+shdata.getMaxAmount()+" EMF)", x+15, y+20, 0x000000);
		}		
		
	}
	private String syndicationStatus = "";
	private String getSyndicationStatusString() {
		SyndicationHubTileEntity shte = (SyndicationHubTileEntity)tile;
		String prefix = "Mode: ";
		if(shte.isReadyForConfiguration()){return prefix+"Ready For Configuration";}
		if(shte.isSyndicationEngaged()){return prefix+"Analyzing Network...";}
		if(shte.isConfigured()){return prefix+"Ready for operation!";}
		
		return "UNKNOWN";
	}
	@Override
	protected void DrawGauges(HOEMachineData data) {
		//DrawProgressbar(data);
	}

}
