package com.chlorobamagames.green_alex_mod.registries.datagen;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GreenAlexMod.MODID, exFileHelper);
    }

    private record InheritedModel<B extends Block, N extends Block> (
            DeferredBlock<B> baseBlock,
            DeferredBlock<N> newBlock
    ){
        private void modelRegister(
                ModBlockStateProvider context
        ) {
            N block = newBlock.get();
            ResourceLocation texture = context.blockTexture(baseBlock.get());
            switch (block) {
                case StairBlock s -> context.stairsBlock(s, texture);
                case SlabBlock s -> context.slabBlock(s, texture, texture);
                case FenceBlock f -> context.fenceBlock(f, texture);
                case FenceGateBlock f ->context.fenceGateBlock(f, texture);
                case WallBlock w -> context.wallBlock(w, texture);
                case ButtonBlock b -> context.buttonBlock(b, texture);
                case PressurePlateBlock p -> context.pressurePlateBlock(p, texture);
                default -> context.blockWithItem(newBlock);
            };
        }
        private String suffix() {
            return switch (newBlock.get()) {
                case WallBlock w -> "_post";
                default -> "";
            };
        }
        public void call(ModBlockStateProvider context){
            modelRegister(context);
            context.blockItem(newBlock, suffix());
        }
    }

    private static final List<DeferredBlock<? extends Block>> blocks = new ArrayList<>();

    private static final List<InheritedModel<? extends Block, ? extends Block>> inheritedModels = new ArrayList<>();

    @FunctionalInterface
    public interface BlockModelAdder<T extends Block> {
        void add(DeferredBlock<T> blockEntry);
    }

    public static <T extends Block> void addBlock(DeferredBlock<T> block) {
        blocks.add(block);
    }

    public static <B extends Block, N extends Block> void addInheritedBlock(
            DeferredBlock<B> baseBlock,
            DeferredBlock<N> newBlock
    ) {
        inheritedModels.add(new InheritedModel<B, N>(baseBlock, newBlock));
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeferredBlock<? extends Block> block : blocks) {
            blockWithItem(block);
        }
        for(InheritedModel<? extends Block, ? extends Block> model : inheritedModels) {
            model.call(this);
        }
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
