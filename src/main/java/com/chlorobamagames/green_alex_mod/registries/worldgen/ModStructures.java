package com.chlorobamagames.green_alex_mod.registries.worldgen;

import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.List;

public class ModStructures {
    public static final ResourceKey<StructureSet> ADD_ALEX_ROOM = registerStructureSet("generate_alex_room");

    public static void bootstrapStructureSet(BootstrapContext<StructureSet> context) {
        StructurePlacement placement = new RandomSpreadStructurePlacement(
                30, // spacing
                10, // separation
                RandomSpreadType.LINEAR,
                123456 // salt
        );

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, "alex_room.json");
        Holder<Structure> holder = context.lookup(Registries.STRUCTURE).getOrThrow(
                ResourceKey.create(Registries.STRUCTURE, location)
        );

        context.register(ADD_ALEX_ROOM, new StructureSet(
                List.of(
                        new StructureSet.StructureSelectionEntry(holder, 1)),
                placement
        ));
    }

//    public static final ResourceKey<StructureSet> ALEX_ROOM = registerStructureSet("alex_room.json");

//    public static void boostrapStructure(BootstrapContext<Structure> context) {
//        HolderSet<Biome> biomes = HolderSet.direct()
//        context.register(
//                ALEX_ROOM,
//                new SinglePieceStructure(
//                        new Structure.StructureSettings.Builder(
//                                biomes
//                        )
//                )
//                );
//    }

    private static ResourceKey<StructureSet> registerStructureSet(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, name));
    }

    private static ResourceKey<Structure> registerStructure(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(GreenAlexMod.MODID, name));
    }
}
