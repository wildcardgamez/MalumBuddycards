package com.wildcard.malumbuddycards;

import com.wildcard.buddycards.item.BuddycardItem;
import com.wildcard.buddycards.item.GradingSleeveItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class RancidSleeveItem extends GradingSleeveItem {
    public RancidSleeveItem(Properties properties, float[] odds) {
        super(properties, odds);
    }

    public boolean canSleeve(ItemStack card, ItemStack sleeves) {
        return card.getItem() instanceof BuddycardItem && BuddycardItem.getGrade(card) != 1;
    }

    public ItemStack sleeveResult(ItemStack card, ItemStack sleeves, Player player, Level level) {
        ItemStack newCard = super.sleeveResult(card, sleeves, player, level);
        int currentGrade = BuddycardItem.getGrade(card);
        return currentGrade == 0 || BuddycardItem.getGrade(newCard) <= currentGrade ? newCard : card.copyWithCount(1);
    }

    public boolean trySleeve(ItemStack card, ItemStack sleeves, @Nullable Player player, Level level) {
        if (level instanceof ServerLevel && this.canSleeve(card, sleeves)) {
            ItemStack result = sleeveResult(card, sleeves, player, level);
            if (!(BuddycardItem.getGrade(card) == BuddycardItem.getGrade(result))) {
                ItemHandlerHelper.giveItemToPlayer(player, result);
                card.shrink(1);
            }
            if (this.CONSUME) {
                sleeves.shrink(1);
            }
            return true;
        } else {
            return false;
        }
    }
}
