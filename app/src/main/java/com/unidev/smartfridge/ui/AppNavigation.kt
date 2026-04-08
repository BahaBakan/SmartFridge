package com.unidev.smartfridge.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unidev.smartfridge.data.DataStoreManager
import com.unidev.smartfridge.ui.screens.AnaEkran
import com.unidev.smartfridge.ui.screens.AyarlarEkrani
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(startDestination: String , dataStoreManager: DataStoreManager){

    // Ekran geçişlerini kontrol edeceğiz.
    val navController = rememberNavController()

    //Hafızaya (DataStore) mühür basmak zaman alan işlemlerden bu yuzden
    //planda yapmalıyız (coroutine). Scope kullanacağız bu yüzden
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = startDestination) {

        // 1.Rota ayarlar_sayfasi denilince AyarlarEkranını aç
        composable("ayarlar_sayfasi"){
            AyarlarEkrani(
                onAnaEkranaGec = {
                    // Butona basılınca arka planda olacak kodlar(scope.launch)
                    scope.launch {
                        //1. DataStore'a kullanıcı ayarları yaptı mührünü basıyoruz.
                        dataStoreManager.ilkKurulumuTamamla()

                        //2. Ana Ekrana geçiş yapıyoruz.
                        navController.navigate("ana_sayfa"){
                            popUpTo("ayarlar_sayfasi") {inclusive = true}
                        }

                    }


                }
            )
        }

        // 2.Rota ana_sayfa denilince AnaEkranı aç
        composable("ana_sayfa"){
            AnaEkran()
        }


    }
}