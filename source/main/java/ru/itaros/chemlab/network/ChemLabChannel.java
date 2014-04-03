package ru.itaros.chemlab.network;

import ru.itaros.chemlab.network.packets.SetHOEMachineRecipePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.relauncher.Side;

public class ChemLabChannel extends FMLIndexedMessageToMessageCodec<IPacketCodecDescriptor> {

	
	
	//Important
	public ChemLabChannel(){
		this.addDiscriminator(SetHOEMachineRecipePacket.getInternalCodecID(),SetHOEMachineRecipePacket.class);
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx,
			IPacketCodecDescriptor msg, ByteBuf target) throws Exception {
		msg.writeBytes(target);
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source,
			IPacketCodecDescriptor msg) {
		msg.readBytes(source);
		
		if(msg instanceof SetHOEMachineRecipePacket){
			//TODO: Integrated server trick
			if(FMLCommonHandler.instance().getEffectiveSide()!=Side.SERVER){return;}
			SetHOEMachineRecipePacket p = (SetHOEMachineRecipePacket)msg;
			p.execute();
		}
		
	}

}
