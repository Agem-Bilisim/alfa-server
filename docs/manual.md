---
layout: default
---

# Göç Bileşenleri Tanıtım Kılavuzu ve Kullanım Kılavuzu

## Alfa Sunucu

### Sistem Envanteri

Sistem Envanteri ekranında; Ajan kurulu sistemlerin listesi ve kurulu sistemlere ait çeşitli bilgiler bulunur.(Hostname, IP Adresleri, MAC Adresleri, İşletim Sistemi, Etiketler). Listede arama yapılabilir ve seçilen sistemlere etiket atanabilir.

![alt text](assets/img/sistem_envanteri.png "Sistem Envanteri")

Ayrıca, listeden bir sistem kaydı seçilip **Detaylar** butonuna basılarak ajan detayları (üzerinde kurulu olan işletim sistemi, yazılımlar, kullanıcılar vb) görüntülenebilir.

![alt text](assets/img/sistem_envanter_detay.png "Sistem Envanter Detay")

**Etiket düzenle** butonuna basılarak kayıtlı etiketlerden birden fazla seçim yapılabilir veya yeni etiket eklenebilir. Bu etiketler ileride bu sistemlerin gruplanması, bu etiketlere atanacak olası sorunların çözüm sürecinin izlenmesi ve dolayısı ile ilgili sistemin göçe hazır olup olmadığının belirlenmesine yardımcı olur.

![alt text](assets/img/sistem_envanter_etiket.png "Sistem Envanter Etiket")

##NOT: YENİ ETİKET EKLEME ??

### Yazılım Envanteri

Yazılım envanteri; kuruma ait olan tüm yazılım envanterlerinin bulunduğu ekrandır. Bu yazılım envanteri sistemler üzerinde kurulu uygulamalarını (applications) ve sistem hizmetlerini (services) kapsar. Bu uygulama ve hizmetler Alfa ajanı tarafından GBYS'ne dahil olan sistemlerinden toparlanıp otomatik olarak kayıt altına alınabildikleri gibi ayrıca kullanıcı tarafından elle de eklenebilir. Yazımlara ait sorunlar ve göç'te bir sorun teşkil etmeyecek uyumlu yazılımlar yine bu arayüzden izlenebilir.

![alt text](assets/img/yazilim_envanteri.png "Yazılım Envanter")

Yazılım listesinde; yukarıda belirtildiği gibi kuruma ait sistemlerde koşan uygulamaların adı ve sürüm bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir yazılım bilgisi girilebilir. Yazılım bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Yazılımın Adı, Sürümü, Ankette gösterilebilir ve Kurumsal yazılım seçenekleri düzenlenebilir.

![alt text](assets/img/yazilim_duzenle.png "Yazılım Düzenle")

Hizmet listesinde; kurum sistemlerinde koşan hizmetlerin (services) listesi mevcuttur. **Yeni** butonuna basılarak yeni bir hizmet bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Hizmetin Adı düzenlenebilir.

![alt text](assets/img/hizmet_listesi.png "Hizmet Listesi")

Sorunlar listesinde; yazılım kaynaklı sorunların listesi mevcuttur. **Yeni** butonuna basılarak yeni bir sorun bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. **Süreç Başlat** butonuyla da, tanımlanan sorunu giderme amacıyla takip süreci başlatılır ve Süreçler ekranından takip edilebilir.

![alt text](assets/img/yazilim_sorunlar.png "Sorunlar Listesi")

Yazılım sorunlarını düzenleme işleminde; soruna ait Tanım, Açıklama, Çalışma başlangıç tarihi, Tahmini bitiş tarihi, Varsa soruna ilişkin yazılım/donanım bilgisi, Sorunun çözülüp çözülmediğine dair seçenek ve Sorun çözülme tarihi bilgileri mevcuttur. Eğer tanımlanan süreç izin veriyor ise, süreç dahilinde bu tarihler otomatik olarak güncellenecektir. Bu tarihler aynı zamanda bu ekran aracılığı ile de elle güncellenebilir.

![alt text](assets/img/sorun_duzenle.png "Sorun Düzenle")

**Süreç Başlat** işleminde de soruna ait Sorumlu Rol, Açıklama, Tahmini Bitiş Tarihi ve Notlar bilgileri doldurulduktan sonra, **Başlat** butonuna basılarak süreç başlatılır. Bu arayüz, sisteme yüklenen ve başlatılacak süreç olarak  aktif süreçler arasından seçilen sürece ait bir başlangıç ekranıdır. Sistem dahilinde tasarlanan süreçlere ve süreç arayüzlerine göre bu ekran farklılık gösterecektir.

![alt text](assets/img/sorun_surec_baslat.png "Süreç Başlat")

Uyumlu Yazılımlar listesinde; kuruma ait yazılımların, uyumlu olanları listelenir.

### Donanım Envanteri

Donanım envanteri; kuruma ait olan tüm donanım envanterlerinin bulunduğu ekrandır. İşlemci, Grafik kart, Disk, RAM Bellek, BIOS, Ağ ve Sorunlar listelerini içerir.

![alt text](assets/img/donanim_envanteri.png "Donanım Envanteri")

İşlemci listesinde; işlemciye ait Marka, Model, Çekirdek Sayısı, Hız, Üretici Firma ve Mimari bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir işlemci bilgisi girilebilir. İşlemci bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; işlemciye ait çeşitli bilgiler düzenlenebilir.

![alt text](assets/img/islemci_duzenle.png "İşlemci Düzenle")

Grafik kart listesinde; ekran kartına ait Altsistem, Bellek, Kernel bilgileri mevcuttur.

![alt text](assets/img/grafik_kart.png "Grafik Düzenle")

Disk listesinde; diske ait Marka, Açıklama, Versiyon, Ürün, Seri Numarası bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir disk bilgisi girilebilir. Disk bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Sağlayıcı, Açıklama, Versiyon, Ürün ve Ajan bilgileri düzenlenebilir.

![alt text](assets/img/disk_duzenle.png "Disk Düzenle")

Bellek listesinde; belleğe ait Marka, Hız, Boyut, Tip bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir bellek bilgisi girilebilir. Bellek bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Sağlayıcı, Açıklama, Versiyon, Ürün ve Ajan bilgileri düzenlenebilir.

![alt text](assets/img/bellek_duzenle.png "Bellek Düzenle")

BIOS listesinde; BIOS'a ait Marka, Versiyon ve Çıkış Tarihi bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir BIOS bilgisi girilebilir.

![alt text](assets/img/bios.png "BIOS Listesi")

Ağ listesinde; ağa ait Marka, Versiyon, Ürün ve Yetenekler bilgileri mevcuttur. **Yeni** butonuna basılarak yeni bir ağ bilgisi girilebilir. Ağ bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Sağlayıcı, Versiyon, Ürün, Yetenekler ve Ajan bilgileri düzenlenebilir.

![alt text](assets/img/network.png "Network Düzenle")

Sorunlar listesinde; donanım kaynaklı sorunların listesi mevcuttur. **Yeni** butonuna basılarak yeni bir sorun bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. **Süreç Başlat** butonuyla da, tanımlanan sorunu giderme amacıyla takip süreci başlatılır ve Süreçler ekranından takip edilebilir.

![alt text](assets/img/donanim_sorunlar.png "Donanım Sorunlar")

Donanım sorunlarını düzenleme işleminde; soruna ait Tanım, Açıklama, Çalışma başlangıç tarihi, Tahmini bitiş tarihi, Varsa soruna ilişkin yazılım/donanım bilgisi, Sorunun çözülüp çözülmediğine dair seçenek ve Sorun çözülme tarihi bilgileri mevcuttur.

![alt text](assets/img/donanim_sorun_duzenle.png "Donanım Sorun Düzenle")

**Süreç Başlat** işleminde de soruna ait Sorumlu Rol, Açıklama, Tahmini Bitiş Tarihi ve Notlar bilgileri doldurulduktan sonra, **Başlat** butonuna basılarak süreç başlatılır.

![alt text](assets/img/donanim_sorun_surec_baslat.png "Donanım Süreç Başlat")

### Çevresel Cihazlar

Çevresel cihazlar listesi; bilgisayarın dışından bilgi almak için kullanılan yardımcı cihazların listesidir.

![alt text](assets/img/cevresel_cihazlar.png "Çevresel Cihazlar Listesi")

 **Yeni** butonuna basılarak yeni bir cihaz bilgisi girilebilir. Cihaz bilgisi **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. Düzenle işleminden; Sağlayıcı, Cihazın Adı, Sistemde gözüken cihaz ID, Cihaz dizini, Varsa ilişkili dizini ve Ankette gösterilebilir bilgileri düzenlenebilir.

![alt text](assets/img/cevresel_cihazlar_duzenle.png "Çevresel Cihazlar Düzenle")

Sorunlar listesinde; çevresel cihazlar kaynaklı sorunların listesi mevcuttur. **Yeni** butonuna basılarak yeni bir sorun bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir veya **Sil** butonuyla silinebilir. **Süreç Başlat** butonuyla da, tanımlanan sorunu giderme amacıyla takip süreci başlatılır ve Süreçler ekranından takip edilebilir.

![alt text](assets/img/cevresel_sorunlar.png "Çevresel Cihazlar Sorunlar")

Çevresel cihazlara ait sorunlarını düzenleme işleminde; soruna ait Tanım, Açıklama, Çalışma başlangıç tarihi, Tahmini bitiş tarihi, Varsa soruna ilişkin yazılım/donanım bilgisi, Sorunun çözülüp çözülmediğine dair seçenek ve Sorun çözülme tarihi bilgileri mevcuttur.

![alt text](assets/img/cevresel_cihaz_sorun_duzenle.png "Çevresel Cihaz Sorun Düzenle")

**Süreç Başlat** işleminde de soruna ait Sorumlu Rol, Açıklama, Tahmini Bitiş Tarihi ve Notlar bilgileri doldurulduktan sonra, **Başlat** butonuna basılarak süreç başlatılır.

![alt text](assets/img/cevresel_sorun_surec_baslat.png "Çevresel Cihaz Sorun Süreç Başlat")

### Organizasyon

Kurumdaki mevcut LDAP sunucularına bağlanıp kullanıcıları ALFA'nın kendi veritabanına çekmesini sağlar. Oluşan listede; Sunucu Adres, Tür, UID Özniteliği, Oluşturma Tarihi bilgileri mevcuttur.

![alt text](assets/img/organizasyon.png "Organizasyon Listesi")

**Yeni** butonuna basılarak yeni bir LDAP Entegrasyon bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir.

![alt text](assets/img/ldap_duzenle.png "LDAP Entegrasyon Düzenle")

**Organizasyon** butonuna basılarak kurumun LDAP'ından çekilen kullanıcı bilgileri görüntülenir.

![alt text](assets/img/organizasyon_kullanici.png "Organizasyon Kullanıcı Bilgileri")

**Senkronize et** butonuna basılarak mevcut sunucu bilgilerini kullanarak entegre olunan LDAP'taki kullanıcıları senkronize eder.

![alt text](assets/img/organizasyon_senkronize.png "Organizasyon Senkronize Et")

### Eğitimler

Kuruma ait eğitim bilgilerinin listelendiği ekrandır. Eğitime ait Tanım ve Açıklama bilgileri listelenir. **Yeni** butonuna basılarak yeni bir eğitim bilgisi girilebilir. **Düzenle** butonuna basılarak düzenlenebilir. **Paylaş** butonuyla eğitimin paylaşılacağı ajanlar seçilebilir. **Sil** butonuyla silinebilir.

![alt text](assets/img/egitimler.png "Eğitimler Listesi")

### Sorunlar

Yazılım ve Donanım Envanteri ve Çevresel Cihazlara ait sorunların listendiği ekrandır. Soruna ait Tanım, Durum, Başlangıç Tarihi, Tahmini Bitiş Tarihi, Çözüldüğü Tarih, İlişkili olduğu çevresel birimler, İlişkili olduğu donanımlar ve İlişkili olduğu yazılımların bilgisi mevcuttur. **Düzenle** butonuna basılarak sorun düzenlenebilir. **Süreç Başlat** butonuyla da, tanımlanan sorunu giderme amacıyla takip süreci başlatılır ve Süreçler ekranından takip edilebilir.

![alt text](assets/img/sorunlar.png "Sorunlar Listesi")

### Anketler

Anket Listesi; yazılım, donanım ve çevresel cihazların kullanımına dair yapılan anketlerin listesini içerir. **Yeni** butonuna basılarak yeni bir anket bilgisi girilebilir. **Düzenle** butonuna basılarak anket düzenlenebilir. **Gönder** butonuyla anketin gönderileceği ajanlar seçilebilir. **Sonuçlar** butonuyla anketin sonucunun gösterileceği ajan seçilebilir. **Sil** butonuyla anket silinebilir.

![alt text](assets/img/anket.png "Anket Listesi")

Anket düzenleme ekranından anketin Tanım ve Açıklama bilgilerinin düzenebileceği gibi anketin tasarımına dair de çeşitli ayarlar mevcuttur.

![alt text](assets/img/anket_duzenle.png "Anket Düzenle")

### Sistem Kullanıcıları

ALFA sistemini kullanan kullanıcıların listesini gösteren ekrandır. Kullanıcıya ait; Kullanıcı Adı, İsim, Soyisim, E-posta ve Rol bilgilerini içerir. **Yeni** butonuna basılarak yeni bir kullanıcı bilgisi girilebilir. **Düzenle** butonuna basılarak kullanıcı bilgileri düzenlenebilir.

![alt text](assets/img/sistem_kullanicilari.png "Kullanıcı Listesi")

### Süreçler

Tanımlanan sorunu giderme amacıyla başlatılan takip süreçlerinin bulunduğu listedir. Sürecin Adı ve Sürecin Sürümü bilgileri mavcuttur.**Yeni** butonuna basılarak yeni bir süreç bilgisi girilebilir. **Düzenle** butonuna basılarak süreç düzenlenebilir. **Sil** butonuyla süreç silinebilir. **Start** butonuyla sorunun giderilmesine ilişkin süreç başlatılır.

![alt text](assets/img/surecler.png "Süreç Listesi")

### Göç Takip Merkezi

Pardus göçünün sayısal ve grafiksel verilerle takip edildiği ekranlardır. Göçe dair Mevcut Durum, Göç Durumu, Sorun İzleme ve Göç Planlama ekranları bulunur.

![alt text](assets/img/goc_takip.png "Göç Takip Merkezi")

Göç Durumu ekranında; Aylık Başarılı Göç Yapılan Sistemler (Kümülatif) ve Aylık Başarılı Göç Yapılan Sistemler grafikleri mevcuttur.

![alt text](assets/img/goc_durumu.png "Göç Durumu")

Sorun İzleme ekranında; Aylık Göç Problemleri Durumuna ait bir grafik bulunur. Grafikte; Devam Eden Problemler, Yeni Problemler ve Çözülen Problemler görüntülenir.

![alt text](assets/img/sorun_izleme.png "Sorun İzleme")

Göç Planlama ekranında; göç için yapılan planların listesi mevcuttur. Planlanan göçlere **Süreç Başlat** butonuna basılarak takip süreci başlatılır.

![alt text](assets/img/goc_planlama.png "Göç Planlama")

### Yardım

TODO

[geri](./)