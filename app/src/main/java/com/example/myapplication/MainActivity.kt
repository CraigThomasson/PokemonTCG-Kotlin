package com.example.myapplication

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.models.PokemonCard
import com.example.myapplication.models.Set
import com.example.myapplication.models.UiState
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.components.BannerImage
import com.example.myapplication.ui.components.Header
import com.example.myapplication.ui.screens.CardListScreen
import com.example.myapplication.ui.screens.SetListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }

        val apiKey = BuildConfig.POKEMON_TCG_API_KEY
        viewModel.loadSets(apiKey)
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SET_LIST_SCREEN,
        modifier = modifier,
    ) {
        composable(Routes.SET_LIST_SCREEN) {
            SetListScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(Routes.CARD_LIST_SCREEN) { backStackEntry ->
            val setId = backStackEntry.arguments?.getString("setId") ?: ""
            CardListScreen(setId = setId, viewModel = viewModel)
        }
    }
}

@Composable
fun LoadingScreen() {
    Text("Loading...")
}

@Composable
fun ErrorScreen(message: String) {
    Text("Error: $message")
}

@Composable
fun SetList(
    sets: List<Set>,
    onSetClick: (String) -> Unit,
) {
    LazyColumn {

        item {
            BannerImage()
        }

        item {
            Header(title = stringResource(R.string.Mian_header_title))
        }
        items(sets) { set ->
            SetCard(set = set, onClick = onSetClick)
        }
    }
}

@Composable
fun SetCard(
    set: Set,
    onClick: (String) -> Unit,
) {
    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(set.id) },
        // Navigate on click
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = set.name,
                fontSize = 18.sp,
                modifier =
                Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Image(
                painter = rememberAsyncImagePainter(set.images.logo),
                contentDescription = null,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
