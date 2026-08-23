package com.wildcard.malumbuddycards;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MalumBuddycards.MOD_ID)
public class MalumBuddycards
{
    public static final String MOD_ID = "malumbuddycards";

    public MalumBuddycards(IEventBus eventBus, ModContainer modContainer)
    {
        RegistryHandler.registerAll(eventBus);
    }

    public static ResourceLocation malumBuddycardsLocation(String string) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, string);
    }
}
