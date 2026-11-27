# API Otomatik Regresyon Test Projesi (Rest Assured)

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-3.9-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=flat-square&logo=junit5&logoColor=white)
![Rest Assured](https://img.shields.io/badge/Rest%20Assured-5.4.0-007A4D?style=flat-square)
![Tests](https://img.shields.io/badge/Tests-4%2F4%20Passed-16A34A?style=flat-square)

**Sektörde Yazılım Test Mühendisliği** dersi kapsamında **Java, Apache Maven, JUnit 5 ve Rest Assured** kullanılarak hazırlanmış otomatik API regresyon testi projesi ve **Yapay Zeka Destekli Yazılım Test Mühendisliği** sunumu.

---

## Proje Özeti

Bu proje, örnek REST API servisi olan **[JSONPlaceholder](https://jsonplaceholder.typicode.com)** üzerinde **GET** ve **POST** (JSON Request Body) isteklerini otomatik olarak test eder. Her test senaryosunda aşağıdaki üç temel doğrulama kontrolü uygulanır:

1. **HTTP Status Code Doğrulaması** (`200 OK`, `201 Created`, `404 Not Found`)
2. **Response Body Değer Kontrolleri** (JSON alan eşleşmeleri, boş olmama, liste boyutu, yeni ID üretimi)
3. **Yanıt Süresi (Performance SLA) Kontrolü** (`< 3000 ms`)

---

## Uygulanan Test Mühendisliği Pratikleri

- **Katmanlı Test Mimarisi (`BaseTest`):** Base URI, ortak JSON header ayarları (`RequestSpecification`) ve maksimum yanıt süresi sabiti tek merkezden yönetilir (DRY prensibi).
- **POJO Tabanlı Request Body (`PostRequest`):** POST isteğinde ham string yerine Jackson ile otomatik serileştirilen tip güvenli Java nesnesi kullanılır.
- **BDD Sözdizimi (`Given – When – Then`):** Okunabilir ve sürdürülebilir test akışı sağlanır.
- **Pozitif ve Negatif Senaryo Kapsamı:** Başarılı GET/POST akışlarının yanında var olmayan kaynak için `404 Not Found` negatif regresyon testi de yer alır.
- **Türkçe Sistem Locale Koruması:** Türkçe Windows ortamlarında 4xx yanıtlarında oluşabilecek `I/ı` karakter dönüşümü hatasına karşı `Locale.setDefault(Locale.ENGLISH)` önlemi eklenmiştir.
- **Canlı Request / Response Loglama:** Test koşumu sırasında gönderilen JSON girdi ve dönen JSON çıktı terminalde okunabilir biçimde görüntülenir.

---

## Kullanılan Teknolojiler

| Teknoloji | Versiyon | Kullanım Amacı |
|---|---|---|
| **Java (JDK)** | 17+ | Programlama dili |
| **Apache Maven** | 3.9.x | Bağımlılık ve build yönetimi |
| **JUnit 5** | 5.10.2 | Test çatısı (`@BeforeAll`, `@Test`, `@Order`, `@DisplayName`) |
| **Rest Assured** | 5.4.0 | REST API istekleri ve BDD doğrulamaları |
| **Jackson Databind** | 2.17.1 | POJO → JSON otomatik serileştirme |
| **Hamcrest** | 2.2 | Okunabilir assertion matcher ifadeleri |

---

## Proje Klasör Yapısı

```text
rest-assured-api-regression-test/
├── pom.xml                                                                  # Maven bağımlılıkları ve surefire yapılandırması
├── README.md                                                                # Proje dokümantasyonu
├── sunum/
│   └── Yapay_Zeka_Destekli_Yazilim_Test_Muhendisligi_Mustafa_Guman.pptx     # Ders sunum dosyası (11 slayt)
└── src/test/java/com/mustafaguman/api/
    ├── base/
    │   └── BaseTest.java                                                    # Ortak Base URI, RequestSpecification ve süre limiti
    ├── models/
    │   └── PostRequest.java                                                 # POST isteği için JSON Request Body modeli (POJO)
    └── tests/
        └── PostApiRegressionTest.java                                       # GET & POST otomatik regresyon test senaryoları
```

---

## Test Senaryoları ve Doğrulama Matrisi

| # | Metot | Endpoint | Senaryo | Status Code | Response Body Kontrolü | Süre |
|---|---|---|---|---|---|---|
| 1 | `GET` | `/posts/1` | Tekil kayıt getirme | `200 OK` | `id=1`, `userId=1`, `title` ve `body` boş değil | `< 3000 ms` |
| 2 | `GET` | `/posts` | Tüm kayıtları listeleme | `200 OK` | Liste boş değil, `100` kayıt, ilk `id=1` | `< 3000 ms` |
| 3 | `POST` | `/posts` | JSON body ile yeni kayıt oluşturma | `201 Created` | Gönderilen `title`, `body`, `userId` eşleşir + yeni `id` | `< 3000 ms` |
| 4 | `GET` | `/posts/99999` | Negatif senaryo (olmayan kayıt) | `404 Not Found` | Boş JSON nesnesi (`{}`) | `< 3000 ms` |

---

## Kurulum ve Testleri Çalıştırma

### Gereksinimler
- Java JDK 17 veya üzeri
- Apache Maven 3.x

### Çalıştırma
```bash
git clone https://github.com/mustafagmn/rest-assured-api-regression-test.git
cd rest-assured-api-regression-test
mvn test
```

### Örnek Test Çıktısı
```text
=== [TEST 3] POST /posts - GONDERILEN GIRDI (REQUEST BODY) & DONEN CIKTI (RESPONSE BODY) ===
Body:
{
    "title": "Mustafa Guman - Yazilim Test Muhendisligi",
    "body": "Rest Assured ile otomatik API regresyon testi ornegi.",
    "userId": 1
}
{
    "title": "Mustafa Guman - Yazilim Test Muhendisligi",
    "body": "Rest Assured ile otomatik API regresyon testi ornegi.",
    "userId": 1,
    "id": 101
}
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Sunum Dosyası

**Yapay Zeka Destekli Yazılım Test Mühendisliği** sunumuna repo içerisinden doğrudan ulaşabilirsiniz:

- [Yapay_Zeka_Destekli_Yazilim_Test_Muhendisligi_Mustafa_Guman.pptx](sunum/Yapay_Zeka_Destekli_Yazilim_Test_Muhendisligi_Mustafa_Guman.pptx)

---

## Hazırlayan

**Mustafa Güman**  
Sektörde Yazılım Test Mühendisliği Proje Ödevi
