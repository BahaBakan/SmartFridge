package com.unidev.smartfridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

            // Ürün 1: Süt
            ModernSecimKarti(
                urunAdi = "Süt (Var/Yok)",
                icon = Icons.Default.ShoppingCart,
                seciliMi = sutTakip,
                onDegisim = { sutTakip = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ürün 2: Yumurta
            ModernSecimKarti(
                urunAdi = "Yumurta (Eşikli)",
                icon = Icons.Default.Star,
                seciliMi = yumurtaTakip,
                onDegisim = { yumurtaTakip = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ürün 3: Domates
            ModernSecimKarti(
                urunAdi = "Domates (Eşikli)",
                icon = Icons.Default.Info,
                seciliMi = domatesTakip,
                onDegisim = { domatesTakip = it }
            )

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

// Şık ve İkonlu Seçim Kartı Tasarımı
@Composable
fun ModernSecimKarti(urunAdi: String, icon: ImageVector, seciliMi: Boolean, onDegisim: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            // Seçili ise daha canlı bir arka plan, değilse soluk
            containerColor = if (seciliMi) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (seciliMi) 2.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (seciliMi) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = urunAdi,
                        tint = if (seciliMi) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = urunAdi,
                    fontSize = 18.sp,
                    fontWeight = if (seciliMi) FontWeight.Bold else FontWeight.Normal,
                    color = if (seciliMi) MaterialTheme.colorScheme.onSurface else Color.Gray
                )
            }
            Switch(
                checked = seciliMi,
                onCheckedChange = { yeniDurum -> onDegisim(yeniDurum) }
            )
        }
    }
}