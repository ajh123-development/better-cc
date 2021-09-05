package net.ddns.minersonline.better_cc.items.punch_card;

import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PunchCardItem extends Item {
    public PunchCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getLevel();
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.CONSUME;
    }
}
