package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "greenalexmod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GreenAlexMod.MODID);

}
