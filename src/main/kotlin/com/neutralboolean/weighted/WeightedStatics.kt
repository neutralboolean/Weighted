package com.neutralboolean.weighted

import com.neutralboolean.weighted.Weighted.identifier
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.EXTRA_HEAVY_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.HEAVY_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.LIGHT_WEIGHT
import com.neutralboolean.weighted.datagen.tags.WeightTagProvider.Companion.MEDIUM_WEIGHT
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.tag.TagKey
import com.neutralboolean.weighted.config.Configs
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.truncate

object WeightedStatics {

    val weightBaseDict: Map<TagKey<Item>, Supplier<Double>> = mapOf(
        LIGHT_WEIGHT to Configs.serverConfig.lightWeight,
        MEDIUM_WEIGHT to Configs.serverConfig.mediumWeight,
        HEAVY_WEIGHT to Configs.serverConfig.heavyWeight,
        EXTRA_HEAVY_WEIGHT to Configs.serverConfig.extraHeavyWeight
    )
    val slotMultDict: Map<String, Supplier<Double>> = mapOf(
        EquipmentSlot.HEAD.getName() to Configs.serverConfig.extremitiesWeightMultiplier,
        EquipmentSlot.CHEST.getName() to Configs.serverConfig.chestWeightMultiplier,
        EquipmentSlot.LEGS.getName() to Configs.serverConfig.legsWeightMultiplier,
        EquipmentSlot.FEET.getName() to Configs.serverConfig.extremitiesWeightMultiplier
    )
    val weightModDict = mapOf(
        EquipmentSlot.HEAD.getName() to identifier("helmet_weight"),
        EquipmentSlot.CHEST.getName() to identifier("chestplate_weight"),
        EquipmentSlot.LEGS.getName() to identifier("leggings_weight"),
        EquipmentSlot.FEET.getName() to identifier("boots_weight")
    )

    val lightArmorMap = mapOf(
        EquipmentSlot.HEAD.getName() to Items.LEATHER_HELMET,
        EquipmentSlot.CHEST.getName() to Items.LEATHER_CHESTPLATE,
        EquipmentSlot.LEGS.getName() to Items.LEATHER_LEGGINGS,
        EquipmentSlot.FEET.getName() to Items.LEATHER_BOOTS
    )
    val mediumArmorMap = mapOf(
        EquipmentSlot.HEAD.getName() to Items.CHAINMAIL_HELMET,
        EquipmentSlot.CHEST.getName() to Items.CHAINMAIL_CHESTPLATE,
        EquipmentSlot.LEGS.getName() to Items.CHAINMAIL_LEGGINGS,
        EquipmentSlot.FEET.getName() to Items.CHAINMAIL_BOOTS
    )
    val heavyArmorMap = mapOf(
        EquipmentSlot.HEAD.getName() to Items.IRON_HELMET,
        EquipmentSlot.CHEST.getName() to Items.IRON_CHESTPLATE,
        EquipmentSlot.LEGS.getName() to Items.IRON_LEGGINGS,
        EquipmentSlot.FEET.getName() to Items.IRON_BOOTS
    )
    val xHeavyArmorMap = mapOf(
        EquipmentSlot.HEAD.getName() to Items.NETHERITE_HELMET,
        EquipmentSlot.CHEST.getName() to Items.NETHERITE_CHESTPLATE,
        EquipmentSlot.LEGS.getName() to Items.NETHERITE_LEGGINGS,
        EquipmentSlot.FEET.getName() to Items.NETHERITE_BOOTS
    )


    fun roundToHalf(dividend: Double): Double {
        val i = dividend.rem(1.0) - 0.5
        if (i > 0.05)
            return ceil(dividend)
        else if (i < -0.05)
            return floor(dividend)
        else
            return truncate(dividend) + 0.5
    }

    fun getWeightTag(itemStack: ItemStack): TagKey<Item>? {
        val wantedWeightTags = setOf(
            LIGHT_WEIGHT, MEDIUM_WEIGHT, HEAVY_WEIGHT, EXTRA_HEAVY_WEIGHT
        )

        return itemStack.streamTags().filter { element -> element in wantedWeightTags }.findAny().getOrNull()
    }
}