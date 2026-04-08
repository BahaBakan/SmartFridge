package com.unidev.smartfridge.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Telefonun hafızasında ayarlar adında küçük bir kutu açıyoruz.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ayarlar")

class DataStoreManager(private val context: Context){

    // Anahtar(key) giriyoruz. Hafızada mührümüzü tutacak
    companion object{
        val ILK_KURULUM_ANAHTARI = booleanPreferencesKey("ilk_kurulum_yapildi")
    }

    // Kritik: Uygulama açılışına bakar. Eğer Mühür yoksa (ilk açılış) "false" döner.
    val ilkKurulumDurumuFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ILK_KURULUM_ANAHTARI] ?: false
        }


// Ayarlar sayfasında ilk defa butona basılırsa mühürü "True" yap.
    suspend fun ilkKurulumuTamamla(){
        context.dataStore.edit { preferences ->
            preferences[ILK_KURULUM_ANAHTARI] = true
        }
    }
}