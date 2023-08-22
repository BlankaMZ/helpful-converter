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
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class UseConverterViewModel @Inject constructor(private val storageService: StorageService) :
    ViewModel() {

    var converterName by mutableStateOf("")
        private set

    private var realToAppWeightFactor by mutableStateOf(0F)

    private var appToRealWeightFactor by mutableStateOf(0F)

    private var caloriesToAppWeightFactor by mutableStateOf(0F)

    var realWeight by mutableStateOf("")
        private set

    var appWeight by mutableStateOf("")
        private set

    var rwError by mutableStateOf(R.string.common_empty_string)
        private set

    var awError by mutableStateOf(R.string.common_empty_string)
        private set

    var dialogVisible by mutableStateOf(false)
        private set

    val caloriesForGivenAppWeight by derivedStateOf {
        if (appWeight.isNotEmpty() && awError.hasEmptyString() && rwError.hasEmptyString()) (appWeight.toInt() * caloriesToAppWeightFactor).roundToInt()
        else 0
    }

    fun getConverter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val converter = storageService.getConverter(id)
            converterName = converter.converterName
            realToAppWeightFactor =
                calculateTheFactor(converter.productWeight, converter.ingredientsWeight)
            appToRealWeightFactor =
                calculateTheFactor(converter.ingredientsWeight, converter.productWeight)
            caloriesToAppWeightFactor =
                calculateTheFactor(converter.productCalories, converter.ingredientsWeight)
        }
    }

    fun onAppWeightChanged(newValue: String) {
        appWeight = newValue
        awError = checkIfItIsNumberError(newValue)
        realWeight = if (!awError.hasEmptyString()) ""
        else {
            (newValue.trim().toInt() * realToAppWeightFactor).roundToInt().toString()

        }
        rwError = checkIfItIsNumberError(realWeight)
    }

    fun onRealWeightChanged(newValue: String) {
        realWeight = newValue
        rwError = checkIfItIsNumberError(newValue)
        appWeight = if (!rwError.hasEmptyString()) ""
        else {
            (newValue.trim().toInt() * appToRealWeightFactor).roundToInt().toString()

        }
        awError = checkIfItIsNumberError(appWeight)
    }

    fun showDialog() {
        dialogVisible = true
    }

    fun hideDialog() {
        dialogVisible = false
    }

}

fun calculateTheFactor(dividend: Int, divider: Int): Float {
    return if (divider == 0) 0F
    else (dividend.toFloat() / divider.toFloat() * 100F).toInt() / 100F
}