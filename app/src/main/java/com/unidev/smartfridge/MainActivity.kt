package com.unidev.smartfridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unidev.smartfridge.data.DataStoreManager
import com.unidev.smartfridge.ui.AppNavigation
import com.unidev.smartfridge.ui.screens.AyarlarEkrani
import com.unidev.smartfridge.ui.theme.SmartFridgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStoreManager = DataStoreManager(this)

        setContent {
            SmartFridgeTheme {
                // DataStore içindeki akışı okuyoruz.(CollactAsState)
                // İlk açılış anında okuma sürebileceği için başlangıcı (null) yani bilinmiyor yaptık.
                val ilkKurulum by dataStoreManager.ilkKurulumDurumuFlow.collectAsState(initial = null)


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.padding(innerPadding)
                    ){
                     // Okuma yap ve sonuç gelmediyse:
                        if (ilkKurulum == null){
                            Text(text = "Hafıza Kontrol Ediliyor...", modifier = Modifier.padding(16.dp))
                        }
                     // Okuma bitti ve True, false kararı bize geldi

                        else{
                            // Eğer daha önce mühür vurulduysa(True) ana ekrandan, yoksa(false) ayarlar'dan bağla.
                            val baslangicRota = if (ilkKurulum == true) "ana_sayfa" else "ayarlar_sayfasi"

                            // Ve beyin/haritayı devreye sok
                            AppNavigation(
                                startDestination = baslangicRota,
                                dataStoreManager = dataStoreManager
                            )
                        }
                    }

                }
            }
        }
    }
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AyarlarEkraniPreview() {
    com.unidev.smartfridge.ui.theme.SmartFridgeTheme {

    }
}



