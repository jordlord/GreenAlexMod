package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block> EXAMPLE = createTag("EXAMPLE");

        public static final TagKey<Block> GREEN = createTag("green");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, name));
        }
    }

    public static class Items {
        //public static final TagKey<Item> EXAMPLE = createTag("EXAMPLE");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> GREEN = createTag("green");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, name));
        }
    }
}
