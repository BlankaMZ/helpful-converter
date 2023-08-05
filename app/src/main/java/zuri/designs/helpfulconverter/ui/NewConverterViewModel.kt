package zuri.designs.helpfulconverter.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zuri.designs.helpfulconverter.R
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject

@HiltViewModel
class NewConverterViewModel @Inject constructor(private val storageService: StorageService) :
    ViewModel() {

    var converterName by mutableStateOf("")
        private set

    var ingredientsWeight by mutableStateOf("")
        private set

    var productWeight by mutableStateOf("")
        private set

    var calories by mutableStateOf("")
        private set

    var cnError by mutableStateOf(R.string.common_empty_string)
        private set

    var iwError by mutableStateOf(R.string.common_empty_string)
        private set

    var pwError by mutableStateOf(R.string.common_empty_string)
        private set

    var cError by mutableStateOf(R.string.common_empty_string)
        private set

    val buttonEnabled by derivedStateOf {
        iwError.hasEmptyString() && pwError.hasEmptyString() && cError.hasEmptyString() && ingredientsWeight.isNotEmpty() && productWeight.isNotEmpty() && converterName.isNotEmpty()
    }

    fun onConverterNameChanged(newValue: String) {
        converterName = newValue
        cnError =
            if (newValue == "") R.string.error_please_enter_name else R.string.common_empty_string
    }

    fun onIngredientsWeightChanged(newValue: String) {
        ingredientsWeight = newValue
        iwError = checkIfItIsNumberError(newValue)
    }

    fun onProductWeightChanged(newValue: String) {
        productWeight = newValue
        pwError = checkIfItIsNumberError(newValue)
    }

    fun onCaloriesChanged(newValue: String) {
        calories = newValue
        cError =
            if (newValue == "") R.string.common_empty_string else checkIfItIsNumberError(newValue)
    }

    fun saveTheConverter(popUpScreen: () -> Unit) {
        viewModelScope.launch {
            storageService.save(
                Converter(
                    ingredientsWeight = ingredientsWeight.toInt(),
                    productWeight = productWeight.toInt(),
                    productCalories = if (calories.isEmpty()) 0 else calories.toInt()
                )
            )
            popUpScreen()
        }
    }
}

fun checkIfItIsNumberError(newValue: String): Int = try {
    if (newValue.trim().toInt() <= 0) R.string.error_number_must_be_grater_then_0
    else R.string.common_empty_string
} catch (e: NumberFormatException) {
    R.string.error_please_enter_number
}

fun Int.hasEmptyString(): Boolean = this == R.string.common_empty_string