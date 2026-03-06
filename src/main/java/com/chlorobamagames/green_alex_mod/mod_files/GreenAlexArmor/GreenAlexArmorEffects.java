package com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor;


import com.chlorobamagames.green_alex_mod.GreenAlexMod;
import com.chlorobamagames.green_alex_mod.registries.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;

import static com.chlorobamagames.green_alex_mod.registries.ModTags.Blocks.GREEN;

@EventBusSubscriber(modid = GreenAlexMod.MODID)
public class GreenAlexArmorEffects {

    public static Boolean isGreenBlock(BlockState block) {
        return block.is(ModTags.Blocks.GREEN);
    }

    public static Boolean isGreenEntity(Entity entity) {
        return entity.getType().is(ModTags.Entities.GREEN);
    }


    public static void regeneration_when_look_at_green_block(Player player){
        if (player.tickCount % 10 != 0) return;

        // Ray trace 10 blocks ahead
        HitResult hit = player.pick(10.0D, 0.0F, false);

        if (hit instanceof BlockHitResult blockHit) {

            BlockPos pos = blockHit.getBlockPos();
            BlockState state = player.level().getBlockState(pos);

            if (isGreenBlock(state)) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.REGENERATION,
                        40,
                        0,
                        true,
                        false
                ));
            }
        }
    }

    public static void get_stronger_with_pages(Player player){
        ItemStack mainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

        int paperCount = 0;
        if (mainHand.getItem() == Items.PAPER) {
            paperCount += mainHand.getCount();
        }
        if (offHand.getItem() == Items.PAPER) {
            paperCount += offHand.getCount();
        }
        if (paperCount > 8){
            paperCount = 8;
        }
        if (paperCount > 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    40,
                    paperCount - 1,
                    true,
                    false
            ));
        } else {
            player.removeEffect(MobEffects.DAMAGE_BOOST);
        }
    }

    public static void jump_higher_with_slime_blocks(Player player){
        ItemStack mainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

        int slimeCount = 0;
        if (mainHand.getItem() == Items.SLIME_BLOCK) {
            slimeCount += mainHand.getCount();
        }
        if (offHand.getItem() == Items.SLIME_BLOCK) {
            slimeCount += offHand.getCount();
        }

        if (slimeCount > 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.JUMP,
                    40,
                    slimeCount-1,
                    true,
                    false
            ));
        } else {
            player.removeEffect(MobEffects.JUMP);
        }
    }

    public static void run_faster_on_green_blocks(Player player){
        // Get block directly under the player
        BlockPos blockPosBelow = player.blockPosition().below();
        BlockState blockState = player.level().getBlockState(blockPosBelow);

        if (isGreenBlock(blockState)) {
            // Give Speed I while on green block
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    40,      // 2 seconds (refresh every tick)
                    2,       // Speed I (amplifier 0 = level 1)
                    true,    // ambient (subtle)
                    false    // no particles
            ));
        } else {
            // Remove effect if they step off
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    }

    public record GREEN_ALEX_ARMOR_EQUIPPED (
            Boolean helmet,
            Boolean chestplate,
            Boolean leggings,
            Boolean boots
    ) {
        public Boolean full_set(){
            return helmet && chestplate && leggings && boots;
        }
    }

    public static GREEN_ALEX_ARMOR_EQUIPPED check_green_alex_armor(Player player){
        return new GREEN_ALEX_ARMOR_EQUIPPED(
            player.getItemBySlot(EquipmentSlot.HEAD).is(GreenAlexArmorItem.GREEN_ALEX_ARMOR.helmet().get()),
            player.getItemBySlot(EquipmentSlot.CHEST).is(GreenAlexArmorItem.GREEN_ALEX_ARMOR.chestplate().get()),
            player.getItemBySlot(EquipmentSlot.LEGS).is(GreenAlexArmorItem.GREEN_ALEX_ARMOR.leggings().get()),
            player.getItemBySlot(EquipmentSlot.FEET).is(GreenAlexArmorItem.GREEN_ALEX_ARMOR.boots().get())
        );
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        GREEN_ALEX_ARMOR_EQUIPPED green_alex_armor_equipped = check_green_alex_armor(player);

        if (green_alex_armor_equipped.helmet){
            regeneration_when_look_at_green_block(player);
        }
        if (green_alex_armor_equipped.chestplate){
            get_stronger_with_pages(player);
        }
        if  (green_alex_armor_equipped.leggings){
            jump_higher_with_slime_blocks(player);
        }
        if (green_alex_armor_equipped.boots){
            run_faster_on_green_blocks(player);
        }
    }

    @SubscribeEvent
    public static void onMobHurt(LivingDamageEvent.Pre event) {

        // Only proceed if the entity hurt is a mob
        if (!(event.getEntity() instanceof Mob mob)) return;

        // Only proceed if the source is a player
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        if(isGreenEntity(mob)){
            // Store the player's UUID as two longs
            CompoundTag data = mob.getPersistentData();
            data.putUUID("GreenAlexAnger", player.getUUID());
        }
    }

    public static boolean checkMobAngered(Player player, Mob mob) {
        CompoundTag data = mob.getPersistentData();

        if (data.contains("GreenAlexAnger")) {
            UUID playerUUID = data.getUUID("GreenAlexAnger");
            return playerUUID.equals(player.getUUID());
        }
        return false;
    }

    @SubscribeEvent
    public static void onMobTarget(LivingChangeTargetEvent event) {
        if (!(event.getOriginalAboutToBeSetTarget() instanceof Player player)) return;

        // Only care about hostile mobs
        if (!(event.getEntity() instanceof Mob mob)) return;

        // Check if mob is tagged as green
        if (!isGreenEntity(mob)) return;

        if (checkMobAngered(player, mob)) return;

        // Check chestplate
        GREEN_ALEX_ARMOR_EQUIPPED green_alex_armor_equipped = check_green_alex_armor(player);
        if (green_alex_armor_equipped.full_set()){
            // Cancel targeting
            event.setNewAboutToBeSetTarget(null);
        }
    }
}
