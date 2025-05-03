package com.neutralboolean.weighted.mixin.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.neutralboolean.weighted.Weighted;
import com.neutralboolean.weighted.WeightedStatics;

import java.util.List;

@Mixin(value = Item.class)
public class ArmorItemWeightMixin {

    @Inject(method = "appendTooltip",
            at = @At(value = "TAIL", target = "Lnet/minecraft/item/Item;appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item/TooltipContext;Ljava/util/List;Lnet/minecraft/item/tooltip/TooltipType;)V"))
    public void appendEquipmentWeightClass(ItemStack stack,
                                           Item.TooltipContext context, List<Text> tooltip,
                                           TooltipType type, CallbackInfo ci) {
        TagKey<Item> weightTag = WeightedStatics.INSTANCE.getWeightTag(stack);
        if (weightTag != null) {
            if (weightTag.id().equals(Weighted.INSTANCE.identifier("weight/light"))) {
                tooltip.add(Text.translatable("text.armored.weight.light"));
            }
            else if (weightTag.id().equals(Weighted.INSTANCE.identifier("weight/medium"))) {
                tooltip.add(Text.translatable("text.armored.weight.medium"));
            }
            else if (weightTag.id().equals(Weighted.INSTANCE.identifier("weight/heavy"))) {
                tooltip.add(Text.translatable("text.armored.weight.heavy"));
            }
            else if (weightTag.id().equals(Weighted.INSTANCE.identifier("weight/extraheavy"))) {
                tooltip.add(Text.translatable("text.armored.weight.extraheavy"));
            }
        }
    }
}
