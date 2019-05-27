/**
 * 
 */
package com.lqq.bookbar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 使用httpclient获取米雅上面的报表数据，然后再上传excel
 * @author lenovo
 *
 */
public class HttpClientApp {
	
	static final String DEFAULT_FILE_PATH = "D:\\我的工作\\BI运维\\手工数据\\手机日报上传";
	static final String DEFAULT_FILE_HIS_PATH = "D:\\我的工作\\BI运维\\手工数据\\手机日报上传\\手机日报增量";
	static final String DEFAULT_FILE_PREX = "xls";
	static final String MIYA_USER_NAME = "aeonpeople";
	static final String MIYA_USER_PWD = "76g0ZC";
	static final String MIYA_USER_TYPE = "2";
	static final String FTP_HOST = "10.100.200.55";
	static final int FTP_PORT = 21;
	static final String FTP_USER_NAME = "ftprep";
	static final String FTP_USER_PWD = "ftprep123";
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 获取最近上传的文件时间，如果没有获取到，返回昨天
	 * @return
	 */
	public LocalDate getLastUploadFileDate() {
		LocalDate localDate = null;
		File[] files = new File(DEFAULT_FILE_PATH).listFiles();
		
		java.util.List<String> list = new ArrayList<String>();
		
		logger.info("1.查找默认目录文件："+files.length+"个");
		//如果当前目录没有文件，去历史目录中查找
		if (files == null || files.length < 4) {
			files = new File(DEFAULT_FILE_HIS_PATH).listFiles();
			logger.info("2.查找历史目录文件："+files.length+"个");
		}
		
		for (File file : files) {
			if (file.isFile()) {
				if (DEFAULT_FILE_PREX.equals(file.getName().split("\\.")[1])) {
					list.add(file.getName());
				}
			}
		}		
		logger.info("3.获取目录下xls文件列表："+list);
		
		LocalDate yesterday = LocalDate.now().minusDays(1);
		int year = yesterday.getYear();
		int month = yesterday.getMonthValue();
		int days = yesterday.getDayOfMonth();
		
		if (list.size() > 0) { //目录下有xls文件则排序，若无则默认取前一天
			String[] list2 = (String[])list.toArray(new String[0]);
			Arrays.sort(list2, new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2)*-1;//使用降序排序
				}
			});
			logger.info("4.降序排列之后的xls文件列表："+Arrays.asList(list2));

			year = Integer.parseInt(list2[0].substring(17, 21));
			month = Integer.parseInt(list2[0].substring(21, 23));
			days = Integer.parseInt(list2[0].substring(23, 25));
			logger.info("5.获取文件目录中最近的日期："+year+"-"+month+"-"+days);
			
		}
		
		localDate = LocalDate.of(year, month, days);
		return localDate;
	}
	
	/**
	 * 从米雅获取特定日期的汇总文件
	 * @return
	 */
	public Map<String,File> getFileFromMY (LocalDate date,LocalDate date2) throws ClientProtocolException,IOException,Exception {
		
		if (date == null || date2 == null || date.compareTo(date2) > 0) {
			throw new Exception("开始日期、结束日期不能为空，开始日期不能大于结束日期");
		}
		int periodDays = Period.between(date, date2).getDays();
		CloseableHttpClient client = HttpClients.createDefault();
		Map<String, File> list = new HashMap<String, File>();
		//HttpPost post = new HttpPost("http://139.196.88.135/newmiya/login.jsp");
		// 设置登录的账号与密码
		HttpUriRequest request = RequestBuilder.post().setUri("http://139.196.88.135/newmiya/Login/loginAction")
				.addParameter("userId", MIYA_USER_NAME).addParameter("password", MIYA_USER_PWD)
				.addParameter("type", MIYA_USER_TYPE).build();
		

		CloseableHttpResponse response = client.execute(request);
		// 响应文本
        String content = EntityUtils.toString(response.getEntity());
        EntityUtils.consume(response.getEntity());
        // 输出响应页面源代码  目前为止登陆成功
        System.out.println("页面："+content);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        for (int i = 0; i < periodDays; i++) {
        	
        	LocalDate curDate = date.minusDays(-1-i);
        	//接下来要打开指定页面下载excel
           
            String dateString = curDate.format(formatter);
            String dateString2 = curDate.format(formatter2);
            HttpUriRequest request2 = RequestBuilder.post().setUri("http://139.196.88.135/newmiya/DailyMarket/export")
    				.addParameter("stores", "").addParameter("market", "")
    				.addParameter("platform", "").addParameter("status", "PAYSUCCESS")
    				.addParameter("begin_time", dateString).addParameter("end_time", dateString).build();

            CloseableHttpResponse response2 = client.execute(request2);
             // 响应流
    	    InputStream is = response2.getEntity().getContent();  
    	    //将网页输出的文件保存至指定目录
    	    File file = new File(DEFAULT_FILE_PATH+"\\temp\\门店汇总_"+dateString+"-"+System.currentTimeMillis()+".xlsx");  
    	    //file.getParentFile().mkdirs();  
    	    FileOutputStream fileout = new FileOutputStream(file);  
    	     
    	    //根据实际运行效果 设置缓冲区大小 
    	    byte[] buffer=new byte[10*1024];  
    	    int ch = 0;  
    	    while ((ch = is.read(buffer)) != -1) {  
    	        fileout.write(buffer,0,ch);  
    	    }  
    	    is.close();  
    	    fileout.flush();  
    	    fileout.close();  
    	    list.put(DEFAULT_FILE_PATH+"\\AEON_ShouJiZhiFu_"+dateString2+".xls", file);
		}
        
        
        client.close();
		return list;
	}
	
	/**
	 * 调用服务器本地com组件打开excel，然后调用具体的宏，返回新生成的文件
	 * 参考文章 https://blog.csdn.net/steve_frank/article/details/88951914
	 * @param file
	 */
	public void execExcelMicroOrder(File file) {
		 ActiveXComponent excel=new ActiveXComponent("Excel.Application");
		 Dispatch workbooks = excel.getProperty("Workbooks").toDispatch();
		 //Dispatch document = Dispatch.call(documents, "Open", file.getName()).toDispatch();//指定要打开的文档并且打开它
		 Dispatch workbook = Dispatch.invoke(
                 workbooks, "Open", Dispatch.Method,
                 new Object[]{file.getPath(), new Variant(false), new Variant(false)},//第二个boolean值表示是否只读
                 new int[1]).toDispatch();
		 Dispatch workbook2 = Dispatch.invoke(
                 workbooks, "Open", Dispatch.Method,
                 new Object[]{"C:/Users/lenovo/AppData/Roaming/Microsoft/Excel/XLSTART/PERSONAL.XLSB", new Variant(false), new Variant(true)},
                 new int[1]).toDispatch();
		 //Dispatch.call(excel, "Run", new Variant("PERSONAL.XLSB!米雅数据处理"));//在这个文档上运行宏
		 Dispatch.call(excel, "Run", new Variant("PERSONAL.XLSB!米雅数据处理"));
		 
		 if (excel != null) {
			 excel.invoke("Quit", new Variant[]{});
			 excel = null;
	        }
	        workbooks = null;
	        workbook2 = null;
	        workbook = null;
	        //ComThread.Release();
	        System.gc();
	}
	
	public void moveFileToDefaultDir(File file) {
		if (file == null) {
			return;
		}
		//移动文件至默认归档目录
		file.renameTo(new File(DEFAULT_FILE_HIS_PATH+"\\"+file.getName()));
	}

	public static void main(String[] args) throws ClientProtocolException, IOException, Exception {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		HttpClientApp app = new HttpClientApp();
		LocalDate localDate = app.getLastUploadFileDate();
		//使用Date和SimpleDateFormat
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Map<String,File> list = app.getFileFromMY(localDate,yesterday);
		FtpUtil.initDefaultFtpClient();
		for (Entry<String, File> entry : list.entrySet()) {
			app.execExcelMicroOrder(entry.getValue());
			//上传文件到服务器
			File file = new File(entry.getKey());
			FtpUtil.uploadDefaultFile(file);
			//app.moveFileToDefaultDir(file);
		}
		FtpUtil.loginOutFtpClient();
		//File file = new File("D:\\我的工作\\BI运维\\手工数据\\手机日报上传\\AEON_ShouJiZhiFu_20190522.xls");
		//移动已经上传完成的文件
		
		
	}
}
