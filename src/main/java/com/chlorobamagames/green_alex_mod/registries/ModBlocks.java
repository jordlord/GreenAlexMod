package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockLootTableProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockStateProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockTagProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    // Create a Deferred Register to hold Blocks which will all be registered under the "green_alex_mod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GreenAlexMod.MODID);

    public record BlockEntry<T extends Block>(
            DeferredBlock<T> block,
            DeferredItem<BlockItem> item
    ) {}

    public record BlockSuite <T extends Block>(
            BlockEntry<T> block,
            Optional<BlockEntry<StairBlock>> stair,
            Optional<BlockEntry<SlabBlock>> slab,
            Optional<BlockEntry<FenceBlock>> fence,
            Optional<BlockEntry<FenceGateBlock>> fenceGate,
            Optional<BlockEntry<WallBlock>> wall,
            Optional<BlockEntry<ButtonBlock>> button,
            Optional<BlockEntry<PressurePlateBlock>> pressurePlate
    ) {}

    public record BlockSuiteParams(
            Boolean stairs,
            Boolean slab,
            Boolean fence,
            Boolean fenceGate,
            Boolean wall,
            Boolean button,
            Boolean pressurePlate
    ) {
        public static BlockSuiteParams all(){
            return new BlockSuiteParams(true, true, true, true, true, true, true);
        }
        public static BlockSuiteParams none(){
            return new BlockSuiteParams(false, false, false, false, false, false, false);
        }
        public static BlockSuiteParams stone(){
            return new BlockSuiteParams(true, true, false, false, true, true, true);
        }
        public static BlockSuiteParams wood(){
            return new BlockSuiteParams(true, true, true, true, false, true, true);
        }
        public static BlockSuiteParams metal(){
            return new BlockSuiteParams(true, true, false, false, true, false, true);
        }
    }

    protected record BlockRegistrationParameters<T extends Block> (
            String baseName,
            Supplier<T> baseBlock,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties itemProperties,
            Set<TagKey<Item>> itemTags
            ) {
        public Set<TagKey<Item>> appendItemTag(TagKey<Item> tag) {
            Set<TagKey<Item>> output = new HashSet<>(itemTags());
            output.add(tag);
            return output;
        }

        public ModBlockTagProvider.BlockTagGrouping appendBlockTag(TagKey<Block> tag){
            return blockTags.appendBlockTag(tag);
        }
    }

    public static <T extends Block> BlockEntry<T> registerBlock(
            String name,
            Supplier<T> blockSupplier,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties blockItemProperties,
            Set<TagKey<Item>> itemTags
    ){
        return registerBlock(
            new BlockRegistrationParameters<>(
                name,
                blockSupplier,
                blockTags,
                blockItemProperties,
                itemTags
            )
        );
    }

    public static <T extends Block> BlockEntry<T> registerOre(
            String name,
            Supplier<T> blockSupplier,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties blockItemProperties,
            Set<TagKey<Item>> itemTags,
            DeferredItem<Item> oreDrop,
            int minimumDrop, int maximumDrop
    ) {
        return registerOre(new BlockRegistrationParameters<>(
                name,
                blockSupplier,
                blockTags,
                blockItemProperties,
                itemTags
                ), oreDrop, minimumDrop, maximumDrop);
    }

    public static <T extends Block> BlockSuite<T> registerBlockSuite(
            String name,
            Supplier<T> blockSupplier,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties blockItemProperties,
            Set<TagKey<Item>> itemTags,
            BlockSuiteParams params
    ) {
        return registerBlockSuite(new BlockRegistrationParameters<>(
                name,
                blockSupplier,
                blockTags,
                blockItemProperties,
                itemTags
        ), params);
    }

    // ==========================
    // Auto-register block + item
    // ==========================
    // Use for simple blocks
    protected static <T extends Block> BlockEntry<T> registerBlock(
            BlockRegistrationParameters<T> regParams) {

        // Register the block
        DeferredBlock<T> reg_block = BLOCKS.register(regParams.baseName, regParams.baseBlock);

        // Register the corresponding BlockItem
        DeferredItem<BlockItem> blockItem =
                ModItems.registerBlockItem(
                        regParams.baseName,
                        reg_block,
                        regParams.itemProperties,
                        regParams.itemTags);

        return new BlockEntry<>(reg_block, blockItem);
    }

    protected static <T extends Block> BlockEntry<T> registerOre(
            BlockRegistrationParameters<T> regParams,
            DeferredItem<Item> oreDrop,
            int minimumDrop, int maximumDrop
            ) {
        BlockEntry<T> reg_block = registerBlock(regParams);
        ModBlockLootTableProvider.addOreToLootTable(reg_block.block(), oreDrop, minimumDrop, maximumDrop);
        return reg_block;
    }

    protected static <T extends Block> BlockSuite<T> registerBlockSuite(
            BlockRegistrationParameters<T> regParams,
            BlockSuiteParams blockSuiteParams
    ){
        BlockEntry<T> block = registerBlock(
                regParams.baseName + "_block",
                regParams.baseBlock,
                regParams.blockTags,
                regParams.itemProperties,
                regParams.itemTags
                );

        BlockSuite<T> output = new BlockSuite<>(
            block,
            registerSuitePart(
                    regParams,
                    blockSuiteParams.stairs,
                    "_stairs",
                    props -> new StairBlock(block.block().get().defaultBlockState(), props),
                    BlockTags.STAIRS, ItemTags.STAIRS),
            registerSuitePart(regParams,
                    blockSuiteParams.slab,
                    "_slab",
                    SlabBlock::new,
                    BlockTags.SLABS, ItemTags.SLABS),
            registerSuitePart(regParams,
                    blockSuiteParams.fence,
                    "_fence",
                    FenceBlock::new,
                    BlockTags.FENCES, ItemTags.FENCES),
            registerSuitePart(
                    regParams,
                    blockSuiteParams.fenceGate,
                    "_fence_gate",
                    props -> new FenceGateBlock(WoodType.CRIMSON, props),
                    BlockTags.FENCE_GATES, ItemTags.FENCE_GATES),
            registerSuitePart(
                    regParams,
                    blockSuiteParams.wall,
                    "_wall",
                    WallBlock::new,
                    BlockTags.WALLS, ItemTags.WALLS),
            registerSuitePart(
                    regParams,
                    blockSuiteParams.button,
                    "_button",
                    props -> new ButtonBlock(BlockSetType.CRIMSON, 10, props),
                    BlockTags.BUTTONS, ItemTags.BUTTONS),
            registerSuitePart(
                    regParams,
                    blockSuiteParams.pressurePlate,
                    "_pressure_plate",
                    props -> new PressurePlateBlock(BlockSetType.CRIMSON, props),
                    BlockTags.PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES)
        );

        ModBlockStateProvider.addBlockSuite(output);
        ModBlockTagProvider.addBlockSuite(output, regParams.blockTags);
        return output;
    }

    private static <T extends Block, B extends Block> Optional<BlockEntry<T>> registerSuitePart(
            BlockRegistrationParameters<B> regParams,
            boolean enabled,
            String suffix,
            Function<BlockBehaviour.Properties, T> factory,
            TagKey<Block> blockTagKey, TagKey<Item> itemTagKey
    ) {
        return enabled
            ? Optional.of(
                registerBlock(
                regParams.baseName() + suffix,
                    () -> factory.apply(regParams.baseBlock.get().properties()),
                    regParams.appendBlockTag(blockTagKey),
                    regParams.itemProperties(),
                    regParams.appendItemTag(itemTagKey)
                ))
            : Optional.empty();
    }
}
