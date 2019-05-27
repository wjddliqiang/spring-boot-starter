package com.lqq.bookbar.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import ch.qos.logback.classic.Logger;

import java.io.*;
import java.net.SocketException;

public class FtpUtil {

	private static final String DEFAULT_FTP_HOST = "10.100.200.55";
	private static final int DEFAULT_FTP_PORT = 21;
	private static final String DEFAULT_FTP_USER_NAME = "ftprep";
	private static final String DEFAULT_FTP_USER_PWD = "ftprep123";
	private static FTPClient ftpClient = new FTPClient();
	private static final String DEFAULT_FILE_PATH = "D:\\我的工作\\BI运维\\手工数据\\手机日报上传";
	private static Log logger = LogFactory.getLog(FtpUtil.class);

	private FtpUtil() {
	}

	public static void initDefaultFtpClient() throws SocketException, IOException {
		ftpClient.connect(DEFAULT_FTP_HOST, DEFAULT_FTP_PORT);// 连接FTP服务器
		ftpClient.login(DEFAULT_FTP_USER_NAME, DEFAULT_FTP_USER_PWD);// 登陆FTP服务器
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			logger.warn("未连接到默认FTP，用户名或密码错误!");
		} else {
			logger.info("成功连接到默认FTP!");
		}
	}
	
	public static void loginOutFtpClient() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean uploadDefaultFile(File file) {
		boolean isOk = false;
		try {
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(DEFAULT_FILE_PATH);
            //文件流
            FileInputStream inputStream = new FileInputStream(file);
            ftpClient.storeFile(file.getName(), inputStream);

            inputStream.close();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                System.out.println("上传成功。。");
                isOk =  true;
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP的端口错误,请正确配置。");
        }
		return isOk;
	}

	/**
	 * 获取FTPClient对象
	 *
	 * @param ftpHost     FTP主机服务器
	 * @param ftpPassword FTP 登录密码
	 * @param ftpUserName FTP登录用户名
	 * @param ftpPort     FTP端口 默认为21
	 * @return
	 */
	private static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				System.out.println("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FTP的端口错误,请正确配置。");
		}finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return ftpClient;
	}

	/*
	 * 从FTP服务器下载文件
	 *
	 * @param ftpHost FTP IP地址
	 * 
	 * @param ftpUserName FTP 用户名
	 * 
	 * @param ftpPassword FTP用户名密码
	 * 
	 * @param ftpPort FTP端口
	 * 
	 * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
	 * 
	 * @param localPath 下载到本地的位置 格式：H:/download
	 * 
	 * @param fileName 文件名称
	 */
	public static void downloadFtpFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
			String ftpPath, String localPath, String fileName) {

		FTPClient ftpClient = null;

		try {
			ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);

			File localFile = new File(localPath + File.separatorChar + fileName);
			OutputStream os = new FileOutputStream(localFile);
			ftpClient.retrieveFile(fileName, os);
			os.close();
			ftpClient.logout();

		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取错误。");
			e.printStackTrace();
		}

	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param ftpHost     FTP服务器hostname
	 * @param ftpUserName 账号
	 * @param ftpPassword 密码
	 * @param ftpPort     端口
	 * @param ftpPath     FTP服务器中文件所在路径 格式： ftptest/aa
	 * @param fileName    ftp文件名称
	 * @param input       文件流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
			String ftpPath, String fileName, InputStream input) {
		boolean success = false;
		FTPClient ftpClient = null;
		try {
			int reply;
			ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);

			ftpClient.storeFile(fileName, input);

			input.close();
			ftpClient.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

}