package net.ddns.minersonline.BetterCC.items;

import net.ddns.minersonline.BetterCC.IWrenchMe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
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
			Block block = blockEntity.getBlockState().getBlock();
			if(block instanceof IWrenchMe){
				ServerPlayer player = (ServerPlayer) useOnContext.getPlayer();
				boolean done = ((IWrenchMe) block).onWrench(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), player);
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
