package com.chlorobamagames.green_alex_mod.registries;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockLootTableProvider;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModBlockStateProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    // Create a Deferred Register to hold Blocks which will all be registered under the "greenalexmod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GreenAlexMod.MODID);

    public record BlockEntry<T extends Block>(
            DeferredBlock<T> block,
            DeferredItem<BlockItem> item
    ) {}

    public record BlockSuite(
            BlockEntry<Block> block,
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
    }

    // ==========================
    // Auto-register block + item
    // ==========================
    // Use for simple blocks
    public static <T extends Block> BlockEntry<T> registerBlock(
            String name,
            Supplier<T> blockSupplier,
            Item.Properties blockItemProperties) {

        // Register the block
        DeferredBlock<T> reg_block = BLOCKS.register(name, blockSupplier);

        // Register the corresponding BlockItem
        DeferredItem<BlockItem> blockItem =
                ModItems.registerBlockItem(name, reg_block, blockItemProperties);

        return new BlockEntry<T>(reg_block, blockItem);
    }

    public static <T extends Block> BlockEntry<T> registerOre(
            String name,
            Supplier<T> blockSupplier,
            Item.Properties blockItemProperties,
            DeferredItem<Item> oreDrop,
            int minimumDrop, int maximumDrop
            ) {
        BlockEntry<T> reg_block = registerBlock(name, blockSupplier, blockItemProperties);
        ModBlockLootTableProvider.addOreToLootTable(reg_block.block(), oreDrop, minimumDrop, maximumDrop);
        return reg_block;
    }

    private record SuiteContext(
            String baseName,
            DeferredBlock<? extends Block> baseBlock,
            Item.Properties itemProperties,
            BlockBehaviour.Properties blockProperties
    ) {}

    public static BlockSuite registerBlockSuite(
            String name,
            Supplier<Block> blockSupplier,
            Item.Properties blockItemProperties,
            BlockSuiteParams params){
        BlockEntry<Block> block = registerBlock(name + "_block", blockSupplier, blockItemProperties);
        BlockBehaviour.Properties blockProperties = block.block.get().properties();

        SuiteContext ctx = new SuiteContext(
                name,
                block.block(),
                blockItemProperties,
                blockProperties
        );

        BlockSuite output = new BlockSuite(
            block,
            registerSuitePart(ctx, params.stairs, "_stairs",
                    props -> new StairBlock(block.block().get().defaultBlockState(), props)),
            registerSuitePart(ctx, params.slab, "_slab",
                    SlabBlock::new),
            registerSuitePart(ctx, params.fence, "_fence",
                    FenceBlock::new),
            registerSuitePart(ctx, params.fenceGate, "_fence_gate",
                    props -> new FenceGateBlock(WoodType.CRIMSON, props)),
            registerSuitePart(ctx, params.wall, "_wall",
                    WallBlock::new),
            registerSuitePart(ctx, params.button, "_button",
                    props -> new ButtonBlock(BlockSetType.CRIMSON, 10, props)),
            registerSuitePart(ctx, params.pressurePlate, "_pressure_plate",
                    props -> new PressurePlateBlock(BlockSetType.CRIMSON, props))
        );

        ModBlockStateProvider.addBlockSuite(output);

        return output;
    }

    private static <T extends Block> Optional<BlockEntry<T>> registerSuitePart(
            SuiteContext ctx,
            boolean enabled,
            String suffix,
            Function<BlockBehaviour.Properties, T> factory
    ) {
        return enabled
            ? Optional.of(
                registerBlock(
                    ctx.baseName() + suffix,
                    () -> factory.apply(ctx.blockProperties()),
                    ctx.itemProperties()
                ))
            : Optional.empty();
    }

}
