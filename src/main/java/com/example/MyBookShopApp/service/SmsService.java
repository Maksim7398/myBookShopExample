package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.data.SmsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {


    private final SmsCodeRepository repository;
    @Autowired
    public SmsService(SmsCodeRepository repository) {
        this.repository = repository;
    }

   public String generateCode(){
       Random random = new Random();
       StringBuilder stringBuilder = new StringBuilder();
       while (stringBuilder.length() < 6){
           stringBuilder.append(random.nextInt(9));
       }
       stringBuilder.insert(3," ");
       return stringBuilder.toString();
   }

   public void savaNewCode(SmsCode smsCode){
        if (repository.findByCode(smsCode.getCode()) == null){
            repository.save(smsCode);
        }
    }

   public Boolean verifyCode(String code){
        SmsCode smsCode = repository.findByCode(code);
        return smsCode != null && !smsCode.isExpired();
   }
}
