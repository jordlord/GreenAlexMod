package com.chlorobamagames.green_alex_mod.mod_files;

import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import com.chlorobamagames.green_alex_mod.registries.ModTags;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockTagProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.*;

@SuppressWarnings("unused")
public class GreenAlexBlocks {
    public static final ModBlocks.BlockEntry<DropExperienceBlock> GREEN_ALEX_ORE =
            ModBlocks.registerOre("green_alex_ore",
                    prop -> new DropExperienceBlock(
                            UniformInt.of(1, 7), prop),
                    BlockBehaviour.Properties
                        .ofFullCopy(Blocks.DIRT)
                        .strength(2.0f)
                        .requiresCorrectToolForDrops(),
                    new ModBlockTagProvider.BlockTagGrouping(
                            Set.of(BlockTags.MINEABLE_WITH_SHOVEL),
                            Optional.of(BlockTags.NEEDS_DIAMOND_TOOL),
                            Set.of(BlockTags.DIRT, ModTags.Blocks.GREEN)
                    ),
                    new Item.Properties(),
                    Set.of(),
                    GreenAlexItems.GREEN_ALEXITE_ITEM,
                    1, 1
            );


    public static final ModBlocks.BlockSuite<Block> GREEN_ALEXITE_BLOCK =
            ModBlocks.registerBlockSuite("green_alexite",
                    Block::new,
                    BlockBehaviour.Properties
                            .ofFullCopy(Blocks.AMETHYST_BLOCK)
                            .mapColor(MapColor.EMERALD),
                    new ModBlockTagProvider.BlockTagGrouping(
                        Set.of(BlockTags.MINEABLE_WITH_PICKAXE),
                        Optional.of(BlockTags.NEEDS_DIAMOND_TOOL),
                        Set.of(ModTags.Blocks.GREEN)
                    ),
                    new Item.Properties(),
                    Set.of(),
                    ModBlocks.BlockSuiteParams.metal()
            );

    public static final ModBlocks.BlockSuite<Block> GREEN_ALEXANIUM_BLOCK =
            ModBlocks.registerBlockSuite(
                    "green_alexanium",
                    Block::new,
                    BlockBehaviour.Properties
                            .ofFullCopy(Blocks.NETHERITE_BLOCK)
                            .mapColor(MapColor.COLOR_GREEN),
                    new ModBlockTagProvider.BlockTagGrouping(
                            Set.of(BlockTags.MINEABLE_WITH_PICKAXE),
                            Optional.of(BlockTags.NEEDS_DIAMOND_TOOL),
                            Set.of(ModTags.Blocks.GREEN)
                    ),
                    new Item.Properties(),
                    Set.of(),
                    ModBlocks.BlockSuiteParams.metal()
            );
}
