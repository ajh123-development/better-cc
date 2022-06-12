/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode;

import net.ddns.minersonline.BetterCC.jcodec.codecs.common.biari.MDecoder;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode.aso.MapManager;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode.aso.Mapper;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.CABAC;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.CAVLC;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.NALUnit;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.PictureParameterSet;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SeqParameterSet;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SliceHeader;
import net.ddns.minersonline.BetterCC.jcodec.common.IntObjectMap;
import net.ddns.minersonline.BetterCC.jcodec.common.io.BitReader;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.H264Utils;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * <p>
 * MPEG 4 AVC ( H.264 ) Frame reader
 * <p>
 * Conforms to H.264 ( ISO/IEC 14496-10 ) specifications
 *
 * @author The JCodec project
 */
public final class FrameReader {
    private final IntObjectMap<SeqParameterSet> sps;
    private final IntObjectMap<PictureParameterSet> pps;

    public FrameReader() {
        this.sps = new IntObjectMap<>();
        this.pps = new IntObjectMap<>();
    }

    public List<SliceReader> readFrame(final List<ByteBuffer> nalUnits) {
        final List<SliceReader> result = new ArrayList<>();

        for (final ByteBuffer nalData : nalUnits) {
            final NALUnit nalUnit = NALUnit.read(nalData);

            H264Utils.unescapeNAL(nalData);
            if (NALUnitType.SPS == nalUnit.type) {
                final SeqParameterSet _sps = SeqParameterSet.read(nalData);
                sps.put(_sps.seqParameterSetId, _sps);
            } else if (NALUnitType.PPS == nalUnit.type) {
                final PictureParameterSet _pps = PictureParameterSet.read(nalData);
                pps.put(_pps.picParameterSetId, _pps);
            } else if (NALUnitType.IDR_SLICE == nalUnit.type || NALUnitType.NON_IDR_SLICE == nalUnit.type) {
                if (sps.size() == 0 || pps.size() == 0) {
                    return null;
                }
                result.add(createSliceReader(nalData, nalUnit));
            }
        }

        return result;
    }

    private SliceReader createSliceReader(final ByteBuffer segment, final NALUnit nalUnit) {
        final BitReader _in = BitReader.createBitReader(segment);
        final SliceHeader sh = SliceHeaderReader.readPart1(_in);
        sh.pps = pps.get(sh.picParameterSetId);
        sh.sps = sps.get(sh.pps.seqParameterSetId);
        SliceHeaderReader.readPart2(sh, nalUnit, sh.sps, sh.pps, _in);

        final Mapper mapper = new MapManager(sh.sps, sh.pps).getMapper(sh);

        final CAVLC[] cavlc = new CAVLC[]{new CAVLC(sh.sps, 2, 2), new CAVLC(sh.sps, 1, 1),
            new CAVLC(sh.sps, 1, 1)};

        final int mbWidth = sh.sps.picWidthInMbsMinus1 + 1;
        final CABAC cabac = new CABAC(mbWidth);

        MDecoder mDecoder = null;
        if (sh.pps.entropyCodingModeFlag) {
            _in.terminate();
            final int[][] cm = new int[2][1024];
            final int qp = sh.pps.picInitQpMinus26 + 26 + sh.sliceQpDelta;
            cabac.initModels(cm, sh.sliceType, sh.cabacInitIdc, qp);
            mDecoder = new MDecoder(segment, cm);
        }

        return new SliceReader(sh.pps, cabac, cavlc, mDecoder, _in, mapper, sh, nalUnit);
    }
}
