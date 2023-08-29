package zuri.designs.helpfulconverter.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zuri.designs.helpfulconverter.R
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject

@HiltViewModel
class NewConverterViewModel @Inject constructor(private val storageService: StorageService) :
    ViewModel() {

    var newConverter by mutableStateOf(true)
        private set

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

    fun saveTheConverter(
        converterId: Int?,
        popUpScreen: () -> Unit
    ) {
        viewModelScope.launch {
            if (converterId != null) {
                storageService.update(
                    Converter(
                        uid = converterId,
                        converterName = converterName.trim(),
                        ingredientsWeight = ingredientsWeight.trim().toInt(),
                        productWeight = productWeight.trim().toInt(),
                        productCalories = if (calories.isEmpty()) 0 else calories.trim().toInt()
                    )
                )
            } else {
                storageService.save(
                    Converter(
                        converterName = converterName.trim(),
                        ingredientsWeight = ingredientsWeight.trim().toInt(),
                        productWeight = productWeight.trim().toInt(),
                        productCalories = if (calories.isEmpty()) 0 else calories.trim().toInt()
                    )
                )
            }
            popUpScreen()
        }
    }

    fun getConverter(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val converterFromDb = storageService.getConverter(id)
                converterName = converterFromDb.converterName
                ingredientsWeight = converterFromDb.ingredientsWeight.toString()
                productWeight = converterFromDb.productWeight.toString()
                calories = converterFromDb.productCalories.toString()
                newConverter = false
            }
        }
    }
}

fun checkIfItIsNumberError(newValue: String): Int = try {
    if (newValue.trim().toInt() <= 0) R.string.error_number_must_be_greater_then_0
    else R.string.common_empty_string
} catch (e: NumberFormatException) {
    R.string.error_please_enter_number
}

fun Int.hasEmptyString(): Boolean = this == R.string.common_empty_string