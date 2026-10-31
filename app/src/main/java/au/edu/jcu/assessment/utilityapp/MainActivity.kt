package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.ui.QuickRateViewModel
import au.edu.jcu.assessment.utilityapp.ui.screens.SettingsScreen
import au.edu.jcu.assessment.utilityapp.ui.screens.UtilityScreen
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTech_UtilityAppTheme {
                UtilityApp()
            }
        }
    }
}

// Returns the current time as text, e.g. "14:05:32"
fun currentTime(): String =
    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

@Composable
fun UtilityApp(
    viewModel: QuickRateViewModel = viewModel(factory = QuickRateViewModel.Factory)
) {
    // Which tab is showing is pure UI state, so it stays here
    var selectedTab by remember { mutableStateOf("Utility") }

    // App data now comes from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(
                    rates = uiState.rates,
                    targetCurrency = uiState.targetCurrency,
                    lastUpdated = uiState.lastUpdated,
                    onRefresh = { viewModel.refreshRates() },
                    decimalPlaces = uiState.decimalPlaces,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    previousRates = uiState.previousRates
                )

                "Settings" -> SettingsScreen(
                    currencies = uiState.rates.keys.toList(),
                    targetCurrency = uiState.targetCurrency,
                    onCurrencySelected = { viewModel.selectCurrency(it) },
                    decimalPlaces = uiState.decimalPlaces,
                    onDecimalPlacesSelected = { viewModel.selectDecimalPlaces(it) }
                )
            }
        }
    }
}