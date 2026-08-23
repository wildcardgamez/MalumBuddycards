package com.wildcard.malumbuddycards;

import com.sammy.malum.common.block.SpiritedGlassBlock;
import com.sammy.malum.common.block.VarnishedTerracottaBlock;
import com.sammy.malum.common.block.curiosities.mana_mote.ManaMoteBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.data.component.SoulwovenBannerPatternDataComponent;
import com.sammy.malum.common.item.curiosities.curios.runes.TotemicRuneCurioItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.geas.GeasEffectType;
import com.sammy.malum.core.systems.registry.DeferredGeasTypes;
import com.sammy.malum.core.systems.registry.DeferredSpiritTypes;
import com.sammy.malum.core.systems.registry.GeasHolder;
import com.sammy.malum.core.systems.registry.SpiritHolder;
import com.sammy.malum.core.systems.registry.rite.DeferredRiteEntityEffectTypes;
import com.sammy.malum.core.systems.registry.rite.DeferredRiteTypes;
import com.sammy.malum.core.systems.registry.rite.RiteEffectHolder;
import com.sammy.malum.core.systems.registry.rite.RiteHolder;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import com.sammy.malum.core.systems.rite.SpiritRiteTypeBuilder;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffect;
import com.sammy.malum.core.systems.rite.effect.SpiritRitePotionEffect;
import com.sammy.malum.core.systems.spirit.SpiritTypeProperty;
import com.sammy.malum.core.systems.spirit.type.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.type.SpiritColorProperties;
import com.sammy.malum.registry.common.block.MalumBlockProperties;
import com.sammy.malum.registry.common.block.MalumBlocks;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.wildcard.buddycards.Buddycards;
import com.wildcard.buddycards.block.BuddycardBoosterBoxBlock;
import com.wildcard.buddycards.block.CardDisplayBlock;
import com.wildcard.buddycards.block.CardStandBlock;
import com.wildcard.buddycards.core.BuddycardSet;
import com.wildcard.buddycards.effect.AttributeMobEffect;
import com.wildcard.buddycards.item.*;
import com.wildcard.buddycards.registries.BuddycardsAttributes;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsComponents;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.malumbuddycards.geas.BulkBoxGeas;
import com.wildcard.malumbuddycards.geas.PerfectionistGeas;
import com.wildcard.malumbuddycards.geas.RainbowGeas;
import com.wildcard.malumbuddycards.geas.SoulPackingGeas;
import com.wildcard.malumbuddycards.item.*;
import com.wildcard.malumbuddycards.spiritrite.effect.BulkBoonRiteEffect;
import com.wildcard.malumbuddycards.spiritrite.effect.GraderSpeedRiteEffect;
import com.wildcard.malumbuddycards.spiritrite.effect.LuckyRipRiteEffect;
import com.wildcard.malumbuddycards.spiritrite.effect.YouthRiteEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.CuriosApi;

import java.awt.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RegistryHandler {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MalumBuddycards.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MalumBuddycards.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MalumBuddycards.MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MalumBuddycards.MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MalumBuddycards.MOD_ID);
    public static final DeferredSpiritTypes SPIRITS = DeferredSpiritTypes.create(MalumBuddycards.MOD_ID);
    public static final DeferredRiteEntityEffectTypes RITE_EFFECTS = DeferredRiteEntityEffectTypes.create(MalumBuddycards.MOD_ID);
    public static final DeferredRiteTypes RITES = DeferredRiteTypes.create(MalumBuddycards.MOD_ID);
    public static final DeferredGeasTypes GEAS = DeferredGeasTypes.create(MalumBuddycards.MOD_ID);

    public static void registerAll(IEventBus eventBus) {
        CHILDISH_SPOIL = ATTRIBUTES.register("childish_spoil", () -> new RangedAttribute("attribute.name.malumbuddycards.childish_spoil", 0, 0, 2048));
        NULL_LUCK = ATTRIBUTES.register("null_luck", () -> new RangedAttribute("attribute.name.malumbuddycards.null_luck", 0.25f, 0, 1));

        SPIRIT = SPIRITS.register("childish", () -> new SpiritArcanaType(SpiritColorProperties.create(new Color(152, 232, 255), new Color(255, 119, 228)).build(), SPIRIT_ITEM));

        BOOSTER_BOX = BLOCKS.register("buddycard_booster_box_malum", () -> new BuddycardBoosterBoxBlock(BuddycardsItems.DEFAULT_BUDDYCARD_REQUIREMENT, BuddycardsBlocks.BOOSTER_BOX_PROPERTIES));

        RUNEWOOD_CARD_DISPLAY = registerDisplay("runewood_card_display", () -> new CardDisplayBlock(MalumBlockProperties.RUNEWOOD()));
        SOULWOOD_CARD_DISPLAY = registerDisplay("soulwood_card_display", () -> new CardDisplayBlock(MalumBlockProperties.SOULWOOD()));
        TAINTED_ROCK_CARD_STAND = registerStand("tainted_rock_card_stand", () -> new CardStandBlock(MalumBlockProperties.TAINTED_ROCK()));
        TWISTED_ROCK_CARD_STAND = registerStand("twisted_rock_card_stand", () -> new CardStandBlock(MalumBlockProperties.TWISTED_ROCK()));

        TOTEM = BLOCKS.register("childish_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion(), MalumBlocks.RUNEWOOD_LOG, false));
        CORRUPTED_TOTEM = BLOCKS.register("corrupted_childish_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion(), MalumBlocks.SOULWOOD_LOG, true));
        TOTEMS = BLOCKS_ENTITIES.register("childish_totem_pole", () -> BlockEntityType.Builder.of((a, b) -> new TotemPoleBlockEntity(TOTEMS.get(), a, b), TOTEM.get(), CORRUPTED_TOTEM.get()).build(null));

        SPIRITED_GLASS = BLOCKS.register("childish_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
        VARNISHED_TERRACOTTA = BLOCKS.register("childish_varnished_terracotta", () -> new VarnishedTerracottaBlock(MalumBlockProperties.VARNISHED_TERRACOTTA(DyeColor.LIGHT_BLUE)));

        PACK = ITEMS.register("buddycard_pack_malum", MalumBuddycardPackItem::new);
        BINDER = ITEMS.register("buddycard_binder_malum", () -> new BuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, MalumBuddycards.malumBuddycardsLocation("textures/gui/buddycard_binder_malum.png"), false));
        LARGE_BINDER = ITEMS.register("large_buddycard_binder_malum", () -> new BuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, MalumBuddycards.malumBuddycardsLocation("textures/gui/large_buddycard_binder_malum.png"), true));
        NULL_PACK = ITEMS.register("buddycard_pack_null_malum", MalumNullBuddycardPackItem::new);
        NULL_BINDER = ITEMS.register("buddycard_binder_null_malum", () -> new MalumNullBuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, MalumBuddycards.malumBuddycardsLocation("textures/gui/buddycard_binder_null_malum.png"), false));
        NULL_LARGE_BINDER = ITEMS.register("large_buddycard_binder_null_malum", () -> new MalumNullBuddycardBinderItem(BuddycardsItems.DEFAULT_BINDER_PROPERTIES, MALUM_SET, MalumBuddycards.malumBuddycardsLocation("textures/gui/large_buddycard_binder_null_malum.png"), true));
        MEDAL = ITEMS.register("buddysteel_medal_malum", () -> new BuddysteelSetMedalItem(MedalTypes.MALUM_SET, MALUM_SET, new Item.Properties().stacksTo(1).component(BuddycardsComponents.COLLECTION_TIER, 0)));

        BOOSTER_BOX_ITEM = ITEMS.register("buddycard_booster_box_malum", () -> new BuddycardBoosterBoxItem(BOOSTER_BOX.get(), PACK, BuddycardsItems.DEFAULT_UNCOMMON_PROPERTIES));

        registerCards(1, 12, Rarity.COMMON, MALUM_REQUIREMENT);
        registerCards(13, 9, Rarity.UNCOMMON, MALUM_REQUIREMENT);
        registerCards(22, 4, Rarity.RARE, MALUM_REQUIREMENT);
        registerCards(26, 2, Rarity.EPIC, MALUM_REQUIREMENT);
        registerNullCards(28, 5, Rarity.UNCOMMON, MALUM_REQUIREMENT);
        registerNullCards(33, 2, Rarity.EPIC, MALUM_REQUIREMENT);

        ITEMS.register("runewood_card_display", () -> new BlockItem(RUNEWOOD_CARD_DISPLAY.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("soulwood_card_display", () -> new BlockItem(SOULWOOD_CARD_DISPLAY.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("tainted_rock_card_stand", () -> new BlockItem(TAINTED_ROCK_CARD_STAND.get(), BuddycardsItems.DEFAULT_PROPERTIES));
        ITEMS.register("twisted_rock_card_stand", () -> new BlockItem(TWISTED_ROCK_CARD_STAND.get(), BuddycardsItems.DEFAULT_PROPERTIES));

        SPIRIT_ITEM = ITEMS.register("childish_spirit", () -> new SpiritShardItem(new Item.Properties(), SPIRIT));
        SPIRITED_GLASS_ITEM = ITEMS.register("childish_spirited_glass", () -> new BlockItem(SPIRITED_GLASS.get(), new Item.Properties()));
        VARNISHED_TERRACOTTA_ITEM = ITEMS.register("childish_varnished_terracotta", () -> new BlockItem(VARNISHED_TERRACOTTA.get(), new Item.Properties()));
        SoulwovenBannerPatternDataComponent.register(MalumBuddycards.malumBuddycardsLocation("collection"));

        CHILDISH_RING = ITEMS.register("childish_ring", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1),
                (m) -> m.put(CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "childish_ring"), 0.25, AttributeModifier.Operation.ADD_VALUE))));
        YOUTHFUL_RING = ITEMS.register("ring_of_youthful_spoils", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1),
                (m) -> m.put(CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "ring_of_youthful_spoils"), 1, AttributeModifier.Operation.ADD_VALUE))));
        MANIC_RING = ITEMS.register("ring_of_manic_laughter", () -> new VoidAttributeCurioItem(new Item.Properties().stacksTo(1),
                (m) -> {
                    m.put(CHILDISH_SPOIL, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "ring_of_manic_laughter"), 2, AttributeModifier.Operation.ADD_VALUE));
                    m.put(NULL_LUCK, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "ring_of_manic_laughter"), 0.5, AttributeModifier.Operation.ADD_VALUE));
                    m.put(BuddycardsAttributes.FOIL_LUCK, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "ring_of_manic_laughter"), 0.5, AttributeModifier.Operation.ADD_VALUE));
                }));
        CHILDISH_BROOCH = ITEMS.register("childish_brooch", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1), (m) -> {
            CuriosApi.addSlotModifier(m, "medal", MalumBuddycards.malumBuddycardsLocation("childish_brooch"), 2, AttributeModifier.Operation.ADD_VALUE);
            CuriosApi.addSlotModifier(m, "belt", MalumBuddycards.malumBuddycardsLocation("childish_brooch"), -1, AttributeModifier.Operation.ADD_VALUE);
        }));
        TRIPLICATE_BROOCH = ITEMS.register("triplicate_brooch", () -> new AttributeCurioItem(new Item.Properties().stacksTo(1), (m) -> {
            CuriosApi.addSlotModifier(m, "medal", MalumBuddycards.malumBuddycardsLocation("triplicate_brooch"), 2, AttributeModifier.Operation.ADD_VALUE);
            m.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(MalumBuddycards.malumBuddycardsLocation("triplicate_brooch"), -0.04, AttributeModifier.Operation.ADD_VALUE));
        }));

        RUNE_OF_WHIMSY = ITEMS.register("rune_of_whimsy", WhimsyRuneItem::new);
        RUNE_OF_LUCKY_RIP = ITEMS.register("rune_of_lucky_rip", () -> new TotemicRuneCurioItem(new Item.Properties().stacksTo(1), APPLY_LUCKY_RIP_EFFECT, SPIRIT));
        RUNE_OF_BULK_BOON = ITEMS.register("rune_of_bulk_boon", () -> new TotemicRuneCurioItem(new Item.Properties().stacksTo(1), APPLY_BULK_BOON_EFFECT, SPIRIT));
        RUNE_OF_NOSTALGIA = ITEMS.register("rune_of_nostalgia", NostalgiaRuneItem::new);

        ITEMS.register("odd_puzzlebox", () -> new Item(new Item.Properties()));
        RANCID_SLEEVE = ITEMS.register("rancid_sleeve", () -> new RancidSleeveItem(new Item.Properties(), new float[]{0.4f, 0.3f, 0.225f, 0.073f}));

        LUCKY_RIP = EFFECTS.register("lucky_rip", () -> new AttributeMobEffect(MobEffectCategory.BENEFICIAL, 10021119)
                .addAttributeModifier(BuddycardsAttributes.BUDDY_LUCK, MalumBuddycards.malumBuddycardsLocation("lucky_rip"), 1, AttributeModifier.Operation.ADD_VALUE)
                .addAttributeModifier(BuddycardsAttributes.BUDDY_BONUS, MalumBuddycards.malumBuddycardsLocation("lucky_rip"), -0.5, AttributeModifier.Operation.ADD_VALUE));
        BULK_BOON = EFFECTS.register("bulk_boon", () -> new AttributeMobEffect(MobEffectCategory.BENEFICIAL, 10021119)
                .addAttributeModifier(BuddycardsAttributes.BUDDY_BONUS, MalumBuddycards.malumBuddycardsLocation("bulk_boon"), 1, AttributeModifier.Operation.ADD_VALUE)
                .addAttributeModifier(BuddycardsAttributes.BUDDY_LUCK, MalumBuddycards.malumBuddycardsLocation("bulk_boon"), -0.5, AttributeModifier.Operation.ADD_VALUE));
        YOUTHFUL_VIGOR = EFFECTS.register("youthful_vigor", () -> new AttributeMobEffect(MobEffectCategory.BENEFICIAL, 10021119)
                .addAttributeModifier(Attributes.SCALE, MalumBuddycards.malumBuddycardsLocation("youthful_vigor"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, MalumBuddycards.malumBuddycardsLocation("youthful_vigor"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        APPLY_LUCKY_RIP_EFFECT = RITE_EFFECTS.register("apply_lucky_rip", LuckyRipRiteEffect::new);
        APPLY_BULK_BOON_EFFECT = RITE_EFFECTS.register("apply_bulk_boon", BulkBoonRiteEffect::new);
        GRADER_SPEED_EFFECT = RITE_EFFECTS.register("grader_speed", GraderSpeedRiteEffect::new);
        YOUTH_EFFECT = RITE_EFFECTS.register("youth", YouthRiteEffect::new);

        RITE_OF_THE_LUCKY_RIP = RITES.register("rite_of_the_lucky_rip", () -> SpiritRiteTypeBuilder.minorTotemRite(SPIRIT).build(APPLY_LUCKY_RIP_EFFECT));
        RITE_OF_THE_BULK_BOON = RITES.register("rite_of_the_bulk_boon", () -> SpiritRiteTypeBuilder.minorTotemRite(SPIRIT).setCorrupted().build(APPLY_BULK_BOON_EFFECT));
        RITE_OF_GRADE = RITES.register("rite_of_grade", () -> SpiritRiteTypeBuilder.majorTotemRite(SPIRIT).build(GRADER_SPEED_EFFECT));
        RITE_OF_YOUTH = RITES.register("rite_of_youth", () -> SpiritRiteTypeBuilder.majorTotemRite(SPIRIT).setCorrupted().build(YOUTH_EFFECT));

        PACT_OF_SOUL_PACKING = GEAS.register("pact_of_soul_packing", () -> new GeasEffectType(SoulPackingGeas::new, SPIRIT));
        PACT_OF_THE_BULK_BOX = GEAS.register("pact_of_the_bulk_box", () -> new GeasEffectType(BulkBoxGeas::new, SPIRIT));
        PACT_OF_THE_RAINBOW = GEAS.register("pact_of_the_rainbow", () -> new GeasEffectType(RainbowGeas::new, SPIRIT, MalumSpiritTypes.INFERNAL_SPIRIT));
        PACT_OF_THE_PERFECTIONIST = GEAS.register("pact_of_the_perfectionist", () -> new GeasEffectType(PerfectionistGeas::new, SPIRIT, MalumSpiritTypes.ELDRITCH_SPIRIT));

        ATTRIBUTES.register(eventBus);
        SPIRITS.register(eventBus);
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        EFFECTS.register(eventBus);
        RITE_EFFECTS.register(eventBus);
        RITES.register(eventBus);
        GEAS.register(eventBus);
    }

    public static final BuddycardSet MALUM_SET = new BuddycardSet("malum");

    public static final BuddycardsItems.BuddycardRequirement MALUM_REQUIREMENT = () -> ModList.get().isLoaded("malum");

    public static DeferredHolder<Attribute, Attribute> CHILDISH_SPOIL;
    public static DeferredHolder<Attribute, Attribute> NULL_LUCK;

    public static SpiritHolder SPIRIT;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemPoleBlockEntity>> TOTEMS;

    public static DeferredBlock<BuddycardBoosterBoxBlock> BOOSTER_BOX;
    public static DeferredBlock<CardDisplayBlock> RUNEWOOD_CARD_DISPLAY;
    public static DeferredBlock<CardDisplayBlock> SOULWOOD_CARD_DISPLAY;
    public static DeferredBlock<CardStandBlock> TAINTED_ROCK_CARD_STAND;
    public static DeferredBlock<CardStandBlock> TWISTED_ROCK_CARD_STAND;
    public static DeferredBlock<TotemPoleBlock> TOTEM;
    public static DeferredBlock<TotemPoleBlock> CORRUPTED_TOTEM;
    public static DeferredBlock<SpiritedGlassBlock> SPIRITED_GLASS;
    public static DeferredBlock<VarnishedTerracottaBlock> VARNISHED_TERRACOTTA;

    public static DeferredItem<BuddycardPackItem> PACK;
    public static DeferredItem<BuddycardBinderItem> BINDER;
    public static DeferredItem<BuddycardBinderItem> LARGE_BINDER;
    public static DeferredItem<BuddycardPackItem> NULL_PACK;
    public static DeferredItem<BuddycardBinderItem> NULL_BINDER;
    public static DeferredItem<BuddycardBinderItem> NULL_LARGE_BINDER;
    public static DeferredItem<BuddysteelSetMedalItem> MEDAL;
    public static DeferredItem<BuddycardBoosterBoxItem> BOOSTER_BOX_ITEM;
    public static DeferredItem<SpiritShardItem> SPIRIT_ITEM;
    public static DeferredItem<BlockItem> SPIRITED_GLASS_ITEM;
    public static DeferredItem<BlockItem> VARNISHED_TERRACOTTA_ITEM;
    public static DeferredItem<Item> CHILDISH_RING;
    public static DeferredItem<Item> YOUTHFUL_RING;
    public static DeferredItem<Item> MANIC_RING;
    public static DeferredItem<Item> CHILDISH_BROOCH;
    public static DeferredItem<Item> TRIPLICATE_BROOCH;
    public static DeferredItem<Item> RANCID_SLEEVE;
    public static DeferredItem<Item> RUNE_OF_WHIMSY;
    public static DeferredItem<Item> RUNE_OF_LUCKY_RIP;
    public static DeferredItem<Item> RUNE_OF_BULK_BOON;
    public static DeferredItem<Item> RUNE_OF_NOSTALGIA;

    public static DeferredHolder<MobEffect, MobEffect> LUCKY_RIP;
    public static DeferredHolder<MobEffect, MobEffect> BULK_BOON;
    public static DeferredHolder<MobEffect, MobEffect> YOUTHFUL_VIGOR;

    public static RiteEffectHolder<SpiritRitePotionEffect<?>> APPLY_LUCKY_RIP_EFFECT;
    public static RiteEffectHolder<SpiritRitePotionEffect<?>> APPLY_BULK_BOON_EFFECT;
    public static RiteEffectHolder<SpiritRiteEffect> GRADER_SPEED_EFFECT;
    public static RiteEffectHolder<SpiritRiteEffect> YOUTH_EFFECT;

    public static RiteHolder<SpiritRiteType> RITE_OF_THE_LUCKY_RIP;
    public static RiteHolder<SpiritRiteType> RITE_OF_THE_BULK_BOON;
    public static RiteHolder<SpiritRiteType> RITE_OF_GRADE;
    public static RiteHolder<SpiritRiteType> RITE_OF_YOUTH;

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

    public static void registerNullCards(int startValue, int amount, Rarity rarity, BuddycardsItems.BuddycardRequirement requirement) {
        for (int i = startValue; i < amount + startValue; i++) {
            int finalI = i;
            ITEMS.register("buddycard_malum" + i, () -> new MalumNullBuddycardItem(requirement, finalI, rarity));
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
