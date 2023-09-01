package zuri.designs.helpfulconverter.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import zuri.designs.helpfulconverter.data.Converter
import zuri.designs.helpfulconverter.service.StorageService

@OptIn(ExperimentalCoroutinesApi::class)
class UseConverterViewModelTest {

    private val storageService: StorageService = mockk()

    private lateinit var viewModel: UseConverterViewModel
    private val converterId = 1
    private val converterName = "First"
    private val testConverter = Converter(converterId, converterName, 200, 150, 350)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = UseConverterViewModel(storageService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun useConverterViewModel_getConverterWithGivenId_converterUpdated() {

        coEvery {
            storageService.getConverter(converterId)
        } returns testConverter

        viewModel.getConverter(converterId)

        coVerify {
            storageService.getConverter(converterId)
        }

        Assert.assertEquals(viewModel.converter, testConverter)
    }

    @Test
    fun useConverterViewModel_deleteGivenConverter_converterDeleted() {

        coEvery {
            storageService.getConverter(converterId)
        } returns testConverter

        coEvery {
            storageService.delete(testConverter)
        } returns Unit

        viewModel.getConverter(converterId)
        viewModel.deleteTheConverter {  }

        coVerify {
            storageService.delete(testConverter)
        }
    }

}