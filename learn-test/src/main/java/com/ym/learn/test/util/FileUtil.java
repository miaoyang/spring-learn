package com.ym.learn.test.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/28 23:15
 * @Desc: 文件操作相关类
 */
public class FileUtil {
    /**
     * 文件默认分块大小 5M
     */
    public static final long FILE_BLOCK_SIZE = 5*1024*1024;
    /**
     * 临时分块文件的后缀
     */
    public static final String CHUNK_FILE_EXTENSION = ".temp";
    /**
     * 过滤掉目录下的某些文件
     */
    public static final List<String> FILE_IGNORE_EXTENSIONS = Lists.newArrayList(".DS_Store");

    /**
     * 根据文件路径获取文件的MD5值
     * @param path
     * @return
     */
    public static String getFileMD5(String path) {
        if (CharSequenceUtil.isEmpty(path)){
            return StrUtil.EMPTY;
        }
        try(FileInputStream read = new FileInputStream(path)) {
            return DigestUtils.md5Hex(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 根据传入的文件得到MD5值
     * @param file
     * @return
     */
    public static String getFileMD5(File file)  {
        if (file == null){
            return StrUtil.EMPTY;
        }
        try(FileInputStream read = new FileInputStream(file)){
            return DigestUtils.md5Hex(read);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 根据当前日期、随机hash值生成文件名称
     *
     * @return
     */
    /**
     * 根据当前日期、随机hash值生成文件名称
     * 比如：
     * fileName: 2023/05/28/39ff5139860b4d889bfc9b225813b3aa
     * fileName: 2023/05/28/7c923755032744ffa619839b69a9428d.png
     * fileName: 2023/05/28/fsfsfsfs.jpeg
     * @param fileMd5 文件fileMD5值
     * @param fileExtension 文件扩展名
     * @return
     */
    public static String getDateMD5Name(String fileMd5, String fileExtension){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(new Date());
        String fileName = "";
        if (StrUtil.isNotEmpty(fileMd5)){
            fileName = format+"/"+fileMd5;
        }else {
            fileName = format+"/"+UUID.randomUUID().toString().replace("-", "");
        }
        if (StrUtil.isNotEmpty(fileExtension)){
            fileName = fileName+fileExtension;
        }
        System.out.println("fileName: " + fileName);
        return fileName;
    }

    /**
     * 文件分块
     * @param src 源文件路径
     * @param dest   文件分块后保存路径
     * @return
     */
    public static boolean chunkFile(String src, String dest) {
        if (StrUtil.isEmpty(src) || StrUtil.isEmpty(dest)){
            return false;
        }
        // 源文件
        File sourceFile = new File(src);
        // 文件分块个数，向上取整
        int chunkNum = (int) Math.ceil(sourceFile.length()*1.0 / FILE_BLOCK_SIZE);
        // 缓冲区
        byte[] bytes = new byte[1024*1024];
        // 文件读入流
        RandomAccessFile read = null;
        try {
            read = new RandomAccessFile(sourceFile,"r");
            for (int i = 0; i < chunkNum; i++) {
                File destFile = new File(dest + "/" + i);
                System.out.println("chunkNum: "+chunkNum+" currentNum: "+i);
                System.out.println("read " + destFile.getAbsolutePath());
                if (!destFile.exists()) {
                    // 创建上级目录
                    destFile.getParentFile().mkdirs();
                    // 创建文件
                    destFile.createNewFile();
                }
                try (RandomAccessFile write = new RandomAccessFile(destFile, "rw")) {
                    int len = -1;
                    while ((len = read.read(bytes)) != -1) {
                        write.write(bytes, 0, len);
                        // 当分块文件达到默认的分块大小后，break
                        if (destFile.length() >= FILE_BLOCK_SIZE) {
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null){
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 合并分块文件为一个文件
     * @param src 源分块文件路径
     * @param dest 合并后文件路径
     * @return
     */
    public static boolean mergeFile(String src, String dest)  {
        if (StrUtil.isEmpty(src) || StrUtil.isEmpty(dest)){
            return false;
        }
        // 查看源文件目录下所有的文件
        File[] files = new File(src).listFiles();
        // 转换数组为list，过滤掉某些文件
        List<File> fileList = Arrays.stream(files)
                // 过滤掉某些文件
                .filter(file -> !FILE_IGNORE_EXTENSIONS.contains(file.getName()))
                // 分块文件升序排序
                .sorted((o1, o2) -> Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName()))
                .collect(Collectors.toList());
        fileList.forEach(it->{
            System.out.println("fileName: "+it.getName());
        });

        RandomAccessFile write = null;
        try {
            write = new RandomAccessFile(dest, "rw");
            byte[] buffer = new byte[1024*1024];
            for (File file : fileList) {
                RandomAccessFile read = new RandomAccessFile(file, "r");
                int len = -1;
                while ((len = read.read(buffer)) != -1) {
                    write.write(buffer,0,len);
                }
                read.close();
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       return true;
    }

    /**
     * 校验文件是否为同一个
     * @param src
     * @param dest
     * @return
     */
    public static boolean checkFileIsSame(String src, String dest){
        if (StrUtil.isEmpty(src) || StrUtil.isEmpty(dest)){
            return false;
        }
        String srcFileMD5 = getFileMD5(src);
        String srcDestMD5 = getFileMD5(dest);
        if (StrUtil.isEmpty(srcFileMD5) || StrUtil.isEmpty(srcDestMD5)){
            return false;
        }
        if (srcFileMD5.equals(srcDestMD5)){
            return true;
        }
        return false;
    }

    /**
     * 根据扩展名获取媒体类型
     * @param extension
     * @return
     */
    public static String getMimeType(String extension){
        if(StrUtil.isEmpty(extension)){
            extension = "";
        }
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //通用mimeType，字节流
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;

    }


    @Test
    public void test(){
        // 测试文件的MD5值
        getDateMD5Name(null,null);
        getDateMD5Name(null,".png");
        getDateMD5Name("fsfsfsfs",".jpeg");

        // 测试分块文件
        String src = "/Users/yangmiao/Downloads/Snipaste-2.8.3-Beta2.dmg";
        String destChunk = "/Users/yangmiao/Downloads/chunkPath/";
        String destMerge = "/Users/yangmiao/Downloads/Snipaste-2.8.3-Beta2_merge.dmg";
        boolean chunkFileRet = chunkFile(src, destChunk);
        Assertions.assertTrue(chunkFileRet);

        boolean mergeFileRet = mergeFile(destChunk, destMerge);
        Assertions.assertTrue(mergeFileRet);

        boolean fileIsSame = checkFileIsSame(src, destMerge);
        System.out.println("fileIsSame: " + fileIsSame);
        Assertions.assertTrue(fileIsSame);
    }
}
