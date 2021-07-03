package net.ddns.minersonline.better_cc.items.wrench;

import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {
    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getPlayer().getCommandSenderWorld();
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }

        BlockPos pos =  context.getClickedPos();
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            Block block = blockEntity.getBlockState().getBlock();
            if ( block instanceof IWrenchMe){
                ((IWrenchMe) block).onWrench(world, blockEntity.getBlockState(), context.getPlayer());
            }
        }
        return ActionResultType.CONSUME;
    }
}
