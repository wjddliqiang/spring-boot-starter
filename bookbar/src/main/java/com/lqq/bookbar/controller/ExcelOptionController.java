/**
 * 
 */
package com.lqq.bookbar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lqq.bookbar.utils.ExcelData;
import com.lqq.bookbar.utils.ExportExcelUtils;

/**
 * @author lenovo
 *
 */
@Controller
public class ExcelOptionController {

	@RequestMapping("/files")
	public String getFtpFiles(HttpServletRequest request) {
		System.out.println("准备访问ftp文件---");
		return "redirect:ftp://10.100.200.55/20190428/AEON_ShouJiZhiFu_20190424.xls";
	}
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public String excel(HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setSheetName("hello");
        List<String> titles = new ArrayList();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<Map<String,String>> rows = new ArrayList();
        Map<String,String> rowmap = new HashMap<String, String>();
        rowmap.put("a1", "1111");
        rowmap.put("a2", "2222");
        rowmap.put("a3", "3333");
        rows.add(rowmap);

        data.setRows(rows);


        //生成本地
        /*File f = new File("c:/test.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcelToUser(response,"hello.xlsx",data);
        return null;
    }
    
    
    @RequestMapping(value = "/excel1", method = RequestMethod.GET)
    public String excel1(HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setSheetName("hello");
        List<String> titles = new ArrayList();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<Map<String,String>> rows = new ArrayList();
        Map<String,String> rowmap = new HashMap<String, String>();
        rowmap.put("c1", "1111");
        rowmap.put("c2", "2222");
        rowmap.put("c3", "3333");
        rows.add(rowmap);

        data.setRows(rows);


        //生成本地
        /*File f = new File("c:/test.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcelToServer("hello.xlsx",data);
        return null;
    }
}
