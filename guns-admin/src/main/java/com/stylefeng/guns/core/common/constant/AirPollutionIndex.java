package com.stylefeng.guns.core.common.constant;

/**
 * 空气污染指数
 * @author admin
 *
 */
public enum AirPollutionIndex {

	level1(0,0,0),
	level2(35,50,50),
	level3(75,150,100),
	level4(115,250,150),
	level5(150,350,200),
	level6(250,420,300),
	level7(350,500,400),
	level8(500,600,500);
	
	private int pm25;
	private int pm10;
	private int aqi;
	
	private AirPollutionIndex(int pm25,int pm10,int aqi){
		this.pm25=pm25;
		this.pm10=pm10;
		this.aqi=aqi;
	}

	public int getPm25() {
		return pm25;
	}

	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}

	public int getPm10() {
		return pm10;
	}

	public void setPm10(int pm10) {
		this.pm10 = pm10;
	}

	public int getAqi() {
		return aqi;
	}

	public void setAqi(int aqi) {
		this.aqi = aqi;
	}
	
	/**
	 * 计算AQI数值
	 * @param code
	 * @param type
	 * @return
	 */
	public static int calAirPollutionIndex(int code,String type){
		int aqi=0;
		AirPollutionIndex[] values = AirPollutionIndex.values();
		int Cl=0,Ch=0,Il=0,Ih=0;
		for(int i=0;i<values.length-1;i++){
			AirPollutionIndex low = values[i];
			AirPollutionIndex high = values[i+1];
			if("pm2.5".equals(type)){
				if(code==low.getPm25()){
					return low.getAqi();
				}else if(code==high.getPm25()){
					return high.getAqi();
				}else if(code>low.getPm25() && code<high.getPm25()){
					Cl=low.getPm25();
					Ch=high.getPm25();
					Il=low.getAqi();
					Ih=high.getAqi();
					break;
				}
			}else if("pm10".equals(type)){
				if(code==low.getPm10()){
					return low.getAqi();
				}else if(code==high.getPm10()){
					return high.getAqi();
				}else if(code>low.getPm10() && code<high.getPm10()){
					Cl=low.getPm10();
					Ch=high.getPm10();
					Il=low.getAqi();
					Ih=high.getAqi();
					break;
				}
			} 
		}
		if(Ch!=Cl){
			aqi=Math.round((Ih-Il)*(code-Cl)/(Ch-Cl)+Il);
		}
		return aqi;
	}
	
}
