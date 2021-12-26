package com.deflatedpickle.entityfirespread.mixins;

import com.deflatedpickle.entityfirespread.EntityFireSpread;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnusedMixin")
@Mixin(Entity.class)
abstract public class MixinFireSpread {
    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    public void onTick(CallbackInfo info) {
        var cast = (Entity) (Object) this;
        EntityFireSpread.INSTANCE.onTick(
                cast.world,
                cast.isOnFire(),
                cast.getBlockPos()
        );
    }
}
