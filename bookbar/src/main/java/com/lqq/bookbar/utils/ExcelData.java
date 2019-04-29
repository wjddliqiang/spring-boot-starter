package com.lqq.bookbar.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExcelData implements Serializable {

    private static final long serialVersionUID = 4444017239100620999L;

    // 表头
    private List<String> titles;

    // 数据
    private List<Map<String, String>> rows;

    // 页签名称
    private String sheetName;
    
    private String fileName;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }


    public List<Map<String, String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}