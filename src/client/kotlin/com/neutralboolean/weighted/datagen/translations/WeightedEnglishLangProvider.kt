package com.neutralboolean.weighted.datagen.translations

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class WeightedEnglishLangProvider(
    dataOutput: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricLanguageProvider(dataOutput, "en_us", registryLookup) {

    override fun generateTranslations(registryLookup: RegistryWrapper.WrapperLookup?, translationBuilder: TranslationBuilder?) {
        translationBuilder?.add("text.weighted.weight.light", "§7Weight Class: §fLight")
        translationBuilder?.add("text.weighted.weight.medium", "§7Weight Class: Medium")
        translationBuilder?.add("text.weighted.weight.heavy", "§7Weight Class: §8Heavy")
        translationBuilder?.add("text.weighted.weight.extraheavy", "§7Weight Class: §8Extra-Heavy")

        translationBuilder?.add("text.weighted.tag.weight.light", "weight/light")
        translationBuilder?.add("text.weighted.tag.weight.medium", "weight/medium")
        translationBuilder?.add("text.weighted.tag.weight.heavy", "weight/heavy")
        translationBuilder?.add("text.weighted.tag.weight.extraheavy", "weight/extra_heavy")

        translationBuilder?.add("effect.weighted.unburdened", "Un-Burdened")
        translationBuilder?.add("effect.weighted.burdened", "Burdened")
        translationBuilder?.add("effect.weighted.overburdened", "Over-Burdened")

        translationBuilder?.add("weighted.armored_server.lightWeight", "\"Light\" Armor Base Weight")
        translationBuilder?.add("weighted.armored_server.lightWeight.desc",
            "The base weight of an individual \"light\"-class equipment piece.\n\n" +
                    "Minimum: 0.0")
        translationBuilder?.add("weighted.armored_server.mediumWeight", "\"Medium\" Armor Base Weight")
        translationBuilder?.add("weighted.armored_server.mediumWeight.desc",
            "The base weight of an individual \"medium\"-class equipment piece.\n\n" +
                    "Minimum: 0.0")
        translationBuilder?.add("weighted.armored_server.heavyWeight", "\"Heavy\" Armor Base Weight")
        translationBuilder?.add("weighted.armored_server.heavyWeight.desc",
            "The base weight of an individual \"heavy\"-class equipment piece.\n\n" +
                    "Minimum: 0.0")
        translationBuilder?.add("weighted.armored_server.extraHeavyWeight", "\"Extra-Heavy\" Armor Base Weight")
        translationBuilder?.add("weighted.armored_server.extraHeavyWeight.desc",
            "The base weight of an individual \"extra-heavy\"-class equipment piece.\n\n" +
                    "Minimum: 0.0")

        translationBuilder?.add("weighted.armored_server.extremitiesWeightMultiplier", "Extremities Weight Multiplier")
        translationBuilder?.add("weighted.armored_server.extremitiesWeightMultiplier.desc",
            "Weight of equipment in the helmet and boots slots are multiplied by this value.\n\n" +
                    "Minimum: 0.0")
        translationBuilder?.add("weighted.armored_server.legsWeightMultiplier", "Legs Weight Multiplier")
        translationBuilder?.add("weighted.armored_server.legsWeightMultiplier.desc",
            "Weight of equipment in the leggings slot is multiplied by this value.\n\n" +
                    "Minimum: 0.0")
        translationBuilder?.add("weighted.armored_server.chestWeightMultiplier", "Chest Weight Multiplier")
        translationBuilder?.add("weighted.armored_server.chestWeightMultiplier.desc",
            "Weight of equipment in the chestplate slot is multiplied by this value.\n\n" +
                    "Minimum: 0.0")

        translationBuilder?.add("weighted.armored_server.statusEffectsSection.burdened", "Chest Weight Multiplier")
        translationBuilder?.add("weighted.armored_server.statusEffectsSection.burdened.desc",
            "Weight of equipment in the chestplate slot is multiplied by this value.\n\n" +
                    "Minimum: 0.0")
    }
}