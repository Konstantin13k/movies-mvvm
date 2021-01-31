package od.konstantin.myapplication.utils.extensions

import androidx.fragment.app.Fragment
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.di.components.AppComponent

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as MyApplication).appComponent