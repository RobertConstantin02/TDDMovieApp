package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tddmovieapp.R
import com.example.tddmovieapp.domain.model.MovieBo
import com.example.tddmovieapp.presentation.component.MovieItem
import com.example.tddmovieapp.presentation.model.MovieVO

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(
        state = state,
        searchTextState = viewModel.queryState,
        moviesItems = state.success,
        onValueChange = { newValue -> viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(newValue)) },
        onSearchClick = { viewModel.onEvent(SearchScreenEvent.OnSearchMovies)}
    )
}

@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    modifier: Modifier = Modifier,
    searchTextState: String,
    moviesItems: List<MovieVO>?,
    onValueChange: (newValue: String) -> Unit,
    onSearchClick: (input: String) -> Unit
) {
    val searchContentDescription = stringResource(R.string.search_field_hint)
    val searchButtonText = stringResource(id = R.string.search_button)
    val lazyColumnContentDescription = stringResource(id = R.string.search_screen_lazy_column)

    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row {
            OutlinedTextField(
                modifier = modifier
                    .semantics { contentDescription = searchContentDescription },
                value = searchTextState,
                onValueChange = {
                    onValueChange(it)
                    if (isError) {
                        isError = false
                    }
                },
                label = { Text(text = searchContentDescription) },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            text = "Bad query",
                            color = Color.Red
                        )
                    }
                }
            )

            Button(
                onClick = { onSearchClick(searchTextState) },
                modifier = modifier.semantics { contentDescription = searchButtonText }
            ) {
                Text(text = searchButtonText)
            }
        }

        when {
            state.success != null -> {
                LazyColumn(
                    modifier = modifier.semantics { contentDescription = lazyColumnContentDescription }
                ) {
                    moviesItems?.let {
                        items(it) {
                            MovieItem(
                                onItemClick = {},
                                imageUrl = it.image.orEmpty(),
                                it.title.orEmpty()
                            )
                        }
                    }
                }
            }
            state.isLoading -> CircularProgressIndicator()
            state.isEmpty -> Text(text = "There are no movies for your search")
            state.error != null -> ErrorScreen()
            state.isQueryFormatError -> {
                isError = true
            }
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Error searching...")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        state = SearchScreenState(success = listOf(
            MovieVO(4532, "Marvel: Avangers", 4.1, "https://m.media-amazon.com/images/I/81Q3-wGudPL._AC_UF1000,1000_QL80_.jpg"),
            MovieVO(5675, "Marvel: Black Panther", 5.0, "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg")
        )),
        searchTextState = "Iron Man",
        moviesItems = emptyList(),
        onValueChange = {},
        onSearchClick = {}
    )
}