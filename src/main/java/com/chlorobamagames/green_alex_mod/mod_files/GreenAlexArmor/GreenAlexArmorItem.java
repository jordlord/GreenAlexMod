package com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexItems;
import com.chlorobamagames.green_alex_mod.registries.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Map;

public class GreenAlexArmorItem {
    public static final ArmorMaterial GREEN_ALEXITE = new ArmorMaterial(
            makeDefense(5, 8, 10, 5, 15),
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            GreenAlexItems.REPAIRS_GREEN_ALEX,
            List.of(new ArmorMaterial.Layer(
                    ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, "green_alexite")
            )),
            4.0F,
            0.5F
    );

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

    private static DeferredItem<Item> registerGreenAlexArmor(String name, ArmorItem.Type type) {
        return ModItems.registerItem(name,
                () -> new ArmorItem(Holder.direct(GREEN_ALEXITE), type, new Item.Properties())
        );
    }

    public static final DeferredItem<Item> GREEN_ALEX_HELMET =
            registerGreenAlexArmor("green_alex_helmet", ArmorItem.Type.HELMET);

    public static final DeferredItem<Item> GREEN_ALEX_CHESTPLATE =
            registerGreenAlexArmor("green_alex_chestplate", ArmorItem.Type.CHESTPLATE);

    public static final DeferredItem<Item> GREEN_ALEX_LEGGINGS =
            registerGreenAlexArmor("green_alex_leggings", ArmorItem.Type.LEGGINGS);

    public static final DeferredItem<Item> GREEN_ALEX_BOOTS =
            registerGreenAlexArmor("green_alex_boots", ArmorItem.Type.BOOTS);

}
