package com.neutralboolean.weighted.effects

import com.github.theredbrain.staminaattributes.StaminaAttributes
import com.github.theredbrain.staminaattributes.entity.StaminaUsingEntity
import com.google.common.collect.Multimaps
import com.neutralboolean.weighted.Weighted.identifier
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.entry.RegistryEntry
import com.neutralboolean.weighted.config.Configs

class OverburdenedEffect: StatusEffect {
    constructor(): super(StatusEffectCategory.HARMFUL, 0x000000)
    constructor(statusEffectCategory: StatusEffectCategory, color: Int): super(statusEffectCategory, color)
    val attributesMap = mutableMapOf<RegistryEntry<EntityAttribute>, EntityAttributeModifier>()

    companion object {
        const val OVERBURDENED_ID: String = "overburdened"
    }

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun onApplied(entity: LivingEntity?, amplifier: Int) {
        if (entity is StaminaUsingEntity) {
            val id = identifier(OVERBURDENED_ID)

            val DEPLETED_DELAY_MULT_MOD: Double = Configs.serverConfig.statusEffectsSettings.overBurdenedEffectStats.get().depletedDelayMult
            val STAMINA_DELAY_MULT_MOD: Double = Configs.serverConfig.statusEffectsSettings.overBurdenedEffectStats.get().staminaDelayMult
            val STAMINA_REGEN_MULT_MOD: Double = Configs.serverConfig.statusEffectsSettings.overBurdenedEffectStats.get().staminaRegenMult

            if (DEPLETED_DELAY_MULT_MOD != 0.0) {
                val depletedStamRegenAttribute = entity.getAttributeInstance(StaminaAttributes.DEPLETED_STAMINA_REGENERATION_DELAY_THRESHOLD)
                // 200% the depleted stamina regen delay
                val depletedRegenMod = EntityAttributeModifier(id, DEPLETED_DELAY_MULT_MOD, Operation.ADD_MULTIPLIED_TOTAL)
                depletedStamRegenAttribute!!.overwritePersistentModifier(depletedRegenMod)
                attributesMap[StaminaAttributes.DEPLETED_STAMINA_REGENERATION_DELAY_THRESHOLD] = depletedRegenMod
            }
            if (STAMINA_DELAY_MULT_MOD != 0.0) {
                val staminaRegenDelayAttribute = entity.getAttributeInstance(StaminaAttributes.STAMINA_REGENERATION_DELAY_THRESHOLD)
                // 100% longer delay in regaining stamina
                val regenDelayMod = EntityAttributeModifier(id, STAMINA_DELAY_MULT_MOD, Operation.ADD_MULTIPLIED_TOTAL)
                staminaRegenDelayAttribute!!.overwritePersistentModifier(regenDelayMod)
                attributesMap[StaminaAttributes.STAMINA_REGENERATION_DELAY_THRESHOLD] = regenDelayMod
            }
            if (STAMINA_REGEN_MULT_MOD != 0.0) {
                val staminaRegenAttribute = entity.getAttributeInstance(StaminaAttributes.STAMINA_REGENERATION)
                // Halve the stamina regen amount
                val staminaRegenMod = EntityAttributeModifier(id, STAMINA_REGEN_MULT_MOD, Operation.ADD_MULTIPLIED_TOTAL)
                staminaRegenAttribute!!.updateModifier(staminaRegenMod)
                attributesMap[StaminaAttributes.STAMINA_REGENERATION] = staminaRegenMod
            }
        }
        super.onApplied(entity, amplifier)
    }

    override fun onRemoved(attributeContainer: AttributeContainer?) {
        attributeContainer!!.removeModifiers(Multimaps.forMap(attributesMap))
        super.onRemoved(attributeContainer)
    }
}