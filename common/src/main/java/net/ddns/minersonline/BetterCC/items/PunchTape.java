package net.ddns.minersonline.BetterCC.items;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PunchTape extends ModItem {
	public PunchTape(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		boolean hasData = false;
		if (itemStack.getTag() != null) {
			hasData = itemStack.getTag().contains("data");
		}
		list.add(Component.translatable(
				"hint."+ BetterCC.MOD_ID + ".state"
		).withStyle(ChatFormatting.GRAY));
		if (!hasData) {
			list.add(Component.translatable(
					"hint."+ BetterCC.MOD_ID + ".state.blank"
			).withStyle(ChatFormatting.BLUE));
		} else {
			list.add(Component.translatable(
					"hint."+ BetterCC.MOD_ID + ".state.full"
			).withStyle(ChatFormatting.BLUE));
		}
		list.add(Component.literal(""));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}
