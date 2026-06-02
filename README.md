# 🧊 Akıllı Buzdolabım (Smart Fridge)

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)
![ESP32](https://img.shields.io/badge/ESP32-E7352C?style=for-the-badge&logo=espressif&logoColor=white)
![YOLO](https://img.shields.io/badge/YOLO-00FFFF?style=for-the-badge&logo=YOLO&logoColor=black)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?style=for-the-badge&logo=tensorflow&logoColor=white)

**Akıllı Buzdolabım**, mutfaktaki stok yönetimini Nesnelerin İnterneti (IoT) ve Makine Öğrenmesi (Machine Learning) teknolojileri ile baştan tasarlayan vizyoner bir donanım ve yazılım projesidir. Proje; **ESP32-CAM** donanımı, YOLO nesne tanıma modeli ve Jetpack Compose ile yazılan modern Android uygulamasından oluşmaktadır.

---

## ✨ Öne Çıkan Özellikler

<p align="center">
  <img width="260" alt="ana_sayfa" src="https://github.com/user-attachments/assets/c19fd538-f15f-433e-9f51-63f84d0d7bc8" /> &nbsp; 
  <img width="260" alt="ayarlar1" src="https://github.com/user-attachments/assets/db544e8f-d9d8-4cb3-9bdf-caa93771bc87" /> &nbsp; 
  <img width="260" alt="ayarlar2" src="https://github.com/user-attachments/assets/190f18ac-4b50-4808-916a-7dc677bd6f11" />
</p>

### 🧠 Yapay Zeka ve Bilgisayarlı Görü İnovasyonları
* **Domain Adaptation (İnce Ayar):** Model sadece internet verisiyle değil, ESP32 OV2640 kamerasının kendine has düşük dinamik aralığına ve renk sapmalarına (pembe/mor tonlar) karşı özel veri seti oluşturularak **Fine-Tune** edilmiştir. Bu sayede zorlu donanım koşullarında bile mükemmel tanıma sağlanır.
* **Multi-Camera Fusion (Kör Nokta Çözümü):** Raf köşelerine "çapraz ateş (cross-fire)" açısıyla yerleştirilen kameralardan gelen veriler asenkron olarak işlenir. Öndeki ürünün arkadakini kapatması (Occlusion) sorunu, iki kameranın verilerini **`maxOf`** algoritmasıyla birleştiren akıllı karar mekanizmasıyla %100 çözülmüştür.
* **TensorFlow Lite Görüntü Optimizasyonu:** Firebase'den inen sıkıştırılmış JPEG'lerin Android tarafından RAM tasarrufu için `RGB_565` formatında çözülüp yapay zekayı "körü etmesi" engellenmiş; pikseller modele girmeden hemen önce donanımsal olarak `ARGB_8888` formatına dönüştürülmüştür.
* **Dinamik Güven Eşiği (Thresholding):** Yanlış pozitifleri (False Positive - gölgelerin veya yansımaların ürün sanılması) engellemek için güven eşiği dinamik olarak %60 seviyelerine kalibre edilmiş ve sıfır hata hedeflenmiştir.

### 📱 Yazılım (Android App)
* **100% Jetpack Compose:** Arayüz tamamen Google'ın en güncel ve modern arayüz geliştirme kiti ile sıfırdan yazılmıştır.
* **Otantik & İnteraktif Tasarım:** Klasik sıkıcı tablolar yerine; spiralli, 3D (üç boyutlu) sayfa çevirme (Page-flip) animasyonlarına sahip, gerçekçi bir not defteri kullanıcı deneyimi (UX) sunar. 
* **Canlı Durum Yönetimi (State Management):** Kullanıcının sayfalar arası geçişi ve ürünler ile etkileşimi (üstünü çizme vb.) için modern StateList mimarileri inşa edilmiştir.

### 🔌 Donanım (Smart Hardware)
* **Custom Trained YOLOv11:** Yapay zeka modeli hazır kullanılmamış, projeye özel 16 farklı ürünle sıfırdan eğitilmiştir (**mAP@50 Doğruluk: 0.935**).
* **Edge Computing (Offline AI):** Görüntü işleme işlemi bulutta değil, tamamen cihaz üzerinde (Offline) **TensorFlow Lite** kullanılarak saniyeler içinde gerçekleştirilir.
* **Akıllı Karar Algoritmaları:** Sistem sadece ürün saymaz; ürünleri "Tek Kullanımlık (Yumurta)" ve "Çok Kullanımlık (Süt)" olarak ayırarak eski JSON verisiyle kıyaslar ve kendi mantıksal eşiklerine göre anlık alışveriş listeleri oluşturur.

---

## 🛠️ Teknolojiler Listesi (Tech Stack)

* **Mobil Uygulama:** Kotlin, Jetpack Compose, Material Design 3, Compose Navigation, DataStore, TensorFlow Lite
* **Donanım:** ESP32-CAM, Güç Bankası / Pil Modülleri, Manyetik Kapı Sensörü
* **Yapay Zeka:** YOLOv11 (Özel Eğitimli Model), TensorFlow Lite (Offline Inference), Computer Vision
* **Veritabanı / İletişim:** Firebase Storage (Görüntü Aktarımı), Firebase Realtime Database (JSON Senkronizasyonu)

---

## 🧑‍💻 Kurulum (Projeyi Çalıştırma)
1. Repository'yi bilgisayarınıza klonlayın:
   ```bash
   git clone https://github.com/KULLANICI_ADIN/SmartFridge.git
