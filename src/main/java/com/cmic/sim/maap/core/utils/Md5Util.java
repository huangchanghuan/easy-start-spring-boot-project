package com.cmic.sim.maap.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author haiqiang
 * @date 2019/2/27 18:45
 * 功能描述：将文件转化为hash码
 */
public class Md5Util {

    private static MessageDigest digest = null;

    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对文件进行hash值的计算
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String makeHash(InputStream inputStream) throws IOException {
         byte[] buffer=new byte[1024];
         int length=-1;
         while((length=inputStream.read(buffer,0,1024))!=-1){
             digest.update(buffer,0,length);
         }
         inputStream.close();
         byte[] md5Bytes=digest.digest();
        BigInteger bigInteger=new BigInteger(1,md5Bytes);
        return bigInteger.toString();
    }

    public static void main(String[] args) throws Exception {
        //InputStream inputStream=new FileInputStream("E://adc.pdf");
        //String code=makeHash(inputStream);
    }
}
