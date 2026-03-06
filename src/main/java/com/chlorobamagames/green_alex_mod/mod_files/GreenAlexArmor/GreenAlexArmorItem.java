package com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexItems;
import com.chlorobamagames.green_alex_mod.registries.ModItems;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModRecipeProvider;
import com.google.common.collect.Maps;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GreenAlexArmorItem {

    public static List<ArmorMaterial.Layer> getLayers(String baseName) {
        return List.of(
                new ArmorMaterial.Layer(
                        ResourceLocation.fromNamespaceAndPath(
                                GreenAlexMod.MODID,
                                baseName
                        )
                )
        );
    }

    public static final ArmorMaterial GREEN_ALEXITE_MATERIAL = new ArmorMaterial(
            makeDefense(
                    5,
                    8,
                    10,
                    5,
                    15),
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            GreenAlexItems.REPAIRS_GREEN_ALEX,
            getLayers("green_alex"),
            4.0F,
            0.1F
    );

    @SuppressWarnings("SameParameterValue")
    private static Map<ArmorItem.Type, Integer> makeDefense(
            int boot_defence_stat,
            int leggings_defence_stat,
            int chestplate_defence_stat,
            int helmet_defence_stat,
            int body_defence_stat) {
        return Maps.newEnumMap(
                Map.of(
                        ArmorItem.Type.BOOTS,
                        boot_defence_stat,
                        ArmorItem.Type.LEGGINGS,
                        leggings_defence_stat,
                        ArmorItem.Type.CHESTPLATE,
                        chestplate_defence_stat,
                        ArmorItem.Type.HELMET,
                        helmet_defence_stat,
                        ArmorItem.Type.BODY,
                        body_defence_stat
                )
        );
    }

    public static ModItems.ArmorSet GREEN_ALEX_ARMOR =
            ModItems.registerArmorSet(
                    "green_alex",
                    GREEN_ALEXITE_MATERIAL);


}
