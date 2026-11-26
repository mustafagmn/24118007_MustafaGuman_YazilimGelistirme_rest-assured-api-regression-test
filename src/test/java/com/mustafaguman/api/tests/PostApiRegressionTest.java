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
 * JSONPlaceholder /posts servisi icin otomatik API regresyon testleri.
 * Her testte: Status Code, Response Body ve Yanit Suresi (Response Time) kontrolleri yapilir.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("JSONPlaceholder Posts API Regresyon Test Paketi")
public class PostApiRegressionTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("GET /posts/1 - Tekil kayit getirme, body ve sure kontrolu")
    void shouldGetSinglePostSuccessfully() {
        given()
                // Ortak base URL ve JSON header ayarlarini kullan
                .spec(requestSpec)
        .when()
                // 1. GET istegi gonder
                .get("/posts/1")
        .then()
                // Hata durumunda detayli log yazdir
                .log().ifValidationFails()
                // 2. Status code kontrolu (200 OK)
                .statusCode(200)
                // 3. Response body icerisinde beklenen deger kontrolleri
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("body", not(emptyOrNullString()))
                // 4. Belirlenen sure (3000 ms) altinda cevap donuldugunun kontrolu
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Test
    @Order(2)
    @DisplayName("GET /posts - Tum kayitlari listeleme, eleman sayisi ve sure kontrolu")
    void shouldGetAllPostsListSuccessfully() {
        given()
                .spec(requestSpec)
        .when()
                // 1. Tum postlari ceken GET istegi
                .get("/posts")
        .then()
                .log().ifValidationFails()
                // 2. Status code kontrolu (200 OK)
                .statusCode(200)
                // 3. Response body kontrolu: Liste bos olmamali, 100 eleman donmeli ve ilk elemanin id'si 1 olmali
                .body("$", not(empty()))
                .body("size()", equalTo(100))
                .body("[0].id", equalTo(1))
                // 4. Cevap suresi kontrolu (< 3000 ms)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Test
    @Order(3)
    @DisplayName("POST /posts - JSON Request Body ile yeni kayit olusturma testi")
    void shouldCreateNewPostWithJsonBody() {
        // Gonderilecek JSON Request Body (POJO nesnesi olarak olusturulur)
        PostRequest yeniKayit = new PostRequest(
                "Yazilim Test Muhendisligi Odevi",
                "Rest Assured ile otomatik API regresyon testi ornegi.",
                1
        );

        given()
                .spec(requestSpec)
                // 1. JSON Request Body ekle
                .body(yeniKayit)
        .when()
                // 2. POST istegi gonder
                .post("/posts")
        .then()
                .log().ifValidationFails()
                // 3. Status code kontrolu (201 Created)
                .statusCode(201)
                // 4. Response body kontrolu: Gonderilen veriler dogru donmus mu ve yeni id olusmus mu?
                .body("title", equalTo(yeniKayit.getTitle()))
                .body("body", equalTo(yeniKayit.getBody()))
                .body("userId", equalTo(yeniKayit.getUserId()))
                .body("id", notNullValue())
                // 5. Cevap suresi kontrolu (< 3000 ms)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Test
    @Order(4)
    @DisplayName("GET /posts/99999 - Negatif Senaryo: Olmayan kayit icin 404 kontrolu")
    void shouldReturn404ForNonExistentPost() {
        given()
                .spec(requestSpec)
        .when()
                // 1. Var olmayan bir ID ile GET istegi gonder
                .get("/posts/99999")
        .then()
                .log().ifValidationFails()
                // 2. Status code kontrolu (404 Not Found)
                .statusCode(404)
                // 3. Response body'nin bos JSON nesnesi dondugunun kontrolu
                .body("isEmpty()", is(true))
                // 4. Cevap suresi kontrolu (< 3000 ms)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }
}
