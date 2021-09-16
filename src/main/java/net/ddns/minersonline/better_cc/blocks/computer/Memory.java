package net.ddns.minersonline.better_cc.blocks.computer;

import java.nio.charset.StandardCharsets;

import net.sleepymouse.microprocessor.IBaseDevice;
import net.sleepymouse.microprocessor.IMemory;

public class Memory implements IMemory, IBaseDevice {

    public String buffer = "";

    static int CONIN = 0x80;//3;
    static int CONOUT = 0x81;//1;

    static int CONIN2 = 2;

    public int key = 0;

    int[] io = new int[0xff];
    int[] ram = new int[128*1024];

    public Memory() {
    }

    @Override
    public int readByte(int address) {
        return ram[address];
    }

    @Override
    public int readWord(int address) {
        return readByte(address)+(readByte(address + 1)*256);
    }

    @Override
    public void writeByte(int address, int data) {
        ram[address] = data&0xff;
    }

    @Override
    public void writeWord(int address, int data) {
        writeByte(address, (data&0x00ff));
        writeByte(address+1, (data&0xff00)>>8);
    }

    @Override
    public int IORead(int address) {
        if (address == CONIN) // con in
        {
            return 1<<2|1<<1|1;
        }


        if (address == CONIN2)
        {
            return (1<<2);
        }

        if (address == CONOUT) // con in
        {
            return key;
        }


        return io[address];
    }

    @Override
    public void IOWrite(int address, int data) {
        if (address == 9999)
        {
            if (data == '\n' || data == '\r')
                return;

            byte[] _bytes = new byte[2];
            _bytes[0] = (byte) data;
            buffer = buffer + new String(_bytes, StandardCharsets.UTF_8);
        }

        if (address == CONOUT) // con out
        {
            if (data != 0xc && data < 130)
            {
                if (data == '\n')
                {
                    buffer = buffer + "\n";
                    return;
                }

                if (data == '\r')
                    return;

                byte[] _bytes = new byte[2];
                _bytes[0] = (byte) data;

                buffer = buffer + new String(_bytes, StandardCharsets.UTF_8);
            }
        }
        io[address] = data;
    }
}