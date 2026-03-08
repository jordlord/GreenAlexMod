package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;

import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "greenalexmod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GreenAlexMod.MODID);

    private static final List<DeferredItem<?>> greenAlexItems = new ArrayList<>();

    public static <I extends Item> void addToCreativeTab(DeferredItem<I> item){
        greenAlexItems.add(item);
    }

    public static boolean loadItemsToCreativeTab() {
        CREATIVE_MODE_TABS.register(GreenAlexMod.MODID, () -> CreativeModeTab.builder()
                .icon(() -> GreenAlexBlocks.GREEN_ALEXANIUM_BLOCK.block().item().toStack())
                .title(Component.translatable("creativetab.green_alex_mod.green_alex_items"))
                .displayItems((itemDisplayParameters, output) -> {
                    for (DeferredItem<?> item : greenAlexItems) {
                        output.accept(item);
                    }
                })
                .build()
        );
        return true;
    }

    public static final boolean addItemsToTab = loadItemsToCreativeTab();

}
