package net.ddns.minersonline.BetterCC.common.inet;

public class StreamSessionDiscriminator extends SocketSessionDiscriminator<StreamSessionImpl> {
    public StreamSessionDiscriminator(
        final int srcIpAddress,
        final short srcPort,
        final int dstIpAddress,
        final short dstPort
    ) {
        super(srcIpAddress, srcPort, dstIpAddress, dstPort);
    }
}
