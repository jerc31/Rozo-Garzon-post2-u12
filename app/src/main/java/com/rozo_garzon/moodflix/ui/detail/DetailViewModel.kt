package com.rozo_garzon.moodflix.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rozo_garzon.moodflix.domain.model.Movie
import com.rozo_garzon.moodflix.domain.model.Result
import com.rozo_garzon.moodflix.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val movie: Movie) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            when (val result = repository.getMovieDetail(movieId)) {
                is Result.Success -> _uiState.value = DetailUiState.Success(result.data)
                is Result.Error -> _uiState.value = DetailUiState.Error(result.exception.message ?: "Error desconocido")
                else -> {}
            }
        }
    }
}
