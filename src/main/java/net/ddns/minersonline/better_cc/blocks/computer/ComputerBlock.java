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

import javax.annotation.Nullable;

import net.minecraftforge.fml.network.NetworkHooks;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.StringLib;


public class ComputerBlock extends HorizontalBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.better-cc.computer");
    PlayerEntity lastPlayer;

    static Globals server_globals;
    public ComputerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));

        // Create server globals with just enough library support to compile user scripts.
        server_globals = new Globals();
        server_globals.load(new JseBaseLib());
        server_globals.load(new PackageLib());
        server_globals.load(new StringLib());


        // To load scripts, we occasionally need a math library in addition to compiler support.
        // To limit scripts using the debug library, they must be closures, so we only install LuaC.
        server_globals.load(new JseMathLib());
        LoadState.install(server_globals);
        LuaC.install(server_globals);

        // Set up the LuaString metatable to be read-only since it is shared across all scripts.
        LuaString.s_metatable = new ReadOnlyLuaTable(LuaString.s_metatable);


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

        runScriptInSandbox( "print('hello')");


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

    }



    // Run a script in a lua thread and limit it to a certain number
    // of instructions by setting a hook function.
    // Give each script its own copy of globals, but leave out libraries
    // that contain functions that can be abused.
    void runScriptInSandbox(String script) {

        // Each script will have it's own set of globals, which should
        // prevent leakage between scripts running on the same server.
        Globals user_globals = new Globals();
        user_globals.load(new JseBaseLib());
        user_globals.load(new PackageLib());
        user_globals.load(new Bit32Lib());
        user_globals.load(new TableLib());
        user_globals.load(new JseMathLib());
        user_globals.load(new StringLib());


        // This library is dangerous as it gives unfettered access to the
        // entire Java VM, so it's not suitable within this lightweight sandbox.
        // user_globals.load(new LuajavaLib());

        // Starting coroutines in scripts will result in threads that are
        // not under the server control, so this libary should probably remain out.
        // user_globals.load(new CoroutineLib());

        // These are probably unwise and unnecessary for scripts on servers,
        // although some date and time functions may be useful.
        // user_globals.load(new JseIoLib());
        // user_globals.load(new JseOsLib());

        // Loading and compiling scripts from within scripts may also be
        // prohibited, though in theory it should be fairly safe.
        // LoadState.install(user_globals);
        // LuaC.install(user_globals);

        // The debug library must be loaded for hook functions to work, which
        // allow us to limit scripts to run a certain number of instructions at a time.
        // However we don't wish to expose the library in the user globals,
        // so it is immediately removed from the user globals once created.
        user_globals.load(new DebugLib());
        LuaValue sethook = user_globals.get("debug").get("sethook");
        user_globals.set("debug", LuaValue.NIL);

        ComputerLib lib = new ComputerLib(this);
        user_globals.set("print", new ComputerLib.print(lib));
        user_globals.set("computer", lib);

        // Set up the script to run in its own lua thread, which allows us
        // to set a hook function that limits the script to a specific number of cycles.
        // Note that the environment is set to the user globals, even though the
        // compiling is done with the server globals.
//        LuaValue chunk = server_globals.load(script, "main", user_globals);
        LuaValue chunk = server_globals.load(script, "main");

        LuaThread thread = new LuaThread(user_globals, chunk);



        // Set the hook function to immediately throw an Error, which will not be
        // handled by any Lua code other than the coroutine.
        LuaValue hookfunc = new ZeroArgFunction() {
            public LuaValue call() {
                // A simple lua error may be caught by the script, but a
                // Java Error will pass through to top and stop the script.
                throw new Error("Script overran resource limits.");
            }
        };
        final int instruction_count = 20;

        sethook.invoke(LuaValue.varargsOf(new LuaValue[] { thread, hookfunc,
                LuaValue.EMPTYSTRING, LuaValue.valueOf(instruction_count) }));

        // When we resume the thread, it will run up to 'instruction_count' instructions
        // then call the hook function which will error out and stop the script.
        Varargs result = thread.resume(LuaValue.NIL);
        better_cc.LOGGER.info(""+script+" -> "+result);
    }

    // Simple read-only table whose contents are initialized from another table.
    static class ReadOnlyLuaTable extends LuaTable {
        public ReadOnlyLuaTable(LuaValue table) {
            presize(table.length(), 0);
            for (Varargs n = table.next(LuaValue.NIL); !n.arg1().isnil(); n = table
                    .next(n.arg1())) {
                LuaValue key = n.arg1();
                LuaValue value = n.arg(2);
                super.rawset(key, value.istable() ? new ReadOnlyLuaTable(value) : value);
            }
        }
        public LuaValue setmetatable(LuaValue metatable) { return error("table is read-only"); }
        public void set(int key, LuaValue value) { error("table is read-only"); }
        public void rawset(int key, LuaValue value) { error("table is read-only"); }
        public void rawset(LuaValue key, LuaValue value) { error("table is read-only"); }
        public LuaValue remove(int pos) { return error("table is read-only"); }
    }
}
