package com.mustafaguman.api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

/**
 * Ortak Test Yapilandirma Sinifi (BaseTest)
 * -----------------------------------------
 * Kod tekrarini onlemek (DRY prensibi) icin tum test siniflarinin ortak kullandigi
 * servis adresi, JSON header ayarlari ve maksimum cevap suresi burada tanimlanir.
 */
public abstract class BaseTest {

    // Test ettigimiz ornek REST API servisinin ana adresi (Base URL)
    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // Bir API isteginin en fazla kac milisaniyede cevap donmesini bekliyoruz (3000 ms = 3 saniye)
    protected static final long MAX_RESPONSE_TIME_MS = 3000L;

    // Tum GET ve POST isteklerinde ortak kullanilacak istek sablonu (Base URI + JSON header'lari)
    protected static RequestSpecification requestSpec;

    /**
     * @BeforeAll: Test sinifi baslamadan once SADECE 1 KEZ calisir ve ortak ayarlari hazirlar.
     */
    @BeforeAll
    static void setup() {
        // Turkce Windows sistemlerde 404 gibi 4xx yanitlarinda olusan I/i karakter donusumu sorununu onler
        Locale.setDefault(Locale.ENGLISH);

        // Rest Assured'a varsayilan servis adresini bildiriyoruz
        RestAssured.baseURI = BASE_URL;

        // Her testte tekrar tekrar yazmamak icin ortak RequestSpecification olusturuyoruz:
        // - Base URI: https://jsonplaceholder.typicode.com
        // - Content-Type: application/json (JSON gonderiyoruz)
        // - Accept: application/json (JSON cevap bekliyoruz)
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}
