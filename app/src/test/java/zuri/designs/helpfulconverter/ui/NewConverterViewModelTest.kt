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
class NewConverterViewModelTest {

    private val storageService: StorageService = mockk()

    private lateinit var viewModel: NewConverterViewModel
    private val converterId = 1
    private val converterName = "First"
    private val newConverterName = "New One"
    private val testConverter = Converter(converterId, converterName, 900, 800, 0)
    private val newConverter = Converter(converterName = newConverterName, ingredientsWeight = 900, productWeight = 800, productCalories = 1020)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = NewConverterViewModel(storageService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun newConverterViewModel_getConverterWithGivenId_converterDataUpdatedAndFlagNewConverterSetToFalse() {

        coEvery {
            storageService.getConverter(converterId)
        } returns testConverter

        viewModel.getConverter(converterId)

        coVerify {
            storageService.getConverter(converterId)
        }

        Assert.assertEquals(viewModel.newConverter, false)
        Assert.assertEquals(viewModel.converterName, converterName)
    }

    @Test
    fun newConverterViewModel_getConverterWithNullId_converterDataEmptyAndFlagNewConverterIsTrue() {

        viewModel.getConverter(null)

        Assert.assertEquals(viewModel.newConverter, true)
        Assert.assertEquals(viewModel.converterName, "")
    }

    @Test
    fun newConverterViewModel_updateConverterWithGivenId_storageServiceUpdateConverter() {

        coEvery {
            storageService.getConverter(converterId)
        } returns testConverter

        coEvery {
            storageService.update(testConverter)
        } returns Unit

        viewModel.getConverter(converterId)
        viewModel.saveTheConverter(testConverter.uid) {}

        coVerify {
            storageService.update(testConverter)
        }
    }

    @Test
    fun newConverterViewModel_saveNewConverter_storageServiceSaveConverter() {


        coEvery {
            storageService.save(newConverter)
        } returns Unit

        viewModel.onConverterNameChanged(newConverter.converterName)
        viewModel.onIngredientsWeightChanged(newConverter.ingredientsWeight.toString())
        viewModel.onProductWeightChanged(newConverter.productWeight.toString())
        viewModel.onCaloriesChanged(newConverter.productCalories.toString())

        viewModel.saveTheConverter(null) {}

        coVerify {
            storageService.save(newConverter)
        }
    }

}