package zuri.designs.helpfulconverter.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConverterDao {

    @Query("SELECT * FROM converters")
    fun getAll(): Flow<List<Converter>>

    @Query("SELECT * FROM converters WHERE uid LIKE :id LIMIT 1")
    fun getOneWithGivenId(id: Int): Flow<Converter>

    @Insert
    suspend fun insert(converter: Converter)

    @Delete
    fun delete(converter: Converter)

}