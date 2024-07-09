package com.example.tddmovieapp.presentation.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tddmovieapp.R
import com.example.tddmovieapp.presentation.component.MovieItem
import com.example.tddmovieapp.presentation.model.MovieVO

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = hiltViewModel()) {
    // TODO: move state to viewModel
    var searchTextState by remember { mutableStateOf("") }
    val moviesItems by remember { mutableStateOf(emptyList<MovieVO>()) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.success != null -> {

        }
        state.isLoading -> {

        }
        state.isEmpty -> {

        }
        state.error != null -> {

        }
        state.isQueryFormatError -> {

        }

    }

    SearchScreenContent(
        searchTextState = viewModel.queryState,
        moviesItems = state.success,
        onValueChange = { newValue -> viewModel.onEvent(SearchScreenEvent.OnUpdateQuery(newValue)) },
        onSearchClick = { viewModel.onEvent(SearchScreenEvent.OnSearchMovies)}
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchTextState: String,
    moviesItems: List<MovieVO>?,
    onValueChange: (newValue: String) -> Unit,
    onSearchClick: (input: String) -> Unit
) {
    val searchContentDescription = stringResource(R.string.search_field_hint)
    val searchButtonText = stringResource(id = R.string.search_button)
    val lazyColumnContentDescription = stringResource(id = R.string.search_screen_lazy_column)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row {
            OutlinedTextField(
                modifier = modifier
                    .semantics { contentDescription = searchContentDescription },
                value = searchTextState,
                onValueChange = onValueChange,
                label = { Text(text = searchContentDescription) },
            )

            Button(
                onClick = { onSearchClick(searchTextState) },
                modifier = modifier.semantics { contentDescription = searchButtonText }
            ) {
                Text(text = searchButtonText)
            }
        }

        LazyColumn(
            modifier = modifier.semantics { contentDescription = lazyColumnContentDescription }
        ) {
            moviesItems?.let {
                items(it) {
                    MovieItem(onItemClick = {}, imageUrl = it.image.orEmpty(), it.title.orEmpty())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        searchTextState = "Iron Man",
        moviesItems = emptyList(),
        onValueChange = {},
        onSearchClick = {}
    )
}