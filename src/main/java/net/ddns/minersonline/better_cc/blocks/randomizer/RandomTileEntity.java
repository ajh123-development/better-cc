package net.ddns.minersonline.better_cc.blocks.randomizer;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.peripheral.RandomPeripheral;
import net.ddns.minersonline.better_cc.setup.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RandomTileEntity extends LockableTileEntity implements ITickableTileEntity {
    public RandomTileEntity() {
        super(ModTileEntityTypes.RANDOM.get());
    }

    private RandomBlock getRandomBlock(){
        BlockState blockstate = this.getBlockState();
        Block block = blockstate.getBlock();
        if (block instanceof RandomBlock) {
            RandomBlock randomBlock = (RandomBlock) block;
            return randomBlock;
        } else {
            return null;
        }
    }

    @Override
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            BlockState blockstate = this.getBlockState();
            Block block = blockstate.getBlock();
            if (block instanceof RandomBlock) {
                RandomBlock randomBlock = (RandomBlock) block;
                RandomBlock.updateSignalStrength(randomBlock, blockstate, this.level, this.worldPosition);
            }
        }

    }

    @Override
    protected ITextComponent getDefaultName() {
        return null;
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return null;
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    private LazyOptional<IPeripheral> peripheralCap;
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable final Direction side )
    {
        if( cap == CAPABILITY_PERIPHERAL )
        {
            RandomBlock randomBlock = getRandomBlock();
            if( peripheralCap == null ) peripheralCap = LazyOptional.of( () -> new RandomPeripheral( this, randomBlock ) );
            return peripheralCap.cast();
        }

        return super.getCapability( cap, side );
    }
}