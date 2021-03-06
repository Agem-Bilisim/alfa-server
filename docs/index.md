---
layout: default
---

# Alfa nedir?

*Pardus ve AKKY* göçünü bir süreç olarak tanımlayan, gereksinimleri belirten, yol haritası koyan, kaynak ve süre kestirimleri yapan bir göç metodolojisi ve tüm bu süreci yönetip yönlendirecek bir yönetim bilgi sistemidir. Proje kapsam ve ana bileşenleri aşağıdaki gibidir:

## Ana bileşenler

* Pardus ve AKKY göç bileşenleri
* Eğitim, destek ve bakım yönerge, içerik ve süreçleri
* LibreOffice yerelleştirme ve geliştirmeleri
* LibreOffice bulut sürümü yerelleştirme ve geliştirme

## Belgelendirme

* [Alfa Göç Metodolojisi ve Yol Haritası](https://github.com/Agem-Bilisim/alfa-server/raw/master/docs/_reports/ALFA_Goc_Metodolojisi%26Yol%20Haritas%C4%B1_v1.0.pdf)
* [Alfa Göç Bileşenleri Tanıtım Rehberi](https://github.com/Agem-Bilisim/alfa-server/raw/master/docs/_reports/ALFA_G%C3%B6%C3%A7_Bile%C5%9Fenleri_Tan%C4%B1t%C4%B1m_Rehberi_v1.0.pdf)
* [Alfa Kullanıcı Rehberi](https://github.com/Agem-Bilisim/alfa-server/raw/master/docs/_reports/ALFA_Kullan%C4%B1c%C4%B1_Rehberi_v1.0.pdf)  


# Göç bileşenleri tanıtım ve kullanım kılavuzu

Alfa kullanıcı arayüzüne dair tanımlar ve görsellerden oluşan tanıtım ve kullanım kılavuzuna [buradan erişebilirsiniz](./manual.md).

# Teknik özellikler

Alfa; göç yönetim bilgi sisteminin merkezi rolünde yer alan, kullanıcı arayüzünü sunan ve göç sürecine tabi makinalar arası iletişimi yöneten *Alfa Sunucu* ile bu makinalarda servis olarak çalışan, Windows ve Debian-tabanlı (Pardus, Ubuntu, Mint vb.) işletim sistemleriyle uyumlu olarak geliştirilmiş, kurum içi envanter toplama ve son kullanıcı etkileşiminden sorumlu *Alfa Ajan* yazılım bileşenlerinden oluşmaktadır. 

* Kuruma özel göç süreçlerinin modellenebilmesi için BPMN2.0 uyumlu iş akış motoru
* Pardus eğitimleri amacıyla kullanılacak LMSDesk sistemiyle tam entegrasyon
* Windows ve Debian-tabanlı (Pardus, Ubuntu, Mint vb.) işletim sistemi uyumlu ajan yazılım
* Kurum içi OpenLDAP ve/veya ActiveDirectory sunucu ile tam entegrasyon
* Göç odaklı bilgilendirme ve geribildirim amacıyla geliştirilmiş anket ve eğitim materyali
* Mobil uyumlu web tabanlı kullanıcı arayüzü
* Kurum içi tüm ağ, sistem (yazılım, donanım) ve çevresel birim envanteri çıkarabilme
* Envanter üzerinden göçe ilişkin sorun takibi ve iş akışı

Yazılım bileşenlerine dair teknik dokümantasyona ilgili proje sayfalarından ulaşabilirisiniz:

* [Alfa sunucu](https://github.com/Agem-Bilisim/alfa-server/blob/master/README.md)
* [Alfa ajan](https://github.com/Agem-Bilisim/alfa-agent/blob/master/README.rst)

LMSDesk entegrasyon ile ilgili teknik dokümantasyona [buradan ulaşabilirsiniz](./lms-integration.md).
