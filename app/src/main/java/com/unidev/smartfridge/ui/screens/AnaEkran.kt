package com.unidev.smartfridge.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import com.unidev.smartfridge.FirebaseHelper
import com.unidev.smartfridge.FridgeAnalyzer
import com.unidev.smartfridge.R // R.font için kendi uygulamanın R kütüphanesini import etmelisin
import java.util.UUID
import kotlin.math.max
import kotlin.math.sin


// Ürün modelimiz (veri sınıfı)

data class AlinacakUrun(
    val id: String = UUID.randomUUID().toString(), // Benzer ürünler karışmasın diye id atıyoruz.
    val ad: String,
    val ustuCizikMi: Boolean = false // Varsayılan olarak ürün listeye çizilmemiş olarak gider.
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnaEkran() {

    //Ekleme penceresinin açılıp kapanmasını tetikleyecek durum
    val eklemeDialoguAcikMi = remember { mutableStateOf(false) }
    //Yeni eklenecek ürünün yazısını tutacak durum
    val yeniUrunAdi = remember { mutableStateOf("") }

    val context = LocalContext.current
    val analyzer = remember { FridgeAnalyzer(context) }

    //Ürün listemiz artık "String" tutmayacak. 'alinacakUrun' nesnelerini tutacak.
    val alinacaklar = remember {
        mutableStateListOf(
            AlinacakUrun(ad = "Domates"), AlinacakUrun(ad = "Taze Soğan"),
            AlinacakUrun(ad = "Şişe Su"), AlinacakUrun(ad = "Buzdolabı Poşeti"), AlinacakUrun(ad = "süt"),
            AlinacakUrun(ad = "peynir"), AlinacakUrun(ad = "Salatalık"), AlinacakUrun(ad = "Kavun"),
            AlinacakUrun(ad = "karpuz"), AlinacakUrun(ad = "Vişne")
        )
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Akıllı Buzdolabım", fontWeight = FontWeight.Medium, fontSize = 20.sp) }, // Daha kibar ve ince
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Arka planla uyumlu, devasa mor kutu yok
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),

                actions = {
                    IconButton(onClick = {
                        // 1. Tıklandığı an klasöre gidiyoruz.
                        FirebaseHelper.enGuncelFotosunuGetir(
                            onBasarili = { indirilenResim ->
                                //2. Resim indi beynimize (Yapay zekaya) yediriyoruz.
                                val tespitEdilenMaddeler = analyzer.fotograftaNeGoruyorsun(indirilenResim)

                                // 3. Ekranda görünen "alinacaklar" defterimizi temizleyip yeni nesneleri basıyoruz!
                                alinacaklar.clear()

                                tespitEdilenMaddeler.forEach { urunAdi ->
                                    alinacaklar.add(AlinacakUrun(ad = urunAdi))
                                }
                            },
                            onHata = { hata ->
                                Log.e("Uygulama", "İndirme Hatası", hata)
                            }
                        )
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Dolabı Güncelle", tint = Color(0xFFF9A826))
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {eklemeDialoguAcikMi.value = true}, //Dialog'u açar
                containerColor = Color(0xFFF9A826), // Deftere yakışır tatlı turuncu kalem rengi
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Ürün Ekle")
            }
        }

    ) { innerPadding ->

        if(eklemeDialoguAcikMi.value) {
            AlertDialog(
                onDismissRequest = {eklemeDialoguAcikMi.value = false},
                title = { Text("Listeye Ürün ekle") },
                text = {
                    OutlinedTextField(
                        value = yeniUrunAdi.value,
                        onValueChange = {yeniUrunAdi.value = it},
                        label = {Text("ürün adı") },
                        singleLine = true
                    )
                },

                confirmButton = {
                    TextButton( onClick = {
                        if (yeniUrunAdi.value.isNotBlank()) {
                            alinacaklar.add(AlinacakUrun(ad = yeniUrunAdi.value.trim()))
                            yeniUrunAdi.value = "" // İşlem bitince içini sıfırlıyoruz
                            eklemeDialoguAcikMi.value = false // Dialog'u kapat
                        }
                    }

                    ) { Text("Ekle") }
                },
                dismissButton = {
                    TextButton(onClick = { eklemeDialoguAcikMi.value = false }) { Text("İptal") }
                }

            )
        }

        // Uzun listeyi defterin satırı kadar (örn: 8'erli gruplara bölüyoruz. (sayfalara) )
        val sayfalar = alinacaklar.chunked(8)
        val sayfaSayisi = max(1,sayfalar.size) // En az bir sayfa olsun diye
        val pagerState = rememberPagerState(pageCount = {sayfaSayisi})

        // Magnetleri getirelim
        val magnetler = listOf(
            R.drawable.italya_magnet,
            R.drawable.fethiye_magnet,
            R.drawable.istanbul_magnet,
            R.drawable.petersburg_magnet,
            R.drawable.switzerland_magnet
        )

        // Sayfa çevirici motoru yazalım
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        )  {
            sayfaIndex -> // Şuan 0. ya da 1. sayfada olduğumuzu tutar.

            // Hangi sayfadaysak sırada o magnet gelsin. 5'i geçerse başa sarsın
            val gecerliMagnet = magnetler[sayfaIndex % magnetler.size]
            val buSayfaninUrunleri = if (sayfalar.isNotEmpty()) sayfalar[sayfaIndex] else emptyList()

            // Animasyon Matematiği
            val sayfaOffset = (pagerState.currentPage - sayfaIndex) + pagerState.currentPageOffsetFraction


            // Sayfanın Kendisi
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer{
                    // 1. Kapı Menteşesi Noktası: Sayfanın sol kenarı
                    transformOrigin = TransformOrigin(0f, 0.5f)

                    // 2. Y ekseninde bir kitap yaprağı gibi kıvrılarak çevrilmesi
                    rotationY = -100f * sayfaOffset.coerceIn(-1f, 1f)

                    // 3. Uzaktan bakıyormuş gibi gerçekçi bir 3D derinlik algısı
                    cameraDistance = 16 * density

                    // 4. Sayfa kaybolurken eski sayfa kararsın/saydamlaşsın
                    alpha = (1f - kotlin.math.abs(sayfaOffset).toFloat()).coerceIn(0f, 1f)
                }

            ) {

                // NOT DEFTERİ (Sadece o sayfaya ait dilimlenmiş ürünlerle)
                OtantikNotDefteriListesi(
                    eksikSiparisler = buSayfaninUrunleri,
                    sayfaNo = sayfaIndex + 1,
                    toplamSayfa = sayfaSayisi,
                    onItemClick = { tiklananUrun ->
                        val i = alinacaklar.indexOfFirst { it.id == tiklananUrun.id }
                        if (i != -1) {
                            alinacaklar[i] = alinacaklar[i].copy(ustuCizikMi = !alinacaklar[i].ustuCizikMi)
                        }
                    },
                    onItemDelete = { silinecekUrun ->
                        alinacaklar.removeIf { it.id == silinecekUrun.id }
                    }
                )
                // O SAYFAYA ÖZEL MAGNET
                Image(
                    painter = painterResource(id = gecerliMagnet),
                    contentDescription = "Sayfa Magneti",
                    modifier = Modifier
                        .align(Alignment.TopEnd) // SAĞ ALT KÖŞEYE ALDIK!
                        .offset(x = 12.dp, y = (-12).dp) // Hafif yukarı ve dışarı sarkıtarak harika bir tutturma efekti
                        .size(130.dp) // KOCAMAN YAPTIK!
                        .rotate(if (sayfaIndex % 2 == 0) 12f else -8f) // Altta olduğu için yana doğru çok tatlı eğilecektir
                )
            }

        }

    }
}



@Composable
fun OtantikNotDefteriListesi(eksikSiparisler: List<AlinacakUrun>, sayfaNo: Int, toplamSayfa: Int, onItemClick: (AlinacakUrun)-> Unit, onItemDelete: (AlinacakUrun)-> Unit) {
    val kagitRengi = Color(0xFFFFF9C4)
    val maviCizgi = Color(0xFF64B5F6)
    val kirmiziMarjin = Color(0xFFEF5350)

    val elYazimiz = FontFamily(Font(R.font.caveat))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(kagitRengi)
                .drawBehind {
                    val ilkSatirY = 130.dp.toPx()
                    val satirAraligi = 56.dp.toPx()

                    // 1. YATAY MAVİ ÇİZGİLER
                    var y = ilkSatirY
                    while (y < size.height) {
                        drawLine(color = maviCizgi, start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 1.dp.toPx())
                        y += satirAraligi
                    }

                    // 2. KUSURSUZ KIRMIZI MARJİN (Çok daha sola çekildi)
                    val solMarjinX = 52.dp.toPx()
                    drawLine(color = kirmiziMarjin, start = Offset(solMarjinX, 0f), end = Offset(solMarjinX, size.height), strokeWidth = 2.dp.toPx())

                    // 3. GERÇEKÇİ STANDART SPİRALLER
                    val delikYaricapi = 6.dp.toPx() // Delikler gözle görülür şekilde büyüdü (Mükemmel boyut)
                    val spiralAraligi = 28.dp.toPx() // Tellerin arası çok daha ferah oldu
                    val spiralX = 16.dp.toPx() // Kenara olan yakınlığı mükemmeldi, onu koruduk
                    var delikMerkeziY = 40.dp.toPx()

                    val telKalinligi = 4.dp.toPx() // Tel biraz büyüdüğü için gümüş metal kalınlığını da artırdık

                    while (delikMerkeziY < size.height - 16.dp.toPx()) {
                        // Kağıt deliği
                        drawCircle(color = Color(0xFFBCAAA4), radius = delikYaricapi, center = Offset(spiralX, delikMerkeziY))
                        drawCircle(color = Color(0xFF6D4C41), radius = delikYaricapi * 0.8f, center = Offset(spiralX, delikMerkeziY))

                        // Tel Gölgesi (Kağıda vuran gölge)
                        drawLine(
                            color = Color(0x40000000),
                            start = Offset(-2.dp.toPx(), delikMerkeziY - 6.dp.toPx()),
                            end = Offset(spiralX + 1.dp.toPx(), delikMerkeziY - 1.dp.toPx()),
                            strokeWidth = telKalinligi * 1.5f, cap = StrokeCap.Round
                        )
                        // GERÇEK METAL TEL (Tam sol uçtan, kağıt dışından deliğe uzanıyor)
                        drawLine(
                            color = Color(0xFF424242),
                            start = Offset(-4.dp.toPx(), delikMerkeziY - 4.dp.toPx()),
                            end = Offset(spiralX + 1.dp.toPx(), delikMerkeziY - 1.dp.toPx()),
                            strokeWidth = telKalinligi, cap = StrokeCap.Round
                        )
                        // TELDEKİ PARLAKLIK (Gümüş metalik yansıma)
                        drawLine(
                            color = Color(0xFFCFD8DC),
                            start = Offset(-2.dp.toPx(), delikMerkeziY - 3.dp.toPx()),
                            end = Offset(spiralX, delikMerkeziY - 1.dp.toPx()),
                            strokeWidth = telKalinligi * 0.4f, cap = StrokeCap.Round
                        )

                        delikMerkeziY += spiralAraligi
                    }
                }
        ) {
            // BAŞLIK (Yer kaplamaması için start = 64.dp'ye çekildi ve font hafif düzeltildi)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(start = 64.dp, end = 16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "Alışveriş Listesi",
                    fontFamily = elYazimiz,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            // MADDELER (Kırmızı çizginin hemen sağından başlıyor)
            eksikSiparisler.forEach { urun ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 64.dp, end = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "- ${urun.ad}",
                        fontFamily = elYazimiz,
                        fontSize = 32.sp,
                        // Üstü çiziliyse soluk gri, değilse koyu yaz.
                        color = if (urun.ustuCizikMi) Color(0xFF9E9E9E) else Color(0xFF333333),
                        // Üstü çiziliyse çizgi çek, değilse çekme.
                        textDecoration = if (urun.ustuCizikMi) androidx.compose.ui.text.style.TextDecoration.LineThrough else null,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .clickable { onItemClick(urun) } // Tıklandığında AnaEkrana haber ver
                            .weight(1f) // Yazı uzarsa ikonu sağa itsin
                    )
                    // Sil Butonu (Zarif el yapımı gibi bir 'X' İkonu)
                    IconButton(
                        onClick = { onItemDelete(urun) }, // Tıklandığında yine haber ver
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Sil",
                            tint = Color(0xFFEF5350).copy(alpha = 0.6f) // Sayfa marjini ile uyumlu soluk kırmızı
                        )
                    }
                }
            }

            // Sayfanın geri kalan tüm boşluğunu yutuyoruz ve en alta sayfa sayısı geliyor
            Spacer(modifier = Modifier.weight(1f))

            // Kağıdın en alt ortasına otantik bir numaratör ekliyoruz

            Text(
                text = "- $sayfaNo / $toplamSayfa -",
                fontFamily = elYazimiz,
                fontSize = 24.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 38.dp)
            )
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