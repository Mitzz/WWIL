package com.enercon.global.utils;

import com.sun.crypto.provider.SunJCE;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DataEncrypt {
    public DataEncrypt() {
    }

    private static Cipher getCipher(String s, int i) throws Exception {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("PBEWithMD5AndDES");
        } catch (Exception exception) {
            SunJCE sunjce = new SunJCE();
            Security.addProvider(sunjce);
        }
        Cipher cipher1;
        byte abyte0[] = { -41, -89, -41, -9, -41, -9, -57, -9 };
        byte byte0 = 26;
        if (s == null)
            s = m_defaultPassPhrase;
        PBEKeySpec pbekeyspec = new PBEKeySpec(s.toCharArray());
        javax.crypto.SecretKey secretkey = 
            SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(pbekeyspec);
        cipher1 = Cipher.getInstance("PBEWithMD5AndDES");
        PBEParameterSpec pbeparameterspec = 
            new PBEParameterSpec(abyte0, byte0);
        cipher1.init(i, secretkey, pbeparameterspec);
        return cipher1;

    }

    public static String encryptData(String s, String s1) throws Exception {
        Cipher cipher = getCipher(s1, 1);
        byte abyte1[];
        byte abyte0[] = s.getBytes();
        abyte1 = cipher.doFinal(abyte0);
        return (new BASE64Encoder()).encode(abyte1);

    }

    public static String decryptData(String s, String s1) throws Exception {
        Cipher cipher = getCipher(s1, 2);
        byte abyte1[];
        byte abyte0[] = (new BASE64Decoder()).decodeBuffer(s);
        abyte1 = cipher.doFinal(abyte0);
        return new String(abyte1);

    }


    private static final String m_algorithm = "PBEWithMD5AndDES";
    private static String m_defaultPassPhrase = "78g>l65789";

}
