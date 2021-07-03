package net.ddns.minersonline.better_cc.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IWrenchMe {
    boolean onWrench(World world, BlockState state, PlayerEntity player);
}
