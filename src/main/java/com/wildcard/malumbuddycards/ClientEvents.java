package com.wildcard.malumbuddycards;

import com.sammy.malum.common.data.component.GeasDataComponent;
import com.sammy.malum.core.systems.geas.GeasEffectType;
import com.sammy.malum.registry.common.MalumCreativeTabs;
import com.sammy.malum.registry.common.MalumTags;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import com.sammy.malum.registry.common.item.MalumItems;
import com.sammy.malum.registry.common.magic.MalumGeasEffectTypes;
import com.wildcard.buddycards.client.renderer.MedalRenderer;
import com.wildcard.buddycards.item.BuddycardItem;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(value = MalumBuddycards.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MalumBuddycards.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        setupRenderers();
    }

    @SubscribeEvent
    public static void creativeTabSetup(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(BuddycardsMisc.MAIN_TAB.getKey())) {
            for (DeferredHolder<Item, ? extends Item> i : RegistryHandler.ITEMS.getEntries())
                if (!(i.get() instanceof BuddycardItem))
                    event.accept(i.get());
        } else if (event.getTabKey().equals(BuddycardsMisc.CARDS_TAB.getKey())) {
            for (DeferredHolder<Item, ? extends Item> i : RegistryHandler.ITEMS.getEntries())
                if(i.get() instanceof BuddycardItem card && card.shouldLoad())
                    event.accept(i.get());
        } else if (event.getTabKey().equals(MalumCreativeTabs.GEAS.getKey())) {
            for(DeferredHolder<GeasEffectType, ? extends GeasEffectType> geas : RegistryHandler.GEAS.getEntries()) {
                event.accept(geas.get().getDummyCreativeStack());
            }
        }
    }

    public static void setupRenderers() {
        CuriosRendererRegistry.register(RegistryHandler.MEDAL.get(), () -> new MedalRenderer("textures/models/medal/buddysteel_medal_malum"));
    }
}
