package com.wildcard.malumbuddycards.item;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.MiracleRuneCurioItem;
import com.wildcard.malumbuddycards.MalumBuddycards;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class WhimsyRuneItem extends MiracleRuneCurioItem {
    public WhimsyRuneItem() {
        super(new Properties().stacksTo(1), RegistryHandler.SPIRIT);
    }

    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        this.addAttributeModifier(map, RegistryHandler.CHILDISH_SPOIL, new AttributeModifier(MalumBuddycards.malumBuddycardsLocation("whimsy_rune"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}
