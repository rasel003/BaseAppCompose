package com.rasel.baseappcompose.data.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.util.Locale


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun Context.toastColorful(message: String) {

    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast.view?.apply {

        //Gets the actual oval background of the Toast then sets the colour filter
        background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        //  background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.BLACK, BlendModeCompat.SRC_IN)

        //Gets the TextView from the Toast so it can be edited
        findViewById<TextView>(android.R.id.message).apply {
            setTextColor(Color.WHITE)
        }
    }

    toast.show()
}

fun Context.toastColorfulShort(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    val view = toast.view?.apply {

        //Gets the actual oval background of the Toast then sets the colour filter
        background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)

        //Gets the TextView from the Toast so it can be edited
        findViewById<TextView>(android.R.id.message).apply {
            setTextColor(Color.WHITE)
        }
    }

    toast.show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

/**
 * Shorthand extension function to make view gone
 */
fun View.makeGone() {
    this.visibility = View.GONE
}

/**
 * Shorthand extension function to make view visible
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}


fun Context.isNetworkAvailable(): Boolean {
    val applicationContext = this.applicationContext
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}


fun String.capitalizeFirstCharacter(): String {
    return substring(0, 1).uppercase(Locale.ROOT) + substring(1)
}

val Int.dp: Int
get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


