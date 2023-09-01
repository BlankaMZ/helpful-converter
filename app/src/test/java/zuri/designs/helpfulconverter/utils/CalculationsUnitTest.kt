package zuri.designs.helpfulconverter.utils

import org.junit.Assert
import org.junit.Test
import zuri.designs.helpfulconverter.R
import zuri.designs.helpfulconverter.ui.calculateTheFactor
import zuri.designs.helpfulconverter.ui.checkIfItIsNumberError

class CalculationsUnitTest {

    @Test
    fun calculateTheFactor_isCorrect() {
        Assert.assertEquals(calculateTheFactor(6,3), 2.0F)
    }

    @Test
    fun calculateTheFactor_whenDividerIsGreaterThenDividend_isCorrect() {
        Assert.assertEquals(calculateTheFactor(3,6), 0.5F)
    }

    @Test
    fun calculateTheFactor_whenDividerEquals0_FactorIsEqual0() {
        Assert.assertEquals(calculateTheFactor(1,0), 0.0F)
    }

    @Test
    fun checkIfItIsNumberError_numberString_error_isEmpty(){
        Assert.assertEquals(checkIfItIsNumberError("2"), R.string.common_empty_string)
    }

    @Test
    fun checkIfItIsNumberError_numberStringWithSpace_error_isEmpty(){
        Assert.assertEquals(checkIfItIsNumberError("2  "), R.string.common_empty_string)
    }

    @Test
    fun checkIfItIsNumberError_notNumberString_error_numberFormatException(){
        Assert.assertEquals(checkIfItIsNumberError("no"), R.string.error_please_enter_number)
    }

    @Test
    fun checkIfItIsNumberError_numberStringLessThen0_error_numberMustBeGreaterThen0(){
        Assert.assertEquals(checkIfItIsNumberError("-7 "),
            R.string.error_number_must_be_greater_then_0
        )
    }
}