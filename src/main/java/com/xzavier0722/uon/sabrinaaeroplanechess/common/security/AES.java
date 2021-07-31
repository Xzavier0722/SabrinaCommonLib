package com.xzavier0722.uon.sabrinaaeroplanechess.common.security;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {

    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public AES(String keyBase64Str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this(Utils.debase64(keyBase64Str));
    }

    public AES(byte[] keyByte) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator.getInstance("AES").init(256);
        SecretKeySpec key = new SecretKeySpec(keyByte, "AES");

        encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String msg) throws IllegalBlockSizeException, BadPaddingException {
        return Utils.base64(encryptCipher.doFinal(msg.getBytes()));
    }

    public String decrypt(String ciphertextBase64Str) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decryptCipher.doFinal(Utils.debase64(ciphertextBase64Str)));
    }

}
