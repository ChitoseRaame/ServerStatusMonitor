package space.nekochocolate;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemLoadInfo
{
	// 入口
	SystemInfo obj_SystemInfo = new SystemInfo();
	// 操作系统对象
	OperatingSystem obj_OS = obj_SystemInfo.getOperatingSystem();
	// 获取系统家族 自启动以来的时间
	String platform_OS = obj_OS.getFamily();
	// System.out.println("系统家族: " + platform_OS);
	// System.out.println("运行时间: " + Utils.convertTimestamp(platform_Uptime));
	// 获取计算机主机名
	String platform_HostName = obj_OS.getNetworkParams()
			.getHostName();
	// 硬件抽象层对象
	HardwareAbstractionLayer obj_HardwareAbstractionLayer = obj_SystemInfo.getHardware();
	// CPU对象
	CentralProcessor obj_CPU = obj_HardwareAbstractionLayer.getProcessor();
	// 获取内存
	volatile long[] oldLoad = obj_CPU.getSystemCpuLoadTicks();
	// RAM对象
	GlobalMemory obj_RAM = obj_HardwareAbstractionLayer.getMemory();
	volatile int cpuLoad = 0;
	
	// 对象初始化方法 (构造方法
	{
		new Thread(() ->
		{
			while (true)
			{
				oldLoad = obj_CPU.getSystemCpuLoadTicks();
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				cpuLoad = (int) Math.round(obj_CPU.getSystemCpuLoadBetweenTicks(oldLoad) * 100);
			}
		}).start();
	}
	
	/*
		// 获取CPU使用
		long[] oldLoad = obj_CPU.getSystemCpuLoadTicks();
		Thread.sleep(1000);
		System.out.println("CPU使用: " + Utils.createBar((int) round(obj_CPU.getSystemCpuLoadBetweenTicks(oldLoad) * 100)));
		
		// 获取内存使用
		long ramTotal_M = obj_RAM.getTotal() / 1048576;
		// long ramAvailable_M = obj_RAM.getAvailable() / 1048576;
		long ramUsed_M = ramTotal_M - obj_RAM.getAvailable() / 1048576;
		// long ramUsed_M = ramTotal_M - ramAvailable_M;
		// System.out.println("内存已用/可用: " + ramUsed_M + "M / " + ramTotal_M + "M");
		System.out.println("内存使用: " + Utils.createBar((int) round((double) ramUsed_M / (double) ramTotal_M * 100)));
	 */
	public String getSystemInfo()
	{
		String result;
		// 获取系统家族和运行时间
		result = "系统节点: " + platform_HostName + Utils.anotherLine() + "系统家族: " + platform_OS + Utils.anotherLine() + "运行时间: " + Utils.convertTimestamp(obj_OS.getSystemUptime()) + Utils.anotherLine();
		// 获取CPU使用
		result += "CPU使用: " + Utils.createBar(cpuLoad) + Utils.anotherLine();
		
		// 获取内存使用
		long ramTotal_M = obj_RAM.getTotal() / 1048576;
		long ramUsed_M = ramTotal_M - obj_RAM.getAvailable() / 1048576;
		result += "内存使用: " + Utils.createBar((int) Math.round((double) ramUsed_M / (double) ramTotal_M * 100));
		return result;
	}
}
