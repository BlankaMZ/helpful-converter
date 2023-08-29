package zuri.designs.helpfulconverter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.data.ConverterDao
import zuri.designs.helpfulconverter.data.ConverterDatabase
import java.io.IOException
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SimpleDatabaseTests {

    private lateinit var converterDao: ConverterDao
    private lateinit var db: ConverterDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ConverterDatabase::class.java).allowMainThreadQueries().build()
        converterDao = db.converterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertConverter_shouldInsert_converter_andGetItById_shouldReturn_it() = runBlocking {
        val converter: Converter = TestUtil.createFirstConverter()
        converterDao.insert(converter)
        val getById = converterDao.getOneWithGivenId(1)
        assertThat(getById, equalTo(converter))
    }

    @Test
    @Throws(Exception::class)
    fun delete_shouldDelete_converter_fromDb() = runBlocking {
        val converter : Converter = TestUtil.createFirstConverter()
        converterDao.insert(converter)
        val getById = converterDao.getOneWithGivenId(1)
        assertThat(getById, equalTo(converter))
        converterDao.delete(converter)
        val getByIdAgain = converterDao.getOneWithGivenId(1)
        assertThat(getByIdAgain, equalTo(null))
    }

    @Test
    @Throws(Exception::class)
    fun update_shouldUpdate_converter_inDb() = runBlocking {
        val converter : Converter = TestUtil.createFirstConverter()
        converterDao.insert(converter)
        val getById = converterDao.getOneWithGivenId(1)
        assertEquals(getById.ingredientsWeight,converter.ingredientsWeight)
        val updatedConverter = converter.copy(ingredientsWeight = converter.ingredientsWeight + 100)
        converterDao.update(updatedConverter)
        val getByIdAgain = converterDao.getOneWithGivenId(1)
        assertEquals(getByIdAgain.ingredientsWeight, updatedConverter.ingredientsWeight)
    }


    @Test
    fun insert_shouldReturn_converter_inFlow() = runTest {
        val converter1 = TestUtil.createFirstConverter()
        val converter2 = TestUtil.createSecondConverter()
        converterDao.insert(converter1)
        converterDao.insert(converter2)

        converterDao.getAll().test {
            val list = awaitItem()
            assert(list.contains(converter1))
            assert(list.contains(converter2))
            cancel()
        }
    }

    @Test
    fun deletedConverter_shouldNot_be_present_inFlow() = runTest {
        val converter1 = TestUtil.createFirstConverter()
        val converter2 = TestUtil.createSecondConverter()
        converterDao.insert(converter1)
        converterDao.insert(converter2)
        converterDao.delete(converter2)

        converterDao.getAll().test {
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(converter1))
            assert(!list.contains(converter2))
            cancel()
        }
    }

    @Test
    fun updateConverter_should_be_updated_inFlow() = runTest {
        val converter = TestUtil.createFirstConverter()
        converterDao.insert(converter)
        val updatedConverter = converter.copy(ingredientsWeight = converter.ingredientsWeight + 100)
        converterDao.update(updatedConverter)

        converterDao.getAll().test {
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(updatedConverter))
            assert(!list.contains(converter))
            assert(list.first().ingredientsWeight == updatedConverter.ingredientsWeight)
            cancel()
        }
    }
}