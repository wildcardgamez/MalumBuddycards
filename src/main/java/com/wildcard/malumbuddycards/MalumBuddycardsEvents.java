package com.wildcard.malumbuddycards;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.WidgetDesign;
import com.sammy.malum.client.screen.codex.WidgetDesignType;
import com.sammy.malum.client.screen.codex.entries.GeasEntries;
import com.sammy.malum.client.screen.codex.entries.TotemMagicEntries;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.pages.recipe.RuneworkingPage;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextItemPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.client.screen.codex.pages.text.TextPage;
import com.sammy.malum.client.screen.codex.pages.text.WeepingWellTextPage;
import com.sammy.malum.client.screen.codex.screens.progression.ArcanaProgressionScreen;
import com.sammy.malum.client.screen.codex.screens.progression.VoidProgressionScreen;
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
import net.minecraft.world.entity.player.Player;
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
        event.add(EntityType.PLAYER, RegistryHandler.NULL_LUCK);
    }

    @SubscribeEvent
    public static void onReap(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source instanceof Player && source.getAttributeValue(RegistryHandler.CHILDISH_SPOIL) > 0) {
            ItemStack weapon = source.getMainHandItem();
            if (weapon.is(MalumTags.ItemTags.SOUL_SHATTER_CAPABLE_WEAPON) && event.getEntity() instanceof Monster entity && entity.isBaby()) {
                SoulHarvestHandler.SpiritSpawner spawner = new SoulHarvestHandler.SpiritSpawner(entity).setPreferredCollector(source);
                double childishSpoil = source.getAttributeValue(RegistryHandler.CHILDISH_SPOIL) - entity.getRandom().nextFloat();
                boolean soulPacking = GeasEffectHandler.hasGeasEffect(source, RegistryHandler.PACT_OF_SOUL_PACKING);
                while (childishSpoil > 0) {
                    if (soulPacking) {
                        float r = entity.getRandom().nextFloat();
                        if (r < .1f)
                            spawner.setCustomItems(new ItemStack(BuddycardsItems.MYSTERY_PACK.get()));
                        if (r < .4f)
                            spawner.setCustomItems(new ItemStack(RegistryHandler.PACK.get()));
                        else
                            spawner.setCustomItems(new ItemStack(RegistryHandler.SPIRIT_ITEM.get()));
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
        if (event.getScreen() instanceof ArcanaProgressionScreen) {
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
                            .addPage(new HeadlineTextPage("buddycard_set", "buddycard_set.1"))
                            .addPage(SpiritInfusionPage.fromOutput(RegistryHandler.PACK.get()))
            );
            event.getScreen().addEntry("rune_of_whimsy", -6, -1, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.RUNE_OF_WHIMSY).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new HeadlineTextPage("rune_of_whimsy", "rune_of_whimsy.1"))
                            .addPage(RuneworkingPage.fromOutput((RegistryHandler.RUNE_OF_WHIMSY.get())))
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
                            .addPage(new HeadlineTextPage("rancid_sleeve", "rancid_sleeve.1"))
                            .addPage(SpiritInfusionPage.fromOutput(RegistryHandler.RANCID_SLEEVE.get()))
            );
            GeasEntries.addBundledGeasEntry(event.getScreen(), "pacts_of_an_addicted_gambler", -7, -3, RegistryHandler.PACT_OF_SOUL_PACKING, RegistryHandler.PACT_OF_THE_BULK_BOX, RegistryHandler.PACT_OF_THE_RAINBOW, RegistryHandler.PACT_OF_THE_PERFECTIONIST);
            //TotemMagicEntries.addBundledRiteEntry(event.getScreen(), "childish_rites", -8, -2, RegistryHandler.RITE_OF_THE_LUCKY_RIP, RegistryHandler.RITE_OF_THE_BULK_BOON, RegistryHandler.RITE_OF_GRADE, RegistryHandler.RITE_OF_YOUTH, RegistryHandler.RUNE_OF_LUCKY_RIP, RegistryHandler.RUNE_OF_BULK_BOON);
            event.getScreen().addEntry("rune_of_lucky_rip", -9, -2, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.RUNE_OF_LUCKY_RIP).setDesign(WidgetDesignType.SMALL, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new HeadlineTextPage("rune_of_lucky_rip", "rune_of_lucky_rip"))
                            .addPage(RuneworkingPage.fromOutput((RegistryHandler.RUNE_OF_LUCKY_RIP.get())))
            );
            event.getScreen().addEntry("rune_of_bulk_boon", -9, -3, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.RUNE_OF_BULK_BOON).setDesign(WidgetDesignType.SMALL, WidgetDesignType.FrameType.RUNEWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new HeadlineTextPage("rune_of_bulk_boon", "rune_of_bulk_boon"))
                            .addPage(RuneworkingPage.fromOutput((RegistryHandler.RUNE_OF_BULK_BOON.get())))
            );
        } else if (event.getScreen() instanceof VoidProgressionScreen) {
            event.getScreen().addEntry("void.null_buddycard_pack", -3, -2, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.NULL_PACK).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.SOULWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new WeepingWellTextPage("void.null_buddycard_pack", "void.null_buddycard_pack.1", RegistryHandler.NULL_PACK.get()))
                            .addPage(new TextPage("void.null_buddycard_pack.2"))
            );
            event.getScreen().addEntry("void.ring_of_manic_laughter", -4, -1, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.MANIC_RING).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.SOULWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new HeadlineTextPage("void.ring_of_manic_laughter", "void.ring_of_manic_laughter.1"))
                            .addPage(SpiritInfusionPage.fromOutput((RegistryHandler.MANIC_RING.get())))
                            .afterUmbralCrystal()
            );
            event.getScreen().addEntry("void.rune_of_nostalgia", -4, -3, (b) ->
                    b.configureWidget((w) -> w.setIcon(RegistryHandler.RUNE_OF_NOSTALGIA).setDesign(WidgetDesignType.DEFAULT, WidgetDesignType.FrameType.SOULWOOD, WidgetDesignType.FillingType.PAPER))
                            .addPage(new HeadlineTextPage("void.rune_of_nostalgia", "void.rune_of_nostalgia.1"))
                            .addPage(RuneworkingPage.fromOutput((RegistryHandler.RUNE_OF_NOSTALGIA.get())))
            );
        }
    }
}

