package com.mustafaguman.api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

/**
 * Tum API regresyon testleri icin ortak konfigurasyon sinifi.
 * Base URI, ortak header bilgileri ve maksimum kabul edilebilir yanit suresi burada tanimlanir.
 */
public abstract class BaseTest {

    // Test edilen servisin temel adresi (Base URL)
    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // Maksimum kabul edilebilir cevap suresi (milisaniye cinsinden: 3000 ms = 3 saniye)
    protected static final long MAX_RESPONSE_TIME_MS = 3000L;

    // Tum isteklerde ortak kullanilacak RequestSpecification
    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setup() {
        // Turkce Windows sistemlerde Rest Assured 4xx yanitlarinda olusan I/i locale sorununu onler
        Locale.setDefault(Locale.ENGLISH);

        RestAssured.baseURI = BASE_URL;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}
