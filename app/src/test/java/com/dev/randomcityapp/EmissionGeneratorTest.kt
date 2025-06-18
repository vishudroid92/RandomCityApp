package com.dev.randomcityapp

import com.dev.randomcityapp.data.repository.EmissionRepository
import com.dev.randomcityapp.usecase.EmissionGenerator
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmissionGeneratorTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repo: EmissionRepository
    private lateinit var generator: EmissionGenerator
    private lateinit var scope: TestScope

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk(relaxed = true)
        scope = TestScope(testDispatcher)
        generator = EmissionGenerator(repo, scope)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        scope.cancel()
    }

    @Test
    fun `emission is generated only up to a limit`() = scope.runTest {
        generator.startGenerating()

        // Simulate two intervals: 5s + 5s
        advanceTimeBy(11000)

        generator.stopGenerating()

        // Should be called ~3 times (one every 5s)
        coVerify(exactly = 3) { repo.generateRandomEmission() }
    }

    @Test
    fun `emission stops after stopGenerating`() = scope.runTest {
        generator.startGenerating()
        advanceTimeBy(5000)
        generator.stopGenerating()

        // Even after advancing time, it shouldn't emit more
        advanceTimeBy(10000)

        coVerify(exactly = 1) { repo.generateRandomEmission() }
    }
}
