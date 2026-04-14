# 🧊 Akıllı Buzdolabım (Smart Fridge)

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)
![ESP32](https://img.shields.io/badge/ESP32-E7352C?style=for-the-badge&logo=espressif&logoColor=white)
![YOLO](https://img.shields.io/badge/YOLO-00FFFF?style=for-the-badge&logo=YOLO&logoColor=black)

**Akıllı Buzdolabım**, mutfaktaki stok yönetimini Nesnelerin İnterneti (IoT) ve Makine Öğrenmesi (Machine Learning) teknolojileri ile baştan tasarlayan vizyoner bir donanım ve yazılım projesidir. Proje; **ESP32-CAM** donanımı, YOLO nesne tanıma modeli ve Jetpack Compose ile yazılan modern Android uygulamasından oluşmaktadır.

---

## ✨ Öne Çıkan Özellikler


<img width="540" height="1138" alt="resim1" src="https://github.com/user-attachments/assets/dc4cf10f-bf81-48a1-b86a-7d7c91e27754" />
<img width="538" height="1141" alt="resim2" src="https://github.com/user-attachments/assets/9ca4a6b8-7e5c-4953-a865-ddecbef00a8c" />
<img width="502" height="943" alt="resim3" src="https://github.com/user-attachments/assets/449187b3-e99e-43a6-a4eb-69db8e120862" />


### 📱 Yazılım (Android App)
* **100% Jetpack Compose:** Arayüz tamamen Google'ın en güncel ve modern arayüz geliştirme kiti ile sıfırdan yazılmıştır.
* **Otantik & İnteraktif Tasarım:** Klasik sıkıcı tablolar yerine; spiralli, 3D (üç boyutlu) sayfa çevirme (Page-flip) animasyonlarına sahip, gerçekçi bir not defteri kullanıcı deneyimi (UX) sunar. 
* **Tasarım Detayları:** Uygulamanın her köşesi dünya ülkelerine ait magnet görselleriyle zenginleştirilmiştir ve el yazısı fontları ile samimi bir alışveriş listesi hissiyatı yakalanmıştır.
* **Canlı Durum Yönetimi (State Management):** Kullanıcının sayfalar arası geçişi ve ürünler ile etkileşimi (üstünü çizme vb.) için modern StateList mimarileri inşa edilmiştir.

### 🔌 Donanım & Yapay Zeka (Smart Hardware)
* **ESP32-CAM Entegrasyonu:** Buzdolabı içerisine yerleştirilen mikrodenetleyici kameralar sayesinde dolabın anlık görüntü verisi alınır.
* **YOLO Nesne Tespiti:** Görüntüler yapay zeka üzerinden geçirilerek içerideki ürünler (Süt, Domates vb.) otomatik olarak algılanır.
* **Otonom Liste:* Eksilen veya eklenen ürünler, uygulamanın not defterine gerçek zamanlı senkronize edilir. (Geliştirme Aşamasında)

---

## 🛠️ Teknolojiler Listesi (Tech Stack)

* **Mobil Uygulama:** Kotlin, Jetpack Compose, Material Design 3, Compose Navigation, DataStore
* **Donanım:** ESP32-CAM, Güç Bankası / Pil Modülleri, Manyetik Kapı Sensörü
* **Yapay Zeka:** YOLOv11 (Nesne Tespiti)
* **Veritabanı / İletişim:** Firebase Realtime Database / Wi-Fi Protokolleri (Gelecek Güncellemede aktifleştirilecek)

---

## 🧑‍💻 Kurulum (Projeyi Çalıştırma)
1. Repository'yi bilgisayarınıza klonlayın:
   ```bash
   git clone https://github.com/KULLANICI_ADIN/SmartFridge.git
   ```
2. Android Studio (En güncel sürüm) üzerinden projeyi açın.
3. Gradle senkronizasyonu tamamlandıktan sonra fiziksel cihazınızda veya `Pixel (API 33+)` Emülatör üzerinde çalıştırın.

---

## 🚀 Bekleyen Geliştirmeler (Roadmap)
- [x] UI Yapısının Jetpack Compose ile oluşturulması
- [x] Otantik alışveriş listesi tasarımının ve 3D sayfa çevirme motorunun eklenmesi
- [ ] Kullanıcı etkileşiminin sağlanması (Ürünlerin üstünü çizme ve silme)
- [ ] Firebase ile canlı veritabanı entegrasyonu
- [ ] Mikrodenetleyici (ESP32-CAM) ile donanım uyumu testleri

> Bu proje bir mezuniyet / Ar-Ge ürünü olarak tamamen açık kaynaklı geliştirilmektedir. Modern Android mimarilerini ve donanım gücünü bir araya getirerek mutfak asistanlığında yepyeni bir sayfa açmayı amaçlar.
