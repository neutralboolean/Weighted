package com.neutralboolean.weighted

import com.github.theredbrain.equipmentweight.EquipmentWeight
import com.neutralboolean.weighted.WeightedStatics.heavyArmorMap
import com.neutralboolean.weighted.WeightedStatics.lightArmorMap
import com.neutralboolean.weighted.WeightedStatics.mediumArmorMap
import com.neutralboolean.weighted.WeightedStatics.roundToHalf
import com.neutralboolean.weighted.WeightedStatics.slotMultDict
import com.neutralboolean.weighted.WeightedStatics.weightBaseDict
import com.neutralboolean.weighted.WeightedStatics.weightModDict
import com.neutralboolean.weighted.WeightedStatics.xHeavyArmorMap
import com.neutralboolean.weighted.effects.BurdenedEffect
import com.neutralboolean.weighted.effects.OverburdenedEffect
import com.neutralboolean.weighted.effects.UnburdenedEffect
import com.neutralboolean.weighted.events.ArmorEquipmentChangeCallback
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.EXTRA_HEAVY_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.HEAVY_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.LIGHT_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.MEDIUM_WEIGHT
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.ArmorItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import com.neutralboolean.weighted.WeightedStatics.getWeightTag
import com.neutralboolean.weighted.config.Configs

object Weighted : ModInitializer {
    const val MODID = "weighted"
    val UNBURDENED: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        identifier(UnburdenedEffect.UNBURDENED_ID),
        UnburdenedEffect()
    )
    val BURDENED: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        identifier(BurdenedEffect.BURDENED_ID),
        BurdenedEffect()
    )
    val OVERBURDENED: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        identifier(OverburdenedEffect.OVERBURDENED_ID),
        OverburdenedEffect()
    )
    private val logger = LoggerFactory.getLogger("weighted")

    // unburdened attribution
    // uh...chet g. petey
    // overburned attribution
    // <a href="https://www.vecteezy.com/vector-art/33170247-pixel-art-illustration-atlas-pixelated-greek-atlas-greek-mythology-atlas-pixelated-for-the-pixel-art-game-and-icon-for-website-and-video-game-old-school-retro">
    // burdened attribution
    // <a href="https://www.vecteezy.com/vector-art/2496154-gym-barbell-equipment">


    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        Configs.init()

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(CommandManager.literal("dump").executes { context ->
                context.source.sendMessage(Text.of("/me dances."))
                1
            })
        }

        ArmorEquipmentChangeCallback.EVENT.register { player, slot, itemStack ->
            equipmentChangeWeight(player, slot, itemStack)
        }
    }

    fun identifier(id: String): Identifier {
        return Identifier.of(MODID, id)
    }

    private fun equipmentChangeWeight(player: LivingEntity, slot: EquipmentSlot, itemStack: ItemStack): ActionResult {
        val equipmentWeight = player.getAttributeInstance(EquipmentWeight.EQUIPMENT_WEIGHT)!!
        val slotModId = weightModDict[slot.getName()]

        if (!itemStack.isOf(Items.AIR)){
            //process itemStack
            val weightTag = getWeightTag(itemStack)
            var newWeight = weightBaseDict.get(weightTag)!!.get()
            newWeight += getProtectionMod(slot, itemStack.item as? ArmorItem, weightTag)
            newWeight *= slotMultDict.get(slot.getName())!!.get()
            equipmentWeight.overwritePersistentModifier(
                EntityAttributeModifier(
                    slotModId,
                    roundToHalf(newWeight),
                    EntityAttributeModifier.Operation.ADD_VALUE
                )
            )
        } else { //itemStack is AIR, i.e. naked equipment slot
            equipmentWeight.overwritePersistentModifier(
                EntityAttributeModifier(
                    slotModId,
                    0.0,
                    EntityAttributeModifier.Operation.ADD_VALUE
                )
            )
        }
        logger.debug("-------------------------------------")
        logger.debug("current weight: ${equipmentWeight.value}")
        logger.debug("=====================================")

        return ActionResult.PASS
    }

    // adds a flat modifier based on how much more protection item has than standard of its weight class
    // force end-game armors in same weight class to weigh more than early game.
    private fun getProtectionMod(slot: EquipmentSlot, item: ArmorItem?, weightTag: TagKey<Item>?): Double {
        val armorProtStandard = when(weightTag) {
            LIGHT_WEIGHT -> (lightArmorMap[slot.getName()] as? ArmorItem)?.protection
            MEDIUM_WEIGHT -> (mediumArmorMap[slot.getName()] as? ArmorItem)?.protection
            HEAVY_WEIGHT -> (heavyArmorMap[slot.getName()] as? ArmorItem)?.protection
            EXTRA_HEAVY_WEIGHT -> (xHeavyArmorMap[slot.getName()] as? ArmorItem)?.protection
            else -> { null }
        }

        if (armorProtStandard != null && item?.protection != null) {
            val protDifference = item.protection - armorProtStandard
            if (protDifference > 6)
                return 2.0
            else if (protDifference >= 5.0)
                return 1.5
            else if (protDifference >= 3.0)
                return 1.0
            else if (protDifference >= 1.0)
                return 0.5
        }

        return 0.0
    }
}