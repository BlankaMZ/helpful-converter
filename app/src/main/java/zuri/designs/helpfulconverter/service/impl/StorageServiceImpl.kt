package zuri.designs.helpfulconverter.service.impl

import kotlinx.coroutines.flow.Flow
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.data.ConverterDao
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject
constructor(private val converterDao: ConverterDao) :
    StorageService {

    override val converters: Flow<List<Converter>>
        get() = converterDao.getAll()

    override suspend fun getConverter(converterId: Int): Converter? {
        TODO("Not yet implemented")
    }

    override suspend fun save(converter: Converter) =
        converterDao.insert(converter)

}
