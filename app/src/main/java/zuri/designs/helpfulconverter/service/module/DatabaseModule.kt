package zuri.designs.helpfulconverter.service.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zuri.designs.helpfulconverter.data.ConverterDao
import zuri.designs.helpfulconverter.data.ConverterDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideConverterDao(appDatabase: ConverterDatabase): ConverterDao {
        return appDatabase.converterDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ConverterDatabase {
        return Room.databaseBuilder(
            appContext,
            ConverterDatabase::class.java,
            "converter_database"
        ).build()
    }
}

