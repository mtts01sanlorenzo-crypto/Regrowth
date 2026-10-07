package com.mactso.regrowth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mactso.regrowth.events.MoveEntityEvent;

import net.minecraft.world.entity.LivingEntity;

// Fabric replacement for Forge's LivingTickEvent: runs at the start of every LivingEntity tick.
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "tick", at = @At("HEAD"))
	private void regrowth$onTick(CallbackInfo ci) {
		MoveEntityEvent.handleEntityMoveEvents((LivingEntity) (Object) this);
	}
}
