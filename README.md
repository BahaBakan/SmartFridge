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

<img width="260" height="2048" alt="ayarlar1" src="https://github.com/user-attachments/assets/609337ce-9c0c-41df-857b-dcb1fff97ad1" />
<img width="260" height="790" alt="ayarlar2" src="https://github.com/user-attachments/assets/84450271-3790-442c-b20a-602e1bacc77e" />
<img width="260" height="790" alt="ana_sayfa" src="https://github.com/user-attachments/assets/306c6d08-667e-4188-9e71-66e0e9a71dfa" />

### 📱 Yazılım (Android App)
* **100% Jetpack Compose:** Arayüz tamamen Google'ın en güncel ve modern arayüz geliştirme kiti ile sıfırdan yazılmıştır.
* **Otantik & İnteraktif Tasarım:** Klasik sıkıcı tablolar yerine; spiralli, 3D (üç boyutlu) sayfa çevirme (Page-flip) animasyonlarına sahip, gerçekçi bir not defteri kullanıcı deneyimi (UX) sunar. 
* **Tasarım Detayları:** Uygulamanın her köşesi dünya ülkelerine ait magnet görselleriyle zenginleştirilmiştir ve el yazısı fontları ile samimi bir alışveriş listesi hissiyatı yakalanmıştır.
* **Canlı Durum Yönetimi (State Management):** Kullanıcının sayfalar arası geçişi ve ürünler ile etkileşimi (üstünü çizme vb.) için modern StateList mimarileri inşa edilmiştir.

### 🔌 Donanım & Yapay Zeka (Smart Hardware)
* **Custom Trained YOLOv11:** Yapay zeka modeli hazır kullanılmamış, projeye özel 16 farklı ürünle sıfırdan eğitilmiştir (**mAP@50 Doğruluk: 0.935**).
* **Edge Computing (Offline AI):** Görüntü işleme işlemi bulutta değil, tamamen cihaz üzerinde (Offline) **TensorFlow Lite** kullanılarak saniyeler içinde gerçekleştirilir.
* **Multi-Camera Mimari:** İki katlı buzdolabındaki 4 farklı ESP32 kamerasından Firebase üzerine "Overwrite" mantığıyla anlık veri akışı sağlanır. Sistem bu 4 açıyı asenkron işleyip en doğru sonucu "Data Fusion" ile tespit eder.
* **Akıllı Karar Algoritmaları:** Sistem sadece ürün saymaz; ürünleri "Tek Kullanımlık (Yumurta)" ve "Çok Kullanımlık (Süt)" olarak ayırarak eski JSON verisiyle kıyaslar ve kendi mantıksal eşiklerine göre alışveriş listeleri oluşturur.

---

## 🛠️ Teknolojiler Listesi (Tech Stack)

* **Mobil Uygulama:** Kotlin, Jetpack Compose, Material Design 3, Compose Navigation, DataStore
* **Donanım:** ESP32-CAM, Güç Bankası / Pil Modülleri, Manyetik Kapı Sensörü
* **Yapay Zeka:** YOLOv11 (Özel Eğitimli Model), TensorFlow Lite (Offline Inference)
* **Veritabanı / İletişim:** Firebase Storage (Görüntü Aktarımı), Firebase Realtime Database (JSON Senkronizasyonu)

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
- [x] Firebase Storage ve Realtime Database entegrasyonlarının tamamlanması
- [x] Özel Yapay Zeka modelinin (Custom YOLOv11) TFLite ile Android'e gömülmesi
- [ ] Multi-Camera Karşılaştırma Algoritmasının (Data Fusion) koda dökülmesi
- [ ] Ayarlar Ekranından "Kullanıcıya Özel Kritik Eşik" girilmesinin sağlanması

> Bu proje bir mezuniyet / Ar-Ge ürünü olarak tamamen açık kaynaklı geliştirilmektedir. Modern Android mimarilerini ve donanım gücünü bir araya getirerek mutfak asistanlığında yepyeni bir sayfa açmayı amaçlar.
