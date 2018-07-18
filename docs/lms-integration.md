---
layout: default
---

# LMS/Pardus Eğitim Entegrasyon

Alfa sunucu LMS Desk sistemine, token tabanlı HTTP/RESTful web servisler üzerinden entegre edilmektedir. Entegrasyon esnasında izlenen adımlar aşağıdaki gibidir.

## API erişim doğrulama

Tüketilecek servisler token tabanlı doğrulamayla güvence altına alındığından her zaman ilk adım olarak Alfa sunucuya atanmış *client_id* ve *client_secret* değerleri kullanılarak aşağıdaki URL üzerinden token üretilir ve takip eden tüm HTTP istemlerinde bu token LMS sunucuya tekrar gönderilerek doğrulama sağlanır.

**URL**: `https://www.pardusegitim.org/apis/v1/apiauth`  
**Yöntem**: POST  
**Gönderilen parametreler**:  

| parametre | tür |
|--------|--------|
|  client_id   |  string   |
|  client_secret   |  string   |

**Dönen cevap**:

| parametre | tür |
|--------|--------|
|  access_token   |  string   |

**Örnek HTTP istemi**:

```
curl --request POST \
--url 'https://www.pardusegitim.org/apis/v1/apiauth?client_id=XXXX&client_secret=XXXX' \
```

**Örnek cevap**:
```
{
"access_token": "1e139e1a75d8cd3175aa2275e775a85600e1cdf5d91b7b39d91d534bf2bc2758e35ed535f596894b4a1e8a980158d8e1acbd0e2d7258af9332c0e8445f137dde",
}
```

## Kullanıcıların içe aktarılması

LMS sunucuda yer alan kullanıcılar aşağıdaki HTTP istemiyle listelenmekte ve dönen cevaptaki e-posta adresleri üzerinden Alfa sunucuda yer alan (LDAP/AD) kullanıcılar ile eşlenerek veritabanındaki bilgileri güncellenmektedir.

Geçmiş eğitim bilgisi ya da kullanıcıyla etkileşim içeren (anket doldurma, eğitime yönlendirme vb.) işlevlerin sekteye uğramaması adına e-posta üzerinden eşleme yapılamayan kullanıcılar sisteme atılmamaktadır. Bu nedenle LMS sunucu ile senkronizasyon işlemine başlamadan önce Alfa sunucunun, kuruma ait OpenLDAP yada ActiveDirectory sunucusuyla entegrasyonunun yapıldığından emin olunmalıdır.  
OpenLDAP/ActiveDirectory entegrasyonuyla kurum içi kullanıcılar otomatik olarak Alfa sunucuya aktarılacak ve bu sayede LMS entegrasyon sırasında e-posta öznitelikleri üzerinden başarıyla eşleştirme yapılabilecektir.

**URL**: `https://www.pardusegitim.org/api-documention.html#`  
**Yöntem**: POST  
**Gönderilen parametreler**:  

| parametre | tür |
|--------|--------|
|  access_token   |  string   |

**Dönen cevap**:

| parametre | tür |
|--------|--------|
|  Kullanıcı listesi   |  dizi   |

**Örnek HTTP istemi**:

```
curl --request POST \
--url 'https://www.pardusegitim.org/apis/v1/apiusers?access_token=d30fbebef325dee67a3a6aad0daa784a6a919039856f4d2a1d322f1e71a890d748ee27b46d14a5c348431232016c5a556fcc40f3a4ea1a02181894a21242afa3' \
```

**Örnek cevap**:
```
[
    {
        "user_id": 1,
        "email": "eposta@eposta.com",
        "full_name": "Ad Soyad",
        "yetki": "Süperadmin"
    },
    {
        "user_id": 2,
        "email": "eposta@eposta.com",
        "full_name": "Ad Soyad",
        "yetki": "Üye"
    },
    {
        "user_id": 3,
        "email": "eposta@eposta.com",
        "full_name": "Ad Soyad",
        "yetki": "Admin"
    }
]
```

## Eğitimlerin içe aktarılması

Kullanıcıların içe aktarılmasına benzer şekilde eğitim kayıtları da aşağıdaki HTTP istemiyle listelenip eğitim adı üzerinden Alfa sunucudaki kayıtlarla eşlenmekte varsa ilgili kayıt güncellenmekte yoksa yenisi oluşturularak her iki sunucu sistemdeki eğitim kayıtları birbiriyle uyumlu hale getirilmektedir.  

**URL**: `https://www.pardusegitim.org/api-documention.html`  
**Yöntem**: POST  
**Gönderilen parametreler**:  

| parametre | tür |
|--------|--------|
|  access_token   |  string   |

**Dönen cevap**:

| parametre | tür |
|--------|--------|
|  Eğitim listesi   |  dizi   |

**Örnek HTTP istemi**:

```
curl --request POST \
--url 'https://www.pardusegitim.org/apis/v1/apieducations?access_token=d30fbebef325dee67a3a6aad0daa784a6a919039856f4d2a1d322f1e71a890d748ee27b46d14a5c348431232016c5a556fcc40f3a4ea1a02181894a21242afa3' \
```

**Örnek cevap**:
```
[
    {
        "id": 13,
        "urun_adi": "Pardus LibreOffice – Kelime İşlemci Eğitimi",
        "katilim_sayisi": 1
    },
    {
        "id": 12,
        "urun_adi": "Pardus İşletim Sistemi Kullanım Eğitimi",
        "katilim_sayisi": 3
    }
]
```

# Kullanıcı bazlı eğitim sonuçlarının içe aktarılması

Entegrasyonun son adımı olarak da kullanıcıların aldığı eğitimler ve bu eğitimlerdeki başarımları da aşağıdaki HTTP istemi yardımıyla Alfa sunucuya aktarılır. Söz konusu kayıtlar senkronize edildikten sonra Eğitimler menüsü altından incelenebilmektedir.

**URL**: `https://www.pardusegitim.org/api-documention.html`  
**Yöntem**: POST  
**Gönderilen parametreler**:  

| parametre | tür |
|--------|--------|
|  access_token   |  string   |

**Dönen cevap**:

| parametre | tür |
|--------|--------|
|  Eğitim sonuçları listesi   |  dizi   |

**Örnek HTTP istemi**:

```
curl --request POST \
--url 'https://www.pardusegitim.org/apis/v1/apieducationreport?access_token=d30fbebef325dee67a3a6aad0daa784a6a919039856f4d2a1d322f1e71a890d748ee27b46d14a5c348431232016c5a556fcc40f3a4ea1a02181894a21242afa3&id=12' \
```

**Örnek cevap**:
```
[
    {
        "durumu": "Tamamlandı",
        "sure": "00:55:59",
        "user_id": 1,
        "email": "eposta@eposta.com",
        "full_name": "Ad Soyad",
        "sinav_durumu": "-",
        "sinav_puani": 0,
        "id": 12,
        "urun_adi": "Pardus İşletim Sistemi Kullanım Eğitimi"
    },
    {
        "durumu": "Başlanmadı",
        "sure": "00:00:00",
        "user_id": 1,
        "email": "eposta2@eposta.com",
        "full_name": "Ad Soyad2",
        "sinav_durumu": "-",
        "sinav_puani": 0,
        "id": 12,
        "urun_adi": "Pardus İşletim Sistemi Kullanım Eğitimi"
    }
]
```

[geri](./)
