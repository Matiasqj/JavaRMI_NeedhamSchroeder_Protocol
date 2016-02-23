/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author Matias Quinteros
 * Algoritmo obtenido de : http://examples.javacodegeeks.com/core-java/crypto/encrypt-decrypt-with-des-using-pass-phrase/
 */
public class DES {

    private static Cipher ecipher;
    private static Cipher dcipher;
    private static final int iterationCount = 10;
    private static byte[] salt = {
        (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2,
        (byte) 0x44, (byte) 0x21, (byte) 0xC3, (byte) 0xC3
    };

    public String encriptado(String llave, String texto_plano) {
 // create a user-chosen password that can be used with password-based encryption (PBE)
        // provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers

        try {
            KeySpec keySpec = new PBEKeySpec(llave.toCharArray(), salt, iterationCount);

            // create a secret (symmetric) key using PBE with MD5 and DES
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            // construct a parameter set for password-based encryption as defined in the PKCS #5 standard
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            // initialize the ciphers with the given key
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            String encrypted = encrypt(texto_plano);
            return encrypted;
        } catch (InvalidAlgorithmParameterException e) {

            System.out.println("Invalid Alogorithm Parameter:" + e.getMessage());

            return null;

        } catch (InvalidKeySpecException e) {

            System.out.println("Invalid Key Spec:" + e.getMessage());

            return null;

        } catch (NoSuchAlgorithmException e) {

            System.out.println("No Such Algorithm:" + e.getMessage());

            return null;

        } catch (NoSuchPaddingException e) {

            System.out.println("No Such Padding:" + e.getMessage());

            return null;

        } catch (InvalidKeyException e) {

            System.out.println("Invalid Key:" + e.getMessage());

            return null;

        }

    }

    public String desencriptado(String llave, String texto_encriptado) {
        try {

            // create a user-chosen password that can be used with password-based encryption (PBE)
            // provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers
            KeySpec keySpec = new PBEKeySpec(llave.toCharArray(), salt, iterationCount);
            // create a secret (symmetric) key using PBE with MD5 and DES
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            // construct a parameter set for password-based encryption as defined in the PKCS #5 standard
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            dcipher = Cipher.getInstance(key.getAlgorithm());
            // initialize the ciphers with the given key
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            String decrypted = decrypt(texto_encriptado);
            return decrypted;

        } catch (InvalidAlgorithmParameterException e) {

            System.out.println("Invalid Alogorithm Parameter:" + e.getMessage());

            return null;

        } catch (InvalidKeySpecException e) {

            System.out.println("Invalid Key Spec:" + e.getMessage());

            return null;

        } catch (NoSuchAlgorithmException e) {

            System.out.println("No Such Algorithm:" + e.getMessage());

            return null;

        } catch (NoSuchPaddingException e) {

            System.out.println("No Such Padding:" + e.getMessage());

            return null;

        } catch (InvalidKeyException e) {

            System.out.println("Invalid Key:" + e.getMessage());

            return null;

        }
    }

    public static String encrypt(String str) {

        try {

            // encode the string into a sequence of bytes using the named charset
            // storing the result into a new byte array.
            byte[] utf8 = str.getBytes("UTF8");

            byte[] enc = ecipher.doFinal(utf8);

// encode to base64
            enc = BASE64EncoderStream.encode(enc);

            return new String(enc);
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
        }

        return null;

    }

    public static String decrypt(String str) {

        try {

            // decode with base64 to get bytes
            byte[] dec = BASE64DecoderStream.decode(str.getBytes());

            byte[] utf8 = dcipher.doFinal(dec);

// create new string based on the specified charset
            return new String(utf8, "UTF8");

        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
        }

        return null;

    }

}
