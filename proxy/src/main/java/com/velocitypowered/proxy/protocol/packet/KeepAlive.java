package com.velocitypowered.proxy.protocol.packet;

import static com.velocitypowered.proxy.protocol.ProtocolConstants.MINECRAFT_1_12_2;

import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolConstants;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;

public class KeepAlive implements MinecraftPacket {

  private long randomId;

  public long getRandomId() {
    return randomId;
  }

  public void setRandomId(long randomId) {
    this.randomId = randomId;
  }

  @Override
  public String toString() {
    return "KeepAlive{"
        + "randomId=" + randomId
        + '}';
  }

  @Override
  public void decode(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
    if (protocolVersion >= MINECRAFT_1_12_2) {
      randomId = buf.readLong();
    } else {
      randomId = ProtocolUtils.readVarInt(buf);
    }
  }

  @Override
  public void encode(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
    if (protocolVersion >= MINECRAFT_1_12_2) {
      buf.writeLong(randomId);
    } else {
      ProtocolUtils.writeVarInt(buf, (int) randomId);
    }
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}
