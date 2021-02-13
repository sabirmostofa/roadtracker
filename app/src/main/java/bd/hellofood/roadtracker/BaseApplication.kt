package bd.hellofood.roadtracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import timber.log.Timber


/**
 * Base app to init Timber, Realm
 */
@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this);
        Timber.plant(Timber.DebugTree())
    }
}