package com.wildcard.malumbuddycards.spiritrite.effect;

import com.sammy.malum.core.systems.rite.effect.SpiritRitePotionEffect;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.world.entity.LivingEntity;

public class LuckyRipRiteEffect extends SpiritRitePotionEffect<LivingEntity> {
    public LuckyRipRiteEffect() {
        super(RegistryHandler.LUCKY_RIP, RegistryHandler.SPIRIT);
    }

    public Class<LivingEntity> getTargetClass() {
        return LivingEntity.class;
    }
}
