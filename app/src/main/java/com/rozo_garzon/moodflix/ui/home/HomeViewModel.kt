package com.rozo_garzon.moodflix.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rozo_garzon.moodflix.domain.model.*
import com.rozo_garzon.moodflix.domain.repository.MovieRepository
import com.rozo_garzon.moodflix.domain.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val movies: List<Movie>,
        val tvShows: List<TvShow>,
        val featured: Movie?,
        val isFromCache: Boolean = false
    ) : HomeUiState()
    data class Error(val message: String, val isOffline: Boolean = false) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val tvRepo: TvRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _currentMood = MutableStateFlow(MoodType.HAPPY)
    val currentMood: StateFlow<MoodType> = _currentMood.asStateFlow()

    private var loadJob: Job? = null

    init { loadContent(MoodType.HAPPY) }

    fun selectMood(mood: MoodType) {
        if (_currentMood.value == mood && _uiState.value is HomeUiState.Success) return
        _currentMood.value = mood
        loadContent(mood)
    }

    private fun loadContent(mood: MoodType) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            combine(
                movieRepo.getMoviesByMood(mood),
                tvRepo.getTvByMood(mood)
            ) { moviesResult, tvResult ->
                when {
                    moviesResult is Result.Success && tvResult is Result.Success -> {
                        HomeUiState.Success(
                            movies = moviesResult.data,
                            tvShows = tvResult.data,
                            featured = moviesResult.data.shuffled().firstOrNull() ?: moviesResult.data.firstOrNull()
                        )
                    }
                    moviesResult is Result.Error -> HomeUiState.Error(moviesResult.exception.message ?: "Error de conexión")
                    tvResult is Result.Error -> HomeUiState.Error(tvResult.exception.message ?: "Error de conexión")
                    else -> HomeUiState.Loading
                }
            }.collect { state -> _uiState.value = state }
        }
    }

    fun retry() = loadContent(_currentMood.value)
}
