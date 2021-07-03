package net.ddns.minersonline.better_cc.setup;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {

    private static <T extends EntityType> RegistryObject<?> register(String name, Supplier<T> entity){
        return Registration.ENTITIES.register(name, entity);
    }
}
