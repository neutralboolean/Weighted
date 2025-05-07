package com.neutralboolean.weighted.config

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.annotations.Version
import me.fzzyhmstrs.fzzy_config.api.FileType
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import me.fzzyhmstrs.fzzy_config.util.Walkable
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber
import com.neutralboolean.weighted.Weighted.identifier

@Version(0)
@RequiresAction(Action.RELOAD_DATA)
class ServerConfig: Config(identifier("weighted_server")) {

    var baseGroup = ConfigGroup("base_weight_group")
    var lightWeight = ValidatedDouble(1.0, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
    var mediumWeight = ValidatedDouble(2.0, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
    var heavyWeight = ValidatedDouble(4.0, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
    @ConfigGroup.Pop
    var extraHeavyWeight = ValidatedDouble(6.0, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)



    var multGroup = ConfigGroup("weight_mult_group")
    var extremitiesWeightMultiplier = ValidatedDouble(1.0, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
    var legsWeightMultiplier = ValidatedDouble(1.25, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)
    @ConfigGroup.Pop
    var chestWeightMultiplier = ValidatedDouble(1.50, 999.0, 0.0, ValidatedNumber.WidgetType.TEXTBOX)

    class StatusEffectsSection: ConfigSection() {
        class WeightedStatusEffectsSetting(ddMult: Double, sdMult: Double, srMult: Double): Walkable {
            constructor(): this(0.0, 0.0, 0.0)

            var depletedDelayMult = ddMult
            var staminaDelayMult = sdMult
            var staminaRegenMult = srMult
        }

        var burdenedEffectStats = ValidatedAny(WeightedStatusEffectsSetting(0.34, 0.0, 0.0))
        var overBurdenedEffectStats = ValidatedAny(WeightedStatusEffectsSetting(2.0, 1.0, -0.5))
        var unBurdenedEffectStats = ValidatedAny(WeightedStatusEffectsSetting(-0.67, -0.5, 1.0))
    }
    var statusEffectsSettings = StatusEffectsSection()


    override fun fileType(): FileType {
        return FileType.JSON5
    }
}