package com.wildcard.malumbuddycards.item;

import com.wildcard.buddycards.item.BuddycardItem;
import com.wildcard.buddycards.item.BuddycardSetPackItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class MalumBuddycardPackItem extends BuddycardSetPackItem {
    public MalumBuddycardPackItem() {
        super(RegistryHandler.MALUM_SET, 4, 1, BuddycardsItems.DEFAULT_RARITY_WEIGHTS, BuddycardsItems.DEFAULT_PACK_PROPERTIES);
    }

    public List<BuddycardItem> getPossibleCards(Rarity rarity) {
        return this.SET.getCards().stream().filter((card) -> card.getRarity() == rarity && card.shouldLoad()).toList();
    }
}
