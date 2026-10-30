package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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

/**
 * Main screen: shows the selected exchange rate "at a glance",
 * plus the other currencies in smaller text.
 */
@Composable
fun UtilityScreen(targetCurrency: String) {
    var lastUpdated by remember { mutableStateOf(currentTime()) }
    val mainRate = fakeRates[targetCurrency] ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("QuickRate", style = MaterialTheme.typography.headlineMedium)

        // Main "at a glance" information: the rate the user cares about most
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("1 SGD =", style = MaterialTheme.typography.titleMedium)
                Text(
                    "%.2f %s".format(mainRate, targetCurrency),
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    "Last updated: $lastUpdated",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Secondary information: the other currencies, in smaller text
        Text("Other currencies", style = MaterialTheme.typography.titleMedium)
        fakeRates
            .filterKeys { it != targetCurrency }
            .forEach { (code, rate) ->
                Text(
                    "1 SGD = %.2f %s".format(rate, code),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        Button(onClick = { lastUpdated = currentTime() }) {
            Text("Refresh")
        }
    }
}