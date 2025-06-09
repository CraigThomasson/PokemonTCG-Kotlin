package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.models.PokemonCard

@Composable
fun CardItem(card: PokemonCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(150.dp)
            .heightIn(min = 250.dp, max = 250.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val fontSize = remember { mutableStateOf(14.sp) }
            val maxLines = remember { mutableStateOf(1) }

            PokemonCardTitleText(
                card = card,
                fontSize = fontSize,
                maxLines = maxLines
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokemonCardImage(card = card)
        }
    }
}

@Composable
private fun PokemonCardImage(card: PokemonCard) {
    card.Images?.large?.let { imageUrl ->
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit,
        )
    } ?: Text(
        text = "Image not available",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun PokemonCardTitleText(
    card: PokemonCard,
    fontSize: MutableState<TextUnit>,
    maxLines: MutableState<Int>
) {
    Text(
        text = card.name,
        fontSize = fontSize.value,
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth()
            .heightIn(min = 45.dp, max = 45.dp),
        textAlign = TextAlign.Center,
        maxLines = maxLines.value,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            lineHeight = fontSize.value * 1.2
        ),
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.hasVisualOverflow) {
                if (maxLines.value == 1 && fontSize.value > 9.sp) {
                    fontSize.value = (fontSize.value.value - 1).sp
                } else if (fontSize.value <= 9.sp) {
                    maxLines.value = 2
                }
            }
        },
    )
}
