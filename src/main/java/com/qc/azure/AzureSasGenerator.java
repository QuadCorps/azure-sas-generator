package com.qc.azure;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.net.URLEncoder.*;

public class AzureSasGenerator {

    public static void main(String... args){
        String namespace = "AZURE_NAMESPACE_FROM_PORTAL";
        String resourceUri = "https://" + namespace + ".servicebus.windows.net/";
        String keyName = "RootManageSharedAccessKey";
        String key = "AZURE_KEY_FROM_PORTAL";
        String sas = getSasToken(resourceUri, keyName, key);
        System.out.println(sas);
    }

    private static String getSasToken(String resourceUri, String keyName, String key)
    {
        long epoch = System.currentTimeMillis()/1000L;
        int week = 60*60*24*7;
        String expiry = Long.toString(epoch + week);

        String sasToken = null;
        try {
            String stringToSign = encode(resourceUri, "UTF-8") + "\n" + expiry;
            String signature = getHMAC256(key, stringToSign);
            sasToken = "SharedAccessSignature sr=" + encode(resourceUri, "UTF-8") +"&sig=" +
                    encode(signature, "UTF-8") + "&se=" + expiry + "&skn=" + keyName;
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return sasToken;
    }


    public static String getHMAC256(String key, String input) {
        Mac sha256_HMAC = null;
        String hash = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            Base64.Encoder encoder = Base64.getEncoder();

            hash = new String(encoder.encode(sha256_HMAC.doFinal(input.getBytes("UTF-8"))));

        } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | IllegalStateException e) {
            e.printStackTrace();
        }

        return hash;
    }
}
