package com.example.myapplication

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.models.PokemonCard
import com.example.myapplication.models.Set
import com.example.myapplication.models.UiState
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Article(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }

        val apiKey = BuildConfig.POKEMON_TCG_API_KEY
        viewModel.loadCardsBySetId(apiKey, "base2")
        viewModel.loadSets(apiKey)
    }
}

@Composable
fun Article(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val cardUiState by viewModel.cardUiState.collectAsState()
    val setUiState by viewModel.setUiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        BannerImage()
        Header(
            title = stringResource(R.string.Mian_header_title),
        )

//        Text("Cards:")
//        when (cardUiState) {
//            is UiState.Loading -> {
//                LoadingScreen()
//            }
//
//            is UiState.Success -> {
//                val cards = (cardUiState as UiState.Success<List<Card>>).data
//                CardList(cards)
//            }
//
//            is UiState.Error -> {
//                val errorMessage = (cardUiState as UiState.Error).message
//                ErrorScreen(errorMessage)
//            }
//        }

        Text("Sets:")
        when (setUiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Success -> {
                val sets = (setUiState as UiState.Success<List<Set>>).data
                SetList(sets)
            }

            is UiState.Error -> {
                val errorMessage = (setUiState as UiState.Error).message
                ErrorScreen(errorMessage)
            }
        }
    }
}


@Composable
fun BannerImage() {
    Image(
        painter = painterResource(R.drawable.pokemon_banner),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier =
        Modifier
            .fillMaxWidth()
            .height(180.dp),
    )
}

@Composable
fun Header(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun LoadingScreen() {
    Text("Loading...")
}

@Composable
fun CardList(pokemonCards: List<PokemonCard>) {
    Column {
        pokemonCards.forEach { pokemonCard ->
            Text(pokemonCard.name)
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Text("Error: $message")
}

@Composable
fun SetCard(set: Set) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = set.name,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(
                painter = rememberAsyncImagePainter(set.images.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun SetList(sets: List<Set>) {
    Column {
        sets.forEach { set ->
            SetCard(set)
        }
    }
}