package ru.itaros.chemlab.network;

import ru.itaros.chemlab.network.packets.SetHOEMachineRecipePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class ChemLabChannel extends FMLIndexedMessageToMessageCodec<IPacketCodecDescriptor> {

	
	
	//Important
	public ChemLabChannel(){
		this.addDiscriminator(SetHOEMachineRecipePacket.getInternalCodecID(),SetHOEMachineRecipePacket.class);
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx,
			IPacketCodecDescriptor msg, ByteBuf target) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source,
			IPacketCodecDescriptor msg) {
		// TODO Auto-generated method stub
		
	}

}
