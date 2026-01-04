
package com.example.corrupted;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CorruptedScript implements ModInitializer {
    public static final String MODID = "corrupted";

    public static Item CURSED_SWORD;
    public static EntityType<TheCenterEntity> CENTER;

    private static int ticks = 0;
    private static boolean started = false;

    @Override
    public void onInitialize() {
        CURSED_SWORD = Registry.register(
            Registries.ITEM,
            new Identifier(MODID, "cursed_sword"),
            new SwordItem(ToolMaterials.NETHERITE, 5, -2.4f, new Item.Settings().maxCount(1)) {
                @Override
                public void onCraft(ItemStack stack, World world, PlayerEntity player) {
                    if (!world.isClient) {
                        started = true;
                        player.sendMessage(Text.literal("Something has noticed you."), false);
                    }
                }
            }
        );

        CENTER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MODID, "the_center"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TheCenterEntity::new)
                .dimensions(EntityDimensions.fixed(0.9f, 2.6f))
                .trackRangeBlocks(64).build()
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(CURSED_SWORD));

        ServerTickEvents.END_SERVER_TICK.register(this::tick);
    }

    private void tick(MinecraftServer server) {
        if (!started) return;
        ticks++;
        if (ticks % (20*30) == 0) {
            server.getPlayerManager().broadcast(Text.literal("It watches when you are still."), false);
        }
        if (ticks == 20*60) {
            server.getPlayerManager().getPlayerList().forEach(p -> {
                var w = p.getWorld();
                var e = CENTER.create(w);
                if (e != null) {
                    e.refreshPositionAndAngles(p.getX()+3, p.getY(), p.getZ()+3, 0, 0);
                    w.spawnEntity(e);
                }
            });
        }
    }
}
