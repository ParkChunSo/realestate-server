package com.example.demolastsecurity.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);
    public static final String MAGIC_KEY = "chunso_token";

    public static String createToken(User userDetails) {
        // 인증토크 유효시간.
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;
        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(userDetails.getUsername());
        tokenBuilder.append(":");
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));
        return tokenBuilder.toString();
    }

    public static String computeSignature(User userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(userDetails.getPassword());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public static String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    public static boolean validateToken(String authToken, User userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        if (expires < System.currentTimeMillis()) {
            logger.info(expires + " : 생성시간이 잘못됨...." + System.currentTimeMillis());
            return false;
        }
        return signature.equals(TokenUtils.computeSignature(userDetails, expires));
    }

}
