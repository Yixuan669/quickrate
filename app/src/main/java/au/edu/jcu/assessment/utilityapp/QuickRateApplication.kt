package au.edu.jcu.assessment.utilityapp

import android.app.Application
import au.edu.jcu.assessment.utilityapp.data.AppContainer
import au.edu.jcu.assessment.utilityapp.data.DefaultAppContainer

/**
 * Custom Application class: creates the AppContainer once when the app starts,
 * so every ViewModel gets its dependencies from the same place.
 */
class QuickRateApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}