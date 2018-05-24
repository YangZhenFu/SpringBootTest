package com.stylefeng.guns.modular.air.task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.stylefeng.guns.core.beetl.function.AirStationFunction;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.other.CHexConver;
import com.stylefeng.guns.core.other.CRC16;
import com.stylefeng.guns.core.other.DateUtil;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.util.Contrast;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

@Component
public class GetUdpDataTask  {  
	
	private Logger logger = LoggerFactory.getLogger(GetUdpDataTask.class);
	
	private IAirSensorService airSensorService=SpringContextHolder.getBean(IAirSensorService.class);
	
	private ISensorTypeService sensorTypeService=SpringContextHolder.getBean(ISensorTypeService.class);
	
	// 定义发送数据报的目的地  
    public  int DEST_PORT;  
    public  String DEST_IP;
	
    // 定义每个数据报的最大大小为64字节
    private final int DATA_LEN = 64;     
    // 定义接收网络数据的字节数组  
    byte[] inBuff = new byte[DATA_LEN];  
    // 以指定的字节数组创建准备接收数据的DatagramPacket对象  
    private DatagramPacket inPacket = null;  
    // 定义一个用于发送的DatagramPacket对象  
    private DatagramPacket outPacket = null;  
    //设置超时时间为1秒
    private final int TIMEOUT = 5000;
    //设置最大重发次数为5次
    public  int RETRIES = 3;
    
    
    public GetUdpDataTask(){
    	
    }
    
	public GetUdpDataTask(String ip,int port){
    	this.DEST_IP=ip;
    	this.DEST_PORT=port;
    }
  
    
    /**
     * 发送查询指令，返回UDP数据
     * @param socket
     * @param bytes
     * @return
     * @throws Exception
     */
    public String sendDatagramPacket(DatagramSocket socket, byte[] bytes) throws Exception{
    	// 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组  
        outPacket = new DatagramPacket(new byte[0] , 0 , InetAddress.getByName(DEST_IP) , DEST_PORT); 
    	// 设置发送用的DatagramPacket中的字节数据  
    	outPacket.setData(bytes);
    	// 定义接收网络数据的字节数组  
    	inBuff = new byte[DATA_LEN]; 
    	// 以指定的字节数组创建准备接收数据的DatagramPacket对象
		inPacket = new DatagramPacket(inBuff , inBuff.length); 
		
		int tries = 0;
		boolean receiveResponse=false;
		do {
			// 发送数据报  
	        socket.send(outPacket);  
	        try {
	        	//设置阻塞时间
	        	socket.setSoTimeout(TIMEOUT);
	        	// 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中  
		        socket.receive(inPacket); 
		        if(!InetAddress.getByName(DEST_IP).equals(inPacket.getAddress())){
		        	throw new IOException("Received packet from an unknown source");
		        }
		        receiveResponse=true;
			} catch (IOException e) { // 当receive不到信息或者receive时间超过1秒时，就向服务器重发请求
				tries += 1;
				logger.error("Timed out, " + (RETRIES - tries) + " more tries...");
			}
		} while ((!receiveResponse) && tries<RETRIES);
        //将byte数组转换成十六进制字符串
        String data = CHexConver.byte2HexStr(inBuff, DATA_LEN);
        return data;
    }
    
    
    
    /**
     * 将读取到的udp数据进行解析
     * @param data
     * @param entity
     */
    public Object[] getEntity(String data, AirStationData entity, AirSensor sensor) {
		if(StringUtils.isNotBlank(data)){
			String[] params = data.trim().split(" ");
			//数据长度
			int n = CHexConver.hexStr2Bytes(params[2])[0];
			List<String> list = Lists.newArrayList(params);
			list=list.subList(0, n+5);
			
			StringBuilder builder=new StringBuilder();
			for(int i=0;i<list.size()-2;i++){
				builder.append(list.get(i));
			}
			String crcCode=list.get(list.size()-1)+list.get(list.size()-2);
			byte[] bytes = StringUtil.HexString2Bytes(builder.toString());
			int crc=CRC16.calcCrc16(bytes);
			//CRC16校验
			if(Integer.parseInt(crcCode, 16)==crc){
				
				SensorType type = sensorTypeService.selectById(sensor.getTypeId());
				
				if(type!=null){
					//风速
					if(type.gettName().contains("风速")){
						logger.info("收到的风速响应信息状态码Tx:{}", list);
						logger.info("CRC16:[{}]", crcCode);
						String windSpeed=params[5]+params[6];
						windSpeed=String.valueOf((double)Integer.parseInt(windSpeed, 16)/10);
						logger.info("风速:{}m/s",windSpeed);
						entity.setWindSpeed(windSpeed);
						
						return new Object[]{Boolean.TRUE,entity};
					}
					//风向
					else if(type.gettName().contains("风向")){
						logger.info("收到的风向响应信息状态码Tx:{}",list);
						logger.info("CRC16:[{}]",crcCode);
						String windDirection=params[3]+params[4];
						WindDirection[] directions = WindDirection.values();
						for(WindDirection  direction : directions){
							if(StringUtils.equals(direction.getCode(), windDirection)){
								logger.info("风向:{}",direction.getMsg());
								entity.setWindDirection(direction.getMark());
							}
						}
						return new Object[]{Boolean.TRUE,entity};
					}
					//百叶窗
					else{
						logger.info("收到的百叶窗响应信息状态码Tx:{}", list);
						logger.info("CRC16:[{}]", crcCode);
						String humidity=params[3]+params[4];
						humidity=String.valueOf((double)Integer.parseInt(humidity, 16)/10);
						String temperature=params[5]+params[6];
						temperature=String.valueOf((double)StringUtil.parseHex4(temperature)/10);
						String PM2_5=params[11]+params[12];
						PM2_5=String.valueOf((double)Integer.parseInt(PM2_5, 16));
						String CO2=params[13]+params[14];
						CO2=String.valueOf((double)Integer.parseInt(CO2, 16));
						String gas=params[15]+params[16];
						gas=String.valueOf((double)Integer.parseInt(gas, 16)/10);
						String light_high=params[17]+params[18];
						String light_low=params[19]+params[20];
						String light=String.valueOf((double)(Integer.parseInt(light_high, 16)+Integer.parseInt(light_low, 16))/2);
						String PM10=params[21]+params[22];
						PM10=String.valueOf((double)Integer.parseInt(PM10, 16));
						String air_pressure_high=params[23]+params[24];
						String air_pressure_low=params[25]+params[26];
						String air_pressure=String.valueOf((double)(Integer.parseInt(air_pressure_high, 16)/100+Integer.parseInt(air_pressure_low, 16)/100)/2);
						String noise=params[27]+params[28];
						noise=String.valueOf((double)Integer.parseInt(noise, 16)/10);
						logger.info("温度：{}℃，湿度：{}%RH，PM2.5：{}μg/m3，CO2浓度：{}ppm，气体浓度：{}ppm，光照：{}Lux，PM10：{}μg/m3，气压：{}kpa，噪声值：{}dB", 
									new Object[]{temperature,humidity,PM2_5,CO2,gas,light,PM10,air_pressure,noise});
						entity.setAirTemperature(temperature);
						entity.setAirHumidity(humidity);
						entity.setPm25(PM2_5);
						entity.setPm10(PM10);
						entity.setNoise(noise);
						
						return new Object[]{Boolean.TRUE,entity};
					}
				}
				
			}else{
				logger.error("CRC16校验失败，该数据无效！");
				return new Object[]{Boolean.FALSE};
			}
			
		}
		return new Object[]{Boolean.FALSE};
	}

   /**
    * 查询百叶箱UDP数据的线程
    */
    public class GetAirDataThread implements Callable<String>{
    	private DatagramSocket socket;
    	
    	public GetAirDataThread(DatagramSocket socket){
    		this.socket=socket;
    	}
    	
		@Override
		public String call() throws Exception {
			 //查询百叶窗所有数据指令01 03 00 00 00 0D 84 0F
            byte[] data=new byte[]{0x01,0x03,0x00,0x00,0x00,(byte) 0x0D,(byte) 0x84,(byte) 0x0F};
            String airData=sendDatagramPacket(socket, data);
			return airData;
		}

    }

    /**
     * 查询风速UDP数据的线程
     */
    public class GetWindSpeedDataThread implements Callable<String>{
    	private DatagramSocket socket;
    	
    	public GetWindSpeedDataThread(DatagramSocket socket) {
    		this.socket=socket;
    	}
		@Override
		public String call() throws Exception {
			//查询风速指令02 03 06 00 00 02 C4 B0
			byte[] data=new byte[]{0x02,0x03,0x06,0x00,0x00,0x02,(byte) 0xC4,(byte) 0xB0};
			String windData=sendDatagramPacket(socket, data);
			return windData;
		}
    	
    }
    
    /**
     * 查询风向UDP数据的线程
     */
    public class GetWindDirectionThread implements Callable<String>{

    	private DatagramSocket socket;
    	
    	public GetWindDirectionThread(DatagramSocket socket){
    		this.socket=socket;
    	}
    	
		@Override
		public String call() throws Exception {
			//查询风向指令03 04 00 03 00 01 C0 28 （old）
			//查询风向指令03 03 00 17 00 01 35 EC （new）
//			byte[] data=new byte[]{0x03,0x04,0x00,0x03,0x00,0x01,(byte) 0xC0,(byte) 0x28};
			byte[] data=new byte[]{0x03,0x03,0x00,0x17,0x00,0x01,0x35,(byte) 0xEC};
			String windDirection=sendDatagramPacket(socket, data);
			return windDirection;
		}
    	
    }
    
    /**
     * <p>Title: GetUdpDataThread</p>  
     * <p>Description: 获取传感器数据的线程</p>  
     * @author YangZhenfu  
     * @date 2018年5月8日
     */
    public class GetUdpDataThread implements Callable<String>{

		private DatagramSocket socket;
		
		private String command;
    	
    	public GetUdpDataThread(DatagramSocket socket,String command){
    		this.socket=socket;
    		this.command=command;
    	}
    	
		@Override
		public String call() throws Exception {
			 //查询百叶窗所有数据指令01 03 00 00 00 0D 84 0F
            byte[] bytes=StringUtil.HexString2Bytes(command);
            String data=sendDatagramPacket(socket, bytes);
			return data;
		}
		
	}
    
    



	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException  {  
        GetUdpDataTask task = new GetUdpDataTask("192.168.1.125",5000);
        task.RETRIES=2;
        ExecutorService service = Executors.newCachedThreadPool();
        DatagramSocket socket=new DatagramSocket();
//        //查询百叶窗数据
//		Future<String> airData = service.submit(task.new GetAirDataThread(socket));
//		System.out.println(airData.get());
//		//查询风速传感器数据
//		Future<String> windSpeed = service.submit(task.new GetWindSpeedDataThread(socket));
//		System.out.println(windSpeed.get());
		//查询风向传感器数据
//		Future<String> windDirection = service.submit(task.new GetWindDirectionThread(socket));
//		System.out.println(windDirection.get());
//		
		Future<String> udp = service.submit(task.new GetUdpDataThread(socket, "03030017000135EC"));
		System.out.println(udp.get());
//        
//        byte[] bytes = StringUtil.HexString2Bytes("03030017000135EC");
//        System.out.println(Arrays.toString(bytes));
//        byte[] data=new byte[]{0x03,0x03,0x00,0x17,0x00,0x01,0x35,(byte) 0xEC};
//        System.out.println(Arrays.toString(data));
        
		service.shutdown();
		socket.close();
    }  
}  