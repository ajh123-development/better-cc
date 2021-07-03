package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.crafting.recpie.PressingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

public final class ModRecipes {
    public static final class Types {
        public static final IRecipeType<PressingRecipe> PRESSING = IRecipeType.register(
                better_cc.MOD_ID + ":pressing");

        private Types() {}
    }

    public static final class Serializers {
        public static final RegistryObject<IRecipeSerializer<?>> PRESSING = Registration.RECIPE_SERIALIZERS.register(
                "pressing", PressingRecipe.Serializer::new);

        private Serializers() {}
    }

    private ModRecipes() {}

    static void register() {}
}
