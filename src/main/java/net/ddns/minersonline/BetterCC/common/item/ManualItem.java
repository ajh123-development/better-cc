/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.item;

import li.cil.manual.api.ManualModel;
import li.cil.manual.api.ManualScreenStyle;
import li.cil.manual.api.ManualStyle;
import li.cil.manual.api.prefab.item.AbstractManualItem;
import net.ddns.minersonline.BetterCC.client.manual.Manuals;
import net.ddns.minersonline.BetterCC.client.manual.ModManualScreenStyle;
import net.ddns.minersonline.BetterCC.client.manual.ModManualStyle;
import net.ddns.minersonline.BetterCC.common.util.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public final class ManualItem extends AbstractManualItem {
    public ManualItem() {
        super(new Properties().tab(ItemGroup.COMMON));
    }

    ///////////////////////////////////////////////////////////////////

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level level, final List<Component> tooltip, final TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        TooltipUtils.tryAddDescription(stack, tooltip);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected ManualModel getManualModel() {
        return Manuals.MANUAL.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected ManualStyle getManualStyle() {
        return ModManualStyle.INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected ManualScreenStyle getScreenStyle() {
        return ModManualScreenStyle.INSTANCE;
    }
}
