package com.daniil.shevtsov.detective.feature.game.presentation

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.measureTime

class Point(
    var x: Int,
    var y: Int,
)

class CacheMissPoints(
    val points: Array<Point>,
)

class CacheFriendlyPoints(
    val xs: IntArray,
    val ys: IntArray,
)

class CacheMissTest {

    @Test
    @Disabled
    fun `should kek`() {
        val iterations = 10000000
        val repeat = 1000
        val cacheMissPoints = CacheMissPoints(Array(iterations) { Point(0, 0) })
        val cacheFriendlyPoints = CacheFriendlyPoints(IntArray(iterations), IntArray(iterations))

        var counter = 0
        var cacheMissSum = 0
        var cacheFriendlySum = 0

        val cachedMissInitTime: Duration = measureTime {
            repeat(repeat) {
                for (i in 0 until iterations) {
                    cacheMissPoints.points[i].x = i
                    cacheMissPoints.points[i].y = i * 2
                }
            }

        }
        val cachedFriendlyInitTime: Duration = measureTime {
            repeat(repeat) {
                for (i in 0 until iterations) {
                    cacheFriendlyPoints.xs[i] = i
                    cacheFriendlyPoints.ys[i] = i * 2
                }
            }
        }

        val cachedMissSumTime: Duration = measureTime {
            repeat(repeat) {
                cacheMissSum = 0
                for (i in 0 until iterations) {
                    cacheMissSum += cacheMissPoints.points[i].x + cacheMissPoints.points[i].y
                }
            }

        }

        val cachedFriendlySumTime: Duration = measureTime {
            repeat(repeat) {
                cacheFriendlySum = 0
                for (i in 0 until iterations) {
                    cacheFriendlySum += cacheFriendlyPoints.xs[i] + cacheFriendlyPoints.ys[i]
                }
            }
        }

        println("Cache Miss:\nInit: $cachedMissInitTime\nCalculation: $cachedMissSumTime\n")
        println("Cache Friendly:\nInit: $cachedFriendlyInitTime\nCalculation: $cachedFriendlySumTime")

        assertTrue(true)
    }

}
