package com.chlorobamagames.green_alex_mod.registries.datagen;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, GreenAlexMod.MODID, existingFileHelper);
    }

    public record BlockTagGrouping(
            Set<TagKey<Block>> miningTools,
            Optional<TagKey<Block>> miningTier,
            Set<TagKey<Block>> otherTags
    ){
        public BlockTagGrouping appendBlockTag(TagKey<Block> tag){
            Set<TagKey<Block>> output = new HashSet<>(otherTags());
            output.add(tag);
            return new BlockTagGrouping(miningTools, miningTier, output);
        }
    }

    public record BlockTagsHolder<T extends Block>(
            DeferredBlock<T> block,
            BlockTagGrouping tags
    ) {}

    private static List<BlockTagsHolder<?>> blockTags = new ArrayList<>();

    public static <T extends Block> void addBlockTags(DeferredBlock<T> block, BlockTagGrouping tags) {
        blockTags.add(new BlockTagsHolder<T>(block, tags));
    }

    private static <T extends Block> void addOptionalSuiteTags(
            Optional<ModBlocks.BlockEntry<T>> optional,
            BlockTagGrouping baseTags,
            TagKey<Block> typeTag
    ) {
        optional.ifPresent(entry -> {

            // Create a new grouping with the extra type tag
            Set<TagKey<Block>> mergedOtherTags = new HashSet<>(baseTags.otherTags());
            mergedOtherTags.add(typeTag);

            BlockTagGrouping merged = new BlockTagGrouping(
                    baseTags.miningTools(),
                    baseTags.miningTier(),
                    mergedOtherTags
            );

            addBlockTags(entry.block(), merged);
        });
    }

    public static <T extends Block> void addBlockSuite(
            ModBlocks.BlockSuite<T> blockSuite,
            BlockTagGrouping baseTags){
        // Base block — no extra type tag
        addBlockTags(blockSuite.block().block(), baseTags);
        addOptionalSuiteTags(blockSuite.stair(), baseTags, BlockTags.STAIRS);
        addOptionalSuiteTags(blockSuite.slab(), baseTags, BlockTags.SLABS);
        addOptionalSuiteTags(blockSuite.fence(), baseTags, BlockTags.FENCES);
        addOptionalSuiteTags(blockSuite.fenceGate(), baseTags, BlockTags.FENCE_GATES);
        addOptionalSuiteTags(blockSuite.wall(), baseTags, BlockTags.WALLS);
        addOptionalSuiteTags(blockSuite.button(), baseTags, BlockTags.BUTTONS);
        addOptionalSuiteTags(blockSuite.pressurePlate(), baseTags, BlockTags.PRESSURE_PLATES);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Automatically generate tags added
        for (BlockTagsHolder<?> tags : blockTags){
            for (TagKey<Block> miningToolTag : tags.tags().miningTools) {
                tag(miningToolTag).add(tags.block().get());
            }
            tags.tags().miningTier.ifPresent(
                    tierTag -> tag(tierTag).add(tags.block().get())
            );
            for (TagKey<Block> otherTags : tags.tags().otherTags) {
                tag(otherTags).add(tags.block().get());
            }
        }
    }
}
