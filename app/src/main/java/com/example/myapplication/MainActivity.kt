package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Article(modifier = Modifier.padding(innerPadding))
                }
            }
        }

        val apiKey = BuildConfig.POKEMON_TCG_API_KEY
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cards = fetchPokemonData(apiKey)
                cards.forEach { card ->
                    Log.d("MainActivity", "Card Name: ${card.name}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching card data", e)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sets = fetchPokemonSets(apiKey)
                sets.forEach { set ->
                    Log.d("MainActivity", "set Name: ${set.name}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching set data", e)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val setId = "base2"
                val cardsBySetId = fetchCardsBySetId(apiKey, setId)
                cardsBySetId.forEach { card ->
                    Log.d("MainActivity", "Card Name by Set ID: ${card.name}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching cards by set ID", e)
            }
        }
    }
}

@Composable
fun Article(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        BannerImage()
        Header(
            title = stringResource(R.string.Mian_header_title),
        )
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
