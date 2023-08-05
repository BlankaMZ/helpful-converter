package zuri.designs.helpfulconverter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converters")
data class Converter(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "converter_name") val converterName: String,
    @ColumnInfo(name = "ingredients_weight") val ingredientsWeight: Int,
    @ColumnInfo(name = "product_weight") val productWeight: Int,
    @ColumnInfo(name = "product_calories") val productCalories: Int
)