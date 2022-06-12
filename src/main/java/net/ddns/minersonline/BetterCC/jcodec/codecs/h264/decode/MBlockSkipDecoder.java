/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode;

import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode.aso.Mapper;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.Frame;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SliceHeader;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SliceType;
import net.ddns.minersonline.BetterCC.jcodec.common.model.Picture;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.H264Const;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.H264Utils;

import java.util.Arrays;

/**
 * A decoder for P skip macroblocks
 *
 * @author The JCodec project
 */
public final class MBlockSkipDecoder extends MBlockDecoderBase {
    private final Mapper mapper;
    private final MBlockDecoderBDirect bDirectDecoder;

    public MBlockSkipDecoder(final Mapper mapper, final MBlockDecoderBDirect bDirectDecoder,
							 final SliceHeader sh, final DeblockerInput di, final int poc, final DecoderState sharedState) {
        super(sh, di, poc, sharedState);
        this.mapper = mapper;
        this.bDirectDecoder = bDirectDecoder;
    }

    public void decodeSkip(final MBlock mBlock, final Frame[][] refs, final Picture mb, final SliceType sliceType) {
        final int mbX = mapper.getMbX(mBlock.mbIdx);
        final int mbY = mapper.getMbY(mBlock.mbIdx);
        final int mbAddr = mapper.getAddress(mBlock.mbIdx);

        if (sliceType == SliceType.P) {
            predictPSkip(refs, mbX, mbY, mapper.leftAvailable(mBlock.mbIdx), mapper.topAvailable(mBlock.mbIdx),
                mapper.topLeftAvailable(mBlock.mbIdx), mapper.topRightAvailable(mBlock.mbIdx), mBlock.x, mb);
            Arrays.fill(mBlock.partPreds, H264Const.PartPred.L0);
        } else {
            bDirectDecoder.predictBDirect(refs, mbX, mbY, mapper.leftAvailable(mBlock.mbIdx),
                mapper.topAvailable(mBlock.mbIdx), mapper.topLeftAvailable(mBlock.mbIdx),
                mapper.topRightAvailable(mBlock.mbIdx), mBlock.x, mBlock.partPreds, mb, H264Const.identityMapping4);
            MBlockDecoderUtils.savePrediction8x8(s, mbX, mBlock.x);
        }

        decodeChromaSkip(refs, mBlock.x, mBlock.partPreds, mbX, mbY, mb);

        MBlockDecoderUtils.collectPredictors(s, mb, mbX);

        MBlockDecoderUtils.saveMvs(di, mBlock.x, mbX, mbY);
        di.mbTypes[mbAddr] = mBlock.curMbType;
        di.mbQps[0][mbAddr] = s.qp;
        di.mbQps[1][mbAddr] = calcQpChroma(s.qp, s.chromaQpOffset[0]);
        di.mbQps[2][mbAddr] = calcQpChroma(s.qp, s.chromaQpOffset[1]);
    }

    public void predictPSkip(final Frame[][] refs, final int mbX, final int mbY, final boolean lAvb, final boolean tAvb, final boolean tlAvb,
							 final boolean trAvb, final H264Utils.MvList x, final Picture mb) {
        int mvX = 0, mvY = 0;
        if (lAvb && tAvb) {
            final int b = s.mvTop.getMv(mbX << 2, 0);
            final int a = s.mvLeft.getMv(0, 0);

            if ((a != 0) && (b != 0)) {
                mvX = MBlockDecoderUtils.calcMVPredictionMedian(a, b, s.mvTop.getMv((mbX << 2) + 4, 0), s.mvTopLeft.getMv(0, 0), lAvb, tAvb, trAvb, tlAvb, 0, 0);
                mvY = MBlockDecoderUtils.calcMVPredictionMedian(a, b, s.mvTop.getMv((mbX << 2) + 4, 0), s.mvTopLeft.getMv(0, 0), lAvb, tAvb, trAvb, tlAvb, 0, 1);
            }
        }

        final int xx = mbX << 2;
        s.mvTopLeft.copyPair(0, s.mvTop, xx + 3);
        MBlockDecoderUtils.saveVect(s.mvTop, 0, xx, xx + 4, H264Utils.Mv.packMv(mvX, mvY, 0));
        MBlockDecoderUtils.saveVect(s.mvLeft, 0, 0, 4, H264Utils.Mv.packMv(mvX, mvY, 0));
        MBlockDecoderUtils.saveVect(s.mvTop, 1, xx, xx + 4, MBlockDecoderUtils.NULL_VECTOR);
        MBlockDecoderUtils.saveVect(s.mvLeft, 1, 0, 4, MBlockDecoderUtils.NULL_VECTOR);

        for (int i = 0; i < 16; i++) {
            x.setMv(i, 0, H264Utils.Mv.packMv(mvX, mvY, 0));
        }
        interpolator.getBlockLuma(refs[0][0], mb, 0, (mbX << 6) + mvX, (mbY << 6) + mvY, 16, 16);

        PredictionMerger.mergePrediction(sh, 0, 0, H264Const.PartPred.L0, 0, mb.getPlaneData(0), null, 0, 16, 16, 16, mb.getPlaneData(0), refs, poc);
    }

    public void decodeChromaSkip(final Frame[][] reference, final H264Utils.MvList vectors, final H264Const.PartPred[] pp, final int mbX, final int mbY, final Picture mb) {
        predictChromaInter(reference, vectors, mbX << 3, mbY << 3, 1, mb, pp);
        predictChromaInter(reference, vectors, mbX << 3, mbY << 3, 2, mb, pp);
    }
}
