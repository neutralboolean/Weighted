package com.neutralboolean.weighted.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface ArmorEquipmentChangeCallback {
    Event<ArmorEquipmentChangeCallback> EVENT = EventFactory.createArrayBacked(ArmorEquipmentChangeCallback.class,
            (listeners) -> (player, slot, itemStack) -> {
                for (ArmorEquipmentChangeCallback listener : listeners) {
                    ActionResult result = listener.interact(player, slot, itemStack);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity player, EquipmentSlot slot, ItemStack itemStack);
}
