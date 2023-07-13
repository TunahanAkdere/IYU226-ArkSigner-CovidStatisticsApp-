import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Firebase Crashlytics'i başlatma
        FirebaseCrashlytics.getInstance().apply {
            setCrashlyticsCollectionEnabled(true)
            sendUnsentReports()
        }

        // Diğer başlatma işlemleri...
    }
}
