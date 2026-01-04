
package com.example.corrupted;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CorruptedScript implements ModInitializer {

    public static final Item CURSED_SWORD = Registry.register(
        Registries.ITEM,
        Identifier.of("corrupted", "cursed_sword"),
        new SwordItem(
            ToolMaterials.IRON,
            new Item.Settings()
        )
    );

    @Override
    public void onInitialize() {
        // initialization handled by static registrations
    }
}
