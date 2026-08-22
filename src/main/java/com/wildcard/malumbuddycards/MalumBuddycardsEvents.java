package com.wildcard.malumbuddycards;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.WidgetDesignType;
import com.sammy.malum.client.screen.codex.entries.GeasEntries;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextItemPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.client.screen.codex.pages.text.TextPage;
import com.sammy.malum.core.handlers.GeasEffectHandler;
import com.sammy.malum.core.handlers.SoulHarvestHandler;
import com.sammy.malum.core.systems.events.CollectSpiritEvent;
import com.sammy.malum.core.systems.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.registry.common.MalumTags;
import com.wildcard.buddycards.Buddycards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@Mod(value = MalumBuddycards.MOD_ID)
@EventBusSubscriber(modid = MalumBuddycards.MOD_ID)
public class MalumBuddycardsEvents {
    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, RegistryHandler.CHILDISH_SPOIL);
    }

    @SubscribeEvent
    public static void onReap(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof LivingEntity source && source.getAttributeValue(RegistryHandler.CHILDISH_SPOIL) > 0) {
            ItemStack weapon = source.getMainHandItem();
            if (weapon.is(MalumTags.ItemTags.SOUL_SHATTER_CAPABLE_WEAPON) && event.getEntity() instanceof Monster entity && entity.isBaby()) {
                SoulHarvestHandler.SpiritSpawner spawner = new SoulHarvestHandler.SpiritSpawner(entity).setPreferredCollector(source);
                double childishSpoil = source.getAttributeValue(RegistryHandler.CHILDISH_SPOIL) - entity.getRandom().nextFloat();
                System.out.println(childishSpoil);
                boolean soulPacking = GeasEffectHandler.hasGeasEffect(source, RegistryHandler.PACT_OF_SOUL_PACKING);
                while(childishSpoil > 0) {
                    if (soulPacking) {
                        float r = entity.getRandom().nextFloat();
                        if (r < .1)
                            spawner.setCustomItems(new ItemStack(BuddycardsItems.MYSTERY_PACK.get()));
                        if (r < .6)
                            spawner.setCustomItems(new ItemStack(RegistryHandler.PACK.get()));
                    } else
                        spawner.setCustomItems(new ItemStack(RegistryHandler.SPIRIT_ITEM.get()));
                    childishSpoil -= 1;
                }
                spawner.spawnSpirits(entity.level());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onCodexSetup(SetupMalumCodexEntriesEvent event) {
        event.getScreen().addEntry("childish_ring", -5, 0, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.CHILDISH_RING).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextPage("childish_ring", "childish_ring.1"))
                        .addPage(CraftingPage.ringPage(RegistryHandler.CHILDISH_RING.get(), BuddycardsItems.BUDDYSTEEL_INGOT.get()))
                        .addReference(new EntryReference(RegistryHandler.YOUTHFUL_RING.get(),
                                BookEntry.create("ring_of_youthful_spoils")
                                        .addPage(new HeadlineTextPage("ring_of_youthful_spoils", "ring_of_youthful_spoils.1"))
                                        .addPage(SpiritInfusionPage.fromOutput((RegistryHandler.YOUTHFUL_RING.get())))))
        );
        event.getScreen().addEntry("childish_spirit", -5, -1, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.SPIRIT_ITEM).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextItemPage("childish_spirit", "childish_spirit.1", RegistryHandler.SPIRIT_ITEM.get()))
                        .addPage(new TextPage("childish_spirit.2"))
        );
        event.getScreen().addEntry("buddycard_set", -4, -2, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.PACK).setDesign(WidgetDesignType.GILDED, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextItemPage("buddycard_set", "buddycard_set.1", RegistryHandler.PACK.get()))
                        .addPage(SpiritInfusionPage.fromOutput(RegistryHandler.PACK.get()))
        );
        event.getScreen().addEntry("childish_brooch", -7, 0, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.CHILDISH_BROOCH).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextPage("childish_brooch", "childish_brooch.1"))
                        .addPage(CraftingPage.broochPage(RegistryHandler.CHILDISH_BROOCH.get(), BuddycardsItems.BUDDYSTEEL_INGOT.get(), BuddycardsItems.BUDDYSTEEL_BLOCK.get()))
        );
        event.getScreen().addEntry("triplicate_brooch", -8, 0, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.TRIPLICATE_BROOCH).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextPage("triplicate_brooch", "triplicate_brooch.1"))
                        .addPage(SpiritInfusionPage.fromOutput((RegistryHandler.TRIPLICATE_BROOCH.get())))
        );
        event.getScreen().addEntry("rancid_sleeve", -4, -3, (b) ->
                b.configureWidget((w) -> w.setIcon(RegistryHandler.RANCID_SLEEVE).setDesign(WidgetDesignType.SMALL, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                        .addPage(new HeadlineTextItemPage("rancid_sleeve", "rancid_sleeve.1", RegistryHandler.RANCID_SLEEVE.get()))
                        .addPage(SpiritInfusionPage.fromOutput(RegistryHandler.RANCID_SLEEVE.get()))
        );
        GeasEntries.addBundledGeasEntry(event.getScreen(), "pacts_of_an_addicted_gambler", -6, -3, RegistryHandler.PACT_OF_SOUL_PACKING, RegistryHandler.PACT_OF_THE_BULK_BOX, RegistryHandler.PACT_OF_THE_RAINBOW, RegistryHandler.PACT_OF_THE_PERFECTIONIST);
    }
}

