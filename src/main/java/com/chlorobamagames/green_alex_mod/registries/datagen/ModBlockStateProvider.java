package com.chlorobamagames.green_alex_mod.registries.datagen;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GreenAlexMod.MODID, exFileHelper);
    }

    private static List<ModBlocks.BlockSuite<? extends Block>> blockSuites = new ArrayList<>();

    public static <T extends Block>  void addBlockSuite(ModBlocks.BlockSuite<T> blockSuite) {
        blockSuites.add(blockSuite);
    }

    @Override
    protected void registerStatesAndModels() {
        for (ModBlocks.BlockSuite<? extends Block> blockSuite : blockSuites) {
            registerBlockSuite(blockSuite);
        }
    }

    private <T extends Block> void registerOptional(
            Optional<ModBlocks.BlockEntry<T>> optional,
            Consumer<T> modelRegistrar
    ) {
        optional.ifPresent(entry -> {
            modelRegistrar.accept(entry.block().get());
            blockItem(entry.block());
        });
    }

    private <T extends Block> void registerBlockSuite(ModBlocks.BlockSuite<T> blockSuite) {
        var baseEntry = blockSuite.block();
        var baseBlock = baseEntry.block().get();
        var texture = blockTexture(baseBlock);
        blockWithItem(baseEntry.block());
        registerOptional(blockSuite.stair(),
                block -> stairsBlock(block, texture));
        registerOptional(blockSuite.slab(),
                block -> slabBlock(block, texture, texture));
        registerOptional(blockSuite.fence(),
                block -> fenceBlock(block, texture));
        registerOptional(blockSuite.fenceGate(),
                block -> fenceGateBlock(block, texture));
        registerOptional(blockSuite.wall(),
                block -> wallBlock(block, texture));
        registerOptional(blockSuite.button(),
                block -> buttonBlock(block, texture));
        registerOptional(blockSuite.pressurePlate(),
                block -> pressurePlateBlock(block, texture));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(GreenAlexMod.MODID + ":block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(GreenAlexMod.MODID + ":block/" + deferredBlock.getId().getPath() + appendix));
    }
}
