package com.wildcard.malumbuddycards.item;

import com.sammy.malum.visual_effects.ScreenParticleEffects;
import com.wildcard.buddycards.Buddycards;
import com.wildcard.buddycards.item.BuddycardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import java.util.List;

public class MalumNullBuddycardItem extends BuddycardItem implements ParticleEmitterHandler.ItemParticleSupplier {
    public MalumNullBuddycardItem(BuddycardsItems.BuddycardRequirement shouldLoad, int cardNumber, Rarity rarity) {
        super(shouldLoad, RegistryHandler.MALUM_SET, cardNumber, rarity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        //Show the cards joke/tooltip
        tooltip.add(Component.translatable(getDescriptionId() + ".desc").withStyle(ChatFormatting.ITALIC).withColor(11295174));
        //Show the set, card number, and shiny symbol if applicable
        MutableComponent cn = Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.number_separator");
        cn.append("" + CARD_NUMBER);
        int foil = getFoil(stack);
        if(getFoil(stack) != 0) {
            if (foil == 1)
                cn.append(Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.foil_symbol").withStyle(ChatFormatting.YELLOW));
            else if (foil == 2)
                cn.append(Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.foil_symbol").withColor(4677798));
            else if (foil == 3)
                cn.append(Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.foil_symbol").withColor(16721358));
        }
        tooltip.add(Component.translatable("item.buddycards.buddycard.set_malum_null").append(cn).withColor(5979018));
        //Show grade
        if(isGraded(stack)) {
            MutableComponent grade = Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.grade").withStyle(ChatFormatting.LIGHT_PURPLE);
            if (getGrade(stack) == 5)
                grade.append(Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.grade.5").withStyle(ChatFormatting.YELLOW));
            else
                grade.append(Component.translatable("item." + Buddycards.MOD_ID + ".buddycard.grade." + getGrade(stack)).withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(grade);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if(stack.getRarity().equals(Rarity.EPIC))
            return Component.translatable(this.getDescriptionId(stack)).withColor(16739658);
        else
            return Component.translatable(this.getDescriptionId(stack)).withColor(16757644);
    }

    public void spawnEarlyParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnVoidItemScreenParticles(target, level, 0.25f * (1 + getFoil(stack)), partialTick);
    }

    @Override
    public boolean shouldLoad() {
        return false;
    }
}
