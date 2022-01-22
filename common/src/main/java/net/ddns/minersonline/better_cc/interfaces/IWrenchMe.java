package net.ddns.minersonline.better_cc.interfaces;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IWrenchMe {
    boolean onWrench(Level world, BlockState state, Player player);
}