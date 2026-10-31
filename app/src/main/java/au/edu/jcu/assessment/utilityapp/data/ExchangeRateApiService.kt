package au.edu.jcu.assessment.utilityapp.data

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * One item in the Frankfurter API response, e.g.
 * {"date":"2026-10-30","base":"SGD","quote":"CNY","rate":5.248}
 */
data class RateDto(
    val date: String,
    val base: String,
    val quote: String,
    val rate: Double
)

/**
 * Retrofit interface describing how to call the Frankfurter exchange rate API.
 * Full URL example:
 * https://api.frankfurter.dev/v2/rates?base=SGD&quotes=CNY,USD,MYR,AUD
 */
interface ExchangeRateApiService {
    @GET("v2/rates")
    suspend fun getRates(
        @Query("base") base: String,
        @Query("quotes") quotes: String
    ): List<RateDto>
}