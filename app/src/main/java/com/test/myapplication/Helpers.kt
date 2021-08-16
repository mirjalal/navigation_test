package com.test.myapplication

import android.os.Parcelable
import androidx.navigation.NavHostController

fun <P : Parcelable> NavHostController.putParcelable(name: String, value: P) =
    currentBackStackEntry?.arguments?.putParcelable(name, value)

fun <P : Parcelable> NavHostController.getParcelable(name: String): P =
    previousBackStackEntry?.arguments?.getParcelable(name)
        ?: throw NullPointerException("""parameter named as "$name" was not set in previous page!""")

fun <P : Parcelable> NavHostController.navigateWith(
    parcelable: P,
    parcelableName: String,
    route: String
): Unit =
    run {
        putParcelable(parcelableName, parcelable)
        navigate(route)
    }
