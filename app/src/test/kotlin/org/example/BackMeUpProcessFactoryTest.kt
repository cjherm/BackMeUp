package org.example

import org.junit.jupiter.api.assertNull
import kotlin.test.Test

class BackMeUpProcessFactoryTest {

    @Test
    fun test_createProcess_noTopArg_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(ARGS_WITHOUT_TOP_ARG)

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg0_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(ARGS_WITH_TWO_TOP_ARGS_0)

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg1_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(ARGS_WITH_TWO_TOP_ARGS_1)

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg2_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(ARGS_WITH_TWO_TOP_ARGS_2)

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoThreeTopArg_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(ARGS_WITH_ALL_TOP_ARG)

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    companion object {
        const val INIT_ARG = "-init"
        const val DIFF_ARG = "-diff"
        const val RESTORE_ARG = "-restore"

        val ARGS_WITHOUT_TOP_ARG = arrayOf("abc", "def")
        val ARGS_WITH_TWO_TOP_ARGS_0 = arrayOf(INIT_ARG, DIFF_ARG)
        val ARGS_WITH_TWO_TOP_ARGS_1 = arrayOf(INIT_ARG, RESTORE_ARG)
        val ARGS_WITH_TWO_TOP_ARGS_2 = arrayOf(DIFF_ARG, RESTORE_ARG)
        val ARGS_WITH_ALL_TOP_ARG = arrayOf(INIT_ARG, DIFF_ARG, RESTORE_ARG)
    }
}