package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;

import com.chlorobamagames.green_alex_mod.registries.datagen.ModItemModelProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModItemTagProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class ModItems {

    // Create a Deferred Register to hold Items which will all be registered under the "greenalexmod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GreenAlexMod.MODID);

    // ==========================
    // Non-block items go here
    // ==========================

    public record ArmorSet(
            DeferredItem<Item> helmet,
            DeferredItem<Item> chestplate,
            DeferredItem<Item> leggings,
            DeferredItem<Item> boots
    ){}

    public static ArmorSet registerArmorSet(
            String baseName,
            ArmorMaterial material
    ) {
        return new ArmorSet(
            registerArmor(baseName + "_helmet", material, ArmorItem.Type.HELMET),
            registerArmor(baseName + "_chestplate", material, ArmorItem.Type.CHESTPLATE),
            registerArmor(baseName + "_leggings", material, ArmorItem.Type.LEGGINGS),
            registerArmor(baseName + "_boots", material, ArmorItem.Type.BOOTS)
        );
    }

    public static DeferredItem<Item> registerArmor(
            String name,
            ArmorMaterial material,
            ArmorItem.Type type
    ) {
        Set<TagKey<Item>> armorTags = Set.of();
        armorTags = switch (type) {
            case BOOTS -> Set.of(ItemTags.FOOT_ARMOR, ItemTags.FOOT_ARMOR_ENCHANTABLE);
            case LEGGINGS -> Set.of(ItemTags.LEG_ARMOR, ItemTags.LEG_ARMOR_ENCHANTABLE);
            case CHESTPLATE -> Set.of(ItemTags.CHEST_ARMOR, ItemTags.CHEST_ARMOR_ENCHANTABLE);
            case HELMET -> Set.of(ItemTags.HEAD_ARMOR, ItemTags.HEAD_ARMOR_ENCHANTABLE);
            default -> armorTags;
        };
        return ModItems.registerItem(
                name,
                () -> new ArmorItem(Holder.direct(material), type, new Item.Properties()),
                armorTags
        );
    }

    // Helper method to register and auto-add to creative tab
    public static <T extends Item> DeferredItem<T> registerItem(
            String name,
            Supplier<T> itemSupplier,
            Set<TagKey<Item>> itemTags
    ) {
        DeferredItem<T> item =
                ITEMS.register(name, itemSupplier);
        ModItemTagProvider.addItemTag(item, itemTags);
        ModItemModelProvider.addItem(item);
        //ModCreativeTabs.addToTab(item);
        return item;
    }

    public static <T extends Block> DeferredItem<BlockItem> registerBlockItem(
            String name,
            DeferredBlock<T> block,
            Item.Properties properties,
            Set<TagKey<Item>> itemTags
    ) {
        DeferredItem<BlockItem> blockItem =
            ITEMS.register(
                name,
                () -> new BlockItem(block.get(), properties)
            );
        ModItemTagProvider.addItemTag(blockItem, itemTags);
        //ModCreativeTabs.addToTab(blockItem);
        return blockItem;
    }

}