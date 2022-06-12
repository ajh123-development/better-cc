/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.item;

import net.ddns.minersonline.BetterCC.api.API;
import net.ddns.minersonline.BetterCC.api.bus.device.DeviceTypes;
import net.ddns.minersonline.BetterCC.client.renderer.entity.RobotWithoutLevelRenderer;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.energy.EnergyStorageItemStack;
import net.ddns.minersonline.BetterCC.common.entity.Entities;
import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.entity.robot.RobotActions;
import net.ddns.minersonline.BetterCC.common.util.LevelUtils;
import net.ddns.minersonline.BetterCC.common.util.NBTUtils;
import net.ddns.minersonline.BetterCC.common.util.TooltipUtils;
import net.ddns.minersonline.BetterCC.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static net.ddns.minersonline.BetterCC.common.util.NBTUtils.makeInventoryTag;
import static net.ddns.minersonline.BetterCC.common.util.RegistryUtils.key;

public final class RobotItem extends ModItem {
    @Override
    public void fillItemCategory(final CreativeModeTab tab, final NonNullList<ItemStack> items) {
        if (allowdedIn(tab)) {
            items.add(getRobotWithFlash());
        }
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level level, final List<Component> tooltip, final TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        TooltipUtils.addEnergyConsumption(Config.robotEnergyPerTick, tooltip);
        TooltipUtils.addEntityEnergyInformation(stack, tooltip);
        TooltipUtils.addEntityInventoryInformation(stack, tooltip);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable final CompoundTag nbt) {
        if (Config.robotsUseEnergy()) {
            return new EnergyStorageItemStack(stack, Config.robotEnergyStorage, Constants.MOD_TAG_NAME, Constants.ENERGY_TAG_NAME);
        } else {
            return null;
        }
    }

    @Override
    public InteractionResult useOn(final UseOnContext context) {
        final Level level = context.getLevel();
        final BlockPos pos = context.getClickedPos();

        final Vec3 position;
        if (level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
            position = Vec3.atCenterOf(pos);
        } else {
            position = Vec3.atCenterOf(pos.relative(context.getClickedFace()));
        }

        final Robot robot = Entities.ROBOT.get().create(context.getLevel());
        if (robot == null) {
            return InteractionResult.FAIL;
        }

        robot.moveTo(position.x, position.y - robot.getBbHeight() * 0.5f, position.z,
            Direction.fromYRot(context.getRotation()).getOpposite().toYRot(), 0);
        if (!level.noCollision(robot)) {
            return super.useOn(context);
        }

        if (!level.isClientSide()) {
            RobotActions.initializeData(robot);
            robot.importFromItemStack(context.getItemInHand());

            level.addFreshEntity(robot);
            LevelUtils.playSound(level, new BlockPos(position), SoundType.METAL, SoundType::getPlaceSound);

            if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        if (context.getPlayer() != null) {
            context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void initializeClient(final Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return new RobotWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            }
        });
    }

    ///////////////////////////////////////////////////////////////////

    private ItemStack getRobotWithFlash() {
        final ItemStack robot = new ItemStack(this);

        final CompoundTag itemsTag = NBTUtils.getOrCreateChildTag(robot.getOrCreateTag(), API.MOD_ID, Constants.ITEMS_TAG_NAME);
        itemsTag.put(key(DeviceTypes.FLASH_MEMORY), makeInventoryTag(
            new ItemStack(Items.FLASH_MEMORY_CUSTOM.get())
        ));

        return robot;
    }
}
