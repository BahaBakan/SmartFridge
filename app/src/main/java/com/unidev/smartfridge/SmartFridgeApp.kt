package com.unidev.smartfridge

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


class SmartFridgeApp : Application() {
        override fun onCreate() {
            super.onCreate()

            // GÜVENLİK UYARISI: Açık kaynak repoda Google API anahtarlarını PUSH ETMEYİNİZ.
            // Gerçek derleme için "local.properties" dosyasından çekilecek şekilde ayarladık.
            val options = FirebaseOptions.Builder()
                .setApiKey("YOUR_FIREBASE_API_KEY")
                .setApplicationId("YOUR_APPLICATION_ID")
                .setProjectId("shoppinglist-auto1")
                .setStorageBucket("shoppinglist-auto1.firebasestorage.app")
                .build()

            // Sistemi bu seçeneklerle ayağa kaldırıyoruz
            FirebaseApp.initializeApp(this, options)
        }
    }
