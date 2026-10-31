package au.edu.jcu.assessment.utilityapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import au.edu.jcu.assessment.utilityapp.QuickRateApplication
import au.edu.jcu.assessment.utilityapp.currentTime
import au.edu.jcu.assessment.utilityapp.data.RateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

/**
 * Everything the UI needs to display, kept in one place.
 */
data class QuickRateUiState(
    val rates: Map<String, Double> = emptyMap(),
    val previousRates: Map<String, Double> = emptyMap(),
    val targetCurrency: String = "CNY",
    val decimalPlaces: Int = 2,
    val lastUpdated: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel: holds the app's state and survives screen changes,
 * so switching tabs or rotating the phone does not reset anything.
 */
class QuickRateViewModel(
    private val rateRepository: RateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuickRateUiState())
    val uiState: StateFlow<QuickRateUiState> = _uiState.asStateFlow()

    init {
        refreshRates()
    }

    /** Asks the repository for the latest rates and records the time. */
    fun refreshRates() {
        viewModelScope.launch {
            // Show the loading indicator and clear any old error
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val rates = rateRepository.getRates("SGD")
                val previousRates = rateRepository.getPreviousRates("SGD")
                _uiState.update {
                    it.copy(
                        rates = rates,
                        previousRates = previousRates,
                        lastUpdated = currentTime(),
                        isLoading = false
                    )
                }
            } catch (e: IOException) {
                // No internet, or the connection dropped
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Could not load rates. Please check your internet connection."
                    )
                }
            } catch (e: HttpException) {
                // The website answered with an error (e.g. 500)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "The exchange rate service is unavailable. Please try again later."
                    )
                }
            }
        }
    }

    fun selectCurrency(code: String) {
        _uiState.update { it.copy(targetCurrency = code) }
    }

    fun selectDecimalPlaces(places: Int) {
        _uiState.update { it.copy(decimalPlaces = places) }
    }

    /** Tells Android how to create this ViewModel using the AppContainer. */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as QuickRateApplication
                QuickRateViewModel(application.container.rateRepository)
            }
        }
    }
}