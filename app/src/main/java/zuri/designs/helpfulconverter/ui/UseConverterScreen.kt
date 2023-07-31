package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UseConverterScreen(
    converterId: String,
    modifier: Modifier = Modifier
) {
    Column{
        Text("It is $converterId")
    }
}