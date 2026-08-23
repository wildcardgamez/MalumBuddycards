package com.wildcard.malumbuddycards.item;

import com.wildcard.buddycards.core.BuddycardSet;
import com.wildcard.buddycards.item.BuddycardBinderItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MalumNullBuddycardBinderItem extends BuddycardBinderItem {
    public MalumNullBuddycardBinderItem(Properties properties, BuddycardSet set, ResourceLocation texture, boolean large) {
        super(properties, set, texture, large);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.buddycards.buddycard.set_malum_null").withColor(5979018));
    }
}
