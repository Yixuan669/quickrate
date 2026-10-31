package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    decimalPlaces: Int = 2,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    val mainRate = rates[targetCurrency]
    val otherRates = rates.filterKeys { it != targetCurrency }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("QuickRate", style = MaterialTheme.typography.headlineMedium)

        // Error message with a retry button (e.g. no internet)
        if (errorMessage != null) {
            ErrorCard(message = errorMessage, onRetry = onRefresh)
        }

        if (isLoading && rates.isEmpty()) {
            // First load: nothing to show yet, so show a spinner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (mainRate != null) {
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
        }

        // Disabled while loading, so the user cannot send many requests at once
        Button(
            onClick = onRefresh,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Loading...")
            } else {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Refresh")
            }
        }
    }
}

/**
 * Red card that explains what went wrong and lets the user try again.
 */
@Composable
private fun ErrorCard(message: String, onRetry: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(message, style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = onRetry) {
                Text("Try again")
            }
        }
    }
}