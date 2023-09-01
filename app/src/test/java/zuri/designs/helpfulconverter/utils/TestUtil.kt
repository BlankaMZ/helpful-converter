package zuri.designs.helpfulconverter.utils

import zuri.designs.helpfulconverter.data.Converter

object TestUtil {

    private fun createFirstConverter() = Converter(
        uid = 1,
        converterName = "First",
        ingredientsWeight = 900,
        productWeight = 800,
        productCalories = 0
    )

    private fun createSecondConverter() = Converter(
        uid = 2,
        converterName = "Second",
        ingredientsWeight = 1000,
        productWeight = 900,
        productCalories = 0
    )

    fun createListOfConverters() = listOf(createFirstConverter(), createSecondConverter())
}