package com.chlorobamagames.green_alex_mod.mod_files;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class GreenAlexItems {
    public static final DeferredItem<Item> GREEN_ALEXITE_ITEM = ModItems.registerItem(
            "green_alexite_item",
            () -> new Item(new Item.Properties()
                    .fireResistant())
    );

    public static final DeferredItem<Item> GREEN_ALEXANIUM_INGOT = ModItems.registerItem(
            "green_alexanium_item",
            () -> new Item(new Item.Properties()
                    .fireResistant())
    );

    public static final DeferredItem<Item> GREEN_ALEX_SOUL = ModItems.registerItem(
            "green_alex_soul_item",
            () -> new Item(new Item.Properties()
                    .fireResistant())
    );

    public static final Supplier<Ingredient> REPAIRS_GREEN_ALEX =
            () -> Ingredient.of(
                    TagKey.create(
                            Registries.ITEM,
                            ResourceLocation.fromNamespaceAndPath(
                                    GreenAlexMod.MODID,
                                    "repairs_green_alex_armor"
                            )
                    )
            );
}
