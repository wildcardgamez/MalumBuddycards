package com.wildcard.malumbuddycards.item;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.MadnessRuneCurioItem;
import com.wildcard.malumbuddycards.MalumBuddycards;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class NostalgiaRuneItem extends MadnessRuneCurioItem {
    public NostalgiaRuneItem() {
        super(new Properties().stacksTo(1), RegistryHandler.SPIRIT);
    }

    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        this.addAttributeModifier(map, Attributes.SCALE, new AttributeModifier(MalumBuddycards.malumBuddycardsLocation("rune_of_nostalgia"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        this.addAttributeModifier(map, Attributes.MOVEMENT_SPEED, new AttributeModifier(MalumBuddycards.malumBuddycardsLocation("rune_of_nostalgia"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}
