package com.genericandwildcard.coronafinder.app.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.genericandwildcard.coronafinder.app.application.injection.DaggerAppComponent
import com.genericandwildcard.coronafinder.app.core.definitions.ActivityProvider
import com.genericandwildcard.coronafinder.app.core.definitions.FragmentManagerProvider
import com.genericandwildcard.coronafinder.app.coronadata.storage.MyObjectBox
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CoronaApp : Application(), HasAndroidInjector,
    FragmentManagerProvider,
    ActivityProvider {

    companion object {
        lateinit var instance: CoronaApp
            private set
    }

    val boxStore by lazy {
        MyObjectBox.builder()
            .androidContext(this)
            .build()
    }

    private val currentActivities: MutableList<AppCompatActivity> = mutableListOf()

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)

        //Setup coil to use SVGs
        val imageLoader = ImageLoader(this) {
            componentRegistry {
                add(SvgDecoder(this@CoronaApp))
            }
        }
        Coil.setDefaultImageLoader(imageLoader)

        // Init dagger
        DaggerAppComponent
            .factory()
            .create(this)
            .inject(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                currentActivities.remove(activity as AppCompatActivity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivities.add(0, activity as AppCompatActivity)
            }

            override fun onActivityResumed(activity: Activity) {
            }
        })
    }

    override val fragmentManager: FragmentManager?
        get() = activity?.supportFragmentManager
    override val activity: AppCompatActivity?
        get() = currentActivities.firstOrNull()

}
