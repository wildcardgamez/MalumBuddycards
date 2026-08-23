package com.wildcard.malumbuddycards.item;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IVoidItem;
import com.wildcard.buddycards.item.AttributeCurioItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.function.Consumer;

public class VoidAttributeCurioItem extends AttributeCurioItem implements IVoidItem {
    public VoidAttributeCurioItem(Properties properties, Consumer<Multimap<Holder<Attribute>, AttributeModifier>> consumer) {
        super(properties, consumer);
    }
}
