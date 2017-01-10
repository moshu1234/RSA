package com.example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MyClass {
    public static void main(String[] args) {
//        System.out.print("hh");
        AESEnc("a");
    }
    public static byte[] getFromAssets(String fileName){
        try{

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                    "/Users/liut1/Documents/Projects/Android/documents/RSAenc/test.txt"));
            byte[] bys = new byte[bis.available()];
            int len = 0;
            while ((len = bis.read(bys)) != -1) {
                System.out.print(new String(bys, 0, len));
            }

            // 释放资源
            bis.close();
            return bys;

        }catch(Exception e){

            e.printStackTrace();

        }
        return null;
    }
    public static void writeFile(byte[] in){
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream("/Users/liut1/Documents/Projects/Android/documents/RSAenc/test.txt.javaenc");

            fos.write(in,0,in.length);

        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            try{
                fos.close();
            }
            catch(Exception e2){
                System.out.println(e2);
            }
        }
    }
    static void AESEnc(String src){
        byte[] text = getFromAssets("/Users/liut1/Documents/Projects/Android/documents/RSAenc/test.txt");
//        System.out.print("------------text="+text);
        byte[] ss = AESUtil.encrypt(text, "123456");
        System.out.print("------------加密后：" + AESUtil.parseByte2HexStr(ss));
        writeFile(ss);
//        String ed = AESUtil.decrypt(ss, "123456");
//        System.out.print("------------:解密后：" + ed);
    }
}

