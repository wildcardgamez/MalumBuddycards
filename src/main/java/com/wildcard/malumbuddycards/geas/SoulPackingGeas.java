package com.wildcard.malumbuddycards.geas;

import com.google.common.collect.Multimap;
import com.sammy.malum.core.helpers.ComponentHelper;
import com.sammy.malum.core.systems.events.CollectSpiritEvent;
import com.sammy.malum.core.systems.events.ModifySpiritSpoilsEvent;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.wildcard.buddycards.registries.BuddycardsAttributes;
import com.wildcard.malumbuddycards.MalumBuddycards;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public class SoulPackingGeas extends GeasEffect {
    public SoulPackingGeas() {
        super(RegistryHandler.PACT_OF_SOUL_PACKING.get());
    }

    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(Component.translatable("malumbuddycards.effect.soul_packing").withStyle(ChatFormatting.BLUE));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        this.addAttributeModifier(modifiers, BuddycardsAttributes.BUDDY_BONUS, -3, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(modifiers, BuddycardsAttributes.FOIL_BONUS, -1, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}
