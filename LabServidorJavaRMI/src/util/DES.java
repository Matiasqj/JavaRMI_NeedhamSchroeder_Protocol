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
 * Clase DES , tiene metodos para desencriptar y encriptar con DES y MD5
 */
public class DES {

    private static Cipher ecipher;
    private static Cipher dcipher;
    private static final int iteraciones = 10;//parametro de entrada para generar llave
    private static byte[] salt = { //arreglo para generar llave
        (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2,
        (byte) 0x44, (byte) 0x21, (byte) 0xC3, (byte) 0xC3
    };
    /**
     *** encriptado: con la clave de entrada utiliza DES para cifrar un texto plano
     * @param llave
     * @param texto_plano
     * @return String con texto_plano encriptado o valor null si hay error
     */
    public String encriptado(String llave, String texto_plano) {
        try {
            //Primero se crean las especificaciones para la clave a utilizar
            KeySpec keySpec = new PBEKeySpec(llave.toCharArray(), salt, iteraciones);

            // se crea una clave secreta PBE con MD5 y DES
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            // Se utilizan los parametros del algoritmo segun estandar PKCS #5 
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iteraciones);
            //se inicializa el cifrador
            ecipher = Cipher.getInstance(key.getAlgorithm());
          
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            //luego de inicializado el cifrador, entonces se encripta el texto plano
            String encrypted = encrypt(texto_plano);
            return encrypted;
        } catch (InvalidAlgorithmParameterException e) {//Para cualquier tipo de error, devuelve valor : null

            System.out.println("Parametro invalido para del algoritmo: " + e.getMessage());

            return null;

        } catch (InvalidKeySpecException e) {

            System.out.println("Especificación invalida para la clave: " + e.getMessage());

            return null;

        } catch (NoSuchAlgorithmException e) {

            System.out.println("Error(No such algorithm): " + e.getMessage());

            return null;

        } catch (NoSuchPaddingException e) {

            System.out.println("Error(No Such Padding): " + e.getMessage());

            return null;

        } catch (InvalidKeyException e) {

            System.out.println("Clave invalida: " + e.getMessage());

            return null;

        }

    }
    /***
     * desencriptado: Toma la clave y el texto encriptado y devuelve un String de texto plano
     * @param llave
     * @param texto_encriptado
     * @return String de texto desencriptado o valor null si hay error
     */
    public String desencriptado(String llave, String texto_encriptado) {
        try {

            //Primero se crean las especificaciones para la clave a utilizar
            KeySpec keySpec = new PBEKeySpec(llave.toCharArray(), salt, iteraciones);
             // se crea una clave secreta PBE con MD5 y DES
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

           // Se utilizan los parametros del algoritmo segun estandar PKCS #5 
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iteraciones);
            dcipher = Cipher.getInstance(key.getAlgorithm());
            //se inicializa el cifrador
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            //se desencripta el texto cifrado
            String decrypted = decrypt(texto_encriptado);
            return decrypted;

        } catch (InvalidAlgorithmParameterException e) {//si hay algun error devuelve nulo

            System.out.println("Parametro invalido para del algoritmo: " + e.getMessage());

            return null;

        } catch (InvalidKeySpecException e) {

            System.out.println("Especificación invalida para la clave: " + e.getMessage());

            return null;

        } catch (NoSuchAlgorithmException e) {

            System.out.println("Error (No Such Algorithm) : " + e.getMessage());

            return null;

        } catch (NoSuchPaddingException e) {

            System.out.println("Error (No Such Padding): " + e.getMessage());

            return null;

        } catch (InvalidKeyException e) {

            System.out.println("Clave invalida: " + e.getMessage());

            return null;

        }
    }
    /**
     * encrypt se encarga de cifrar con DES y parametros definidos para ecipher
     * @param str
     * @return String en base64 y texto cifrado, caso contrario retorna null(error)
     */
    public static String encrypt(String str) {

        try {
            //cifra el string en la secuencia de bytes de 
            //guarda en nuevo byte con codficacion utf8
            byte[] utf8 = str.getBytes("UTF8");
            //realiza el cifrado definido para ecipher
            byte[] enc = ecipher.doFinal(utf8);

            //lo cifra en base64
            enc = BASE64EncoderStream.encode(enc);
            //retorna el string    
            return new String(enc);
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            //si hay error retorna nulo
            return null;
        }
    }
    /**
     * decryt: toma un texto encriptado y lo transforma en un string de texto plano utilizando parametros definidos en ecipher
     * @param str
     * @return String de texto plano
     */
    public static String decrypt(String str) {

        try {

            // se decifra con base 64
            byte[] dec = BASE64DecoderStream.decode(str.getBytes());
            //utiliza el descifrado con parametros ya definidos
            byte[] utf8 = dcipher.doFinal(dec);

            //retorna el string desencriptado en base utf8
            return new String(utf8, "UTF8");

        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
          return null; //si hay error retorna nulo
        }
    }

}
