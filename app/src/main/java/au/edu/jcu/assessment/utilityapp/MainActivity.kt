package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

// Placeholder rates: 1 SGD = x of each currency.
// These will be replaced with live data from a web API in Week 5.
val fakeRates = mapOf(
    "CNY" to 5.25,
    "USD" to 0.78,
    "MYR" to 3.30,
    "AUD" to 1.18
)

// Returns the current time as text, e.g. "14:05:32"
fun currentTime(): String =
    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    MobileTech_UtilityAppTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }

    // Shared state: chosen on the Settings screen, displayed on the Utility screen
    var targetCurrency by remember { mutableStateOf("CNY") }

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
                "Utility" -> UtilityScreen(targetCurrency = targetCurrency)
                "Settings" -> SettingsScreen(
                    targetCurrency = targetCurrency,
                    onCurrencySelected = { targetCurrency = it }
                )
            }
        }
    }
}

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