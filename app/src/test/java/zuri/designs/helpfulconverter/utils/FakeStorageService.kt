package zuri.designs.helpfulconverter.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.service.StorageService

class FakeStorageService : StorageService {
    override val converters: Flow<List<Converter>>
        get() = flow {
            emit(TestUtil.createListOfConverters())
        }

    override suspend fun getConverter(converterId: Int): Converter {
        TODO("Not yet implemented")
    }

    override suspend fun save(converter: Converter) {
        TODO("Not yet implemented")
    }

    override suspend fun update(converter: Converter) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(converter: Converter) {
        TODO("Not yet implemented")
    }

}