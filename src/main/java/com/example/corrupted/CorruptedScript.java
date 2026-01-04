
package com.example.corrupted;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class CorruptedScript implements ModInitializer {

    public static final String MOD_ID = "corrupted";

    public static Item CURSED_SWORD;
    public static EntityType<TheCenterEntity> CENTER;

    @Override
    public void onInitialize() {

        // Item
        CURSED_SWORD = BuiltInRegistries.ITEM.register(
                new ResourceLocation(MOD_ID, "cursed_sword"),
                new SwordItem(
                        Tiers.IRON,
                        5,
                        -2.4F,
                        new Item.Properties()
                )
        );

        // Entity
        CENTER = BuiltInRegistries.ENTITY_TYPE.register(
                new ResourceLocation(MOD_ID, "the_center"),
                EntityType.Builder
                        .of(TheCenterEntity::new, MobCategory.MONSTER)
                        .sized(0.6F, 1.95F)
                        .build("the_center")
        );
    }
}
