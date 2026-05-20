package com.rozo_garzon.moodflix.ui.detail

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rozo_garzon.moodflix.ui.theme.RedPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    Scaffold(
        containerColor = Color(0xFF0D0D0D)
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = RedPrimary
                    )
                }
                is DetailUiState.Success -> {
                    val movie = state.movie
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Poster / Backdrop Header
                        Box(modifier = Modifier.fillMaxWidth().height(450.dp)) {
                            AsyncImage(
                                model = movie.backdropUrl ?: movie.posterUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color(0x80000000),
                                                Color(0xFF0D0D0D)
                                            )
                                        )
                                    )
                            )
                        }

                        // Content
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .offset(y = (-40).dp)
                        ) {
                            Text(
                                text = movie.title.uppercase(),
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black,
                                lineHeight = 38.sp
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, "Rating", tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = movie.voteAverage.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = movie.releaseYear.toString(),
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Genres
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                movie.genres.take(3).forEach { genre ->
                                    Surface(
                                        color = Color(0xFF222222),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = genre,
                                            color = Color.LightGray,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "SINOPSIS",
                                color = RedPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = movie.overview.ifEmpty { "No hay descripción disponible." },
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 16.sp,
                                lineHeight = 26.sp
                            )
                            
                            Spacer(modifier = Modifier.height(40.dp))
                            
                            Button(
                                onClick = { /* Play logic */ },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("REPRODUCIR", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                is DetailUiState.Error -> {
                    Text(state.message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
            }

            // Back Button
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(top = 48.dp, start = 16.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
            }
        }
    }
}
