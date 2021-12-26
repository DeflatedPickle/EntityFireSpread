/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.entityfirespread

import net.fabricmc.api.ModInitializer
import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameRules
import net.minecraft.world.World
import kotlin.random.Random

@Suppress("UNUSED")
object EntityFireSpread : ModInitializer {
    private const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    override fun onInitialize() {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))
    }

    fun onTick(world: World, isOnFire: Boolean, blockPos: BlockPos) {
        if (!world.isClient) {
            if (isOnFire && world.gameRules.getBoolean(GameRules.DO_FIRE_TICK)) {
                if (Random.nextInt(
                        if (world.hasHighHumidity(blockPos)) 25
                        else 10
                    ) == 0
                ) {
                    if (AbstractFireBlock.canPlaceAt(world, blockPos, null)) {
                        val fire = AbstractFireBlock.getState(world, blockPos)
                        world.setBlockState(blockPos, fire, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
                    }
                }
            }
        }
    }
}
