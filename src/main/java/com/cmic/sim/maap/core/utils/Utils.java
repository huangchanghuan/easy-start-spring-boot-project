package com.cmic.sim.maap.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ClassName Utils
 * @date 2016年7月7日 - 下午10:29:20
 * @version 1.0
 */
public class Utils {
    private static final String BASECODE = "0123456789";
    private static final char[] LEGALCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();
    static String regEx = "[\u4e00-\u9fa5]";
    static Pattern pat = Pattern.compile(regEx);

    /**
     *
     * @功能说明:在日志文件中，打印异常堆栈
     * @param e
     * @return:String
     */
    public static String logExceptionStack(Exception e) {
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }

    /**
     *
     * @Description 获取时间
     * @param date
     * @return
     */
    public static String getTimes(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sf.format(date);
        return dateStr;
    }

    /**
     * 返回随机字符
     *
     * @param length
     *            字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASECODE.length());
            sb.append(BASECODE.charAt(number));
        }
        return sb.toString();
    }

    /**
     *
     * @Description 转为unicode
     * @param value
     * @return
     */
    public static String encodeUnicode(String value) {
        try {
            String unicode = gbEncoding(value);
            return unicode;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 十六进制字符串转换成byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0;
        int n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    /**
     *
     * Description： 将字符串转换成UCS2编码字节数组
     *
     * @param src
     *
     */
    public static byte[] str2UCS2(String src) {
        if (null != src) {
            if (src.length() > 0) {
                try {
                    return src.getBytes("UTF-16BE");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     *
     * @Description str2ASCII
     * @param src
     * @return
     */
    public static byte[] str2ASCII(String src) {
        if (null != src) {
            if (src.length() > 0) {
                try {
                    return src.getBytes("ASCII");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    /**
     *
     * @Description 判断字符串中是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     *
     * @Description tag长度,不够2位前面补0
     * @param len
     * @return
     */
    public static String pathZero(String len) {
        if (len.length() == 1) {
            len = "0" + len;
        }
        return len;
    }

    /**
     *
     * @Description base64Decode
     * @param s
     * @return
     */
    public static byte[] base64Decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    /**
     * data[]进行编码
     *
     * @param data
     * @return
     */
    public static String base64Encoder(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(LEGALCHARS[(d >> 18) & 63]);
            buf.append(LEGALCHARS[(d >> 12) & 63]);
            buf.append(LEGALCHARS[(d >> 6) & 63]);
            buf.append(LEGALCHARS[d & 63]);

            i += 3;

            if (n++ >= 14) {
                //不需要空格
                n = 0;
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

            buf.append(LEGALCHARS[(d >> 18) & 63]);
            buf.append(LEGALCHARS[(d >> 12) & 63]);
            buf.append(LEGALCHARS[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(LEGALCHARS[(d >> 18) & 63]);
            buf.append(LEGALCHARS[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     *
     * @Description gbEncoding
     * @param gbString
     * @return
     */
    private static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + hexB;
        }
        return unicodeBytes;
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();
        while (true) {
            while (i < len && s.charAt(i) <= ' ') {
                i++;
            }
            if (i == len) {
                break;
            }
            int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6) + (decode(s.charAt(i + 3)));
            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=') {
                break;
            }
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=') {
                break;
            }
            os.write(tri & 255);
            i += 4;
        }
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return ((int) c) - 65;
        } else if (c >= 'a' && c <= 'z') {
            return ((int) c) - 97 + 26;
        } else if (c >= '0' && c <= '9') {
            return ((int) c) - 48 + 26 + 26;
        } else {
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
        }
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }
}
