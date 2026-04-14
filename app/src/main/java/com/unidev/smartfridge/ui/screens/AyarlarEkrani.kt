package com.unidev.smartfridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable // Bunu yukarı importların arasına eklemeyi unutma
import androidx.compose.foundation.layout.aspectRatio


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyarlarEkrani(onAnaEkranaGec: () -> Unit = {}) {

    var sutTakip by remember { mutableStateOf(true) }
    var yumurtaTakip by remember { mutableStateOf(true) }
    var domatesTakip by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Uygulama Ayarları", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Takip Edilecek Ürünler",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Buzdolabında stok takibini yapmak istediğiniz ürünleri seçin.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, bottom = 24.dp)
            )

            // İlk Satır (Süt ve Yumurta yanyana durur)
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    UrunKartOgesi(urunAdi = "Süt", icon = Icons.Default.ShoppingCart, seciliMi = sutTakip) {
                        sutTakip = !sutTakip
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    UrunKartOgesi(urunAdi = "Yumurta", icon = Icons.Default.Star, seciliMi = yumurtaTakip) {
                        yumurtaTakip = !yumurtaTakip
                    }
                }
            }
            // İkinci Satır (Domates tek başına durur, yanına yeni bir ürün gelene kadar boşluk bırakırız)
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    UrunKartOgesi(urunAdi = "Domates", icon = Icons.Default.Info, seciliMi = domatesTakip) {
                        domatesTakip = !domatesTakip
                    }
                }
                Spacer(modifier = Modifier.weight(1f)) // Ekranın sağı boş kalsın diye Spacer koyduk
            }


            Spacer(modifier = Modifier.weight(1f)) // Kalan tüm boşluğu itip butonu en alta attık.

            Button(
                onClick = {
                    // Veritabanını yapana kadar boş şekilde basılı bırak.
                    println("Kaydedildi: Süt: $sutTakip yumurta: $yumurtaTakip")
                    onAnaEkranaGec()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Ayarları Kaydet ve Başla", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun UrunKartOgesi(
    urunAdi: String,
    icon: ImageVector,
    seciliMi: Boolean,
    onClick: () -> Unit
){
    //Seçiliyse farklı seçili değilse farklı arkaplan rengi
    val arkaPlanRengi = if (seciliMi) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val ikonRengi = if (seciliMi) MaterialTheme.colorScheme.primary else Color.Gray

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f) //Kartın kare (bire bir oran) olmasını sağlar.
        .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = arkaPlanRengi),
        elevation = CardDefaults.cardElevation(defaultElevation = if (seciliMi) 4.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = urunAdi, modifier = Modifier.size(48.dp), tint = ikonRengi)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = urunAdi,
                fontWeight = if(seciliMi) FontWeight.Bold else FontWeight.Normal,
                color = if (seciliMi) MaterialTheme.colorScheme.onSurface else Color.Gray
            )
        }

    }
}

