package com.unidev.smartfridge

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.common.ops.NormalizeOp

class FridgeAnalyzer(context: Context) {

    private var interpreter: Interpreter? = null

    init {
        try {
            Log.d("YapayZeka", "Yeni Interpreter Beyni Yükleniyor...")
            // Modeli direkt RAM'e haritalayarak okuyoruz (eski ObjectDetector engellerini aşmak için)
            val mappedByteBuffer = FileUtil.loadMappedFile(context, "best_float16.tflite")
            
            // Telefonun çekirdeklerini kullanmak için basit bir ayar
            val options = Interpreter.Options()
            options.numThreads = 4
            
            interpreter = Interpreter(mappedByteBuffer, options)
            
            val ib = interpreter?.getInputTensor(0)?.shape()?.contentToString() ?: "Bilinmiyor"
            val numOutputs = interpreter?.outputTensorCount ?: 0
            
            Log.d("YapayZeka", "BEYİN YÜKLENDİ! Input: $ib | Output Sayısı: $numOutputs")
            
            for (i in 0 until numOutputs) {
                val os = interpreter?.getOutputTensor(i)?.shape()?.contentToString() ?: "Bilinmiyor"
                Log.d("YapayZeka", "Output Tensor [$i] Boyutu: $os")
            }
            
        } catch (e: Exception) {
            Log.e("YapayZeka", "Beyin dosyası (tflite) başlatılırken hata oluştu!", e)
        }
    }

    // Firebase'den indirdiğimiz fotoğrafları (Bİtmap) paslayacağımız fonksiyon
    fun fotograftaNeGoruyorsun(bitmap: Bitmap): List<String> {
        val buldugumuzElemanlar = mutableListOf<String>()

        if (interpreter == null) {
            Log.e("YapayZeka", "Interpreter boş! Model yüklenememiş.")
            return buldugumuzElemanlar
        }

        try {
            val ishape = interpreter!!.getInputTensor(0).shape()
            val oshape = interpreter!!.getOutputTensor(0).shape()

            // Modelin istediği giriş boyutu (Örn: 320x320)
            val hedefGenislik = ishape[1]
            val hedefYukseklik = ishape[2]

            // TensorImage ile görseli boyuta uygun hale getiriyoruz
            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(hedefYukseklik, hedefGenislik, ResizeOp.ResizeMethod.BILINEAR))
                .add(NormalizeOp(0f, 255f)) // YOLO genellikle pikselleri 0-1 arası (float) ister
                .build()

            var tensorImage = TensorImage(interpreter!!.getInputTensor(0).dataType())
            tensorImage.load(bitmap)
            tensorImage = imageProcessor.process(tensorImage)

            Log.d("YapayZeka", "Görsel $hedefGenislik x $hedefYukseklik olarak boyutlandırıldı. Tensor çalışıyor...")

            // Çıktı buffer'ını dinamik olarak okumak için modelin yapısına bakıyoruz
        // Çıktı buffer'ını dinamik olarak okumak için modelin yapısına bakıyoruz
            if (oshape.size == 3 && oshape[1] > 0 && oshape[2] >= 6) {
                // YOLO NMS Export: [1, max_det, 6] formatında oluyor. [Batch, 300 Kutu, 6 Özellik]
                val dim1 = oshape[0] // 1
                val dim2 = oshape[1] // 300
                val dim3 = oshape[2] // 6
                
                val outputBuffer = Array(dim1) { Array(dim2) { FloatArray(dim3) } }
                interpreter!!.run(tensorImage.buffer, outputBuffer)
                Log.d("YapayZeka", "Tensor Çalıştı! 3D Dizi Alındı: [$dim1, $dim2, $dim3]")
                
                // Array içinde dönerek bulduğumuz ürünleri listeye ekliyoruz
                var cizilenKutuSayisi = 0
                for (i in 0 until dim2) {
                    val guvenSkoru = outputBuffer[0][i][4] // Index 4: Confidence
                    val sinifId = outputBuffer[0][i][5]    // Index 5: Class ID
                    
                    // Modelin en ufak bir şey bile görüp çizdiği kutuyu say
                    if (guvenSkoru > 0.02f) {
                        cizilenKutuSayisi++
                    }
                    
                    if (guvenSkoru >= 0.35f) { // Güven eşiğimiz %35
                        val id = sinifId.toInt()
                        val isim = sinifAdiniBul(id)
                        buldugumuzElemanlar.add(isim)
                        Log.d("YapayZeka", "Ekranda $isim buldum! (Skor: ${(guvenSkoru * 100).toInt()}%) - LİSTEYE ALINDI")
                    } else if (guvenSkoru >= 0.10f) {
                        // Sadece merakını gidermek için arkada elenen kutuları da Analiz raporuna yazıyoruz
                        val id = sinifId.toInt()
                        Log.d("YapayZeka", "HASSAS BOX: Yapay Zeka bir şeye %${(guvenSkoru * 100).toInt()} ihtimal verdi ama %35'i geçemediği için eledi. (ID: $id)")
                    }
                }
                Log.d("YapayZeka", "--- ANALİZ RAPORU ---")
                Log.d("YapayZeka", "Model bu resimde toplam $cizilenKutuSayisi adet birbirinden bağımsız Box (Kutu) çizdi ancak sadece %35'i geçenleri ekrana verdi.")
                Log.d("YapayZeka", "---------------------")
            } else {
                 Log.e("YapayZeka", "Çıktı boyutu bilinmeyen formatta: ${oshape.contentToString()}")
            }

        } catch (e: Exception) {
            Log.e("YapayZeka", "Fotoğrafı incelerken beyni yaktık!", e)
        }
        
        // Liste içerisinde aynı üründen 3 tane Apple bulduysa 3 kere yazar. (Listemize olduğu gibi döner)
        return buldugumuzElemanlar
    }
    
    // Arkadaşının metadata.yaml içinden verdiği isimleri (İngilizce/Reyon kodlarını) Kullanıcı dostu kelimelere çevirme
    private fun sinifAdiniBul(classId: Int): String {
        return when (classId) {
            in 97..100 -> "Süt" // 100_milk
            in 101..107 -> "Süt"
            in 122..133 -> "Çikolata"
            in 134..141 -> "Sakız"
            in 142..151 -> "Şeker / Jelibon"
            200 -> "Elma"
            201 -> "Muz"
            202 -> "Dolmalık Biber"
            203 -> "Ekmek"
            204 -> "Havuç"
            205 -> "Deterjan"
            206 -> "İçecek"
            207 -> "Yumurta"
            208 -> "Limon"
            209 -> "Portakal"
            210 -> "Çilek"
            else -> "Genel Ürün (ID: $classId)"
        }
    }
}