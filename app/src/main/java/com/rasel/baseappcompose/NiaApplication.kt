/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rasel.baseappcompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.rasel.baseappcompose.data.AppContainer
import com.rasel.baseappcompose.data.AppContainerImpl
import com.rasel.baseappcompose.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

const val DEEPLINK_DOMAIN = "pl-coding.com"

/**
 * [Application] class for NiA
 */
@HiltAndroidApp
class NiaApplication : Application(), ImageLoaderFactory {

    companion object {
        const val JETNEWS_APP_URI = "https://developer.android.com/jetnews"
    }

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer


    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>
    /*@Inject lateinit var imageLoader: ImageLoader*/

   /* @Inject
    lateinit var profileVerifierLogger: ProfileVerifierLogger*/

    override fun onCreate() {
        super.onCreate()

        container = AppContainerImpl(this)

        setStrictModePolicy()

        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
//        profileVerifierLogger()
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()
//    override fun newImageLoader(): ImageLoader = imageLoader


    /**
     * Return true if the application is debuggable.
     */
    private fun isDebuggable(): Boolean {
        return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }

    /**
     * Set a thread policy that detects all potential problems on the main thread, such as network
     * and disk access.
     *
     * If a problem is found, the offending call will be logged and the application will be killed.
     */
    private fun setStrictModePolicy() {
       /* if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                Builder().detectAll().penaltyLog().penaltyDeath().build(),
            )
        }*/
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "channel_id",
            "channel_name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {
        val activityIntent = Intent(this, MainActivity::class.java).apply {
            data = "https://$DEEPLINK_DOMAIN/87".toUri()
        }
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App Launched!")
            .setContentText("Tap to open deep link")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.notify(1, notification)
    }
}
