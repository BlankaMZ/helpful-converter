package zuri.designs.helpfulconverter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zuri.designs.helpfulconverter.R

@Composable
fun UseConverterScreen(
    converterId: Int,
    onEditConverterButtonClicked: (Int) -> Unit,
    popUpScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UseConverterViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getConverter(converterId)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewModel.converter.converterName) },
                actions = {
                    IconButton(onClick = {
                        onEditConverterButtonClicked(viewModel.converter.uid)
                    }) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
                    }

                    IconButton(onClick = {
                        viewModel.showDialog()
                    }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Text(
                    stringResource(id = R.string.real_weight_of_product),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = viewModel.realWeight,
                    onValueChange = viewModel::onRealWeightChanged,
                    supportingText = { Text(stringResource(viewModel.rwError)) },
                    isError = !viewModel.rwError.hasEmptyString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Text(
                    stringResource(id = R.string.weight_in_the_app),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = viewModel.appWeight,
                    onValueChange = viewModel::onAppWeightChanged,
                    supportingText = { Text(stringResource(viewModel.awError)) },
                    isError = !viewModel.awError.hasEmptyString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                if (viewModel.caloriesForGivenAppWeight != 0) {
                    Text(
                        stringResource(id = R.string.number_of_calories) + viewModel.caloriesForGivenAppWeight,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            if (viewModel.dialogVisible) {
                DeleteDialog(
                    onDismiss = { viewModel.hideDialog() },
                    onConfirm = { viewModel.deleteTheConverter(popUpScreen) }
                )
            }
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    return AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(stringResource(id = R.string.common_no))
            }
        },
        title = {
            Text(stringResource(id = R.string.common_be_careful))
        },
        text = {
            Text(stringResource(id = R.string.info_about_deleting_converter))
        },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(stringResource(id = R.string.common_yes))
            }
        }
    )
}