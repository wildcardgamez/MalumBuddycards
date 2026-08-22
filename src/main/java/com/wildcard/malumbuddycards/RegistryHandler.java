package com.wildcard.malumbuddycards;

import com.sammy.malum.common.block.SpiritedGlassBlock;
import com.sammy.malum.common.block.VarnishedTerracottaBlock;
import com.sammy.malum.common.data.component.SoulwovenBannerPatternDataComponent;
import com.sammy.malum.common.item.GeasItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.sammy.malum.core.systems.geas.GeasEffectType;
import com.sammy.malum.core.systems.registry.DeferredGeasTypes;
import com.sammy.malum.core.systems.registry.DeferredSpiritTypes;
import com.sammy.malum.core.systems.registry.GeasHolder;
import com.sammy.malum.core.systems.registry.SpiritHolder;
import com.sammy.malum.core.systems.spirit.type.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.type.SpiritColorProperties;
import com.sammy.malum.registry.common.block.MalumBlockProperties;
import com.sammy.malum.registry.common.item.MalumItems;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.wildcard.buddycards.Buddycards;
import com.wildcard.buddycards.block.BuddycardBoosterBoxBlock;
import com.wildcard.buddycards.block.CardDisplayBlock;
import com.wildcard.buddycards.block.CardStandBlock;
import com.wildcard.buddycards.core.BuddycardSet;
import com.wildcard.buddycards.item.*;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsComponents;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.malumbuddycards.geas.BulkBoxGeas;
import com.wildcard.malumbuddycards.geas.PerfectionistGeas;
import com.wildcard.malumbuddycards.geas.RainbowGeas;
import com.wildcard.malumbuddycards.geas.SoulPackingGeas;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.CuriosApi;

import java.awt.*;
import java.util.function.Supplier;

public class RegistryHandler {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MalumBuddycards.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MalumBuddycards.MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MalumBuddycards.MOD_ID);
    public static final DeferredSpiritTypes SPIRITS = DeferredSpiritTypes.create(MalumBuddycards.MOD_ID);
    public static final DeferredGeasTypes GEAS = DeferredGeasTypes.create(MalumBuddycards.MOD_ID);

    public static void registerAll(IEventBus eventBus) {
        CHILDISH_SPOIL = ATTRIBUTES.register("childish_spoil", () -> new RangedAttribute("attribute.name.malumbuddycards.childish_spoil", 0, 0, 2048));
        
        BOOSTER_BOX = BLOCKS.register("buddycard_booster_box_malum", () -> new BuddycardBoosterBoxBlock(BuddycardsItems.DEFAULT_BUDDYCARD_REQUIREMENT, BuddycardsBlocks.BOOSTER_BOX_PROPERTIES));

        RUNEWOOD_CARD_DISPLAY = registerDisplay("runewood_card_display", () -> new CardDisplayBlock(MalumBlockProperties.RUNEWOOD()));
        SOULWOOD_CARD_DISPLAY = registerDisplay("soulwood_card_display", () -> new CardDisplayBlock(MalumBlockProperties.SOULWOOD()));
        TAINTED_ROCK_CARD_STAND = registerStand("tainted_rock_card_stand", () -> new CardStandBlock(MalumBlockProperties.TAINTED_ROCK()));
        TWISTED_ROCK_CARD_STAND = registerStand("twisted_rock_card_stand", () -> new CardStandBlock(MalumBlockProperties.TWISTED_ROCK()));

        SPIRITED_GLASS = BLOCKS.register("childish_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
        VARNISHED_TERRACOTTA = BLOCKS.register("childish_varnished_terracotta", () -> new VarnishedTerracottaBlock(MalumBlockProperties.VARNISHED_TERRACOTTA(DyeColor.LIGHT_BLUE)));

        SPIRIT = SPIRITS.register("childish", () -> new SpiritArcanaType(SpiritColorProperties.create(new Color(152, 232, 255), new Color(255, 119, 228)).build(), SPIRIT_ITEM));

        PACK = ITEMS.register("buddycard_pack_malum", () -> new BuddycardSetPackItem(MALUM_SET, 4, 1, BuddycardsItems.DEFAULT_RARITY_WEIGHTS, BuddycardsItems.DEFAULT_PACK_PROPERTIES));
        BINDER = ITEMS.register("buddycard_binder_malum", () -> new BuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "textures/gui/buddycard_binder_malum.png"), false));
        LARGE_BINDER = ITEMS.register("large_buddycard_binder_malum", () -> new BuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "textures/gui/large_buddycard_binder_malum.png"), true));
        MEDAL = ITEMS.register("buddysteel_medal_malum", () -> new BuddysteelSetMedalItem(MedalTypes.MALUM_SET, MALUM_SET, new Item.Properties().stacksTo(1).component(BuddycardsComponents.COLLECTION_TIER, 0)));

        BOOSTER_BOX_ITEM = ITEMS.register("buddycard_booster_box_malum", () -> new BuddycardBoosterBoxItem(BOOSTER_BOX.get(), PACK, BuddycardsItems.DEFAULT_UNCOMMON_PROPERTIES));

        registerCards(1, 12, Rarity.COMMON, MALUM_REQUIREMENT);
        registerCards(13, 9, Rarity.UNCOMMON, MALUM_REQUIREMENT);
        registerCards(22, 4, Rarity.RARE, MALUM_REQUIREMENT);
        registerCards(26, 2, Rarity.EPIC, MALUM_REQUIREMENT);

        ITEMS.register("runewood_card_display", () -> new BlockItem(RUNEWOOD_CARD_DISPLAY.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("soulwood_card_display", () -> new BlockItem(SOULWOOD_CARD_DISPLAY.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("tainted_rock_card_stand", () -> new BlockItem(TAINTED_ROCK_CARD_STAND.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("twisted_rock_card_stand", () -> new BlockItem(TWISTED_ROCK_CARD_STAND.get(), BuddycardsItems.DEFAULT_PROPERTIES));

        SPIRIT_ITEM = ITEMS.register("childish_spirit", () -> new SpiritShardItem(new Item.Properties(), SPIRIT));
        SPIRITED_GLASS_ITEM = ITEMS.register("childish_spirited_glass", () -> new BlockItem(SPIRITED_GLASS.get(), new Item.Properties()));
        VARNISHED_TERRACOTTA_ITEM = ITEMS.register("childish_varnished_terracotta", () -> new BlockItem(VARNISHED_TERRACOTTA.get(), new Item.Properties()));
        SoulwovenBannerPatternDataComponent.register(ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "collection"));

        CHILDISH_RING = ITEMS.register("childish_ring", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1),
                (m) -> m.put(CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "childish_ring"), 0.25, AttributeModifier.Operation.ADD_VALUE))));
        YOUTHFUL_RING = ITEMS.register("ring_of_youthful_spoils", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1),
                (m) -> m.put(CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "ring_of_youthful_spoils"), 1, AttributeModifier.Operation.ADD_VALUE))));
        CHILDISH_BROOCH = ITEMS.register("childish_brooch", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1), (m) -> {
            CuriosApi.addSlotModifier(m, "medal", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "childish_brooch"), 2, AttributeModifier.Operation.ADD_VALUE);
            CuriosApi.addSlotModifier(m, "belt", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "childish_brooch"), -1, AttributeModifier.Operation.ADD_VALUE);
        }));
        TRIPLICATE_BROOCH = ITEMS.register("triplicate_brooch", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1), (m) -> {
            CuriosApi.addSlotModifier(m, "medal", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "triplicate_brooch"), 2, AttributeModifier.Operation.ADD_VALUE);
            m.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, "triplicate_brooch"), -0.04, AttributeModifier.Operation.ADD_VALUE));
        }));

        ITEMS.register("odd_puzzlebox", () -> new Item(new Item.Properties()));
        RANCID_SLEEVE = ITEMS.register("rancid_sleeve", () -> new RancidSleeveItem(new Item.Properties(), new float[]{0.4f, 0.3f, 0.225f, 0.073f}));

        PACT_OF_SOUL_PACKING = GEAS.register("pact_of_soul_packing", () -> new GeasEffectType(SoulPackingGeas::new, SPIRIT));
        PACT_OF_THE_BULK_BOX = GEAS.register("pact_of_the_bulk_box", () -> new GeasEffectType(BulkBoxGeas::new, SPIRIT));
        PACT_OF_THE_RAINBOW = GEAS.register("pact_of_the_rainbow", () -> new GeasEffectType(RainbowGeas::new, SPIRIT, MalumSpiritTypes.INFERNAL_SPIRIT));
        PACT_OF_THE_PERFECTIONIST = GEAS.register("pact_of_the_perfectionist", () -> new GeasEffectType(PerfectionistGeas::new, SPIRIT, MalumSpiritTypes.ELDRITCH_SPIRIT));

        ATTRIBUTES.register(eventBus);
        SPIRITS.register(eventBus);
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        GEAS.register(eventBus);
    }

    public static final BuddycardSet MALUM_SET = new BuddycardSet("malum");

    public static final BuddycardsItems.BuddycardRequirement MALUM_REQUIREMENT = () -> ModList.get().isLoaded("malum");

    public static DeferredHolder<Attribute, Attribute> CHILDISH_SPOIL;

    public static SpiritHolder SPIRIT;

    public static DeferredBlock<BuddycardBoosterBoxBlock> BOOSTER_BOX;
    public static DeferredBlock<CardDisplayBlock> RUNEWOOD_CARD_DISPLAY;
    public static DeferredBlock<CardDisplayBlock> SOULWOOD_CARD_DISPLAY;
    public static DeferredBlock<CardStandBlock> TAINTED_ROCK_CARD_STAND;
    public static DeferredBlock<CardStandBlock> TWISTED_ROCK_CARD_STAND;
    public static DeferredBlock<SpiritedGlassBlock> SPIRITED_GLASS;
    public static DeferredBlock<VarnishedTerracottaBlock> VARNISHED_TERRACOTTA;

    public static DeferredItem<BuddycardPackItem> PACK;
    public static DeferredItem<BuddycardBinderItem> BINDER;
    public static DeferredItem<BuddycardBinderItem> LARGE_BINDER;
    public static DeferredItem<BuddysteelSetMedalItem> MEDAL;
    public static DeferredItem<BuddycardBoosterBoxItem> BOOSTER_BOX_ITEM;
    public static DeferredItem<SpiritShardItem> SPIRIT_ITEM;
    public static DeferredItem<BlockItem> SPIRITED_GLASS_ITEM;
    public static DeferredItem<BlockItem> VARNISHED_TERRACOTTA_ITEM;
    public static DeferredItem<Item> CHILDISH_RING;
    public static DeferredItem<Item> YOUTHFUL_RING;
    public static DeferredItem<Item> CHILDISH_BROOCH;
    public static DeferredItem<Item> TRIPLICATE_BROOCH;
    public static DeferredItem<Item> RANCID_SLEEVE;

    public static GeasHolder<GeasEffectType> PACT_OF_THE_BULK_BOX;
    public static GeasHolder<GeasEffectType> PACT_OF_SOUL_PACKING;
    public static GeasHolder<GeasEffectType> PACT_OF_THE_RAINBOW;
    public static GeasHolder<GeasEffectType> PACT_OF_THE_PERFECTIONIST;

    public static void registerCards(int startValue, int amount, Rarity rarity, BuddycardsItems.BuddycardRequirement requirement) {
        for (int i = startValue; i < amount + startValue; i++) {
            int finalI = i;
            ITEMS.register("buddycard_malum" + i, () -> new BuddycardItem(requirement, MALUM_SET, finalI, rarity));
        }
    }

    public static DeferredBlock<CardDisplayBlock> registerDisplay(String id, Supplier<CardDisplayBlock> supplier) {
        DeferredBlock<CardDisplayBlock> display = BLOCKS.register(id, supplier);
        BuddycardsBlocks.DISPLAY_BLOCKS.add(display);
        return display;
    }

    public static DeferredBlock<CardStandBlock> registerStand(String id, Supplier<CardStandBlock> supplier) {
        DeferredBlock<CardStandBlock> stand = BLOCKS.register(id, supplier);
        BuddycardsBlocks.STAND_BLOCKS.add(stand);
        return stand;
    }
}
