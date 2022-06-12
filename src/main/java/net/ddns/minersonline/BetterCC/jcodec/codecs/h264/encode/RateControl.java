/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.codecs.h264.encode;

import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SliceType;
import net.ddns.minersonline.BetterCC.jcodec.common.model.Picture;
import net.ddns.minersonline.BetterCC.jcodec.common.model.Size;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * <p>
 * MPEG 4 AVC ( H.264 ) Encoder pluggable rate control mechanism
 *
 * @author The JCodec project
 */
public interface RateControl {
    int startPicture(Size sz, int maxSize, SliceType sliceType);

    int initialQpDelta(Picture pic, int mbX, int mbY);

    int accept(int bits);
}
