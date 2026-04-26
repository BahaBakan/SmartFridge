package com.unidev.smartfridge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

object FirebaseHelper {

    private val storage = FirebaseStorage.getInstance()

    fun enGuncelFotosunuGetir(onBasarili: (Bitmap) -> Unit, onHata: (Exception) -> Unit) {
        val klasorReferansi = storage.reference.child("photos")

        klasorReferansi.listAll()
            .addOnSuccessListener { listeSonucu ->
                if (listeSonucu.items.isNotEmpty()) {
                    val enSonDosya = listeSonucu.items.last()

                    // AJAN 1: Hangi dosyayı gözüne kestirdiğini yazdırıyoruz!
                    Log.d("FirebaseAjan", "DİKKAT: Firebase şu an '${enSonDosya.name}' isimli dosyaya el attı!")

                    val maksimumBoyut: Long = 5 * 1024 * 1024

                    enSonDosya.getBytes(maksimumBoyut)
                        .addOnSuccessListener { inenVeri ->
                            // AJAN 2: Veri null geldiyse direkt çökmesini engelliyoruz!
                            if (inenVeri == null) {
                                Log.e("FirebaseAjan", "HATA: '${enSonDosya.name}' dosyası tamamen boş (null) geldi!")
                                return@addOnSuccessListener
                            }

                            try {
                                val resim = BitmapFactory.decodeByteArray(inenVeri, 0, inenVeri.size)
                                if (resim != null) {
                                    Log.d("FirebaseAjan", "BAŞARI: Resim sapasağlam çözüldü, Yapay Zekaya gönderiliyor...")
                                    onBasarili(resim)
                                } else {
                                    Log.e("FirebaseAjan", "HATA: İnen dosya bir fotoğraf değil, bozuk bir veri!")
                                }
                            } catch (e: Exception) {
                                Log.e("FirebaseAjan", "Kritik Çökme Engellendi: ", e)
                            }
                        }
                        .addOnFailureListener { hata ->
                            Log.e("FirebaseAjan", "Dosya indirilirken hata oldu!", hata)
                        }
                } else {
                    Log.e("FirebaseAjan", "Klasörün içi tamamen boş!")
                }
            }
            .addOnFailureListener { hata ->
                Log.e("FirebaseAjan", "Klasöre erişim sağlanamadı!", hata)
            }
    }
}
