package od.konstantin.myapplication

import android.app.Application
import od.konstantin.myapplication.di.AppComponent
import od.konstantin.myapplication.di.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}