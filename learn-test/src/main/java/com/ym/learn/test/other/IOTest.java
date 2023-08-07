package com.ym.learn.test.other;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/24 20:35
 * @Desc:
 */
public class IOTest {
    /**
     * 注意文件路径，不加这个前缀，除非将文件放在module根目录下
     */
    private static final String FILE_PREFIX = "src/main/java/com/ym/learn/test/other/";

    @Test
    public void testIO() throws IOException {
        long startTime = System.currentTimeMillis();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(FILE_PREFIX+"test.txt");
            // 没有文件时，创建
            File newFile = new File(FILE_PREFIX+"tmp.txt");
            if (!newFile.exists()){
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            byte[] bytes = new byte[1024*1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("运行时间："+costTime);
    }

    @Test
    public void testChannel() throws IOException {
        long startTime = System.currentTimeMillis();
        String inputFilePath = FILE_PREFIX+"test.txt";
        String outputFilePath = FILE_PREFIX+"tmp.txt";
        File file = new File(outputFilePath);
        if(!file.exists()){
            file.createNewFile();
        }
        // 创建随机存取文件对象
        RandomAccessFile read = new RandomAccessFile(inputFilePath, "rw");
        RandomAccessFile write = new RandomAccessFile(outputFilePath, "rw");
        // 获取文件通道
        FileChannel readChannel = read.getChannel();
        FileChannel writeChannel = write.getChannel();
        // 使用ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (readChannel.read(byteBuffer) > 0){
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        // 关闭通道
        read.close();
        write.close();

        // time
        long endTime = System.currentTimeMillis();
        System.out.println("时间："+(endTime-startTime));
    }
}
