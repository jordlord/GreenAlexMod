package com.chlorobamagames.green_alex_mod.mod_files.Structures;

import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class AlexRoomStructure extends SinglePieceStructure {
    protected AlexRoomStructure(PieceConstructor constructor, int width, int depth, StructureSettings settings) {
        super(constructor, width, depth, settings);
    }

    @Override
    public StructureType<AlexRoomStructure> type() {
        return null;
    }
}
