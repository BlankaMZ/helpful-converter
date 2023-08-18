package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UseConverterScreen(
    converterId: String,
    viewModel: UseConverterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit){
        viewModel.getConverter(converterId.toInt())
    }

    Column{
        Text("It is $converterId")
        Text("And this is real to app: ${viewModel.realToAppWeightFactor}")
        Text("And this is app to real: ${viewModel.appToRealWeightFactor}")
        TextField(
            value = viewModel.realWeight,
            onValueChange = viewModel::onRealWeightChanged,
            supportingText = { Text(stringResource(viewModel.rwError)) },
            isError = !viewModel.rwError.hasEmptyString(),
            singleLine = true
        )
        TextField(
            value = viewModel.appWeight,
            onValueChange = viewModel::onAppWeightChanged,
            supportingText = { Text(stringResource(viewModel.awError)) },
            isError = !viewModel.awError.hasEmptyString(),
            singleLine = true
        )
    }
}