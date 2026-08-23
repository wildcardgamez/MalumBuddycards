package com.wildcard.malumbuddycards.spiritrite.effect;

import com.sammy.malum.common.entity.activator.BlockRiteEffectActivatorEntity;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteBlockEffect;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffectTag;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.wildcard.buddycards.block.entity.GraderBlockEntity;
import com.wildcard.malumbuddycards.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GraderSpeedRiteEffect extends SpiritRiteBlockEffect {
    public GraderSpeedRiteEffect() {
        super(SpiritRiteEffectTag.GREATER_RITE);
    }

    public void applyEffect(ServerLevel level, BlockRiteEffectActivatorEntity entity, BlockState state, BlockPos pos, float impact) {
        BlockEntity var7 = level.getBlockEntity(pos);
        if (var7 instanceof GraderBlockEntity grader) {
            this.createEffect(level, pos, RegistryHandler.SPIRIT, MalumSpiritTypes.ELDRITCH_SPIRIT);
            GraderBlockEntity.tick(level, pos, state, grader);
        }

    }
}