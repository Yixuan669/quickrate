package au.edu.jcu.assessment.utilityapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

/**
 * Returns a flag emoji for a currency code,
 * so the user can recognise each currency at a glance.
 */
fun currencyFlag(code: String): String = when (code) {
    "SGD" -> "🇸🇬"
    "CNY" -> "🇨🇳"
    "USD" -> "🇺🇸"
    "MYR" -> "🇲🇾"
    "AUD" -> "🇦🇺"
    else -> "🏳️"
}

/**
 * Large card showing the main exchange rate "at a glance".
 * Uses the theme's primary container colour so it stands out.
 */
@Composable
fun RateCard(
    baseCurrency: String,
    targetCurrency: String,
    rate: Double,
    lastUpdated: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "${currencyFlag(baseCurrency)} 1 $baseCurrency =",
                style = MaterialTheme.typography.titleMedium
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    currencyFlag(targetCurrency),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "%.2f %s".format(rate, targetCurrency),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                "Last updated: $lastUpdated",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * One row in the "other currencies" list:
 * flag and currency code on the left, rate on the right.
 */
@Composable
fun CurrencyRow(
    code: String,
    rate: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(currencyFlag(code), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            code,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            "%.2f".format(rate),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
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
        CurrencyRow(code = "USD", rate = 0.78)
    }
}