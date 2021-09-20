package net.ddns.minersonline.better_cc.blocks.computer;

import net.ddns.minersonline.better_cc.blocks.machinebase.UpgradeableMachineBlockBase;
import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.sleepymouse.microprocessor.IBaseDevice;
import net.sleepymouse.microprocessor.ProcessorException;
import net.sleepymouse.microprocessor.Z80.Z80Core;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ComputerBlock extends UpgradeableMachineBlockBase {

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

    public ComputerBlock(Properties properties) {
        super(properties);
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

    @Override
    public void tick(World world, BlockState state){
        super.tick(world, state);
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

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ComputerTileEntity();
    }

}
