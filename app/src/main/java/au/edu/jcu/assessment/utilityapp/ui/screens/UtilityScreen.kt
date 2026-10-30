package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.components.CurrencyRow
import au.edu.jcu.assessment.utilityapp.ui.components.RateCard

/**
 * Main screen: shows the selected exchange rate "at a glance",
 * plus the other currencies in a list below.
 * It holds no state itself; everything comes from the ViewModel.
 */
@Composable
fun UtilityScreen(
    rates: Map<String, Double>,
    targetCurrency: String,
    lastUpdated: String,
    onRefresh: () -> Unit,
    decimalPlaces: Int = 2
) {
    val mainRate = rates[targetCurrency] ?: 0.0
    val otherRates = rates.filterKeys { it != targetCurrency }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("QuickRate", style = MaterialTheme.typography.headlineMedium)

        // Main "at a glance" information
        RateCard(
            baseCurrency = "SGD",
            targetCurrency = targetCurrency,
            rate = mainRate,
            lastUpdated = lastUpdated,
            decimalPlaces = decimalPlaces
        )

        // Secondary information: the other currencies, separated by dividers
        Text("Other currencies (per 1 SGD)", style = MaterialTheme.typography.titleMedium)
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            otherRates.entries.forEachIndexed { index, (code, rate) ->
                CurrencyRow(
                    code = code,
                    rate = rate,
                    decimalPlaces = decimalPlaces,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                // Draw a line between rows, but not after the last one
                if (index < otherRates.size - 1) {
                    HorizontalDivider()
                }
            }
        }

        Button(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Refresh")
        }
    }
}