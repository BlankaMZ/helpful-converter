package zuri.designs.helpfulconverter

import zuri.designs.helpfulconverter.data.Converter

object TestUtil {

    fun createFirstConverter() = Converter(
        uid = 1,
        converterName = "First",
        ingredientsWeight = 900,
        productWeight = 800,
        productCalories = 0
    )

    fun createSecondConverter() = Converter(
        uid = 2,
        converterName = "Second",
        ingredientsWeight = 1000,
        productWeight = 900,
        productCalories = 0
    )

}