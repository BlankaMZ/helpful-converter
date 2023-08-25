package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zuri.designs.helpfulconverter.R

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNewConverterButtonClicked: () -> Unit = {},
    onExistingConverterClicked: (Int) -> Unit = {},
    viewModel: StartViewModel = hiltViewModel(),
) {

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val list = viewModel.converters.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNewConverterButtonClicked) {
                Text(" + ", modifier = Modifier.padding(8.dp))
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.common_search)) }
            )
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .weight(1F),
                    content = {
                        items(list.value) { listItem ->
                            Text(
                                listItem.converterName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onExistingConverterClicked(listItem.uid) }
                                    .padding(16.dp)
                            )
                        }
                    })
            }
        }
    }
}
