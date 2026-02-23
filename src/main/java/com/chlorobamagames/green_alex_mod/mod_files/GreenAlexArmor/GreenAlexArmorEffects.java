package com.chlorobamagames.green_alex_mod.mod_files.GreenAlexArmor;


//@Mod.EventBusSubscriber
public class GreenAlexArmorEffects {
//
//    private static final TagKey<Block> GREEN_BLOCKS =
//            TagKey.create(Registries.BLOCK,
//                    Identifier.fromNamespaceAndPath(ItTakesACityMod.MODID,
//                            "green_blocks"));
//
//    private static final TagKey<EntityType<?>> GREEN_MOBS =
//            TagKey.create(Registries.ENTITY_TYPE,
//                    Identifier.fromNamespaceAndPath(ItTakesACityMod.MODID,
//                            "green_mobs"));
//
//    public static Boolean isGreenBlock(BlockState block) {
//        return block.is(GREEN_BLOCKS);
//    }
//
//    public static Boolean isGreenEntity(Entity entity) {
//        return entity.getType().is(GREEN_MOBS);
//    }
//
//
//    public static void regeneration_when_look_at_green_block(Player player){
//        if (player.tickCount % 10 != 0) return;
//
//        // Ray trace 10 blocks ahead
//        HitResult hit = player.pick(10.0D, 0.0F, false);
//
//        if (hit instanceof BlockHitResult blockHit) {
//
//            BlockPos pos = blockHit.getBlockPos();
//            BlockState state = player.level().getBlockState(pos);
//
//            if (isGreenBlock(state)) {
//                player.addEffect(new MobEffectInstance(
//                        MobEffects.REGENERATION,
//                        40,
//                        0,
//                        true,
//                        false
//                ));
//            }
//        }
//    }
//
//    public static void get_stronger_with_pages(Player player){
//        ItemStack mainHand = player.getInventory().getEquipment().get(EquipmentSlot.MAINHAND);
//        ItemStack offHand = player.getInventory().getEquipment().get(EquipmentSlot.OFFHAND);
//
//        int paperCount = 0;
//        if (mainHand.getItem() == Items.PAPER) {
//            paperCount += mainHand.getCount();
//        }
//        if (offHand.getItem() == Items.PAPER) {
//            paperCount += offHand.getCount();
//        }
//        if (paperCount > 8){
//            paperCount = 8;
//        }
//        if (paperCount > 0) {
//            player.addEffect(new MobEffectInstance(
//                    MobEffects.STRENGTH,
//                    40,
//                    paperCount - 1,
//                    true,
//                    false
//            ));
//        } else {
//            player.removeEffect(MobEffects.STRENGTH);
//        }
//    }
//
//    public static void jump_higher_with_slime_blocks(Player player){
//        ItemStack mainHand = player.getInventory().getEquipment().get(EquipmentSlot.MAINHAND);
//        ItemStack offHand = player.getInventory().getEquipment().get(EquipmentSlot.OFFHAND);
//
//        int slimeCount = 0;
//        if (mainHand.getItem() == Items.SLIME_BLOCK) {
//            slimeCount += mainHand.getCount();
//        }
//        if (offHand.getItem() == Items.SLIME_BLOCK) {
//            slimeCount += offHand.getCount();
//        }
//
//        if (slimeCount > 0) {
//            player.addEffect(new MobEffectInstance(
//                    MobEffects.JUMP_BOOST,
//                    40,
//                    slimeCount-1,
//                    true,
//                    false
//            ));
//        } else {
//            player.removeEffect(MobEffects.JUMP_BOOST);
//        }
//    }
//
//    public static void run_faster_on_green_blocks(Player player){
//        // Get block directly under the player
//        BlockPos blockPosBelow = player.blockPosition().below();
//        BlockState blockState = player.level().getBlockState(blockPosBelow);
//
//        if (isGreenBlock(blockState)) {
//            // Give Speed I while on green block
//            player.addEffect(new MobEffectInstance(
//                    MobEffects.SPEED,
//                    40,      // 2 seconds (refresh every tick)
//                    2,       // Speed I (amplifier 0 = level 1)
//                    true,    // ambient (subtle)
//                    false    // no particles
//            ));
//        } else {
//            // Remove effect if they step off
//            player.removeEffect(MobEffects.SPEED);
//        }
//    }
//
//    public record GREEN_ALEX_ARMOR_EQUIPPED (
//            Boolean helmet,
//            Boolean chestplate,
//            Boolean leggings,
//            Boolean boots
//    ) {
//        public Boolean full_set(){
//            return helmet && chestplate && leggings && boots;
//        }
//    }
//
//    public static GREEN_ALEX_ARMOR_EQUIPPED check_green_alex_armor(Player player){
//        EntityEquipment equipment = player.getInventory().getEquipment();
//        return new GREEN_ALEX_ARMOR_EQUIPPED(
//            equipment.get(EquipmentSlot.HEAD).is(GreenAlexArmorItem.GREEN_ALEX_HELMET.get()),
//            equipment.get(EquipmentSlot.CHEST).is(GreenAlexArmorItem.GREEN_ALEX_CHESTPLATE.get()),
//            equipment.get(EquipmentSlot.LEGS).is(GreenAlexArmorItem.GREEN_ALEX_LEGGINGS.get()),
//            equipment.get(EquipmentSlot.FEET).is(GreenAlexArmorItem.GREEN_ALEX_BOOTS.get())
//        );
//    }
//
//    @SubscribeEvent
//    public static void onLivingTick(LivingTickEvent event) {
//
//        // Server-side only
//        if (!(event.getEntity() instanceof Player player)) return;
//
//        if (player.level().isClientSide()) return;
//
//        GREEN_ALEX_ARMOR_EQUIPPED green_alex_armor_equipped = check_green_alex_armor(player);
//
//        if (green_alex_armor_equipped.helmet){
//            regeneration_when_look_at_green_block(player);
//        }
//        if (green_alex_armor_equipped.chestplate){
//            get_stronger_with_pages(player);
//        }
//        if  (green_alex_armor_equipped.leggings){
//            jump_higher_with_slime_blocks(player);
//        }
//        if (green_alex_armor_equipped.boots){
//            run_faster_on_green_blocks(player);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onMobHurt(LivingHurtEvent event) {
//
//        // Only proceed if the entity hurt is a mob
//        if (!(event.getEntity() instanceof Mob mob)) return;
//
//        // Only proceed if the source is a player
//        if (!(event.getSource().getEntity() instanceof Player player)) return;
//
//        // Only proceed for your tagged green mobs
//        if (!mob.getType().is(GREEN_MOBS)) return;
//
//        // Store the player's UUID as two longs
//        CompoundTag data = mob.getPersistentData();
//        UUID playerUUID = player.getUUID();
//        data.putLong("GreenAlexAngerMost", playerUUID.getMostSignificantBits());
//        data.putLong("GreenAlexAngerLeast", playerUUID.getLeastSignificantBits());
//    }
//
//    public static boolean checkMobAngered(Player player, Mob mob) {
//        CompoundTag data = mob.getPersistentData();
//
//        // Check that both keys exist
//        if (data.contains("GreenAlexAngerMost") && data.contains("GreenAlexAngerLeast")) {
//
//            // Retrieve the longs safely
//            Optional<Long> mostOpt = data.getLong("GreenAlexAngerMost");
//            Optional<Long> leastOpt = data.getLong("GreenAlexAngerLeast");
//
//            if (mostOpt.isPresent() && leastOpt.isPresent()) {
//                long most = mostOpt.get();
//                long least = leastOpt.get();
//
//                UUID angeredPlayerUUID = new UUID(most, least);
//                return player.getUUID().equals(angeredPlayerUUID);
//            }
//        }
//
//        return false;
//    }
//
//    @SubscribeEvent
//    public static void onMobTarget(LivingChangeTargetEvent event) {
//        if (!(event.getOriginalTarget() instanceof Player player)) return;
//
//        // Only care about hostile mobs
//        if (!(event.getEntity() instanceof Mob mob)) return;
//
//        // Check if mob is tagged as green
//        if (!isGreenEntity(mob)) return;
//
//        if (checkMobAngered(player, mob)) return;
//
//        // Check chestplate
//        GREEN_ALEX_ARMOR_EQUIPPED green_alex_armor_equipped = check_green_alex_armor(player);
//        if (green_alex_armor_equipped.full_set()){
//            // Cancel targeting
//            event.setNewTarget(null);
//        }
//    }
}
