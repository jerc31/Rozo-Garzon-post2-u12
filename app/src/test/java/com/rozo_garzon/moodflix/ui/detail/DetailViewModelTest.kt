package com.rozo_garzon.moodflix.ui.detail

import com.rozo_garzon.moodflix.domain.model.AppException
import com.rozo_garzon.moodflix.domain.model.Movie
import com.rozo_garzon.moodflix.domain.model.Result
import com.rozo_garzon.moodflix.fakes.FakeMovieRepository
import com.rozo_garzon.moodflix.ui.home.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var movieRepo: FakeMovieRepository
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        movieRepo = FakeMovieRepository()
        viewModel = DetailViewModel(movieRepo)
    }

    @Test
    fun `uiState is initially Loading`() {
        assertTrue(viewModel.uiState.value is DetailUiState.Loading)
    }

    @Test
    fun `loadMovieDetail emits Success when repository returns data successfully`() = runTest {
        // Given
        movieRepo.shouldReturnError = false
        val expectedMovie = Movie(123, "Test Detail Movie", "Test Overview", null, null, 9.0, 150, 2024, listOf("Action"), 12.0)
        movieRepo.movieDetailResult = Result.Success(expectedMovie)

        // When
        viewModel.loadMovieDetail(123)

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is DetailUiState.Success)
        val successState = state as DetailUiState.Success
        assertEquals(123, successState.movie.id)
        assertEquals("Test Detail Movie", successState.movie.title)
    }

    @Test
    fun `loadMovieDetail emits Error when repository returns failure`() = runTest {
        // Given
        movieRepo.shouldReturnError = true
        movieRepo.errorException = AppException.NotFound

        // When
        viewModel.loadMovieDetail(123)

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is DetailUiState.Error)
        val errorState = state as DetailUiState.Error
        assertEquals("Recurso no encontrado", errorState.message)
    }
}
