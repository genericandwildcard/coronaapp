package com.genericandwildcard.coronafinder.app.core.injection

import android.app.Activity
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ActivityKey(val value: KClass<out Activity>)
