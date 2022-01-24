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
        BlockPos pos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity != null && !level.isClientSide()){
            System.out.println("Hi?");
            Block block = blockEntity.getBlockState().getBlock();
            if(block instanceof IWrenchMe){
                boolean done = ((IWrenchMe) block).onWrench(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), useOnContext.getPlayer());
                if(done){
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}