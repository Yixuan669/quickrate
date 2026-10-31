package au.edu.jcu.assessment.utilityapp.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Repository: the single place the app gets exchange rates from.
 * The rest of the app only talks to this interface, so the data source
 * can be swapped (placeholder data or the web API).
 */
interface RateRepository {
    /**
     * Returns how much of each currency equals 1 [base] currency,
     * e.g. for base "SGD": "CNY" to 5.25, "USD" to 0.78.
     */
    suspend fun getRates(base: String): Map<String, Double>

    /**
     * Returns the rates from the previous available day,
     * so the app can show whether each rate went up or down.
     */
    suspend fun getPreviousRates(base: String): Map<String, Double>
}

/**
 * Repository that returns placeholder rates.
 * Useful for testing without an internet connection.
 */
class FakeRateRepository : RateRepository {
    override suspend fun getRates(base: String): Map<String, Double> = mapOf(
        "CNY" to 5.25,
        "USD" to 0.78,
        "MYR" to 3.30,
        "AUD" to 1.18
    )

    override suspend fun getPreviousRates(base: String): Map<String, Double> = mapOf(
        "CNY" to 5.20,
        "USD" to 0.79,
        "MYR" to 3.30,
        "AUD" to 1.17
    )
}

/**
 * Repository that gets live exchange rates from the Frankfurter web API.
 */
class NetworkRateRepository(
    private val apiService: ExchangeRateApiService
) : RateRepository {

    override suspend fun getRates(base: String): Map<String, Double> =
        apiService
            .getRates(base = base, quotes = SUPPORTED_CURRENCIES.joinToString(","))
            .associate { it.quote to it.rate }

    override suspend fun getPreviousRates(base: String): Map<String, Double> {
        // Ask for the last week, so weekends and holidays (no new rates) are covered
        val history = apiService.getRateHistory(
            base = base,
            quotes = SUPPORTED_CURRENCIES.joinToString(","),
            from = daysAgo(7)
        )
        // For each currency, sort by date and take the second-newest rate
        return history
            .groupBy { it.quote }
            .mapNotNull { (quote, items) ->
                val sorted = items.sortedBy { it.date }
                if (sorted.size >= 2) quote to sorted[sorted.size - 2].rate else null
            }
            .toMap()
    }

    /** Returns the date [days] days ago as text, e.g. "2026-10-24". */
    private fun daysAgo(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
    }

    companion object {
        /** The currencies this app shows. */
        val SUPPORTED_CURRENCIES = listOf("CNY", "USD", "MYR", "AUD")
    }
}