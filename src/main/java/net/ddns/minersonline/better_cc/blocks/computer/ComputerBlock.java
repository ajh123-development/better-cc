package net.ddns.minersonline.better_cc.blocks.computer;

import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.sleepymouse.microprocessor.IBaseDevice;
import net.sleepymouse.microprocessor.ProcessorException;
import net.sleepymouse.microprocessor.Z80.Z80Core;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ComputerBlock extends HorizontalBlock {

    public Memory cpuRAM = new Memory();
    public IBaseDevice cpuIO;
    public Z80Core cpuCore = new Z80Core(cpuRAM, cpuRAM);
    public boolean starting = true;

    public void loadHexStringIntoRAM(String s) {
        int len = s.length();
        for (int i = 0; i < len; i += 2) {
            cpuRAM.writeByte((int)i/2, (int) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16)));
        }
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }

    public void loadFileIntoRAM(String s, int offset)
    {
        try {
            byte[] filebytes = getBytesFromFile(new File(s));

            for (int i = 0; i < filebytes.length; i++)
            {
                cpuRAM.writeByte(offset+i, filebytes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFileIntoRAM(InputStream s, int offset)
    {
        try {
            byte[] filebytes = IOUtils.toByteArray(s);

            for (int i = 0; i < filebytes.length; i++)
            {
                cpuRAM.writeByte(offset+i, filebytes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.better-cc.computer");
    PlayerEntity lastPlayer;

    public ComputerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));

//        InputStream input = getClass().getResourceAsStream("/assets/cpu/INTMINI.OBJ");
//        InputStream input2 = getClass().getResourceAsStream("/assets/cpu/BASICMINI.OBJ");
//
//        loadFileIntoRAM(input, 0x0);
//        loadFileIntoRAM(input2, 0x100);
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState().setValue(FACING, itemUseContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, POWER);
    }

    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    public int getSignal(BlockState state, IBlockReader blockReader, BlockPos pos, Direction direction) {
        return state.getValue(POWER);
    }
    public static void updateSignalStrength(BlockState state, World world, BlockPos pos) {
        if (world.dimensionType().hasSkyLight()) {
            int i = 0;

            i = MathHelper.clamp(i, 0, 15);
            if (state.getValue(POWER) != i) {
                world.setBlock(pos, state.setValue(POWER, Integer.valueOf(i)), 3);
            }

        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        updateSignalStrength(state, world, pos);
        this.interactWith(world, pos, player);
        this.lastPlayer = player;


        return ActionResultType.CONSUME;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof ComputerTileEntity && player instanceof ServerPlayerEntity) {
            ComputerTileEntity te = (ComputerTileEntity) tileEntity;
            NetworkHooks.openGui((ServerPlayerEntity) player, te, te::encodeExtraData);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ComputerTileEntity();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public void tick(World world, BlockState state){
        if (this.starting) {
            try {
                InputStream input = better_cc.class.getClassLoader().getResourceAsStream("assets/better-cc/cpu/INTMINI.OBJ");
                InputStream input2 = better_cc.class.getClassLoader().getResourceAsStream("assets/better-cc/cpu/BASICMINI.OBJ");

                loadFileIntoRAM(input, 0x0);
                loadFileIntoRAM(input2, 0x100);
                starting = false;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            try {
                cpuCore.executeOneInstruction();
            } catch (ProcessorException e) {
                e.printStackTrace();
            }
        }
    }
}
