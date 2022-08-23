package com.example.euriskocodechallenge.common

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.euriskocodechallenge.BuildConfig
import com.example.euriskocodechallenge.utils.ConnectivityLiveData
import java.security.AccessControlContext
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class UtilityFunctions {
    companion object {
        fun showtoast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        const val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
        const val salt = "QWlGNHNhMTJTQWZ2bGhpV3U="
        const val iv = "bVQzNFNhRkQ1Njc4UUFaWA=="

        fun encrypt(strToEncrypt: String): String {
            try {
                val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec = PBEKeySpec(
                    secretKey.toCharArray(),
                    Base64.decode(salt, Base64.DEFAULT),
                    10000,
                    256
                )
                val tmp = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
                return Base64.encodeToString(
                    cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
                    Base64.DEFAULT
                )
            } catch (e: Exception) {
                println("Error while encrypting: $e")
            }
            return ""
        }

        fun decrypt(strToDecrypt: String): String {
            try {

                val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec = PBEKeySpec(
                    secretKey.toCharArray(),
                    Base64.decode(salt, Base64.DEFAULT),
                    10000,
                    256
                )
                val tmp = factory.generateSecret(spec);
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
            } catch (e: Exception) {
                println("Error while decrypting: $e");
            }
            return ""
        }
        fun printLogs(tag:String, message: String){
            if(BuildConfig.DEBUG)
                Log.e(tag,message)
        }
    }


}