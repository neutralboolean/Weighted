package com.neutralboolean.weighted.datagen

import com.neutralboolean.weighted.datagen.tags.WeightTagProvider
import neutralboolean.weighted.datagen.translations.WeightedEnglishLangProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import org.slf4j.LoggerFactory

object WeightedDataGeneratorEntrypoint : DataGeneratorEntrypoint {
	private val logger = LoggerFactory.getLogger("weighteddatagen")
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::WeightTagProvider)
		pack.addProvider(::WeightedEnglishLangProvider)
	}
}