package zuri.designs.helpfulconverter.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import zuri.designs.helpfulconverter.service.StorageService
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val storageService: StorageService) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _converters = storageService.converters

    @OptIn(FlowPreview::class)
    val converters = searchText
        .debounce(300L)
        .onEach { _isSearching.update { true } }
        .combine(_converters) { text, converters ->
            if (text.isBlank()) {
                converters
            } else {
                converters.filter { converter ->
                    converter.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }


}