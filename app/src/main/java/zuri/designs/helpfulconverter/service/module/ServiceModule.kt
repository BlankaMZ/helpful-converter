package zuri.designs.helpfulconverter.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zuri.designs.helpfulconverter.service.StorageService
import zuri.designs.helpfulconverter.service.impl.StorageServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

}
