/* SPDX-License-Identifier: BSD 2-Clause "Simplified" License */

package net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode;

import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.H264Const;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.decode.aso.Mapper;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.Frame;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.io.model.SliceHeader;
import net.ddns.minersonline.BetterCC.jcodec.common.model.Picture;
import net.ddns.minersonline.BetterCC.jcodec.common.tools.MathUtil;
import net.ddns.minersonline.BetterCC.jcodec.codecs.h264.H264Utils;

/**
 * A decoder for B direct macroblocks
 *
 * @author The JCodec project
 */
public final class MBlockDecoderBDirect extends MBlockDecoderBase {
    private final Mapper mapper;

    public MBlockDecoderBDirect(final Mapper mapper, final SliceHeader sh, final DeblockerInput di, final int poc, final DecoderState decoderState) {
        super(sh, di, poc, decoderState);
        this.mapper = mapper;
    }

    public void decode(final MBlock mBlock, final Picture mb, final Frame[][] references) {
        final int mbX = mapper.getMbX(mBlock.mbIdx);
        final int mbY = mapper.getMbY(mBlock.mbIdx);
        final boolean lAvb = mapper.leftAvailable(mBlock.mbIdx);
        final boolean tAvb = mapper.topAvailable(mBlock.mbIdx);
        final int mbAddr = mapper.getAddress(mBlock.mbIdx);
        final boolean tlAvb = mapper.topLeftAvailable(mBlock.mbIdx);
        final boolean trAvb = mapper.topRightAvailable(mBlock.mbIdx);

        predictBDirect(references, mbX, mbY, lAvb, tAvb, tlAvb, trAvb, mBlock.x, mBlock.partPreds, mb, H264Const.identityMapping4);

        predictChromaInter(references, mBlock.x, mbX << 3, mbY << 3, 1, mb, mBlock.partPreds);
        predictChromaInter(references, mBlock.x, mbX << 3, mbY << 3, 2, mb, mBlock.partPreds);

        if (mBlock.cbpLuma() > 0 || mBlock.cbpChroma() > 0) {
            s.qp = (s.qp + mBlock.mbQPDelta + 52) % 52;
        }
        di.mbQps[0][mbAddr] = s.qp;

        residualLuma(mBlock);

        MBlockDecoderUtils.savePrediction8x8(s, mbX, mBlock.x);
        MBlockDecoderUtils.saveMvs(di, mBlock.x, mbX, mbY);

        final int qp1 = calcQpChroma(s.qp, s.chromaQpOffset[0]);
        final int qp2 = calcQpChroma(s.qp, s.chromaQpOffset[1]);

        decodeChromaResidual(mBlock, qp1, qp2);

        di.mbQps[1][mbAddr] = qp1;
        di.mbQps[2][mbAddr] = qp2;

        MBlockDecoderUtils.mergeResidual(mb, mBlock.ac, mBlock.transform8x8Used ? H264Const.COMP_BLOCK_8x8_LUT : H264Const.COMP_BLOCK_4x4_LUT,
            mBlock.transform8x8Used ? H264Const.COMP_POS_8x8_LUT : H264Const.COMP_POS_4x4_LUT);

        MBlockDecoderUtils.collectPredictors(s, mb, mbX);

        di.mbTypes[mbAddr] = mBlock.curMbType;
        di.tr8x8Used[mbAddr] = mBlock.transform8x8Used;
    }

    public void predictBDirect(final Frame[][] refs, final int mbX, final int mbY, final boolean lAvb, final boolean tAvb, final boolean tlAvb,
							   final boolean trAvb, final H264Utils.MvList x, final H264Const.PartPred[] pp, final Picture mb, final int[] blocks) {
        if (sh.directSpatialMvPredFlag)
            predictBSpatialDirect(refs, mbX, mbY, lAvb, tAvb, tlAvb, trAvb, x, pp, mb, blocks);
        else
            predictBTemporalDirect(refs, mbX, mbY, x, pp, mb, blocks);
    }

    private void predictBTemporalDirect(final Frame[][] refs, final int mbX, final int mbY,
										final H264Utils.MvList x, final H264Const.PartPred[] pp, final Picture mb, final int[] blocks8x8) {
        for (final int blk8x8 : blocks8x8) {
            final int blk4x4_0 = H264Const.BLK8x8_BLOCKS[blk8x8][0];
            pp[blk8x8] = H264Const.PartPred.Bi;

            if (!sh.sps.direct8x8InferenceFlag) {
                final int[] js = H264Const.BLK8x8_BLOCKS[blk8x8];
                for (final int blk4x4 : js) {
                    predTemp4x4(refs, mbX, mbY, x, blk4x4);

                    final int blkIndX = blk4x4 & 3;
                    final int blkIndY = blk4x4 >> 2;

                    final int blkPredX = (mbX << 6) + (blkIndX << 4);
                    final int blkPredY = (mbY << 6) + (blkIndY << 4);

                    interpolator.getBlockLuma(refs[0][x.mv0R(blk4x4)], mbb[0], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4], blkPredX + x.mv0X(blk4x4), blkPredY + x.mv0Y(blk4x4), 4, 4);
                    interpolator.getBlockLuma(refs[1][0], mbb[1], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4], blkPredX + x.mv1X(blk4x4), blkPredY + x.mv1Y(blk4x4), 4, 4);
                }
            } else {
                final int blk4x4Pred = H264Const.BLK_DISP_MAP[blk8x8 * 5];
                predTemp4x4(refs, mbX, mbY, x, blk4x4Pred);
                propagatePred(x, blk8x8, blk4x4Pred);

                final int blkIndX = blk4x4_0 & 3;
                final int blkIndY = blk4x4_0 >> 2;

                final int blkPredX = (mbX << 6) + (blkIndX << 4);
                final int blkPredY = (mbY << 6) + (blkIndY << 4);

                interpolator.getBlockLuma(refs[0][x.mv0R(blk4x4_0)], mbb[0], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], blkPredX
                    + x.mv0X(blk4x4_0), blkPredY + x.mv0Y(blk4x4_0), 8, 8);
                interpolator.getBlockLuma(refs[1][0], mbb[1], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], blkPredX
                    + x.mv1X(blk4x4_0), blkPredY + x.mv1Y(blk4x4_0), 8, 8);
            }
            PredictionMerger.mergePrediction(sh, x.mv0R(blk4x4_0), x.mv1R(blk4x4_0), H264Const.PartPred.Bi, 0, mbb[0].getPlaneData(0), mbb[1].getPlaneData(0),
                H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], 16, 8, 8, mb.getPlaneData(0), refs, poc);
        }
    }

    private void predTemp4x4(final Frame[][] refs, final int mbX, final int mbY, final H264Utils.MvList x, final int blk4x4) {
        final int mbWidth = sh.sps.picWidthInMbsMinus1 + 1;

        final Frame picCol = refs[1][0];
        final int blkIndX = blk4x4 & 3;
        final int blkIndY = blk4x4 >> 2;

        final int blkPosX = (mbX << 2) + blkIndX;
        final int blkPosY = (mbY << 2) + blkIndY;

        int mvCol = picCol.getMvs().getMv(blkPosX, blkPosY, 0);
        final Frame refL0;
        final int refIdxL0;
        if (H264Utils.Mv.mvRef(mvCol) == -1) {
            mvCol = picCol.getMvs().getMv(blkPosX, blkPosY, 1);
            if (H264Utils.Mv.mvRef(mvCol) == -1) {
                refIdxL0 = 0;
                refL0 = refs[0][0];
            } else {
                refL0 = picCol.getRefsUsed()[mbY * mbWidth + mbX][1][H264Utils.Mv.mvRef(mvCol)];
                refIdxL0 = findPic(refs[0], refL0);
            }
        } else {
            refL0 = picCol.getRefsUsed()[mbY * mbWidth + mbX][0][H264Utils.Mv.mvRef(mvCol)];
            refIdxL0 = findPic(refs[0], refL0);
        }

        final int td = MathUtil.clip(picCol.getPOC() - refL0.getPOC(), -128, 127);
        if (!refL0.isShortTerm() || td == 0) {
            x.setPair(blk4x4, H264Utils.Mv.packMv(H264Utils.Mv.mvX(mvCol), H264Utils.Mv.mvY(mvCol), refIdxL0), 0);
        } else {
            final int tb = MathUtil.clip(poc - refL0.getPOC(), -128, 127);
            final int tx = (16384 + Math.abs(td / 2)) / td;
            final int dsf = MathUtil.clip((tb * tx + 32) >> 6, -1024, 1023);

            x.setPair(blk4x4, H264Utils.Mv.packMv((dsf * H264Utils.Mv.mvX(mvCol) + 128) >> 8, (dsf * H264Utils.Mv.mvY(mvCol) + 128) >> 8, refIdxL0),
                H264Utils.Mv.packMv((x.mv0X(blk4x4) - H264Utils.Mv.mvX(mvCol)), (x.mv0Y(blk4x4) - H264Utils.Mv.mvY(mvCol)), 0));
        }
    }

    private int findPic(final Frame[] frames, final Frame refL0) {
        for (int i = 0; i < frames.length; i++)
            if (frames[i] == refL0)
                return i;
        return 0;
    }

    private void predictBSpatialDirect(final Frame[][] refs, final int mbX, final int mbY, final boolean lAvb, final boolean tAvb, final boolean tlAvb,
									   final boolean trAvb, final H264Utils.MvList x, final H264Const.PartPred[] pp, final Picture mb, final int[] blocks8x8) {
        final int a0 = s.mvLeft.getMv(0, 0);
        final int a1 = s.mvLeft.getMv(0, 1);
        final int b0 = s.mvTop.getMv(mbX << 2, 0);
        final int b1 = s.mvTop.getMv(mbX << 2, 1);
        final int c0 = s.mvTop.getMv((mbX << 2) + 4, 0);
        final int c1 = s.mvTop.getMv((mbX << 2) + 4, 1);
        final int d0 = s.mvTopLeft.getMv(0, 0);
        final int d1 = s.mvTopLeft.getMv(0, 1);

        final int refIdxL0 = calcRef(a0, b0, c0, d0, lAvb, tAvb, tlAvb, trAvb);
        final int refIdxL1 = calcRef(a1, b1, c1, d1, lAvb, tAvb, tlAvb, trAvb);

        if (refIdxL0 < 0 && refIdxL1 < 0) {
            for (final int blk8x8 : blocks8x8) {
                final int[] js = H264Const.BLK8x8_BLOCKS[blk8x8];
                for (final int blk4x4 : js) {
                    x.setPair(blk4x4, 0, 0);
                }
                pp[blk8x8] = H264Const.PartPred.Bi;

                final int blkOffX = (blk8x8 & 1) << 5;
                final int blkOffY = (blk8x8 >> 1) << 5;
                interpolator.getBlockLuma(refs[0][0], mbb[0], H264Const.BLK_8x8_MB_OFF_LUMA[blk8x8], (mbX << 6) + blkOffX,
                    (mbY << 6) + blkOffY, 8, 8);
                interpolator.getBlockLuma(refs[1][0], mbb[1], H264Const.BLK_8x8_MB_OFF_LUMA[blk8x8], (mbX << 6) + blkOffX,
                    (mbY << 6) + blkOffY, 8, 8);
                PredictionMerger.mergePrediction(sh, 0, 0, H264Const.PartPred.Bi, 0, mbb[0].getPlaneData(0), mbb[1].getPlaneData(0),
                    H264Const.BLK_8x8_MB_OFF_LUMA[blk8x8], 16, 8, 8, mb.getPlaneData(0), refs, poc);
            }
            return;
        }
        final int mvX0 = MBlockDecoderUtils.calcMVPredictionMedian(a0, b0, c0, d0, lAvb, tAvb, trAvb, tlAvb, refIdxL0, 0);
        final int mvY0 = MBlockDecoderUtils.calcMVPredictionMedian(a0, b0, c0, d0, lAvb, tAvb, trAvb, tlAvb, refIdxL0, 1);
        final int mvX1 = MBlockDecoderUtils.calcMVPredictionMedian(a1, b1, c1, d1, lAvb, tAvb, trAvb, tlAvb, refIdxL1, 0);
        final int mvY1 = MBlockDecoderUtils.calcMVPredictionMedian(a1, b1, c1, d1, lAvb, tAvb, trAvb, tlAvb, refIdxL1, 1);

        final Frame col = refs[1][0];
        final H264Const.PartPred partPred = refIdxL0 >= 0 && refIdxL1 >= 0 ? H264Const.PartPred.Bi : (refIdxL0 >= 0 ? H264Const.PartPred.L0 : H264Const.PartPred.L1);
        for (final int blk8x8 : blocks8x8) {
            final int blk4x4_0 = H264Const.BLK8x8_BLOCKS[blk8x8][0];

            if (!sh.sps.direct8x8InferenceFlag) {
                final int[] js = H264Const.BLK8x8_BLOCKS[blk8x8];
                for (final int blk4x4 : js) {
                    pred4x4(mbX, mbY, x, pp, refIdxL0, refIdxL1, mvX0, mvY0, mvX1, mvY1, col, partPred, blk4x4);

                    final int blkIndX = blk4x4 & 3;
                    final int blkIndY = blk4x4 >> 2;

                    final int blkPredX = (mbX << 6) + (blkIndX << 4);
                    final int blkPredY = (mbY << 6) + (blkIndY << 4);

                    if (refIdxL0 >= 0)
                        interpolator.getBlockLuma(refs[0][refIdxL0], mbb[0], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4], blkPredX
                            + x.mv0X(blk4x4), blkPredY + x.mv0Y(blk4x4), 4, 4);
                    if (refIdxL1 >= 0)
                        interpolator.getBlockLuma(refs[1][refIdxL1], mbb[1], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4], blkPredX
                            + x.mv1X(blk4x4), blkPredY + x.mv1Y(blk4x4), 4, 4);
                }
            } else {
                final int blk4x4Pred = H264Const.BLK_DISP_MAP[blk8x8 * 5];
                pred4x4(mbX, mbY, x, pp, refIdxL0, refIdxL1, mvX0, mvY0, mvX1, mvY1, col, partPred, blk4x4Pred);
                propagatePred(x, blk8x8, blk4x4Pred);

                final int blkIndX = blk4x4_0 & 3;
                final int blkIndY = blk4x4_0 >> 2;

                final int blkPredX = (mbX << 6) + (blkIndX << 4);
                final int blkPredY = (mbY << 6) + (blkIndY << 4);

                if (refIdxL0 >= 0)
                    interpolator.getBlockLuma(refs[0][refIdxL0], mbb[0], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], blkPredX
                        + x.mv0X(blk4x4_0), blkPredY + x.mv0Y(blk4x4_0), 8, 8);
                if (refIdxL1 >= 0)
                    interpolator.getBlockLuma(refs[1][refIdxL1], mbb[1], H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], blkPredX
                        + x.mv1X(blk4x4_0), blkPredY + x.mv1Y(blk4x4_0), 8, 8);
            }
            PredictionMerger.mergePrediction(sh, x.mv0R(blk4x4_0), x.mv1R(blk4x4_0),
                refIdxL0 >= 0 ? (refIdxL1 >= 0 ? H264Const.PartPred.Bi : H264Const.PartPred.L0) : H264Const.PartPred.L1, 0, mbb[0].getPlaneData(0), mbb[1].getPlaneData(0),
                H264Const.BLK_4x4_MB_OFF_LUMA[blk4x4_0], 16, 8, 8, mb.getPlaneData(0), refs, poc);
        }
    }

    private int calcRef(final int a0, final int b0, final int c0, final int d0, final boolean lAvb, final boolean tAvb, final boolean tlAvb, final boolean trAvb) {
        return minPos(minPos(lAvb ? H264Utils.Mv.mvRef(a0) : -1, tAvb ? H264Utils.Mv.mvRef(b0) : -1), trAvb ? H264Utils.Mv.mvRef(c0) : (tlAvb ? H264Utils.Mv.mvRef(d0) : -1));
    }

    private void propagatePred(final H264Utils.MvList x, final int blk8x8, final int blk4x4Pred) {
        final int b0 = H264Const.BLK8x8_BLOCKS[blk8x8][0];
        final int b1 = H264Const.BLK8x8_BLOCKS[blk8x8][1];
        final int b2 = H264Const.BLK8x8_BLOCKS[blk8x8][2];
        final int b3 = H264Const.BLK8x8_BLOCKS[blk8x8][3];
        x.copyPair(b0, x, blk4x4Pred);
        x.copyPair(b1, x, blk4x4Pred);
        x.copyPair(b2, x, blk4x4Pred);
        x.copyPair(b3, x, blk4x4Pred);
    }

    private void pred4x4(final int mbX, final int mbY, final H264Utils.MvList x, final H264Const.PartPred[] pp, final int refL0, final int refL1, final int mvX0, final int mvY0,
						 final int mvX1, final int mvY1, final Frame col, final H264Const.PartPred partPred, final int blk4x4) {
        final int blkIndX = blk4x4 & 3;
        final int blkIndY = blk4x4 >> 2;

        final int blkPosX = (mbX << 2) + blkIndX;
        final int blkPosY = (mbY << 2) + blkIndY;

        int mvCol = col.getMvs().getMv(blkPosX, blkPosY, 0);
        if (H264Utils.Mv.mvRef(mvCol) == -1)
            mvCol = col.getMvs().getMv(blkPosX, blkPosY, 1);

        final boolean colZero = col.isShortTerm() && H264Utils.Mv.mvRef(mvCol) == 0 && (MathUtil.abs(H264Utils.Mv.mvX(mvCol)) >> 1) == 0
            && (MathUtil.abs(H264Utils.Mv.mvY(mvCol)) >> 1) == 0;

        int x0 = H264Utils.Mv.packMv(0, 0, refL0), x1 = H264Utils.Mv.packMv(0, 0, refL1);
        if (refL0 > 0 || !colZero) {
            x0 = H264Utils.Mv.packMv(mvX0, mvY0, refL0);
        }
        if (refL1 > 0 || !colZero) {
            x1 = H264Utils.Mv.packMv(mvX1, mvY1, refL1);
        }
        x.setPair(blk4x4, x0, x1);

        pp[H264Const.BLK_8x8_IND[blk4x4]] = partPred;
    }

    private int minPos(final int a, final int b) {
        return a >= 0 && b >= 0 ? Math.min(a, b) : Math.max(a, b);
    }
}
