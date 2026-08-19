package com.wildcard.malumbuddycards;

import com.wildcard.buddycards.Buddycards;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(value = MalumBuddycards.MOD_ID)
@EventBusSubscriber(modid = MalumBuddycards.MOD_ID)
public class Datagen {
    @SubscribeEvent
    static void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, new CardModelGen(event.getGenerator().getPackOutput(), "malumbuddycards", event.getExistingFileHelper()));
    }

    static private class CardModelGen extends ItemModelProvider {
        public CardModelGen(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
            super(output, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            for (int i = 1; i <= 27; i++) {
                genCardModel(i);
            }
            ItemModelBuilder medal = getBuilder(ModelProvider.ITEM_FOLDER + "/buddysteel_medal_malum")
                    .parent(factory.apply(ResourceLocation.withDefaultNamespace("item/generated")))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/malum" + "_set/" + "medal"));
            for (int i = 1; i < 5; i++) {
                ItemModelBuilder tierMedal = getBuilder(ModelProvider.ITEM_FOLDER + "/buddysteel_medal_malum" + i)
                        .parent(factory.apply(ResourceLocation.withDefaultNamespace("item/generated")))
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/malum" + "_set/" + "medal" + i));
                medal.override().predicate(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "tier"), i).model(tierMedal);
            }
        }

        void genCardModel(int cardNum) {
            ItemModelBuilder card = getBuilder(ModelProvider.ITEM_FOLDER + "/buddycard_malum" + cardNum)
                    .parent(factory.apply(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/buddycard")))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/malum_set/" + cardNum));
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j <= 3; j++)
                    if (j + i != 0)
                        card.override().predicate(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "grade"), i).predicate(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, "foil"), j).model(genFoiledGradedCardModel(cardNum, i, j));
            }
        }

        ModelFile genFoiledGradedCardModel(int cardNum, int grade, int foil) {
            ItemModelBuilder card = getBuilder(ModelProvider.ITEM_FOLDER + "/buddycard_malum" + cardNum + "_g" + grade + "_f" + foil)
                    .parent(factory.apply(ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/buddycard")))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(MalumBuddycards.MOD_ID, ModelProvider.ITEM_FOLDER + "/malum_set/" + cardNum));
            if (foil != 0)
                card.texture("layer1", ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID,ModelProvider.ITEM_FOLDER + "/foil" + foil));
            if (grade != 0)
                card.texture(foil == 0 ? "layer1" : "layer2", ResourceLocation.fromNamespaceAndPath(Buddycards.MOD_ID,ModelProvider.ITEM_FOLDER + "/grade" + grade));
            return card;
        }
    }
}
