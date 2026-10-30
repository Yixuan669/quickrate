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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.currentTime
import au.edu.jcu.assessment.utilityapp.fakeRates
import au.edu.jcu.assessment.utilityapp.ui.components.CurrencyRow
import au.edu.jcu.assessment.utilityapp.ui.components.RateCard

/**
 * Main screen: shows the selected exchange rate "at a glance",
 * plus the other currencies in a list below.
 */
@Composable
fun UtilityScreen(targetCurrency: String) {
    var lastUpdated by remember { mutableStateOf(currentTime()) }
    val mainRate = fakeRates[targetCurrency] ?: 0.0
    val otherRates = fakeRates.filterKeys { it != targetCurrency }

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
            lastUpdated = lastUpdated
        )

        // Secondary information: the other currencies, separated by dividers
        Text("Other currencies (per 1 SGD)", style = MaterialTheme.typography.titleMedium)
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            otherRates.entries.forEachIndexed { index, (code, rate) ->
                CurrencyRow(
                    code = code,
                    rate = rate,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                // Draw a line between rows, but not after the last one
                if (index < otherRates.size - 1) {
                    HorizontalDivider()
                }
            }
        }

        Button(
            onClick = { lastUpdated = currentTime() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Refresh")
        }
    }
}