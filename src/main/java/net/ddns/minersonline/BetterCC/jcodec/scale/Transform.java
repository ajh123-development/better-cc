/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.scale;

import net.ddns.minersonline.BetterCC.jcodec.common.model.Picture;

/**
 * This class is part of JCodec ( www.jcodec.org )
 * This software is distributed under FreeBSD License
 *
 * @author The JCodec project
 */
public interface Transform {
    void transform(Picture src, Picture dst);
}
