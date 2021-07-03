package net.ddns.minersonline.better_cc.states;

import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComputerState extends EnumProperty<ComputerState.ComputerStateEnum> {
    public ComputerState(String p_i45649_1_, Class<ComputerStateEnum> p_i45649_2_, Collection<ComputerStateEnum> p_i45649_3_) {
        super(p_i45649_1_, p_i45649_2_, p_i45649_3_);
    }


    enum ComputerStateEnum implements IStringSerializable {
        ON,
        OFF,
        SLEEPING;

        @Override
        public String getSerializedName() {
            return "";
        }
    }
}