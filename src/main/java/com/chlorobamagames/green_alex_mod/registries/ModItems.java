package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {

    // Create a Deferred Register to hold Items which will all be registered under the "greenalexmod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GreenAlexMod.MODID);

    // ==========================
    // Non-block items go here
    // ==========================

    // Helper method to register and auto-add to creative tab
    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        DeferredItem<Item> item =
                ITEMS.register(name, itemSupplier);
        //ModCreativeTabs.addToTab(item);
        return item;
    }

    public static <T extends Block> DeferredItem<BlockItem> registerBlockItem(
            String name,
            DeferredBlock<T> block,
            Item.Properties properties) {
        DeferredItem<BlockItem> blockItem =
                ITEMS.register(name, () -> new BlockItem(block.get(), properties)
        );
        //ModCreativeTabs.addToTab(blockItem);
        return blockItem;
    }

}