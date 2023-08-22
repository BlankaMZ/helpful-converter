package zuri.designs.helpfulconverter.service

import kotlinx.coroutines.flow.Flow
import zuri.designs.helpfulconverter.data.Converter

interface StorageService {

    val converters: Flow<List<Converter>>

    suspend fun getConverter(converterId: Int): Converter

    suspend fun save(converter: Converter)

    suspend fun delete(converter: Converter)

}