package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.fakeRates

/**
 * Settings screen: lets the user choose which currency
 * is shown as the main rate on the Utility screen.
 */
@Composable
fun SettingsScreen(
    targetCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Text("Main currency to show:", style = MaterialTheme.typography.titleMedium)

        // One radio button per currency
        fakeRates.keys.forEach { code ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = code == targetCurrency,
                    onClick = { onCurrencySelected(code) }
                )
                Text(code, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}