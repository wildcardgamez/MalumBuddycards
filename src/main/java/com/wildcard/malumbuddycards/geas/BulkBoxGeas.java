package com.wildcard.malumbuddycards.geas;

import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.wildcard.buddycards.registries.BuddycardsAttributes;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class BulkBoxGeas extends GeasEffect {
    public BulkBoxGeas() {
        super(RegistryHandler.PACT_OF_THE_BULK_BOX.get());
    }

    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        this.addAttributeModifier(modifiers, BuddycardsAttributes.BUDDY_BONUS, 3, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(modifiers, BuddycardsAttributes.FOIL_BONUS, 1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(modifiers, BuddycardsAttributes.BUDDY_LUCK, -1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(modifiers, BuddycardsAttributes.FOIL_LUCK, -1, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}
