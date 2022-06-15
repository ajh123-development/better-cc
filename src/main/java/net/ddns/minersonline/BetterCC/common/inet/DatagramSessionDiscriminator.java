package net.ddns.minersonline.BetterCC.common.inet;

final class DatagramSessionDiscriminator extends SocketSessionDiscriminator<DatagramSessionImpl> {
    public DatagramSessionDiscriminator(
        final int srcIpAddress,
        final short srcPort,
        final int dstIpAddress,
        final short dstPort
    ) {
        super(srcIpAddress, srcPort, dstIpAddress, dstPort);
    }
}
