package com.chlorobamagames.green_alex_mod.registries.datagen;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor.GreenAlexArmorItem;
import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexItems;
import com.chlorobamagames.green_alex_mod.registries.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blockTags,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, GreenAlexMod.MODID, existingFileHelper);
    }

    private record ItemTagHolder <T extends Item>(
            DeferredItem<T> item,
            Set<TagKey<Item>> tags
    ){}

    private static final List<ItemTagHolder<?>> itemTags = new ArrayList<>();

    public static <T extends Item> void addItemTag(
            DeferredItem<T> item,
            Set<TagKey<Item>> tags){
        itemTags.add(new ItemTagHolder<T>(item, tags));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (ItemTagHolder<?> itemTagHolder : itemTags) {
            for (TagKey<Item> tagKey : itemTagHolder.tags) {
                tag(tagKey).add(itemTagHolder.item.get());
            }
        }
    }
}
