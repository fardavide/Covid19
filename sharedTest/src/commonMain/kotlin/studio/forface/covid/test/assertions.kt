package studio.forface.covid.test

import kotlin.test.assertTrue

/** Assert that [actual] [Int] is between [expected] [IntRange] */
fun assertBetween(expected: IntRange, actual: Int, message: String? = null) = assertTrue(actual in expected, message)
