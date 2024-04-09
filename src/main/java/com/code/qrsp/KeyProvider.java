package com.code.qrsp;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class KeyProvider {

        private static final String salt = "your_salt_value"; // Replace with your actual salt value

        public static String generateVerificationKey(String str) throws NoSuchAlgorithmException,
                InvalidKeySpecException {
            int iterations = 10000;
            int keyLength = 512;

            char[] strChars = str.toCharArray();
            byte[] saltBytes = salt.getBytes();

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(strChars, saltBytes, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] hashedBytes = key.getEncoded();

            return Hex.encodeHexString(hashedBytes);
        }
    }

