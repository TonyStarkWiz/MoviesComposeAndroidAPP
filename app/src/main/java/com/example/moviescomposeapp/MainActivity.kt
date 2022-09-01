package com.example.moviescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.moviescomposeapp.model.MoviesItem
import com.example.moviescomposeapp.ui.theme.MoviesComposeAppTheme
import com.example.moviescomposeapp.utils.ResultState
import com.example.moviescomposeapp.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {

    private val movieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state: ResultState by movieViewModel.movies.observeAsState(initial = ResultState.LOADING)
                    MovieList(state)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MovieList(state: ResultState) {
    when (state) {
        is ResultState.LOADING -> {

        }
        is ResultState.SUCCESS<*> -> {
            LazyColumn {
                val movies = (state as ResultState.SUCCESS<List<MoviesItem>>).result
                itemsIndexed(items = movies) { index, item ->
                    MovieItem(movie = item)
                }
            }
        }
        is ResultState.ERROR -> {

        }
    }
}

@Composable
fun MovieItem(movie: MoviesItem) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Surface {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = movie.imageUrl,
                        builder = {
                            scale(Scale.FILL)
                            placeholder(R.drawable.ic_launcher_foreground)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = movie.desc,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                   Text(
                       text = movie.name ?: "NO NAME",
                       style = MaterialTheme.typography.subtitle1,
                       fontWeight = FontWeight.Bold
                   )
                    Text(
                        text = movie.category ?: "NO CATEGORY",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(4.dp)
                    )
                    Text(
                        text = movie.desc ?: "NO DESCRIPTION",
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesComposeAppTheme {
        MovieItem(
            MoviesItem(
                category = "CATEGORY",
                desc = "DESCRIPTION",
                name = "NAME"
            )
        )
    }
}