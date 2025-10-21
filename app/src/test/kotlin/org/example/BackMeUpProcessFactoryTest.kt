package org.example

import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import kotlin.io.path.absolutePathString
import kotlin.io.path.createTempFile
import kotlin.test.Test
import kotlin.test.assertEquals

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

    @Test
    fun test_createProcess_initOneSaveOneStorage_returnsInitProcess() {
        // arrange
        val argLine = arrayOf(INIT_ARG, SAVE_ARG, VALID_NONEMPTY_DIR, STORAGE_ARG, VALID_EMPTY_DIR)
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpInitializer::class, result::class)
    }

    @Test
    fun test_createProcess_initOneSaveTwoStorage_returnsInitProcess() {
        // arrange
        val argLine = arrayOf(
            INIT_ARG,
            SAVE_ARG,
            VALID_NONEMPTY_DIR,
            STORAGE_ARG,
            VALID_EMPTY_DIR,
            STORAGE_ARG,
            VALID_EMPTY_DIR
        )
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpInitializer::class, result::class)
    }

    @Test
    fun test_createProcess_initTwoSaveOneStorage_returnsInitProcess() {
        // arrange
        val argLine = arrayOf(
            INIT_ARG,
            SAVE_ARG,
            VALID_NONEMPTY_DIR,
            SAVE_ARG,
            VALID_NONEMPTY_DIR,
            STORAGE_ARG,
            VALID_EMPTY_DIR
        )
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpInitializer::class, result::class)
    }

    @Test
    fun test_createProcess_initTwoSaveTwoStorage_returnsInitProcess() {
        // arrange
        val argLine = arrayOf(
            INIT_ARG,
            SAVE_ARG,
            VALID_NONEMPTY_DIR,
            SAVE_ARG,
            VALID_NONEMPTY_DIR,
            STORAGE_ARG,
            VALID_EMPTY_DIR,
            STORAGE_ARG,
            VALID_EMPTY_DIR
        )
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpInitializer::class, result::class)
    }

    @Test
    fun test_createProcess_diffOneSrcNoneStorage_returnsDiffProcess() {
        // arrange
        val sut = BackMeUpProcessFactory(DIFF_ARGS_ONE_SRC_NONE_STORAGE)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpDifferentiator::class, result::class)
    }

    @Test
    fun test_createProcess_diffOneSrcOneStorage_returnsDiffProcess() {
        // arrange
        val sut = BackMeUpProcessFactory(DIFF_ARGS_ONE_SRC_ONE_STORAGE)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpDifferentiator::class, result::class)
    }

    @Test
    fun test_createProcess_restoreOneFromOneTo_returnsRestoreProcess() {
        // arrange
        val sut = BackMeUpProcessFactory(RESTORE_ARGS_ONE_FROM_ONE_TO)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpRestorer::class, result::class)
    }

    companion object {
        const val INIT_ARG = "-init"
        const val DIFF_ARG = "-diff"
        const val RESTORE_ARG = "-restore"

        const val SAVE_ARG = "-save"
        const val STORAGE_ARG = "-storage"
        const val FROM_ARG = "-from"
        const val TO_ARG = "-to"
        const val SRC_ARG = "-src"

        val ARGS_WITHOUT_TOP_ARG = arrayOf("abc", "def")
        val ARGS_WITH_TWO_TOP_ARGS_0 = arrayOf(INIT_ARG, DIFF_ARG)
        val ARGS_WITH_TWO_TOP_ARGS_1 = arrayOf(INIT_ARG, RESTORE_ARG)
        val ARGS_WITH_TWO_TOP_ARGS_2 = arrayOf(DIFF_ARG, RESTORE_ARG)
        val ARGS_WITH_ALL_TOP_ARG = arrayOf(INIT_ARG, DIFF_ARG, RESTORE_ARG)

        val VALID_EMPTY_DIR = createTempFile("testEmptyDir").absolutePathString()
        val VALID_NONEMPTY_DIR = createTempFile("testNonEmptyDir").absolutePathString()

        val DIFF_ARGS_ONE_SRC_NONE_STORAGE =
            arrayOf(DIFF_ARG, SRC_ARG, VALID_NONEMPTY_DIR)
        val DIFF_ARGS_ONE_SRC_ONE_STORAGE =
            arrayOf(DIFF_ARG, SRC_ARG, VALID_NONEMPTY_DIR, STORAGE_ARG, VALID_EMPTY_DIR)
        val RESTORE_ARGS_ONE_FROM_ONE_TO =
            arrayOf(RESTORE_ARG, FROM_ARG, VALID_NONEMPTY_DIR, TO_ARG, VALID_EMPTY_DIR)
    }
}