package com.neutralboolean.weighted.datagen.tags

import com.neutralboolean.weighted.Weighted.identifier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import java.util.concurrent.CompletableFuture

class WeightTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricTagProvider<Item>(output, RegistryKeys.ITEM, registriesFuture) {

    companion object {
        val LIGHT_WEIGHT: TagKey<Item> = TagKey.of(RegistryKeys.ITEM, identifier("weight/light"))
        val MEDIUM_WEIGHT: TagKey<Item> = TagKey.of(RegistryKeys.ITEM, identifier("weight/medium"))
        val HEAVY_WEIGHT: TagKey<Item> = TagKey.of(RegistryKeys.ITEM, identifier("weight/heavy"))
        val EXTRA_HEAVY_WEIGHT: TagKey<Item> = TagKey.of(RegistryKeys.ITEM, identifier("weight/extraheavy"))
    }

    override fun configure(p0: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(LIGHT_WEIGHT)
            .add(Items.LEATHER_HELMET)
            .add(Items.LEATHER_CHESTPLATE)
            .add(Items.LEATHER_LEGGINGS)
            .add(Items.LEATHER_BOOTS)
            .setReplace(false)
        getOrCreateTagBuilder(MEDIUM_WEIGHT)
            .add(Items.CHAINMAIL_HELMET)
            .add(Items.CHAINMAIL_CHESTPLATE)
            .add(Items.CHAINMAIL_LEGGINGS)
            .add(Items.CHAINMAIL_BOOTS)
            .setReplace(false)
        getOrCreateTagBuilder(HEAVY_WEIGHT)
            .add(Items.IRON_HELMET)
            .add(Items.GOLDEN_HELMET)
            .add(Items.DIAMOND_HELMET)
            .add(Items.IRON_CHESTPLATE)
            .add(Items.GOLDEN_CHESTPLATE)
            .add(Items.DIAMOND_CHESTPLATE)
            .add(Items.IRON_LEGGINGS)
            .add(Items.GOLDEN_LEGGINGS)
            .add(Items.DIAMOND_LEGGINGS)
            .add(Items.IRON_BOOTS)
            .add(Items.GOLDEN_BOOTS)
            .add(Items.DIAMOND_BOOTS)
            .setReplace(false)
        getOrCreateTagBuilder(EXTRA_HEAVY_WEIGHT)
            .add(Items.NETHERITE_HELMET)
            .add(Items.NETHERITE_CHESTPLATE)
            .add(Items.NETHERITE_LEGGINGS)
            .add(Items.NETHERITE_BOOTS)
            .setReplace(false)
    }
}