package net.ddns.minersonline.BetterCC.items;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.ddns.minersonline.BetterCC.setup.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
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
		String id = "block."+ BetterCC.MOD_ID + "." + this+".desc";
		String desc = Component.translatable(id).getString();
		if (!desc.equals(id)) {
			for (String line : desc.split("\n")) {
				list.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
			}
		}
		boolean isConnectable = getBlock().defaultBlockState().is(ModTags.SERIAL_CABLE_CONNECTABLE);
		if (isConnectable) {
			list.add(Component.literal(""));
			list.add(Component.translatable(
					"hint."+ BetterCC.MOD_ID + ".connects_with_serial"
			).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
		}
	}
}
