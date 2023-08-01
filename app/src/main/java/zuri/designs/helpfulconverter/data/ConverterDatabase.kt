package zuri.designs.helpfulconverter.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Converter::class], version = 1)
abstract class ConverterDatabase : RoomDatabase() {

   abstract fun converterDao(): ConverterDao

}