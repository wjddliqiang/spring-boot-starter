/**
 * 
 */
package com.lqq.bookbar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author lenovo
 *
 */
public class CompressUtils {

	public static enum CompressMethod {
		ZIP, RAR, GZ
	}

	public static void compressDirFiles(CompressMethod method, String fileFullPath) {
		switch (method) {
		case ZIP:

			break;

		default:
			break;
		}
	}

	/**
	 * 压缩单个文件
	 * 如果输入的路径是文件夹则不处理，如果是文件则压缩这个文件，默认压缩名称是 原路径下原文件再加上.gz，例如输入
	 * F:\\MyTest\\myTestFile.txt 则压缩后的文件为 F:\\MyTest\\myTestFile.txt.gz
	 * 
	 * @param fileFullPath
	 */
	private static void compressDirFilesToGz(String fileFullPath) {
		if (fileFullPath == null || "".equals(fileFullPath)) {
			return;
		}
		// 开始压缩文件到一个.gz包中 而这里的.gz之前的文件名可与之前不一致，如下面的文件名，
		// 使用解压工具看到里面的文件是去掉.gz之前的部分myTestFile001.txt
		String newFileName = fileFullPath + ".gz";
		File file = new File(fileFullPath);
		if (file.isFile()) {
			try{
				FileOutputStream fileOutputStream = new FileOutputStream(newFileName);
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
				FileInputStream fileInputStream = new FileInputStream(fileFullPath);
				byte[] b = new byte[1024 * 1024 * 5];
				int length = 0;
				while ((length = fileInputStream.read(b)) != -1) {
					gzipOutputStream.write(b, 0, length);
				}
				fileInputStream.close();
				gzipOutputStream.close();
				fileOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		String pathString = "D:\\我的工作\\BI运维\\手工数据\\手机日报上传\\temp\\门店汇总_2019-05-22-1558604347118.xlsx";
		compressDirFilesToGz(pathString);
	}
}
