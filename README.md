# API Otomatik Regresyon Test Projesi (Rest Assured)

**Hazırlayan:** Mustafa Güman  
**Ders:** Sektörde Yazılım Test Mühendisliği  
**Konu:** Java, Maven, JUnit 5 ve Rest Assured kullanarak otomatik API regresyon testi ve Yapay Zeka Destekli Yazılım Test Mühendisliği sunumu.

---

## Kullanılan Teknolojiler

| Teknoloji | Versiyon | Kullanım Amacı |
|---|---|---|
| **Java (JDK)** | 17 | Programlama dili |
| **Apache Maven** | 3.x | Bağımlılık ve proje yönetimi |
| **JUnit 5** | 5.10.2 | Test çatısı (Test Framework) |
| **Rest Assured** | 5.4.0 | REST API istekleri ve doğrulamaları |
| **Jackson** | 2.17.1 | POJO → JSON serileştirme |
| **Hamcrest** | 2.2 | Okunabilir assertion ifadeleri |

---

## Proje Yapısı

```text
rest-assured-api-regression-test/
├── pom.xml                                                 # Maven bağımlılıkları ve yapılandırması
├── sunum/                                                  # Ders sunum dosyası (.pptx)
└── src/test/java/com/mustafaguman/api/
    ├── base/
    │   └── BaseTest.java                                   # Ortak Base URI, RequestSpecification ve süre sabiti
    ├── models/
    │   └── PostRequest.java                                # POST isteği için JSON Request Body (POJO)
    └── tests/
        └── PostApiRegressionTest.java                      # GET ve POST regresyon test senaryoları
```

---

## Test Edilen Servis ve Senaryolar

Testler, herkese açık örnek REST API olan **[JSONPlaceholder](https://jsonplaceholder.typicode.com)** servisi üzerinde koşulmaktadır.

| # | HTTP Metodu | Endpoint | Test Senaryosu | Kontroller |
|---|---|---|---|---|
| 1 | `GET` | `/posts/1` | Tekil kayıt getirme | Status `200`, body alanları (`id`, `userId`, `title`, `body`), süre `< 3000 ms` |
| 2 | `GET` | `/posts` | Tüm kayıtları listeleme | Status `200`, liste boş değil, 100 eleman, süre `< 3000 ms` |
| 3 | `POST` | `/posts` | JSON body ile yeni kayıt oluşturma | Status `201`, gönderilen JSON alanlarının doğrulanması, yeni `id` oluşumu, süre `< 3000 ms` |
| 4 | `GET` | `/posts/99999` | Negatif senaryo (olmayan kayıt) | Status `404`, boş response body, süre `< 3000 ms` |

---

## Testleri Çalıştırma

Proje kök dizininde aşağıdaki komutu çalıştırarak tüm regresyon testlerini koşabilirsiniz:

```bash
mvn test
```
