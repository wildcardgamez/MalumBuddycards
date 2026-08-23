package com.wildcard.malumbuddycards.geas;

import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.wildcard.buddycards.registries.BuddycardsAttributes;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class RainbowGeas extends GeasEffect {
    public RainbowGeas() {
        super(RegistryHandler.PACT_OF_THE_RAINBOW.get());
    }

    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        this.addAttributeModifier(modifiers, BuddycardsAttributes.FOIL_LUCK, 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(modifiers, BuddycardsAttributes.BUDDY_BONUS, -2, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}