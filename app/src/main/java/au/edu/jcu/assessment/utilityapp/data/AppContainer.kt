package au.edu.jcu.assessment.utilityapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Dependency injection container: creates the objects the app needs
 * in one place, so they can be swapped easily (e.g. fake data -> web API).
 */
interface AppContainer {
    val rateRepository: RateRepository
}

/**
 * The container used by the real app.
 * It builds Retrofit and provides a repository that reads live rates.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.frankfurter.dev/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ExchangeRateApiService by lazy {
        retrofit.create(ExchangeRateApiService::class.java)
    }

    override val rateRepository: RateRepository by lazy {
        NetworkRateRepository(apiService)
    }
}