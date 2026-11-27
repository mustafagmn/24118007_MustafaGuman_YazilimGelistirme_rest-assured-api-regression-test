package com.mustafaguman.api.models;

/**
 * POST /posts Istegi Icin Veri Modeli (POJO - Plain Old Java Object)
 * ------------------------------------------------------------------
 * POST isteginde gonderilecek JSON Request Body verisini duz metin (String)
 * olarak yazmak yerine, best practice olarak Java nesnesi seklinde tutuyoruz.
 * Jackson kutuphanesi bu nesneyi otomatik olarak JSON formatina donusturur:
 * { "title": "...", "body": "...", "userId": 1 }
 */
public class PostRequest {

    // Olusturulacak kaydin basligi
    private String title;

    // Olusturulacak kaydin icerik metni
    private String body;

    // Kaydi olusturan kullanicinin kimlik numarasi (ID)
    private int userId;

    // Jackson serilestirme/deserilestirme islemi icin bos yapici metot (default constructor)
    public PostRequest() {
    }

    // Test icerisinde tek satirda yeni JSON body nesnesi olusturmak icin parametreli yapici metot
    public PostRequest(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    // Getter ve Setter metotlari: Jackson JSON uretirken ve testlerde deger karsilastirirken kullanilir
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
