package com.lqq.bookbar.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import ch.qos.logback.classic.Logger;

import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FtpUtil {

	private static final String DEFAULT_FTP_HOST = "10.100.200.55";
	private static final int DEFAULT_FTP_PORT = 21;
	private static final String DEFAULT_FTP_USER_NAME = "ftprep";
	private static final String DEFAULT_FTP_USER_PWD = "ftprep123";
	//private static final String DEFAULT_FTP_REMOTE_DIR = "/APP/ftpdata/data_handmade";
	private static final String DEFAULT_FTP_REMOTE_DIR = "/APP/ftpdata/data_handmade";
	private static FTPClient ftpClient = new FTPClient();
	//private static final String DEFAULT_FILE_PATH = "D:\\我的工作\\BI运维\\手工数据\\手机日报上传";
	private static Log logger = LogFactory.getLog(FtpUtil.class);

	private FtpUtil() {
	}
	
	private static void initDefaultFtpClient() throws SocketException, IOException {
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
		if (ftpClient != null) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
				logger.info("断开FTP连接");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传多个本地文件
	 * @param localFiles
	 * @return
	 */
	public static boolean uploadDefaultFile(List<File> localFiles) {
		
		boolean isOk = true;
		if (localFiles == null || localFiles.size() == 0) {
			return !isOk;
		}
		try {
			if(!ftpClient.isConnected()) {
				initDefaultFtpClient();
			}
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //设置远程的上传目录
            ftpClient.changeWorkingDirectory(DEFAULT_FTP_REMOTE_DIR);
            //文件流
            for (File file2 : localFiles) {
            	FileInputStream inputStream = new FileInputStream(file2);
                ftpClient.storeFile(file2.getName(), inputStream);
               
                inputStream.close();
                if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                	logger.info("上传失败："+file2.getName());
                	isOk =  false;
                } else {
                    logger.info("上传成功");
                }
			}
            loginOutFtpClient();
        } catch (SocketException e) {
            e.printStackTrace();
            isOk =  false;
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            isOk =  false;
            logger.info("FTP的端口错误,请正确配置。");
        }
		return isOk;
	}

	/**
	 * 上传单个文件，不建议使用，请使用uploadDefaultFile(File[] localFiles) 这个方法
	 * @param file
	 * @return
	 */
	@Deprecated
	public static boolean uploadDefaultFile(File file) {
		boolean isOk = false;
		try {
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //设置远程的上传目录
            ftpClient.changeWorkingDirectory(DEFAULT_FTP_REMOTE_DIR);
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

	public static FTPFile[] listFiles(String path) throws IOException {
		if(!ftpClient.isConnected()) {
			initDefaultFtpClient();
		}
		return ftpClient.listFiles(path);
	}
	
	public static void main(String[] args) throws IOException {
		FtpUtil.initDefaultFtpClient();
		//ftpClient.changeWorkingDirectory("/APP/ftpdata/aeon_bi");
		String addr_pre = "/APP/ftpdata/aeon_bi/";
		LocalDate   date1 = LocalDate.of(2019, 5, 1);
		LocalDate   date2 = LocalDate.of(2019, 5, 31);
		LocalDate tmp = date1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		while(tmp.compareTo(date2) <= 0) {
			
			System.out.println();
			FTPFile[] fs = ftpClient.listFiles("/APP/ftpdata/aeon_bi/"+tmp.format(formatter)+"/");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//ftp服务器上面的时间设置和本地不一样
			TimeZone gmt8 = TimeZone.getTimeZone("GMT+8");
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			//sdf.setTimeZone(gmt16);
			//TimeZone.setDefault(TimeZone.getTimeZone("GMT+16"));
			System.out.println(tmp.format(formatter)+" 超时传输的文件：");
			for (FTPFile ftpFile : fs) {
				int timeOffset = gmt.getRawOffset() - gmt8.getRawOffset();    
	            Date d1 = new Date(ftpFile.getTimestamp().getTime().getTime() - timeOffset);    
				
				//c.add(Calendar.HOUR, 8);
				//System.out.println(c.getTimeZone());
				//c.setd(timeZone);
				if(d1.getHours()>5 || (d1.getHours()==5 && d1.getMinutes() >= 30)) {
					System.out.println(ftpFile.getName()+"     "+sdf.format(d1)+" HH:"+d1.getHours()+" mm:"+d1.getMinutes());
				}
				
			}
			
			tmp = tmp.plusDays(1);
		}
		
		
		
		
		
	}
}