package net.ddns.minersonline.better_cc.blocks;

import net.ddns.minersonline.better_cc.setup.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RandomEntity extends BlockEntity {
    public RandomEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlocksEntities.RANDOM.get(), blockPos, blockState);
    }
}
