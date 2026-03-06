package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockLootTableProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockStateProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockTagProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModRecipeProvider;
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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
            Function<BlockBehaviour.Properties, T> baseBlock,
            BlockBehaviour.Properties properties,
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

        public Supplier<T> supplier(){
            return () -> baseBlock.apply(properties);
        }
    }

    public static <T extends Block> BlockEntry<T> registerOre(
            String baseName,
            Function<BlockBehaviour.Properties, T> baseBlock,
            BlockBehaviour.Properties properties,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties itemProperties,
            Set<TagKey<Item>> itemTags,
            DeferredItem<Item> oreDrop,
            int minimumDrop, int maximumDrop
    ) {
        return registerOre(new BlockRegistrationParameters<>(
                baseName,
                baseBlock,
                properties,
                blockTags,
                itemProperties,
                itemTags
                ),
                oreDrop, minimumDrop, maximumDrop);
    }

    public static <T extends Block> BlockSuite<T> registerBlockSuite(
            String baseName,
            Function<BlockBehaviour.Properties, T> baseBlock,
            BlockBehaviour.Properties properties,
            ModBlockTagProvider.BlockTagGrouping blockTags,
            Item.Properties itemProperties,
            Set<TagKey<Item>> itemTags,
            BlockSuiteParams params
    ) {
        return registerBlockSuite(new BlockRegistrationParameters<>(
                baseName,
                baseBlock,
                properties,
                blockTags,
                itemProperties,
                itemTags
        ), params);
    }

    // ==========================
    // Auto-register block + item
    // ==========================
    // Use for simple blocks
    protected static <T extends Block> BlockEntry<T> registerBlock(
            BlockRegistrationParameters<T> regParams,
            ModBlockStateProvider.BlockModelAdder<T> modelAdder,
            ModBlockLootTableProvider.LootTableAdder<T> lootTableAdder
            ) {

        // Register the block
        DeferredBlock<T> deferredBlock = BLOCKS.register(regParams.baseName, regParams.supplier());

        // Register the corresponding BlockItem
        DeferredItem<BlockItem> blockItem =
                ModItems.registerBlockItem(
                        regParams.baseName,
                        deferredBlock,
                        regParams.itemProperties,
                        regParams.itemTags);

        // Data gen model
        modelAdder.add(deferredBlock);
        // Data gen block tags
        ModBlockTagProvider.addBlockTags(deferredBlock, regParams.blockTags);
        // Data gen loot table
        lootTableAdder.add(deferredBlock);

        return new BlockEntry<>(deferredBlock, blockItem);
    }

    protected static <T extends Block> BlockEntry<T> registerBasicBlock(
            BlockRegistrationParameters<T> regParams
    ) {
        return registerBlock(
                regParams,
                ModBlockStateProvider::addBlock,
                ModBlockLootTableProvider::addBlockToLootTable);
    }

    protected static <T extends Block, B extends Block> BlockEntry<T> registerInheritedBlock(
            BlockRegistrationParameters<T> regParams,
            DeferredBlock<B> baseBlock
    ) {
        return registerBlock(
                regParams,
                newBlock -> ModBlockStateProvider.addInheritedBlock(baseBlock, newBlock),
                ModBlockLootTableProvider::addBlockToLootTable);
    }

    protected static <T extends Block> BlockEntry<T> registerOre(
            BlockRegistrationParameters<T> regParams,
            DeferredItem<Item> oreDrop,
            int minimumDrop, int maximumDrop
            ) {
        // Register the block
        return registerBlock(
                regParams,
                ModBlockStateProvider::addBlock,
                (block) -> ModBlockLootTableProvider.addOreToLootTable(block, oreDrop, minimumDrop, maximumDrop)
        );
    }

    protected static <B extends Block> BlockSuite<B> registerBlockSuite(
            BlockRegistrationParameters<B> regParams,
            BlockSuiteParams blockSuiteParams
    ){

        BlockEntry<B> block =
            registerBasicBlock(
                new BlockRegistrationParameters<>(
                    regParams.baseName + "_block",
                    regParams.baseBlock,
                    regParams.properties,
                    regParams.blockTags,
                    regParams.itemProperties,
                    regParams.itemTags
                ));

        return new BlockSuite<>(
            block,
            registerSuitePart(regParams, block,
                    blockSuiteParams.stairs,
                    "_stairs",
                    props -> new StairBlock(block.block().get().defaultBlockState(), props),
                    ModRecipeProvider.ShapedCraftingRecipe::stairs,
                    BlockTags.STAIRS, ItemTags.STAIRS),
            registerSuitePart(regParams, block,
                    blockSuiteParams.slab,
                    "_slab",
                    SlabBlock::new,
                    ModRecipeProvider.ShapedCraftingRecipe::slabs,
                    BlockTags.SLABS, ItemTags.SLABS),
            registerSuitePart(regParams, block,
                    blockSuiteParams.fence,
                    "_fence",
                    FenceBlock::new,
                    ModRecipeProvider.ShapedCraftingRecipe::fences,
                    BlockTags.FENCES, ItemTags.FENCES),
            registerSuitePart(regParams, block,
                    blockSuiteParams.fenceGate,
                    "_fence_gate",
                    props -> new FenceGateBlock(WoodType.CRIMSON, props),
                    ModRecipeProvider.ShapedCraftingRecipe::fenceGates,
                    BlockTags.FENCE_GATES, ItemTags.FENCE_GATES),
            registerSuitePart(regParams, block,
                    blockSuiteParams.wall,
                    "_wall",
                    WallBlock::new,
                    ModRecipeProvider.ShapedCraftingRecipe::walls,
                    BlockTags.WALLS, ItemTags.WALLS),
            registerSuitePart(regParams, block,
                    blockSuiteParams.button,
                    "_button",
                    props -> new ButtonBlock(BlockSetType.CRIMSON, 10, props),
                    ModRecipeProvider.ShapedCraftingRecipe::button,
                    BlockTags.BUTTONS, ItemTags.BUTTONS),
            registerSuitePart(regParams, block,
                    blockSuiteParams.pressurePlate,
                    "_pressure_plate",
                    props -> new PressurePlateBlock(BlockSetType.CRIMSON, props),
                    ModRecipeProvider.ShapedCraftingRecipe::pressurePlate,
                    BlockTags.PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES)
        );
    }


    private static <T extends Block, B extends Block> Optional<BlockEntry<T>> registerSuitePart(
            BlockRegistrationParameters<B> regParams,
            BlockEntry<B> baseBlock,
            boolean enabled,
            String suffix,
            Function<BlockBehaviour.Properties, T> factory,
            BiFunction<DeferredItem<BlockItem>, DeferredItem<BlockItem>, ModRecipeProvider.ShapedCraftingRecipe> recipe,
            TagKey<Block> blockTagKey, TagKey<Item> itemTagKey
    ) {
        if (enabled) {
            BlockEntry<T> blockEntry =
                registerInheritedBlock(
                    new BlockRegistrationParameters<>(
                        regParams.baseName() + suffix,
                        factory,
                        regParams.properties(),
                        regParams.appendBlockTag(blockTagKey),
                        regParams.itemProperties(),
                        regParams.appendItemTag(itemTagKey)
                    ),
                    baseBlock.block()
                );
            ModRecipeProvider.addShapedRecipe(recipe.apply(baseBlock.item(), blockEntry.item()));
            return Optional.of(blockEntry);
        } else {
            return Optional.empty();
        }
    }
}
