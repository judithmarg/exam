package com.ucb.exam

import android.content.Context
import android.os.Bundle
import android.util.Log
import coil.compose.AsyncImage
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.exam.ui.theme.ExamTheme
import com.ucb.exam.ui.theme.Purple40
import com.ucb.exam.ui.theme.Purple80
import com.ucb.network.FilmDataSource
import com.ucb.network.ResponseDto
import com.ucb.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = applicationContext

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FilmUI(
                        modifier = Modifier.padding(innerPadding), context
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmUI(modifier: Modifier = Modifier, context: Context) {
    val dataSource = FilmDataSource(RetrofitBuilder)
    val filmsState = remember { mutableStateOf<List<ResponseDto>>(emptyList()) }
    val columns = 3
    LaunchedEffect(Unit) {
        try {
            val response = dataSource.getInfo()
            filmsState.value = response.results
            Log.d("FilmUI", "Films response: ${response.results}")
        } catch (e: Exception) {
            Log.e("FilmUI", "Exception: ${e.localizedMessage}")
        }
    }

    FlowRow(
        modifier = modifier
            .fillMaxSize()
            .background(Purple80)
            .verticalScroll(rememberScrollState()),
        maxItemsInEachRow = columns,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        filmsState.value.forEach{ film ->
            FilmItem(film = film)
        }
    }

}
@Composable
fun FilmItem(film: ResponseDto) {
    Card(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 5.dp)
            .width(120.dp)
            .height(150.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (modifier = Modifier.fillMaxSize()
            .background(Purple40)
            .padding(vertical = 2.dp, horizontal = 5.dp)
        ){
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185/${film.posterPath}",
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)))
            Text(
                text = film.title.toString(),
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

