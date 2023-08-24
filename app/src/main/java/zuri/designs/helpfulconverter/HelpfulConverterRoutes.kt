package zuri.designs.helpfulconverter

import androidx.annotation.StringRes


enum class ConverterScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    NewConverter(title = R.string.add_new),
    UseConverter(title = R.string.convert),
    EditConverter(title = R.string.edit_converter)
}

const val CONVERTER_ID = "converterId"
const val CONVERTER_DEFAULT_ID = "-1"
const val CONVERTER_ID_ARG = "?$CONVERTER_ID={$CONVERTER_ID}"