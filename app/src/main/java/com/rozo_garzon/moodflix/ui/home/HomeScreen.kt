package com.rozo_garzon.moodflix.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rozo_garzon.moodflix.domain.model.MoodType
import com.rozo_garzon.moodflix.domain.model.Movie
import com.rozo_garzon.moodflix.ui.theme.RedPrimary

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentMood by viewModel.currentMood.collectAsState()

    Scaffold(
        containerColor = Color(0xFF0D0D0D)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header con degradado según el Mood
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                val featuredMovie = (uiState as? HomeUiState.Success)?.featured
                AsyncImage(
                    model = featuredMovie?.backdropUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF0D0D0D))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "DESTACADO HOY",
                        color = RedPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = featuredMovie?.title ?: "Cargando...",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Selector de Moods Premium
            Text(
                text = "¿Cuál es tu mood?",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(MoodType.values()) { mood ->
                    MoodChip(
                        mood = mood,
                        isSelected = mood == currentMood,
                        onClick = { viewModel.selectMood(mood) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de Películas
            Text(
                text = "Recomendaciones para ti",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = RedPrimary)
                    }
                }
                is HomeUiState.Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.movies) { movie ->
                            MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                    }
                }
                is HomeUiState.Error -> {
                    Text(state.message, color = Color.Red, modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}

@Composable
fun MoodChip(mood: MoodType, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Color(mood.color) else Color(0xFF1A1A1A),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp
    ) {
        Text(
            text = mood.label,
            color = if (isSelected) Color.Black else Color.White,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(210.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "⭐ ${movie.voteAverage}",
            color = Color(0xFFFFD700),
            fontSize = 12.sp
        )
    }
}
