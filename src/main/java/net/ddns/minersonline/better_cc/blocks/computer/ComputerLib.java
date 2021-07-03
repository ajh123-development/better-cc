package net.ddns.minersonline.better_cc.blocks.computer;

import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.LibFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class ComputerLib extends TwoArgFunction {
    ComputerBlock myBlock = null;
    public ComputerLib(ComputerBlock myBlock) {
        this.myBlock = myBlock;
    }

    @Override
    public LuaValue call(LuaValue var1, LuaValue var2) {
        LuaTable var3 = new LuaTable();
        var3.set("sendMessage", new sendMessage());
        return var3;
    }

    static final class sendMessage extends LibFunction {
        sendMessage() {
        }

        public LuaValue call(LuaValue var1, LuaValue var2) {
            String player = var1.checkjstring();
            String message = var2.checkjstring();

            better_cc.LOGGER.info(player+" "+message);
            return LuaBoolean.TRUE;
        }
    }

    static final class print extends LibFunction {
        ComputerLib lib;
        print(ComputerLib lib) {
            this.lib = lib;
        }

        public LuaValue call(LuaValue var1) {
            String message = var1.checkjstring();

            this.lib.myBlock.lastPlayer.sendMessage(new StringTextComponent(message), this.lib.myBlock.lastPlayer.getUUID());

            return LuaBoolean.TRUE;
        }
    }
}
