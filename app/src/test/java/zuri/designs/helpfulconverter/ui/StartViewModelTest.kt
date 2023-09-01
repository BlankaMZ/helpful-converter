package zuri.designs.helpfulconverter.ui

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.service.StorageService
import zuri.designs.helpfulconverter.utils.FakeStorageService
import zuri.designs.helpfulconverter.utils.TestUtil

@OptIn(ExperimentalCoroutinesApi::class)
class StartViewModelTest {

    private val storageService: StorageService = FakeStorageService()

    private lateinit var viewModel: StartViewModel
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = StartViewModel(storageService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun startViewModel_getConvertersWithEmptySearch_showAllConverters() = runTest {

        val listOfConvertersFromStorage = storageService.converters.first()
        lateinit var listOfConvertersWithSearch : List<Converter>

        viewModel.converters.test {
            listOfConvertersWithSearch = awaitItem()
            cancel()
        }

        assertEquals(listOfConvertersFromStorage, listOfConvertersWithSearch)
        assertEquals(TestUtil.createListOfConverters(), listOfConvertersWithSearch)
    }

    @Test
    fun startViewModel_getConvertersWithSearch_showOneConverterThatContainsSearchText() = runTest {

        val listOfConvertersFromStorage = storageService.converters.first()
        lateinit var listOfConvertersWithSearch : List<Converter>

        viewModel.onSearchTextChange("First")

        viewModel.converters.test {
            listOfConvertersWithSearch = awaitItem()
            cancel()
        }

        assert(listOfConvertersFromStorage.size > listOfConvertersWithSearch.size)
        assertEquals(listOfConvertersWithSearch.size, 1)
        assertEquals(TestUtil.createListOfConverters().first().converterName, listOfConvertersWithSearch.first().converterName)
    }

    @Test
    fun startViewModel_getConvertersWithSearch_showNoConverter() = runTest {

        val listOfConvertersFromStorage = storageService.converters.first()
        lateinit var listOfConvertersWithSearch : List<Converter>

        viewModel.onSearchTextChange("Third")

        viewModel.converters.test {
            listOfConvertersWithSearch = awaitItem()
            cancel()
        }

        assert(listOfConvertersFromStorage.size > listOfConvertersWithSearch.size)
        assertEquals(listOfConvertersWithSearch.size, 0)
    }
}
