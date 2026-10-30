package au.edu.jcu.assessment.utilityapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

/**
 * Large card showing the main exchange rate "at a glance".
 */
@Composable
fun RateCard(
    baseCurrency: String,
    targetCurrency: String,
    rate: Double,
    lastUpdated: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("1 $baseCurrency =", style = MaterialTheme.typography.titleMedium)
            Text(
                "%.2f %s".format(rate, targetCurrency),
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                "Last updated: $lastUpdated",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * One line showing a secondary exchange rate, e.g. "1 SGD = 3.30 MYR".
 */
@Composable
fun CurrencyRow(
    baseCurrency: String,
    code: String,
    rate: Double,
    modifier: Modifier = Modifier
) {
    Text(
        "1 $baseCurrency = %.2f %s".format(rate, code),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RateCardPreview() {
    MobileTech_UtilityAppTheme {
        RateCard(
            baseCurrency = "SGD",
            targetCurrency = "CNY",
            rate = 5.25,
            lastUpdated = "12:00:00"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyRowPreview() {
    MobileTech_UtilityAppTheme {
        CurrencyRow(baseCurrency = "SGD", code = "USD", rate = 0.78)
    }
}