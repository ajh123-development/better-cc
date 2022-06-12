/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.common;

import net.ddns.minersonline.BetterCC.jcodec.common.model.Picture;

import java.nio.ByteBuffer;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 *
 * @author The JCodec project
 */
public abstract class VideoDecoder {
    /**
     * Decodes a video frame to an uncompressed picture in codec native
     * colorspace
     *
     * @param data Compressed frame data
     */
    public abstract Picture decodeFrame(ByteBuffer data, byte[][] buffer);
}
