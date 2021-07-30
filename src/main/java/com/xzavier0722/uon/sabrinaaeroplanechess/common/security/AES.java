package com.xzavier0722.uon.sabrinaaeroplanechess.common.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {

    private final SecretKeySpec key;
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public AES(String keyBase64Str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this(Base64.getDecoder().decode(keyBase64Str));
    }

    public AES(byte[] keyByte) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator.getInstance("AES").init(256);
        this.key = new SecretKeySpec(keyByte, "AES");

        encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.key);

        decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
    }

    public String encrypt(String msg) throws IllegalBlockSizeException, BadPaddingException {
        return Base64.getEncoder().encodeToString(encryptCipher.doFinal(msg.getBytes()));
    }

    public String decrypt(String ciphertextBase64Str) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decryptCipher.doFinal(Base64.getDecoder().decode(ciphertextBase64Str)));
    }

}
