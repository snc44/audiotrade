package ru.sfedu.musicapp.models;

public enum PayType {
    VISA("Visa"),MASTERCARD("MasterCard"),MAESTRO("Maestro"),QIWI("Qiwi"),WEBMONEY("Webmoney"),YANDEXMONEY("YandexMoney");
    private String paytype;

    PayType(String paytype){
        this.paytype = paytype;
    }

    public String getTitle(){
        return this.paytype;
    }

}
