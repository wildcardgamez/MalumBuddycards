package com.wildcard.malumbuddycards;

import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.MalumAttributes;
import com.wildcard.buddycards.Buddycards;
import com.wildcard.buddycards.gear.IMedalTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Optional;

public enum MedalTypes implements IMedalTypes {
    MALUM_SET(null, ((map, mod) -> {
        map.put(RegistryHandler.CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "buddysteel_medal_malum"), 0.25 * (mod + 1), AttributeModifier.Operation.ADD_VALUE));
        map.put(MalumAttributes.SPIRIT_SPOILS, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "buddysteel_medal_malum"), 0.25 * (mod + 1), AttributeModifier.Operation.ADD_VALUE));
    }));

    MedalTypes(MedalTick effect, MedalAttributes attributes) {
        this.effect = Optional.ofNullable(effect);
        this.attributes = Optional.ofNullable(attributes);
    }

    private final Optional<MedalTick> effect;
    private final Optional<MedalAttributes> attributes;

    @Override
    public void effectTick(LivingEntity player, int mod) {
        effect.ifPresent(medalTick -> medalTick.applyEffect(player, mod));
    }

    @Override
    public void applyAttributes(Multimap<Holder<Attribute>, AttributeModifier> map, int mod) {
        attributes.ifPresent(medalAttributes -> medalAttributes.applyAttributes(map, mod));
    }

    interface MedalTick {
        void applyEffect(LivingEntity player, int mod);
    }

    interface MedalAttributes {
        void applyAttributes(Multimap<Holder<Attribute>, AttributeModifier> map, int mod);
    }
}
