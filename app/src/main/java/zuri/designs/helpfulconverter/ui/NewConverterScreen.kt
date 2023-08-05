package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zuri.designs.helpfulconverter.R

@Composable
fun NewConverterScreen(
    modifier: Modifier = Modifier,
    viewModel: NewConverterViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.add_new_converter)) }) },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            Text(
                textWithAStar(stringResource(id = R.string.name_of_converter)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = viewModel.converterName,
                onValueChange = viewModel::onConverterNameChanged,
                supportingText = { Text(stringResource(viewModel.cnError)) },
                isError = !viewModel.cnError.hasEmptyString(),
                singleLine = true
            )
            Text(
                textWithAStar(stringResource(id = R.string.total_weight_of_ingredients)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = viewModel.ingredientsWeight,
                onValueChange = viewModel::onIngredientsWeightChanged,
                supportingText = { Text(stringResource(viewModel.iwError)) },
                isError = !viewModel.iwError.hasEmptyString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Text(
                textWithAStar(stringResource(id = R.string.weight_of_final_product)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = viewModel.productWeight,
                onValueChange = viewModel::onProductWeightChanged,
                supportingText = { Text(stringResource(viewModel.pwError)) },
                isError = !viewModel.pwError.hasEmptyString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Text(
                stringResource(id = R.string.total_amount_of_calories),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = viewModel.calories,
                onValueChange = viewModel::onCaloriesChanged,
                supportingText = { Text(stringResource(viewModel.cError)) },
                isError = !viewModel.cError.hasEmptyString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Button(
                onClick = { /*TODO*/ },
                enabled = viewModel.buttonEnabled,
                modifier = Modifier
                    .widthIn(160.dp)
                    .padding(vertical = 16.dp)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }

}

fun textWithAStar(text: String): AnnotatedString = buildAnnotatedString {

    append(text)

    withStyle(style = SpanStyle(color = Color.Red, baselineShift = BaselineShift(0.2F))) {
        append("*")
    }
}