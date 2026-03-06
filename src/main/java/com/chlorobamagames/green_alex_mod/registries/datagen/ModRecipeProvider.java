package com.chlorobamagames.green_alex_mod.registries.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public record SmithingRecipe <B extends Item, O extends Item, R extends Item>(
        Optional<DeferredItem<SmithingTemplateItem>> template,
        Supplier<B> baseItem,
        Supplier<O> outputItem,
        Supplier<R> requiredIngredient,
        RecipeCategory category
    ){
        public void build(RecipeOutput recipeOutput){
            SmithingTransformRecipeBuilder.smithing(
                    Ingredient.of(template.isPresent() ? template.get() : Items.AIR),
                    Ingredient.of(baseItem.get()),
                    Ingredient.of(requiredIngredient.get()), category, outputItem.get()).unlocks(getHasName(requiredIngredient.get()),
                    has(requiredIngredient.get())).save(recipeOutput, getItemName(outputItem.get()) + "_smithing"
            );
        }
    }

    public record CookingRecipe <I extends Item, O extends Item>(
            DeferredItem<I> inputItem,
            DeferredItem<O> outputItem,
            int experience,
            int time,
            RecipeCategory category,
            boolean smeltInFurnace,
            boolean smeltInBlaster,
            boolean smeltInSmoker,
            boolean smeltInCampfire
    ) {
        public void build(RecipeOutput recipeOutput){
            if (smeltInFurnace) {
                genericCooking(
                    recipeOutput,
                    RecipeSerializer.SMELTING_RECIPE,
                    SmeltingRecipe::new,
                    time,
                    "smelting"
                );
            }
            if (smeltInBlaster) {
                genericCooking(
                    recipeOutput,
                    RecipeSerializer.BLASTING_RECIPE,
                    BlastingRecipe::new,
                    time / 2,
                    "blasting"
                );
            }
            if (smeltInSmoker) {
                genericCooking(
                    recipeOutput,
                    RecipeSerializer.SMOKING_RECIPE,
                    SmokingRecipe::new,
                    time / 2,
                    "smoking"
                );
            }
            if (smeltInCampfire){
                genericCooking(
                    recipeOutput,
                    RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                    CampfireCookingRecipe::new,
                    time * 3,
                    "campfire_cooking"
                );
            }
        }
        private <T extends AbstractCookingRecipe> void genericCooking(
            RecipeOutput recipeOutput,
            RecipeSerializer<T> cookingSerializer,
            AbstractCookingRecipe.Factory<T> factory,
            int cookingTime,
            String recipeName) {
                SimpleCookingRecipeBuilder
                .generic(
                    Ingredient.of(inputItem),
                    category,
                    outputItem,
                    experience,
                    cookingTime,
                    cookingSerializer,
                    factory
                    )
                .unlockedBy(getHasName(inputItem), has(inputItem))
                .save(
                    recipeOutput,
                    getItemName(outputItem) + "_" + recipeName + "_" + getItemName(inputItem)
                );
        }
    }

    public record ShapedCraftingRecipe(
        List<String> craftingPattern,
        Map<Character, Supplier<? extends Item>> itemToPattern,
        DeferredItem<? extends Item> output,
        int amount,
        DeferredItem<? extends Item> unlockedBy,
        String recipeSuffix,
        RecipeCategory category
    ){
        public static ShapedCraftingRecipe nineCompact(DeferredItem<? extends Item> input, DeferredItem<? extends Item> output){
            return new ShapedCraftingRecipe(
                    List.of("AAA", "AAA", "AAA"),
                    Map.of('A', input),
                    output,
                    1,
                    input,
                    "compact",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe stairs(DeferredItem<? extends Item> input, DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("#  ", "## ", "###"),
                    Map.of('#', input),
                    output,
                    4,
                    input,
                    "",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe slabs(DeferredItem<? extends Item> input, DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("###"),
                    Map.of('#', input),
                    output,
                    6,
                    input,
                    "",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe pressurePlate(DeferredItem<? extends Item> input, DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("##"),
                    Map.of('#', input),
                    output,
                    6,
                    input,
                    "",
                    RecipeCategory.REDSTONE
            );
        }

        public static ShapedCraftingRecipe walls(DeferredItem<? extends Item> input, DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("###", "###"),
                    Map.of('#', input),
                    output,
                    6,
                    input,
                    "",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe fences(
                DeferredItem<? extends Item> input,
                DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("#S#", "#S#"),
                    Map.of('#', input, 'S', () -> Items.STICK),
                    output,
                    6,
                    input,
                    "",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe fenceGates(
                DeferredItem<? extends Item> input,
                DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("S#S", "S#S"),
                    Map.of('#', input, 'S', () -> Items.STICK),
                    output,
                    1,
                    input,
                    "",
                    RecipeCategory.BUILDING_BLOCKS
            );
        }

        public static ShapedCraftingRecipe button(
                DeferredItem<? extends Item> input,
                DeferredItem<BlockItem> output){
            return new ShapedCraftingRecipe(
                    List.of("#"),
                    Map.of('#', input),
                    output,
                    1,
                    input,
                    "",
                    RecipeCategory.REDSTONE

            );
        }

        public void build(RecipeOutput recipeOutput){
            ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(category, output.get(), amount);
            for(String row :  craftingPattern){
                recipe.pattern(row);
            }
            for (Map.Entry<Character, Supplier<? extends Item>> entry : itemToPattern.entrySet()) {
                recipe.define(entry.getKey(), entry.getValue().get());
            }
            recipe.unlockedBy(getHasName(unlockedBy), has(unlockedBy))
                    .save(recipeOutput, getItemName(output.get()) + "_recipe_" + recipeSuffix);
        }
    }

    public record ShapelessCraftingRecipe(
        List<Supplier<? extends Item>> requiredItems,
        DeferredItem<? extends Item> output,
        int amount,
        DeferredItem<? extends Item> unlockedBy,
        String recipeSuffix,
        RecipeCategory category
    ){
        public static ShapelessCraftingRecipe unpack(
                DeferredItem<? extends Item> input,
                DeferredItem<? extends Item> output,
                int amount){
            return new ShapelessCraftingRecipe(
                List.of(input),
                output,
                amount,
                input,
                "unpack",
                RecipeCategory.MISC
            );
        }
        public void build(RecipeOutput recipeOutput){
            ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(category, output, amount);
            for (Supplier<? extends Item> requiredItem : requiredItems) {
                recipe.requires(requiredItem.get());
            }
            recipe.unlockedBy(getHasName(unlockedBy), has(unlockedBy))
                    .save(recipeOutput, getItemName(output.get()) +  "_recipe_" + recipeSuffix);
        }
    }

    private static final List<SmithingRecipe<?,?,?>> smithingRecipes =  new ArrayList<>();

    private static final List<CookingRecipe<?, ?>> cookingRecipes =  new ArrayList<>();

    private static final List<ShapelessCraftingRecipe> shapelessRecipes =  new ArrayList<>();

    private static final List<ShapedCraftingRecipe> shapedRecipes =  new ArrayList<>();

    public static SmithingRecipe<?,?,?> addSmithingRecipe(SmithingRecipe<?,?,?> recipe){
        smithingRecipes.add(recipe);
        return recipe;
    }

    public static CookingRecipe<?, ?> addCookingRecipe(CookingRecipe<?, ?> recipe){
        cookingRecipes.add(recipe);
        return recipe;
    }

    public static ShapelessCraftingRecipe addShapelessRecipe(ShapelessCraftingRecipe recipe) {
        shapelessRecipes.add(recipe);
        return recipe;
    }

    public static ShapedCraftingRecipe addShapedRecipe(ShapedCraftingRecipe recipe){
        shapedRecipes.add(recipe);
        return recipe;
    }

    // TODO: Automatically add crafting recipes to the recipe output
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        for(SmithingRecipe<?, ?, ?> recipe :  smithingRecipes){
            recipe.build(recipeOutput);
        }
        for(CookingRecipe<?, ?> recipe : cookingRecipes){
            recipe.build(recipeOutput);
        }
        for(ShapelessCraftingRecipe recipe : shapelessRecipes){
            recipe.build(recipeOutput);
        }
        for(ShapedCraftingRecipe recipe : shapedRecipes){
            recipe.build(recipeOutput);
        }
    }
}
