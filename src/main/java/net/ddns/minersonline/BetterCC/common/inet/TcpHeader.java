package net.ddns.minersonline.BetterCC.common.inet;

import java.nio.ByteBuffer;

public class TcpHeader {
    private static final int MIN_HEADER_SIZE = 20;

    private static final byte OPTION_END = 0;
    private static final byte OPTION_NOOP = 1;
    private static final byte OPTION_MAX_SEGMENT_SIZE = 2;

    ////////////////////////////////////////////////////////////////////////////

    public int sequenceNumber, acknowledgmentNumber;
    public boolean urg, ack, psh, rst, syn, fin; // flags
    public int window;
    public int urgentPointer;

    // Options
    public int maxSegmentSize;

    ////////////////////////////////////////////////////////////////////////////

    public boolean read(final ByteBuffer data) {
        if (data.remaining() < MIN_HEADER_SIZE) {
            return false;
        }
        final int position = data.position();
        sequenceNumber = data.getInt();
        acknowledgmentNumber = data.getInt();
        final int dataOffset = position + ((data.get() >>> 2) & 0x3C);
        final int flags = Byte.toUnsignedInt(data.get());
        urg = ((flags >>> 5) & 1) == 1;
        ack = ((flags >>> 4) & 1) == 1;
        psh = ((flags >>> 3) & 1) == 1;
        rst = ((flags >>> 2) & 1) == 1;
        syn = ((flags >>> 1) & 1) == 1;
        fin = (flags & 1) == 1;
        window = Short.toUnsignedInt(data.getShort());
        data.getShort(); // checksum
        urgentPointer = Short.toUnsignedInt(data.getShort());

        maxSegmentSize = -1;

        while (dataOffset > data.position()) {
            final byte type = data.get();
            switch (type) {
                case OPTION_END:
                    data.position(dataOffset);
                    return true;
                case OPTION_NOOP:
                    continue;
                default:
                    break;
            }
            final int size = Byte.toUnsignedInt(data.get());
            if (type == OPTION_MAX_SEGMENT_SIZE) {
                if (size != 2) {
                    data.position(position);
                    return false;
                }
                maxSegmentSize = Short.toUnsignedInt(data.getShort());
            } else {
                // Skip unknown option
                data.position(data.position() + size - 2);
            }
        }
        data.position(dataOffset);
        return true;
    }
}
