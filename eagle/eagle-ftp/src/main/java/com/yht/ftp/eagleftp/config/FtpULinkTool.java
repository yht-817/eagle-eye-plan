package com.yht.ftp.eagleftp.config;


import cn.hutool.log.StaticLog;
import cn.hutool.setting.dialect.Props;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Properties;

/**
 * Ftp的链接对象
 */
public class FtpULinkTool {

    private static int port; // 主机端口
    private static String path;// 文件路径
    private static String hostName;// 主机IP
    private static String userName;// 账号名
    private static String password;// 密  码


    static {
        // 静态加载配置信息
        Properties prop = Props.getProp("ftp.properties");
        hostName = prop.getProperty("ftp.hostname");
        port = Integer.parseInt(prop.getProperty("ftp.port"));
        userName = prop.getProperty("ftp.username");
        password = prop.getProperty("ftp.password");
        path = prop.getProperty("ftp.path");
    }

    /**
     * 获取FTP的链接实例
     */
    public static FTPClient getFtpClient() {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            //设置编码
            ftpClient.setControlEncoding("utf-8");
            //连接ftp服务器
            ftpClient.connect(hostName, port);
            //登录ftp服务器
            ftpClient.login(userName, password);
            //是否成功登录服务器
            int replyCode = ftpClient.getReplyCode();
            //开通一个端口来传输数据
            ftpClient.enterLocalPassiveMode();
            //设置连接超时
            ftpClient.setConnectTimeout(60 * 1000 * 10);
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                StaticLog.error("connect fail...FTP服务器:" + hostName + ":" + port);
            } else {
                StaticLog.error("connect success...FTP服务器:" + hostName + ":" + port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 销毁FTP链接实例
     */
    public static void destructionFtpClient(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                if (!ftpClient.isConnected()) {
                    StaticLog.info("FTP实例--destruction");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成文件名和获取文件后缀
     */
    public static String fileName(MultipartFile multipartFile) {
        String file_name = multipartFile.getOriginalFilename();
        // 生成时间戳
        return System.currentTimeMillis() + "." + file_name.substring(file_name.lastIndexOf(".") + 1);
    }


    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        FtpULinkTool.port = port;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        FtpULinkTool.path = path;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        FtpULinkTool.hostName = hostName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        FtpULinkTool.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        FtpULinkTool.password = password;
    }
}
