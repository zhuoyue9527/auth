package com.afis.web.utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.afis.web.exception.FileUploaderException;

/**
 * Created by zqj on 2017/2/9.
 */
public class FileUploadingUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String dstPath = null;
    private long maxSize = 10485760;

    public static final String SEPARATOR = File.separator;

    public FileUploadingUtil() {

    }

    public void upload(HttpServletRequest request) throws FileUploaderException, IOException, FileUploadException {
        if (dstPath == null) {
            throw new FileUploaderException(FileUploaderException.DESTINATION_PATH_NOT_SETTING);
        }

        File dest = new File(dstPath);
        if (!dest.exists()) {
            FileUtils.forceMkdir(dest);
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxSize);
        List<FileItem> fileItems = upload.parseRequest(request);
        Iterator<FileItem> iterator = fileItems.iterator();
        System.out.println(fileItems.size());
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (item.isFormField()) {
                String name = item.getName();
                long size = item.getSize();
                logger.debug("file name +=================" + name);
                logger.debug("field name + =================" + item.getFieldName());
                logger.debug("file size +=====" + item.getSize());
            }
        }

    }

    public String getDstPath() {
        return dstPath;
    }

    public void setDstPath(String dstPath) {
        this.dstPath = dstPath;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 上传多个文件，返回文件名称和服务器存储路径列表
     *
     * @param files
     * @return
     * @throws IOException
     */
//    public static Map<String, String> upload(@RequestParam("file") Map<String, MultipartFile> files) throws IOException {
//        if(FILEDIR == null) {
//            return null;
//        }
//        File file = new File(FILEDIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//
//        Map<String, String> result = new HashMap<String, String>();
//        Iterator<Entry<String, MultipartFile>> iter = files.entrySet().iterator();
//        while (iter.hasNext()) {
//            MultipartFile aFile = iter.next().getValue();
//            if (aFile.getSize() != 0 && !"".equals(aFile.getName())) {
//                result.put(aFile.getOriginalFilename(), uploadFile(aFile));
//            }
//        }
//        return result;
//    }

    /**
     * 上传单个文件，并返回其在服务器中的存储路径
     *
     * @param aFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
//    private static String uploadFile(MultipartFile aFile) throws IOException {
//        String filePath = initFilePath(aFile.getOriginalFilename());
//        try {
//            aFile.transferTo(new File(filePath));
//            //write(aFile.getInputStream(), new FileOutputStream(filePath));
//        } catch (FileNotFoundException e) {
//            //logger.error("上传的文件: " + aFile.getName() + " 不存在！！");
//            e.printStackTrace();
//        }
//        return filePath;
//    }

    /**
     * 写入数据
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private static void write(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 遍历服务器目录，列举出目录中的所有文件（含子目录）
     *
     * @return
     */
//    public static Map<String, String> getFileMap() {
//        //logger.info(FileUploadingUtil.FILEDIR);
//        Map<String, String> map = new HashMap<String, String>();
//        File[] files = new File(FileUploadingUtil.FILEDIR).listFiles();
//        if (files != null) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    File[] files2 = file.listFiles();
//                    if (files2 != null) {
//                        for (File file2 : files2) {
//                            String name = file2.getName();
//                            //logger.info(file2.getParentFile().getAbsolutePath());
//                            //logger.info(file2.getAbsolutePath());
//                            map.put(file2.getParentFile().getName() + separator + name,
//                                    name.substring(name.lastIndexOf("_") + 1));
//                        }
//                    }
//                }
//            }
//        }
//        return map;
//    }

    /**
     * 返回文件存储路径，为防止重名文件被覆盖，在文件名称中增加了随机数
     *
     * @param name
     * @return
     */
//    private static String initFilePath(String name) {
//        String dir = getFileDir(name) + "";
//        File file = new File(FILEDIR + dir);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        Long num = new Date().getTime();
//        Double d = Math.random() * num;
//        return (file.getPath() + separator + num + d.longValue() + "_" + name).replaceAll(" ", "-");
//    }

    /**
     * @param name
     * @return
     */
    private static int getFileDir(String name) {
        return name.hashCode() & 0xf;
    }
}

