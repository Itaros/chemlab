package ru.itaros.chemlab.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import ru.itaros.chemlab.tiles.syndication.ISyndicationPiping;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SyndicationSystemPipingProtection {

	@SubscribeEvent
	public void onBreakBlock(BlockEvent.BreakEvent event){
		EntityPlayer player = event.getPlayer();
		TileEntity te=event.world.getTileEntity(event.x, event.y, event.z);
		if(player==null || te==null){return;}
		if(te instanceof ISyndicationPiping){
			ISyndicationPiping piping = (ISyndicationPiping)te;
			boolean evaluation = SyndicationPipingTileEntity.utility_askControllerToDie(player, piping);
			if(!evaluation){event.setCanceled(true);}
		}
	}
	
	
	
}
