package od.konstantin.myapplication

import android.app.Application
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.components.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}