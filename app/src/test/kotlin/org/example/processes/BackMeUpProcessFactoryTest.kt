package org.example.processes

import org.example.configs.DiffProcessConfig
import org.example.configs.InitProcessConfig
import org.example.configs.RestoreProcessConfig
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import kotlin.test.Test
import kotlin.test.assertEquals

class BackMeUpProcessFactoryTest {

    @Test
    fun test_createProcess_noTopArg_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(arrayOf("abc", "def"))

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg0_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(arrayOf(INIT_ARG, DIFF_ARG))

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg1_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(arrayOf(INIT_ARG, RESTORE_ARG))

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_twoTopArg2_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(arrayOf(DIFF_ARG, RESTORE_ARG))

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_threeTopArg_returnsNull() {
        // arrange
        val sut = BackMeUpProcessFactory(arrayOf(INIT_ARG, DIFF_ARG, RESTORE_ARG))

        // act
        val result = sut.createProcess()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createProcess_initOneSaveOneStorage_returnsInitProcess() {
        // arrange
        val argLine = makeArray(INIT_ARG, VALID_SAVE, VALID_STORAGE)
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
        val argLine = makeArray(INIT_ARG, VALID_STORAGE, VALID_SAVE, VALID_STORAGE)
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
        val argLine = makeArray(INIT_ARG, VALID_SAVE, VALID_STORAGE, VALID_SAVE)
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
        val argLine = makeArray(INIT_ARG, VALID_STORAGE, VALID_SAVE, VALID_STORAGE, VALID_SAVE)
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
        val argLine = makeArray(DIFF_ARG, VALID_SRC)
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpDifferentiator::class, result::class)
    }

    @Test
    fun test_createProcess_diffOneSrcOneStorage_returnsDiffProcess() {
        // arrange
        val argLine = makeArray(DIFF_ARG, VALID_SRC, VALID_STORAGE)
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpDifferentiator::class, result::class)
    }

    @Test
    fun test_createProcess_restoreOneFromOneTo_returnsRestoreProcess() {
        // arrange
        val argLine = makeArray(RESTORE_ARG, VALID_FROM, VALID_TO)
        val sut = BackMeUpProcessFactory(argLine)

        // act
        val result = sut.createProcess()

        // assert
        assertNotNull(result)
        assertEquals(BackMeUpRestorer::class, result::class)
    }

    @Test
    fun test_createProcess_initDuplicateSaveDuplicateStorage_createsCorrectConfig() {
        // arrange
        val saveDirsArg = listOf(VALID_NONEMPTY_DIR, VALID_NONEMPTY_DIR)
        val storageDirsArg = listOf(VALID_EMPTY_DIR, VALID_EMPTY_DIR)
        val argsLine = makeArray(INIT_ARG, SAVE_ARG, saveDirsArg, STORAGE_ARG, storageDirsArg)

        val expectedSaveDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedStorageDirs = listOf(VALID_EMPTY_DIR)

        val sut = BackMeUpProcessFactory(argsLine)

        // act
        val result = sut.createProcess()!!.getConfig() as InitProcessConfig

        // assert
        assertEquals(expectedSaveDirs, result.getListOfDirectoriesToBackUp())
        assertEquals(expectedStorageDirs, result.getListOfDirectoriesToStoreBackUps())
    }

    @Test
    fun test_createProcess_diffDuplicateSrcDuplicateStorage_createsCorrectConfig() {
        // arrange
        val srcDirsArg = listOf(VALID_NONEMPTY_DIR, VALID_NONEMPTY_DIR)
        val storageDirsArg = listOf(VALID_EMPTY_DIR, VALID_EMPTY_DIR)
        val argsLine = makeArray(DIFF_ARG, SRC_ARG, srcDirsArg, STORAGE_ARG, storageDirsArg)

        val expectedSrcDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedStorageDirs = listOf(VALID_EMPTY_DIR)

        val sut = BackMeUpProcessFactory(argsLine)

        // act
        val result = sut.createProcess()!!.getConfig() as DiffProcessConfig

        // assert
        assertEquals(expectedSrcDirs, result.getListOfDirectoriesToDiffFrom())
        assertEquals(expectedStorageDirs, result.getListOfDirectoriesToStoreBackUps())
    }

    @Test
    fun test_createProcess_restoreDuplicateFromDuplicateTo_createsCorrectConfig() {
        // arrange
        val fromDirsArg = listOf(VALID_NONEMPTY_DIR, VALID_NONEMPTY_DIR)
        val toDirsArg = listOf(VALID_EMPTY_DIR, VALID_EMPTY_DIR)
        val argsLine = makeArray(RESTORE_ARG, FROM_ARG, fromDirsArg, TO_ARG, toDirsArg)

        val expectedFromDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedToDirs = listOf(VALID_EMPTY_DIR)

        val sut = BackMeUpProcessFactory(argsLine)

        // act
        val result = sut.createProcess()!!.getConfig() as RestoreProcessConfig

        // assert
        assertEquals(expectedFromDirs, result.getListOfDirectoriesToDiffFrom())
        assertEquals(expectedToDirs, result.getListOfDirectoriesToStoreBackUps())
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

        // TODO Replace with mechanism to auto create/remove files and directories for tests
        const val VALID_EMPTY_DIR = "src/test/resources/emptyDir"
        const val VALID_NONEMPTY_DIR = "src/test/resources/nonEmptyDir"

        val VALID_SAVE = arrayOf<String>(SAVE_ARG, VALID_NONEMPTY_DIR)
        val VALID_STORAGE = arrayOf<String>(STORAGE_ARG, VALID_EMPTY_DIR)
        val VALID_FROM = arrayOf<String>(FROM_ARG, VALID_NONEMPTY_DIR)
        val VALID_TO = arrayOf<String>(TO_ARG, VALID_EMPTY_DIR)
        val VALID_SRC = arrayOf<String>(SRC_ARG, VALID_NONEMPTY_DIR)

        private fun makeArray(vararg items: Any): Array<String> {
            val result = mutableListOf<String>()
            for (item in items) {
                when (item) {
                    is String -> result.add(item)
                    is Array<*> -> result.addAll(item.filterIsInstance<String>())
                    is List<*> -> result.addAll(item.filterIsInstance<String>())
                }
            }
            return result.toTypedArray()
        }
    }
}