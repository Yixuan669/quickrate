package au.edu.jcu.assessment.utilityapp.data

/**
 * Repository: the single place the app gets exchange rates from.
 * The rest of the app only talks to this interface, so the data source
 * can be swapped later (placeholder data now, web API in Week 5).
 */
interface RateRepository {
    /**
     * Returns how much of each currency equals 1 [base] currency,
     * e.g. for base "SGD": "CNY" to 5.25, "USD" to 0.78.
     */
    suspend fun getRates(base: String): Map<String, Double>
}

/**
 * Temporary repository that returns placeholder rates.
 * Will be replaced by a network repository in Week 5.
 */
class FakeRateRepository : RateRepository {
    override suspend fun getRates(base: String): Map<String, Double> = mapOf(
        "CNY" to 5.25,
        "USD" to 0.78,
        "MYR" to 3.30,
        "AUD" to 1.18
    )
}