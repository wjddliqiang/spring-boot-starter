package com.lqq.bookbar.task;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;

import com.lqq.bookbar.utils.FtpUtil;

public class CheckFtpFileTest {

	private static DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static Log logger = LogFactory.getLog(CheckFtpFileTest.class);
	@Test
	public void testCheck55FtpTask() throws IOException {
		LocalDate localdate = LocalDate.now().minusDays(23);
		while(localdate.isBefore(localdate.now())){
			String dateforcheck = localdate.format(dateformatter);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			FTPFile[] fs = FtpUtil.listFiles("/APP/ftpdata/aeon_bi/"+dateforcheck+"/");
			//ftp服务器上面的时间设置和本地不一样
			TimeZone gmt8 = TimeZone.getTimeZone("GMT+8");
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			StringBuilder sb = new StringBuilder();
			for (FTPFile ftpFile : fs) {
				int timeOffset = gmt.getRawOffset() - gmt8.getRawOffset();    
	            Date d1 = new Date(ftpFile.getTimestamp().getTime().getTime() - timeOffset);
				if(d1.getHours()>5 || (d1.getHours()==5 && d1.getMinutes() >= 30)) {
					sb.append("文件："+ftpFile.getName()+"   上传时间："+sdf.format(d1)+"\n");
				}
			}
			
			//System.out.println(sb.toString());
			if(!"".equals(sb.toString())) {
				//System.out.println(sb.toString());
				logger.warn("BI 55ftp文件上传时间异常"+sb.toString());
				logger.warn("检查FTP文件夹["+dateforcheck+"] 发现问题");
			}else {
				logger.info("检查FTP文件夹["+dateforcheck+"]正常！");
			}
			
			FtpUtil.loginOutFtpClient();
			localdate = localdate.plusDays(1);
		}
	}

}
