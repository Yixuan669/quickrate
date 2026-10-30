package au.edu.jcu.assessment.utilityapp.data

/**
 * Dependency injection container: creates the objects the app needs
 * in one place, so they can be swapped easily (e.g. fake data -> web API).
 */
interface AppContainer {
    val rateRepository: RateRepository
}

/**
 * The container used by the real app.
 */
class DefaultAppContainer : AppContainer {
    override val rateRepository: RateRepository by lazy {
        FakeRateRepository()
    }
}