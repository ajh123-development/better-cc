package net.ddns.minersonline.BetterCC.items;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItem extends Item {
	public ModItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		String[] desc = Component.translatable("item."+ BetterCC.MOD_ID + "." + this+".desc").toString().split("\n");
		for (String line : desc) {
			list.add(Component.literal(line));
		}
	}
}
