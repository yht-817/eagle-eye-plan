package com.yht.ftp.eagleftp.config;

import cn.hutool.log.StaticLog;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 进行FTP的数据操作
 */
public class FtpOperation {

    /**
     * 上传单个文件
     */
    public static String uploadSingle(MultipartFile multipartFile, String directory) {
        FTPClient ftpClient = FtpULinkTool.getFtpClient();
        String fileName = FtpULinkTool.fileName(multipartFile);
        try {
            //设置文件类型
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            //设置缓存大小
            ftpClient.setBufferSize(1024);
            //创建目录
            ftpClient.makeDirectory(directory);
            //切换至FTP目录
            StaticLog.error("切换至FTP目录:{}", directory);
            if (ftpClient.changeWorkingDirectory(directory)) {
                try {
                    StaticLog.info("上传的文件名{}", fileName);
                    boolean b = ftpClient.storeFile(fileName, multipartFile.getInputStream());
                    StaticLog.error("FTP上传文件:{}", b);
                    if (b) {
                        return FtpULinkTool.getPath() + directory + fileName;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                StaticLog.error("切换目录失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FtpULinkTool.destructionFtpClient(ftpClient);
        }
        return null;
    }


}
