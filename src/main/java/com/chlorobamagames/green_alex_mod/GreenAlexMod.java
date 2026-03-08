package com.chlorobamagames.green_alex_mod;

import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor.GreenAlexArmorItem;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexBlocks;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexItems;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexRecipes;
import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import com.chlorobamagames.green_alex_mod.registries.ModCreativeTabs;
import com.chlorobamagames.green_alex_mod.registries.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(GreenAlexMod.MODID)
public class GreenAlexMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "green_alex_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Object load_green_alex_blocks = new GreenAlexBlocks();
    public static final Object load_green_alex_armor = new GreenAlexArmorItem();
    public static final Object load_green_alex_items = new GreenAlexItems();
    public static final Object load_green_alex_recipes = new GreenAlexRecipes();
    public static final Object load_creative_tabs = new ModCreativeTabs();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public GreenAlexMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (GreenAlexMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        //modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
