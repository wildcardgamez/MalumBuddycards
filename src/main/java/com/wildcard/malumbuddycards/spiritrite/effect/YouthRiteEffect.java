package com.wildcard.malumbuddycards.spiritrite.effect;

import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffectTag;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEntityEffect;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;

public class YouthRiteEffect extends SpiritRiteEntityEffect<Mob> {
    public YouthRiteEffect() {
        super(SpiritRiteEffectTag.GREATER_RITE);
    }

    @Override
    public Class<Mob> getTargetClass() {
        return Mob.class;
    }

    @Override
    public void applyEffect(ServerLevel level, Mob mob) {
        if (!mob.isBaby() && !mob.hasEffect(RegistryHandler.YOUTHFUL_VIGOR) && level.getRandom().nextFloat() <= 0.02F) {
            mob.setBaby(true);
            if(!mob.isBaby())
                mob.addEffect(new MobEffectInstance(RegistryHandler.YOUTHFUL_VIGOR, 6000));
            this.createEffect(level, mob, MalumSpiritTypes.ELDRITCH_SPIRIT, RegistryHandler.SPIRIT);
        }
    }
}
