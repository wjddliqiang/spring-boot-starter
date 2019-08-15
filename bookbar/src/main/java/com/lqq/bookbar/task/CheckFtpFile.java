/**
 * 
 */
package com.lqq.bookbar.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lqq.bookbar.service.MailService;
import com.lqq.bookbar.utils.FtpUtil;

/**
 * @author administrator
 *
 */
@Component
public class CheckFtpFile {
	
	@Autowired
	private MailService mailService;

	private static DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static Log logger = LogFactory.getLog(CheckFtpFile.class);
/*
 * 可以使用cron表达式写，* 5 * * * * *。以下为cron表达式详解：
Cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每一个域代表一个含义，Cron有如下两种语法格式：
Seconds Minutes Hours DayofMonth Month DayofWeek Year或
Seconds Minutes Hours DayofMonth Month DayofWeek
每一个域可出现的字符如下：
Seconds:可出现", - * /"四个字符，有效范围为0-59的整数
Minutes:可出现", - * /"四个字符，有效范围为0-59的整数
Hours:可出现", - * /"四个字符，有效范围为0-23的整数
DayofMonth:可出现", - * / ? L W C"八个字符，有效范围为0-31的整数
Month:可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEc
DayofWeek:可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推
Year:可出现", - * /"四个字符，有效范围为1970-2099年
每一个域都使用数字，但还可以出现如下特殊字符，它们的含义是：
(1)*：表示匹配该域的任意值，假如在Minutes域使用*, 即表示每分钟都会触发事件。
(2)?:只能用在DayofMonth和DayofWeek两个域。它也匹配域的任意值，但实际不会。因为DayofMonth和DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。
(3)-:表示范围，例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次
(4)/：表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次.
(5),:表示列出枚举值值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。
(6)L:表示最后，只能出现在DayofWeek和DayofMonth域，如果在DayofWeek域使用5L,意味着在最后的一个星期四触发。
(7)W:表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份
(8)LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。
(9)#:用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2，表示某月的第二个星期三。
举几个例子:
0 0 2 1 * ? * 表示在每月的1日的凌晨2点调度任务
0 15 10 ? * MON-FRI 表示周一到周五每天上午10：15执行作业
0 15 10 ? 6L 2002-2006表示2002-2006年的每个月的最后一个星期五上午10:15执行作
一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
按顺序依次为
秒（0~59）
分钟（0~59）
小时（0~23）
天（月）（0~31，但是你需要考虑你月的天数）
月（0~11）
天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
年份（1970－2099）
其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?
0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时
0 0 12 ? * WED 表示每个星期三中午12点
"0 0 12 * * ?" 每天中午12点触发
"0 15 10 ? * *" 每天上午10:15触发
"0 15 10 * * ?" 每天上午10:15触发
"0 15 10 * * ? *" 每天上午10:15触发
"0 15 10 * * ? 2005" 2005年的每天上午10:15触发
"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
"0 15 10 15 * ?" 每月15日上午10:15触发
"0 15 10 L * ?" 每月最后一日的上午10:15触发
"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
有些子表达式能包含一些范围或列表
例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT”
“*”字符代表所有可能的值
因此，“*”在子表达式（月）里表示每个月的含义，“*”在子表达式（天（星期））表示星期的每一天
“/”字符用来指定数值的增量
例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟
在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样
“？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值
当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”
“L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写
但是它在两个子表达式里的含义是不同的。
在天（月）子表达式中，“L”表示一个月的最后一天
在天（星期）自表达式中，“L”表示一个星期的最后一天，也就是SAT
如果在“L”前有具体的内容，它就具有其他的含义了
例如：“6L”表示这个月的倒数第6天，“FRIL”表示这个月的最一个星期五
注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题
字段 允许值 允许的特殊字符
秒 0-59 , - * /
分 0-59 , - * /
小时 0-23 , - * /
日期 1-31 , - * ? / L W C
月份 1-12 或者 JAN-DEC , - * /
星期 1-7 或者 SUN-SAT , - * ? / L C #
年（可选） 留空, 1970-2099 , - * /
 */
	/*
	 * @Scheduled(fixedRate = 2000) public void testTasks() {
	 * System.out.println("每隔两秒输出一次"); }
	 */
	
	/*
	 * @Scheduled(cron = "0 33 10 ? * *") public void testTasks1() {
	 * System.out.println("每天10：33执行"); }
	 */
	
	/**
	 * 每天的早上9点执行，检查ftp上面昨天创建的文件夹中的文件是否有超过5点30上传的，如果有发送邮件通知
	 * @throws IOException 
	 */
	@Scheduled(cron = "0 0 09 ? * *")
	public void check55FtpTask() throws IOException {
		LocalDate localdate = LocalDate.now();
		localdate = localdate.minusDays(1);
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
			mailService.sendSimpleMail("jerry_li@aeonchina.com.cn", "BI 55ftp文件上传时间异常", sb.toString());
			logger.warn("检查FTP文件夹["+dateforcheck+"] 发现问题，请检查右键！");
		}else {
			logger.info("检查FTP文件夹["+dateforcheck+"]正常！");
		}
	}
}
