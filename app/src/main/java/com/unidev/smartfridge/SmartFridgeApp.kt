package com.unidev.smartfridge

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


class SmartFridgeApp : Application() {
        override fun onCreate() {
            super.onCreate()

            // Arkadaşınızın verdiği Web şifreleriyle Android Firebase bağlantımızı özel olarak kuruyoruz!
            val options = FirebaseOptions.Builder()
                .setApiKey("AIzaSyC4vvVztkMd_sA1lnm_v7gO6_QXsAz1QVQ")
                .setApplicationId("1:134828467419:web:3b1038b7c009c5134318fe")
                .setProjectId("shoppinglist-auto1")
                .setStorageBucket("shoppinglist-auto1.firebasestorage.app")
                .build()

            // Sistemi bu seçeneklerle ayağa kaldırıyoruz
            FirebaseApp.initializeApp(this, options)
        }
    }

