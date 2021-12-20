/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("unused")

package com.deflatedpickle.thisisfine.mixins

import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameRules
import net.minecraft.world.World
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import kotlin.random.Random

@Mixin(Entity::class)
abstract class MixinFireSpread {
    @Shadow lateinit var blockPos: BlockPos
    @Shadow abstract fun isOnFire(): Boolean
    @Shadow abstract fun getWorld(): World

    @Inject(
        method = ["tick"],
        at = [At("RETURN")],
    )
    fun onTick(info: CallbackInfo) {
        val world = getWorld()

        if (Random.nextInt(10) == 0 && !world.isClient && world.gameRules.getBoolean(GameRules.DO_FIRE_TICK) && this.isOnFire()) {
            if (AbstractFireBlock.canPlaceAt(world, this.blockPos, null)) {
                val fire = AbstractFireBlock.getState(world, this.blockPos)
                world.setBlockState(this.blockPos, fire, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            }
        }
    }
}
