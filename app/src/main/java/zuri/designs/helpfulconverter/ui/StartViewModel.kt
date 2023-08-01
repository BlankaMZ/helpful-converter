package zuri.designs.helpfulconverter.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val storageService: StorageService) : ViewModel() {

    val converters = storageService.converters

}