package com.stylefeng.guns.core.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 全局ID生成器，保持趋势递增，尾数均匀，每毫秒获取不超过65535个就不会重复。
 * 值位组成：41位(自动扩展位数)毫秒+24位机器标识+16位进程标识+16位累加数。
 * 使用31位10进制整数或20位36进制字符串可用到6300年后，可认为无限使用。
 * 实测生成百万个用时不到2秒(视配置而定)，每毫秒500个，相对于65535来说是非常安全的。
 *
 * @author Ewing
 */
public class GlobalIdWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalIdWorker.class);
    // 机器标识及进程标识
    private static String runMacProcBit;
    // 计数器 可以溢出可循环使用 实际取后16位
    private static final AtomicInteger counter = new AtomicInteger(new SecureRandom().nextInt());
    // 序号掩码（16个1）也是最大值65535
    private final static int counterMask = 0xffff;
    // 序号标志位 保证长度一定是16+1位 再用substring去掉标志位
    private final static int counterFlag = 1 << 16;

    /**
     * 生成唯一ID
     */
    public static BigInteger nextBigInteger() {
        long timestamp = System.currentTimeMillis();

        int count = counter.getAndIncrement() & counterMask;

        // ID偏移组合生成最终的ID，并返回ID
        String idBit = Long.toBinaryString(timestamp) + runMacProcBit +
                Integer.toBinaryString(count | counterFlag).substring(1);

        return new BigInteger(idBit, 2);
    }

    /**
     * 初始化worker
     */
    static {
        // 保证一定是24位机器ID + 16位进程ID
        int machineId = createMachineIdentifier() & 0xffffff | (1 << 24);
        int processId = createProcessIdentifier() & 0xffff | (1 << 16);
        String machineIdBit = Integer.toBinaryString(machineId).substring(1);
        String processIdBit = Integer.toBinaryString(processId).substring(1);
        runMacProcBit = machineIdBit + processIdBit;
    }

    /**
     * 获取36进制20位长度的String类型的ID
     */
    public static String nextString() {
        StringBuilder idStr = new StringBuilder(nextBigInteger().toString(36));
        while (idStr.length() < 20) idStr.insert(0, '0');
        return idStr.toString();
    }

    /**
     * 使用JDK生成UUID并转换成25位36进制字符串
     */
    public static String uuidString() {
        UUID id = UUID.randomUUID();
        // 直接toString是36位16进制带下划线的 需要转换 采用高低位转换比直接转换快一些
        StringBuilder mb = new StringBuilder(Long.toHexString(id.getMostSignificantBits()));
        while (mb.length() < 16) mb.insert(0, '0');
        StringBuilder lb = new StringBuilder(Long.toHexString(id.getLeastSignificantBits()));
        while (lb.length() < 16) lb.insert(0, '0');
        StringBuilder idb = new StringBuilder(new BigInteger(mb.append(lb).toString(), 16).toString(36));
        while (idb.length() < 25) idb.insert(0, '0');
        return idb.toString();
    }

    /**
     * 获取机器标识的HashCode。
     */
    private static int createMachineIdentifier() {
        int machineHash;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException bue) {
                        // Mac地址少于6字节 继续
                    }
                }
            }
            machineHash = sb.toString().hashCode();
        } catch (Throwable t) {
            machineHash = new SecureRandom().nextInt();
            LOGGER.warn("无法获取机器标识，使用随机数值替代！", t);
        }
        return machineHash;
    }

    /**
     * 获取进程标识，转换双字节型。
     */
    private static short createProcessIdentifier() {
        short processId;
        try {
            String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
            } else {
                processId = (short) java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
            }
        } catch (Throwable t) {
            processId = (short) new SecureRandom().nextInt();
            LOGGER.warn("无法获取进程标识，使用随机数值替代！", t);
        }
        return processId;
    }

    public static void main(String[] args) {
		String string = GlobalIdWorker.uuidString();
		System.out.println(string);
	}
} 