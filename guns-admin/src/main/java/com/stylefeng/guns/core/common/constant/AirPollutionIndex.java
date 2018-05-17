package com.stylefeng.guns.core.common.constant;

/**
 * 空气污染指数
 * @author admin
 *
 */
public enum AirPollutionIndex {

	level1(0,0, 0,0,0,0, 0),
	level2(35,50, 50,40,2,100, 50),
	level3(75,150, 150,80,4,160, 100),
	level4(115,250, 475,180,14,215, 150),
	level5(150,350, 800,280,24,265, 200),
	level6(250,420, 1600,565,36,800, 300),
	level7(350,500, 2100,750,48,1000, 400),
	level8(500,600, 2620,940,60,1200, 500);
	
	private int pm25;
	private int pm10;
	private int SO2;
	private int NO2;
	private int CO;
	private int O3;
	private int aqi;
	
	private AirPollutionIndex(int pm25,int pm10,int SO2,int NO2,int CO,int O3,int aqi){
		this.pm25=pm25;
		this.pm10=pm10;
		this.aqi=aqi;
		this.SO2=SO2;
		this.NO2=NO2;
		this.CO=CO;
		this.O3=O3;
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
	 * @return the sO2  
	 */
	public int getSO2() {
		return SO2;
	}

	/**
	 * @param sO2 the sO2 to set
	 */
	public void setSO2(int sO2) {
		SO2 = sO2;
	}

	/**
	 * @return the nO2  
	 */
	public int getNO2() {
		return NO2;
	}

	/**
	 * @param nO2 the nO2 to set
	 */
	public void setNO2(int nO2) {
		NO2 = nO2;
	}

	/**
	 * @return the cO  
	 */
	public int getCO() {
		return CO;
	}

	/**
	 * @param cO the cO to set
	 */
	public void setCO(int cO) {
		CO = cO;
	}

	/**
	 * @return the o3  
	 */
	public int getO3() {
		return O3;
	}

	/**
	 * @param o3 the o3 to set
	 */
	public void setO3(int o3) {
		O3 = o3;
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
			else if("SO2".equals(type)){
				if(code==low.getSO2()){
					return low.getAqi();
				}else if(code==high.getSO2()){
					return high.getAqi();
				}else if(code>low.getSO2() && code<high.getSO2()){
					Cl=low.getSO2();
					Ch=high.getSO2();
					Il=low.getAqi();
					Ih=high.getAqi();
					break;
				}
			} 
			
			else if("NO2".equals(type)){
				if(code==low.getNO2()){
					return low.getAqi();
				}else if(code==high.getNO2()){
					return high.getAqi();
				}else if(code>low.getNO2() && code<high.getNO2()){
					Cl=low.getNO2();
					Ch=high.getNO2();
					Il=low.getAqi();
					Ih=high.getAqi();
					break;
				}
			} 
			else if("CO".equals(type)){
				if(code==low.getCO()){
					return low.getAqi();
				}else if(code==high.getCO()){
					return high.getAqi();
				}else if(code>low.getCO() && code<high.getCO()){
					Cl=low.getCO();
					Ch=high.getCO();
					Il=low.getAqi();
					Ih=high.getAqi();
					break;
				}
			} 
			else if("O3".equals(type)){
				if(code==low.getO3()){
					return low.getAqi();
				}else if(code==high.getO3()){
					return high.getAqi();
				}else if(code>low.getO3() && code<high.getO3()){
					Cl=low.getO3();
					Ch=high.getO3();
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
