package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@Service
public class PaymentsService {

    @Value("${robokassa.merchant.login}")
    private String merchantLogin;

    @Value("${robokassa.pass.first.test}")
    private String passFirstTest;

    public String getPaymentUrl(List<Book> bookFromCookiesSlugs) throws NoSuchAlgorithmException {
        Double payments = bookFromCookiesSlugs.stream().mapToDouble(Book::discountPrice).sum();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId="5";//Только для тестирования, генерировать номера заказов более осмысленно
        md.update((merchantLogin+":"+payments.toString()+":"+invId+":"+passFirstTest).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + merchantLogin +
                "&InvId=" + invId
                + "&Culture=ru"+
                "&Encoding=utf-8"+
                "&OutSum=" + payments +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&IsTest=1";
    }
}
