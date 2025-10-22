package org.example.configs

import org.example.processes.BackMeUpProcess.ProcessType
import org.example.processes.BackMeUpProcessFactoryTest.Companion.VALID_EMPTY_DIR
import org.example.processes.BackMeUpProcessFactoryTest.Companion.VALID_NONEMPTY_DIR
import org.junit.jupiter.api.assertNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProcessConfigFactoryTest {

    @Test
    fun test_createConfig_initNonEmptyProperties_createsCorrectConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.INIT)
        sut.saveValuesList = listOf(VALID_NONEMPTY_DIR)
        sut.storageValuesList = listOf(VALID_EMPTY_DIR)

        // act
        val result = sut.createConfig()

        // assert
        assertNotNull(result)
        assertEquals(InitProcessConfig::class, result::class)
    }

    @Test
    fun test_createConfig_initNonEmptyProperties_createsCorrectConfigWithProperties() {
        // arrange
        val expectedSaveDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedStorageDirs = listOf(VALID_EMPTY_DIR)
        val sut = ProcessConfigFactory(ProcessType.INIT)

        // act
        sut.saveValuesList = expectedSaveDirs
        sut.storageValuesList = expectedStorageDirs
        val result = sut.createConfig() as InitProcessConfig

        // assert
        assertEquals(expectedSaveDirs, result.getListOfDirectoriesToBackUp())
        assertEquals(expectedStorageDirs, result.getListOfDirectoriesToStoreBackUps())
    }

    @Test
    fun test_createConfig_initEmptyProperties_createsNoConfig() {
        // arrange
        val expectedSaveDirs = emptyList<String>()
        val expectedStorageDirs = emptyList<String>()
        val sut = ProcessConfigFactory(ProcessType.INIT)

        // act
        sut.saveValuesList = expectedSaveDirs
        sut.storageValuesList = expectedStorageDirs
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createConfig_initWithoutProperties_createsNoConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.INIT)

        // act
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createConfig_diffNonEmptyProperties_createsCorrectConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.DIFF)
        sut.srcValuesList = listOf(VALID_NONEMPTY_DIR)
        sut.storageValuesList = listOf(VALID_EMPTY_DIR)

        // act
        val result = sut.createConfig()

        // assert
        assertNotNull(result)
        assertEquals(DiffProcessConfig::class, result::class)
    }

    @Test
    fun test_createConfig_diffNonEmptyProperties_createsCorrectConfigWithProperties() {
        // arrange
        val expectedSrcDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedStorageDirs = listOf(VALID_EMPTY_DIR)
        val sut = ProcessConfigFactory(ProcessType.DIFF)

        // act
        sut.srcValuesList = expectedSrcDirs
        sut.storageValuesList = expectedStorageDirs
        val result = sut.createConfig() as DiffProcessConfig

        // assert
        assertEquals(expectedSrcDirs, result.getListOfDirectoriesToDiffFrom())
        assertEquals(expectedStorageDirs, result.getListOfDirectoriesToStoreBackUps())
    }

    @Test
    fun test_createConfig_diffEmptyProperties_createsNoConfig() {
        // arrange
        val expectedSrcDirs = emptyList<String>()
        val expectedStorageDirs = emptyList<String>()
        val sut = ProcessConfigFactory(ProcessType.DIFF)

        // act
        sut.srcValuesList = expectedSrcDirs
        sut.storageValuesList = expectedStorageDirs
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createConfig_diffWithoutProperties_createsNoConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.DIFF)

        // act
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createConfig_restoreNonEmptyProperties_createsCorrectConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.RESTORE)
        sut.fromValuesList = listOf(VALID_NONEMPTY_DIR)
        sut.toValuesList = listOf(VALID_EMPTY_DIR)

        // act
        val result = sut.createConfig()

        // assert
        assertNotNull(result)
        assertEquals(RestoreProcessConfig::class, result::class)
    }

    @Test
    fun test_createConfig_restoreNonEmptyProperties_createsCorrectConfigWithProperties() {
        // arrange
        val expectedFromDirs = listOf(VALID_NONEMPTY_DIR)
        val expectedToDirs = listOf(VALID_EMPTY_DIR)
        val sut = ProcessConfigFactory(ProcessType.RESTORE)

        // act
        sut.fromValuesList = expectedFromDirs
        sut.toValuesList = expectedToDirs
        val result = sut.createConfig() as RestoreProcessConfig

        // assert
        assertEquals(expectedFromDirs, result.getListOfDirectoriesToDiffFrom())
        assertEquals(expectedToDirs, result.getListOfDirectoriesToStoreBackUps())
    }

    @Test
    fun test_createConfig_restoreEmptyProperties_createsNoConfig() {
        // arrange
        val expectedFromDirs = emptyList<String>()
        val expectedToDirs = emptyList<String>()
        val sut = ProcessConfigFactory(ProcessType.RESTORE)

        // act
        sut.fromValuesList = expectedFromDirs
        sut.toValuesList = expectedToDirs
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }

    @Test
    fun test_createConfig_restoreWithoutProperties_createsNoConfig() {
        // arrange
        val sut = ProcessConfigFactory(ProcessType.RESTORE)

        // act
        val result = sut.createConfig()

        // assert
        assertNull(result)
    }
}