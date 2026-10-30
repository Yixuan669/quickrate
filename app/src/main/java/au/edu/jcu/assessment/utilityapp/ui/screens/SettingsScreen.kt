package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.fakeRates
import au.edu.jcu.assessment.utilityapp.ui.components.currencyFlag

/**
 * Settings screen: lets the user choose which currency is shown
 * as the main rate, and how many decimal places to display.
 */
@Composable
fun SettingsScreen(
    targetCurrency: String,
    onCurrencySelected: (String) -> Unit,
    decimalPlaces: Int,
    onDecimalPlacesSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        // Section 1: which currency to show as the main rate
        Text("Main currency to show:", style = MaterialTheme.typography.titleMedium)
        fakeRates.keys.forEach { code ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = code == targetCurrency,
                    onClick = { onCurrencySelected(code) }
                )
                Text("${currencyFlag(code)}  $code", style = MaterialTheme.typography.bodyLarge)
            }
        }

        HorizontalDivider()

        // Section 2: how many decimal places to show
        Text("Decimal places:", style = MaterialTheme.typography.titleMedium)
        listOf(2, 4).forEach { places ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = places == decimalPlaces,
                    onClick = { onDecimalPlacesSelected(places) }
                )
                Text(
                    if (places == 2) "2 (e.g. 5.25)" else "4 (e.g. 5.2500)",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}