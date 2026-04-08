package com.unidev.smartfridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnaEkran() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Akıllı Buzdolabım", fontWeight = FontWeight.Bold, fontSize = 28.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- DOLAPTAKİLER BÖLÜMÜ ---
            item {
                BolumBasligi(baslik = "Dolaptakiler (YOLOv11 Kamerası)", icon = Icons.Default.CheckCircle, iconTint = Color(0xFF388E3C))
            }
            item {
                ModernUrunKarti(isim = "Süt", detay = "Durum: Var", type = "ok", icon = Icons.Default.Check)
            }
            item {
                ModernUrunKarti(isim = "Yumurta", detay = "Mevcut: 5 (Eşik: 3)", type = "ok", icon = Icons.Default.Star)
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            // --- ALINACAKLAR BÖLÜMÜ ---
            item {
                BolumBasligi(baslik = "Alınacaklar Listesi", icon = Icons.Default.ShoppingCart, iconTint = Color(0xFFD32F2F))
            }
            item {
                // Alınacak ürünü belirgin şekilde kırmızımsı (hata/alert rengi) uyarı kartıyla gösteriyoruz.
                ModernUrunKarti(isim = "Domates", detay = "Durum: Bitti (Mevcut: 0, Eşik: 2)", type = "alert", icon = Icons.Default.Info)
            }
        }
    }
}

// Modern Bölüm Başlığı Tasarımı
@Composable
fun BolumBasligi(baslik: String, icon: ImageVector, iconTint: Color) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = baslik,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            color = iconTint.copy(alpha = 0.3f),
            thickness = 2.dp
        )
    }
}

// Eski UrunKarti'nın Modern ve İkonlu Versiyonu
@Composable
fun ModernUrunKarti(isim: String, detay: String, type: String, icon: ImageVector) {
    // Ürün bizdeyse (ok) gri/soft renkler, bittiyse ve alınacaksa (alert) kırmızımsı renkler
    val containerColor = if (type == "ok") MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer
    val contentColor = if (type == "ok") MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // İkon Arka Plan Çerçevesi
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = isim, tint = contentColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            
            // Metinler
            Column {
                Text(text = isim, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = contentColor)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = detay, fontSize = 14.sp, color = contentColor.copy(alpha = 0.8f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnaEkranPreview() {
    com.unidev.smartfridge.ui.theme.SmartFridgeTheme {
        AnaEkran()
    }
}