package com.chlorobamagames.green_alex_mod.mod_files;

import com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor.GreenAlexArmorItem;
import com.chlorobamagames.green_alex_mod.registries.datagen.ModRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;

public class GreenAlexRecipes {

    public static ModRecipeProvider.CookingRecipe<?, ?> GREEN_ALEX_ORE_COOKING_RECIPE =
        ModRecipeProvider.addCookingRecipe(
            new ModRecipeProvider.CookingRecipe<>(
                    GreenAlexBlocks.GREEN_ALEX_ORE.item(),
                    GreenAlexItems.GREEN_ALEXITE_ITEM,
                    5,
                    80,
                    RecipeCategory.MISC,
                    true,
                    true,
                    false,
                    false
            )
        );

    public static ModRecipeProvider.ShapedCraftingRecipe GREEN_ALEXITE_COMPACT_RECIPE =
            ModRecipeProvider.addShapedRecipe(
                    ModRecipeProvider.ShapedCraftingRecipe.nineCompact(
                            GreenAlexItems.GREEN_ALEXITE_ITEM,
                            GreenAlexBlocks.GREEN_ALEXITE_BLOCK.block().item()
                    )
            );

    public static ModRecipeProvider.ShapedCraftingRecipe GREEN_ALEXANIUM_COMPACT_RECIPE =
            ModRecipeProvider.addShapedRecipe(
                    ModRecipeProvider.ShapedCraftingRecipe.nineCompact(
                            GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                            GreenAlexBlocks.GREEN_ALEXANIUM_BLOCK.block().item()
                    )
            );

    public static ModRecipeProvider.ShapelessCraftingRecipe GREEN_ALEXANIUM_BLOCK_RECIPE =
            ModRecipeProvider.addShapelessRecipe(
                    new ModRecipeProvider.ShapelessCraftingRecipe(
                            List.of(
                                    GreenAlexItems.GREEN_ALEX_SOUL,
                                    GreenAlexBlocks.GREEN_ALEXITE_BLOCK.block().item(),
                                    () -> Items.CREEPER_HEAD,
                                    () -> Items.CACTUS,
                                    () -> Items.EMERALD_BLOCK,
                                    () -> Items.SLIME_BLOCK,
                                    () -> Items.TURTLE_SCUTE,
                                    () -> Items.PITCHER_PLANT,
                                    () -> Items.MUSIC_DISC_CAT
                            ),
                            GreenAlexBlocks.GREEN_ALEXANIUM_BLOCK.block().item(),
                            1,
                            GreenAlexItems.GREEN_ALEXITE_ITEM,
                            "green_transformation",
                            RecipeCategory.MISC
                    )
            );

    public static ModRecipeProvider.ShapelessCraftingRecipe GREEN_ALEXITE_FROM_BLOCK_RECIPE =
            ModRecipeProvider.addShapelessRecipe(
                    ModRecipeProvider.ShapelessCraftingRecipe.unpack(
                            GreenAlexBlocks.GREEN_ALEXITE_BLOCK.block().item(),
                            GreenAlexItems.GREEN_ALEXITE_ITEM,
                            9
                    )
            );

    public static ModRecipeProvider.ShapelessCraftingRecipe GREEN_ALEXANIUM_FROM_BLOCK_RECIPE =
            ModRecipeProvider.addShapelessRecipe(
                    ModRecipeProvider.ShapelessCraftingRecipe.unpack(
                            GreenAlexBlocks.GREEN_ALEXANIUM_BLOCK.block().item(),
                            GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                            9
                    )
            );

    public static ModRecipeProvider.SmithingRecipe<?, ?, ?> GREEN_ALEX_HELMET_SMITHING_RECIPE = ModRecipeProvider.addSmithingRecipe(
            new ModRecipeProvider.SmithingRecipe<>(
                    Optional.empty(),
                    () -> Items.NETHERITE_HELMET,
                    GreenAlexArmorItem.GREEN_ALEX_ARMOR.helmet(),
                    GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                    RecipeCategory.COMBAT
            )
    );

    public static ModRecipeProvider.SmithingRecipe<?, ?, ?> GREEN_ALEX_CHESTPLATE_SMITHING_RECIPE = ModRecipeProvider.addSmithingRecipe(
            new ModRecipeProvider.SmithingRecipe<>(
                    Optional.empty(),
                    () -> Items.NETHERITE_CHESTPLATE,
                    GreenAlexArmorItem.GREEN_ALEX_ARMOR.chestplate(),
                    GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                    RecipeCategory.COMBAT
            )
    );

    public static ModRecipeProvider.SmithingRecipe<?, ?, ?> GREEN_ALEX_LEGGINGS_SMITHING_RECIPE = ModRecipeProvider.addSmithingRecipe(
            new ModRecipeProvider.SmithingRecipe<>(
                    Optional.empty(),
                    () -> Items.NETHERITE_LEGGINGS,
                    GreenAlexArmorItem.GREEN_ALEX_ARMOR.leggings(),
                    GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                    RecipeCategory.COMBAT
            )
    );

    public static ModRecipeProvider.SmithingRecipe<?, ?, ?> GREEN_ALEX_BOOTS_SMITHING_RECIPE = ModRecipeProvider.addSmithingRecipe(
            new ModRecipeProvider.SmithingRecipe<>(
                    Optional.empty(),
                    () -> Items.NETHERITE_BOOTS,
                    GreenAlexArmorItem.GREEN_ALEX_ARMOR.boots(),
                    GreenAlexItems.GREEN_ALEXANIUM_INGOT,
                    RecipeCategory.COMBAT
            )
    );
}
