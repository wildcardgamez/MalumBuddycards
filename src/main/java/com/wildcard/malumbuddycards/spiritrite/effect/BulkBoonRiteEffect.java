package com.wildcard.malumbuddycards.spiritrite.effect;

import com.sammy.malum.core.systems.rite.effect.SpiritRitePotionEffect;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.world.entity.LivingEntity;

public class BulkBoonRiteEffect extends SpiritRitePotionEffect<LivingEntity> {
    public BulkBoonRiteEffect() {
        super(RegistryHandler.BULK_BOON, RegistryHandler.SPIRIT);
    }

    public Class<LivingEntity> getTargetClass() {
        return LivingEntity.class;
    }
}
