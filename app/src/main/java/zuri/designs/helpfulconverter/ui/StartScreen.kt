package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNewConverterButtonClicked: () -> Unit = {},
    onExistingConverterClicked: (Int) -> Unit = {},
    viewModel: StartViewModel = hiltViewModel(),
) {

    val list = viewModel.converters.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
    ) {
        Text("Here will be searchbar")
        LazyColumn(content = {
            items(list.value) { listItem ->
                Text(
                    "Ingredients weight: ${listItem.ingredientsWeight}",
                    modifier = Modifier.clickable { onExistingConverterClicked(listItem.uid) })
            }
        })
        Button(onClick = onNewConverterButtonClicked) {
            Text("New Converter")
        }
    }
}
