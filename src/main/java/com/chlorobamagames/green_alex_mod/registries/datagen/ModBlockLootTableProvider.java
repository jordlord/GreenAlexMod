package com.chlorobamagames.green_alex_mod.registries.datagen;

import com.chlorobamagames.green_alex_mod.registries.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    private static List<OreLootTable<?>> addedOreLootTables = new ArrayList<>();

    protected record OreLootTable<T extends Block>(
            DeferredBlock<T> block,
            DeferredItem<Item> item,
            int minimumDrops,
            int maximumDrops
    ) { }

    protected <T extends Block> LootTable.Builder generateLootTable(OreLootTable<T> ore) {
        return createMultipleOreDrops(ore.block, ore.item, ore.minimumDrops, ore.maximumDrops);
    }

    protected <T extends Block> LootTable.Builder createMultipleOreDrops(
            DeferredBlock<T> pBlock,
            DeferredItem<Item> item,
            int minDrops, int maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock.get(),
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    public static <T extends Block> void addOreToLootTable(
            DeferredBlock<T> block,
            DeferredItem<Item> item,
            int minimumDrops, int maximumDrops) {
        addedOreLootTables.add(new OreLootTable<T>(block, item, minimumDrops, maximumDrops));
    }

    @Override
    protected void generate() {
        for (OreLootTable<?> oreLootTable : addedOreLootTables) {
            add((Block) oreLootTable.block.get(), generateLootTable(oreLootTable));
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
