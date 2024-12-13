package com.code4galaxy.paging3withjetpackcompose.presentation

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.code4galaxy.paging3withjetpackcompose.R
import com.squareup.picasso.Picasso
import java.util.Locale

/**
 * Composable function to display the News Screen with search functionality,
 * sorting options, and paginated article results using Jetpack Compose and Paging 3.
 */
@Composable
fun NewsScreen() {
    val viewModel: NewsViewModel = hiltViewModel()
    var query by remember { mutableStateOf("") }
    var sortBy by remember { mutableStateOf("popularity") }
    val articles = viewModel.getBreakingNews(query, sortBy).collectAsLazyPagingItems()

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = { newQuery -> query = newQuery },
            label = { Text("Enter search query") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Sort By Options
        Text(
            text = "Sort by:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButtonOption("relevancy", sortBy) { sortBy = it }
            Spacer(modifier = Modifier.width(2.dp))
            RadioButtonOption("popularity", sortBy) { sortBy = it }
            Spacer(modifier = Modifier.width(2.dp))
            RadioButtonOption("publishedAt", sortBy) { sortBy = it }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(articles.itemCount) { index ->
                val article = articles[index]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                ) {
                    // Display image with Picasso
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                Picasso.get()
                                    .load(article?.urlToImage)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.error)
                                    .into(this)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display title
                    Text(
                        text = article?.title ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display description
                    Text(
                        text = article?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display published date
                    Text(
                        text = "Published at: ${article?.publishedAt ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            // Handle loading and error states
            when (articles.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        LoadingIndicator(text = "Loading articles...")
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorIndicator(message = "Error loading data. Please try again.")
                    }
                }
                else -> {}
            }

            when (articles.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        LoadingIndicator(text = "Loading more articles...")
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorIndicator(message = "Error loading more data.")
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun RadioButtonOption(value: String, selected: String, onSelect: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 4.dp)
    ) {
        RadioButton(
            selected = (value == selected),
            onClick = { onSelect(value) }
        )
        Text(
            text = value.capitalize(Locale.ROOT),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
    }
}


@Composable
fun LoadingIndicator(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun ErrorIndicator(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Red,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
