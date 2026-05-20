package com.rozo_garzon.moodflix.ui.home

import com.rozo_garzon.moodflix.domain.model.AppException
import com.rozo_garzon.moodflix.domain.model.MoodType
import com.rozo_garzon.moodflix.fakes.FakeMovieRepository
import com.rozo_garzon.moodflix.fakes.FakeTvRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var movieRepo: FakeMovieRepository
    private lateinit var tvRepo: FakeTvRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        movieRepo = FakeMovieRepository()
        tvRepo = FakeTvRepository()
    }

    @Test
    fun `uiState emits Success when repositories return data successfully`() = runTest {
        // Given
        movieRepo.shouldReturnError = false
        tvRepo.shouldReturnError = false

        // When
        viewModel = HomeViewModel(movieRepo, tvRepo)

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is HomeUiState.Success)
        val successState = state as HomeUiState.Success
        assertEquals(2, successState.movies.size)
        assertEquals(2, successState.tvShows.size)
        assertEquals("Movie 1", successState.movies[0].title)
        assertEquals("TV Show 1", successState.tvShows[0].name)
    }

    @Test
    fun `uiState emits Error when movie repository returns error`() = runTest {
        // Given
        movieRepo.shouldReturnError = true
        movieRepo.errorException = AppException.Unknown("Error al cargar peliculas")
        tvRepo.shouldReturnError = false

        // When
        viewModel = HomeViewModel(movieRepo, tvRepo)

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is HomeUiState.Error)
        val errorState = state as HomeUiState.Error
        assertEquals("Error al cargar peliculas", errorState.message)
    }

    @Test
    fun `selectMood changes current mood and loads new content`() = runTest {
        // Given
        viewModel = HomeViewModel(movieRepo, tvRepo)
        assertEquals(MoodType.HAPPY, viewModel.currentMood.value)

        // When
        viewModel.selectMood(MoodType.SAD)

        // Then
        assertEquals(MoodType.SAD, viewModel.currentMood.value)
        val state = viewModel.uiState.value
        assertTrue(state is HomeUiState.Success)
    }

    @Test
    fun `retry loads content again`() = runTest {
        // Given
        movieRepo.shouldReturnError = true
        viewModel = HomeViewModel(movieRepo, tvRepo)
        assertTrue(viewModel.uiState.value is HomeUiState.Error)

        // When
        movieRepo.shouldReturnError = false
        viewModel.retry()

        // Then
        assertTrue(viewModel.uiState.value is HomeUiState.Success)
    }
}
