package com.lqq.bookbar.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class tests {

	/**
	 * 计算地图上两个坐标的距离
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
    public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b){
        double pk = 180 / 3.14169;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6371000 * tt;
    }
    
    public static void main(String[] args) {
    	/*
    	  * 用户1坐标 113.328563035892,23.1357552072951
                       *用户2坐标 113.329521589854,23.1342418067308
                       * 店铺坐标  0001 113.329465949709,23.1369858733551
                       * 店铺坐标  8001 113.350885014231,23.1292031080757
    	 */
		//计算用户1与0001门店距离
    	System.out.println("用户1与0001门店距离："+Math.round(getDistance(113.328563035892,23.1357552072951,113.329465949709,23.1369858733551)));
    	//计算用户1与8001门店距离
    	System.out.println("用户1与8001门店距离："+Math.round(getDistance(113.328563035892,23.1357552072951,113.350885014231,23.1292031080757)));
    	
    	//计算用户2与0001门店距离
    	System.out.println("用户2与0001门店距离："+Math.round(getDistance(113.329521589854,23.1342418067308,113.329465949709,23.1369858733551)));
    	//计算用户2与8001门店距离
    	System.out.println("用户2与8001门店距离："+Math.round(getDistance(113.329521589854,23.1342418067308,113.350885014231,23.1292031080757)));
	}

	
//	public static void main(String[] args) {
//		final LocalDate localDate1 = LocalDate.now().plusDays(1);
//		LocalDate localDate2 = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		localDate2 = localDate2.plusDays(1);
//		System.out.println("localDate1:"+localDate1.format(formatter));
//		System.out.println("localDate2:"+localDate2.format(formatter));
//		if (localDate1.isAfter(localDate2)) {
//			
//			System.out.println("hahah");
//		}
//	}
}
