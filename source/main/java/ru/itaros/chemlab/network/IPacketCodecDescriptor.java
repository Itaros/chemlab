package ru.itaros.chemlab.network;

import io.netty.buffer.ByteBuf;

public interface IPacketCodecDescriptor {
	

	
    public void readBytes(ByteBuf bytes);
    public void writeBytes(ByteBuf bytes);
}
