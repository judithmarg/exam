package com.ucb.exam

import android.os.Bundle
import coil.compose.AsyncImage
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucb.exam.ui.theme.ExamTheme
import com.ucb.network.FilmDataSource
import com.ucb.network.ResponseDto
import com.ucb.network.RetrofitBuilder

class FilmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FilmUI(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun FilmUI(modifier: Modifier = Modifier) {
    val dataSource = FilmDataSource(RetrofitBuilder)

    // Estado para mantener los datos
    var films by remember { mutableStateOf<List<ResponseDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Efecto que se lanzará una vez que la Composable entre en composición
    LaunchedEffect(Unit) {
        try {
            val response = dataSource.getInfo("themoviedb_key") // Cambia "themoviedb_key" por tu clave de API
            
        } catch (e: Exception) {
            errorMessage = "Exception: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Mostrar indicador de carga o mensaje de error si ocurre un problema
    when {
        isLoading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        errorMessage != null -> {
            Text(text = errorMessage ?: "Unknown error", color = Color.Red)
        }
        else -> {
            LazyColumn(modifier = modifier) {
                items(films) { film ->
                    FilmItem(
                        imageUrl = "https://image.tmdb.org/t/p/w500${film.posterPath}", // URL completa del póster
                        name = film.title,
                    )
                }
            }
        }
    }
}

@Composable
fun FilmItem(imageUrl: String, name: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(model = imageUrl, contentDescription = "" )
        Text(text = name)
    }
}
