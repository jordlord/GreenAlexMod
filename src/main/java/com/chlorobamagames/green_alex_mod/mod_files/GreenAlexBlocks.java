package com.chlorobamagames.green_alex_mod.mod_files;

import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class GreenAlexBlocks {
    public static final ModBlocks.BlockEntry<DropExperienceBlock> GREEN_ALEX_ORE =
            ModBlocks.registerOre("green_alex_ore",
                    () -> new DropExperienceBlock(
                            UniformInt.of(1, 7),
                            BlockBehaviour.Properties
                                .ofFullCopy(Blocks.DIRT)
                                .strength(2.0f)
                                .requiresCorrectToolForDrops()
                    ),
                    new Item.Properties(),
                    GreenAlexItems.GREEN_ALEXITE_ITEM,
                    1, 1
            );


    public static final ModBlocks.BlockSuite GREEN_ALEXITE_BLOCK =
            ModBlocks.registerBlockSuite("green_alexite",
                    () -> new Block(BlockBehaviour.Properties
                            .ofFullCopy(Blocks.AMETHYST_BLOCK)
                            .mapColor(MapColor.EMERALD)),
                    new Item.Properties(),
                    ModBlocks.BlockSuiteParams.stone()
            );

    public static final ModBlocks.BlockSuite GREEN_ALEXANIUM_BLOCK =
            ModBlocks.registerBlockSuite("green_alexanium",
                    () -> new Block(BlockBehaviour.Properties
                            .ofFullCopy(Blocks.NETHERITE_BLOCK)
                            .mapColor(MapColor.COLOR_GREEN)),
                    new Item.Properties(),
                    ModBlocks.BlockSuiteParams.stone()
            );

}
