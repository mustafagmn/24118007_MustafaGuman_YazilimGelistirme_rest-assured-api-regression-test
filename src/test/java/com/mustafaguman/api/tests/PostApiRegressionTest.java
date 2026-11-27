package com.mustafaguman.api.tests;

import com.mustafaguman.api.base.BaseTest;
import com.mustafaguman.api.models.PostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * JSONPlaceholder /posts Servisi Otomatik API Regresyon Testleri
 * --------------------------------------------------------------
 * Bu sinif, Rest Assured'un Given - When - Then (BDD) yapisini kullanarak
 * GET ve POST isteklerini test eder.
 *
 * Her testte 3 temel kontrol yapilir:
 *   1. Status Code kontrolu (200, 201, 404)
 *   2. Response Body icerisinde beklenen degerlerin kontrolu
 *   3. Belirlenen sure (3000 ms) altinda cevap donuldugunun kontrolu
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Testlerin @Order sirasina gore calismasini saglar
@DisplayName("JSONPlaceholder Posts API Regresyon Test Paketi")
public class PostApiRegressionTest extends BaseTest {

    // =========================================================================
    // TEST 1: TEKIL KAYIT GETIRME (GET /posts/1)
    // =========================================================================
    @Test
    @Order(1)
    @DisplayName("GET /posts/1 - Tekil kayit getirme, body ve sure kontrolu")
    void shouldGetSinglePostSuccessfully() {
        given()
                // ON KOSUL: BaseTest'te tanimlanan ortak Base URL ve JSON header ayarlarini kullan
                .spec(requestSpec)
        .when()
                // AKSIYON: 1 numarali kaydi cekmek icin GET istegi gonder
                .get("/posts/1")
        .then()
                // Sadece bir dogrulama basarisiz olursa detayli hata logu yazdir
                .log().ifValidationFails()
                // 1. STATUS CODE KONTROLU: Istek basarili sekilde 200 OK donmeli
                .statusCode(200)
                // 2. RESPONSE BODY KONTROLU: Donen JSON icindeki alanlari dogrula
                .body("id", equalTo(1))                      // id degeri 1 olmali
                .body("userId", equalTo(1))                  // userId degeri 1 olmali
                .body("title", not(emptyOrNullString()))     // title alani bos veya null olmamali
                .body("body", not(emptyOrNullString()))      // body alani bos veya null olmamali
                // 3. SURE KONTROLU: Cevap 3000 ms (3 saniye) altinda donmeli
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    // =========================================================================
    // TEST 2: TUM KAYITLARI LISTELEME (GET /posts)
    // =========================================================================
    @Test
    @Order(2)
    @DisplayName("GET /posts - Tum kayitlari listeleme, eleman sayisi ve sure kontrolu")
    void shouldGetAllPostsListSuccessfully() {
        given()
                // ON KOSUL: Ortak istek ayarlarini uygula
                .spec(requestSpec)
        .when()
                // AKSIYON: Tum post listesini cekmek icin GET istegi gonder
                .get("/posts")
        .then()
                .log().ifValidationFails()
                // 1. STATUS CODE KONTROLU: 200 OK donmeli
                .statusCode(200)
                // 2. RESPONSE BODY KONTROLU: Donen JSON dizisini (liste) dogrula
                .body("$", not(empty()))                     // Liste bos olmamali
                .body("size()", equalTo(100))                // Toplam 100 adet kayit donmeli
                .body("[0].id", equalTo(1))                  // Ilk kaydin id degeri 1 olmali
                // 3. SURE KONTROLU: Cevap 3000 ms altinda donmeli
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    // =========================================================================
    // TEST 3: JSON REQUEST BODY ILE YENI KAYIT OLUSTURMA (POST /posts)
    // =========================================================================
    @Test
    @Order(3)
    @DisplayName("POST /posts - JSON Request Body ile yeni kayit olusturma testi")
    void shouldCreateNewPostWithJsonBody() {
        // Request Body olarak gonderilecek veriyi POJO nesnesi seklinde hazirliyoruz
        // (Jackson bu nesneyi otomatik olarak JSON formatina cevirip istege ekler)
        PostRequest yeniKayit = new PostRequest(
                "Yazilim Test Muhendisligi Odevi",
                "Rest Assured ile otomatik API regresyon testi ornegi.",
                1
        );

        given()
                // ON KOSUL: Ortak ayarlar + JSON Request Body ekleniyor
                .spec(requestSpec)
                .body(yeniKayit)
        .when()
                // AKSIYON: Yeni kayit olusturmak icin /posts adresine POST istegi gonder
                .post("/posts")
        .then()
                .log().ifValidationFails()
                // 1. STATUS CODE KONTROLU: Yeni kayit olusturuldugu icin 201 Created donmeli
                .statusCode(201)
                // 2. RESPONSE BODY KONTROLU: Gonderdigimiz veriler cevapta aynen donmus mu?
                .body("title", equalTo(yeniKayit.getTitle()))     // Baslik eslesmeli
                .body("body", equalTo(yeniKayit.getBody()))       // Icerik eslesmeli
                .body("userId", equalTo(yeniKayit.getUserId()))   // Kullanici ID eslesmeli
                .body("id", notNullValue())                       // Servis yeni bir id uretmis olmali
                // 3. SURE KONTROLU: Cevap 3000 ms altinda donmeli
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    // =========================================================================
    // TEST 4: NEGATIF SENARYO - OLMAYAN KAYIT SORGULAMA (GET /posts/99999)
    // =========================================================================
    @Test
    @Order(4)
    @DisplayName("GET /posts/99999 - Negatif Senaryo: Olmayan kayit icin 404 kontrolu")
    void shouldReturn404ForNonExistentPost() {
        given()
                // ON KOSUL: Ortak istek ayarlarini uygula
                .spec(requestSpec)
        .when()
                // AKSIYON: Sistemde var olmayan bir ID (99999) ile GET istegi gonder
                .get("/posts/99999")
        .then()
                .log().ifValidationFails()
                // 1. STATUS CODE KONTROLU: Kayit bulunamadigi icin 404 Not Found donmeli
                .statusCode(404)
                // 2. RESPONSE BODY KONTROLU: Donen JSON nesnesi bos ({}) olmali
                .body("isEmpty()", is(true))
                // 3. SURE KONTROLU: Hata cevabi da 3000 ms altinda donmeli
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }
}
