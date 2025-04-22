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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.models.Card
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

        // Trigger data fetch
        val apiKey = BuildConfig.POKEMON_TCG_API_KEY
        viewModel.loadCardsBySetId(apiKey, "base2")
    }
}

@Composable
fun Article(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier) {
        // Keep the existing header and banner
        BannerImage()
        Header(
            title = stringResource(R.string.Mian_header_title),
        )

        // Add UI state handling below the header
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Success -> {
                val cards = (uiState as UiState.Success<List<Card>>).data
                CardList(cards)
            }

            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
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
fun CardList(cards: List<Card>) {
    Column {
        cards.forEach { card ->
            Text(card.name)
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Text("Error: $message")
}