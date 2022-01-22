package net.ddns.minersonline.better_cc.items;

import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WrenchItem extends Item {
    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if(level.isClientSide()){
            return InteractionResult.SUCCESS;
        }
        BlockPos pos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity != null){
            Block block = blockEntity.getBlockState().getBlock();
            if(block instanceof IWrenchMe){
                ((IWrenchMe) block).onWrench(level, blockEntity.getBlockState(), useOnContext.getPlayer());
            }
        }
        return InteractionResult.CONSUME;
    }
}