package com.nextgenz.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // Simulating OTP storage (In production, use Redis or Database with expiration)
    private Map<String, String> otpStorage = new HashMap<>();

    public String generateOtp(String phoneNumber) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String otpString = String.valueOf(otp);
        otpStorage.put(phoneNumber, otpString);
        return otpString;
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        if (otpStorage.containsKey(phoneNumber)) {
            String storedOtp = otpStorage.get(phoneNumber);
            if (storedOtp.equals(otp)) {
                otpStorage.remove(phoneNumber); // Clear OTP after successful validation
                return true;
            }
        }
        return false;
    }
}
