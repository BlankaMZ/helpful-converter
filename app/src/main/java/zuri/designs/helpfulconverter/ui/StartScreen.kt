package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zuri.designs.helpfulconverter.data.Converter

@Composable
fun StartScreen(
    onNewConverterButtonClicked: () -> Unit = {},
    onExistingConverterClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val list: List<Converter> = listOf(Converter(id = "masło"), Converter("mleko"), Converter("miód"))

    Column() {
        Text("Here will be searchbar")
        LazyColumn(content = {
            items(list) { listItem ->
                Text(listItem.id, modifier = Modifier.clickable { onExistingConverterClicked(listItem.id) })
            }
        })
        Button(onClick = onNewConverterButtonClicked) {
            Text("New Converter")
        }
    }
}
