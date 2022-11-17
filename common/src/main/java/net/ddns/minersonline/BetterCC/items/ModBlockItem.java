package net.ddns.minersonline.BetterCC.items;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBlockItem extends BlockItem {
	public ModBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		String desc = Component.translatable("item."+ BetterCC.MOD_ID + "." + this+".desc").getString();
		for (String line : desc.split("\n")) {
			list.add(Component.literal(line));
		}
	}
}
