package com.neutralboolean.weighted.mixin;

import com.github.theredbrain.equipmentweight.EquipmentWeight;
import com.neutralboolean.weighted.events.ArmorEquipmentChangeCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = LivingEntity.class)
abstract public class ArmorLivingEntityMixin extends Entity {
	@Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);

	public ArmorLivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "onEquipStack", at = @At(value = "TAIL", target = "Lnet/minecraft/entity/Entity;onEquipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V"))
	private void LivingEntityEquipmentChange(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
		if (!this.getEntityWorld().isClient() && this.isPlayer() && slot.isArmorSlot()) {
			if (this.getAttributeInstance(EquipmentWeight.EQUIPMENT_WEIGHT) != null) {
				ArmorEquipmentChangeCallback.EVENT.invoker().interact((LivingEntity) (Object) this, slot, newStack);
			}
		}
	}
}