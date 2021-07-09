package com.rpa;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.Resource;
import com.sun.management.OperatingSystemMXBean;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyController {
	@GetMapping("api/rsc")
	public Resource GetResource() {
		Resource rsc = new Resource();
		OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		String cpuUsage = String.format("%.2f", osBean.getSystemCpuLoad() * 100);
		String memoryUsage = String.format("%.2f", 100d - (((double)osBean.getFreePhysicalMemorySize()) / ((double)osBean.getTotalPhysicalMemorySize()) * 100d));
		File file = new File("C:");
		long totalSpace = file.getTotalSpace();
		long freeSpace = file.getFreeSpace();
		String diskUsage = String.format("%.2f", (double)(totalSpace - freeSpace) * 100d / totalSpace);
		
		rsc.setCpu(cpuUsage);
		rsc.setMemory(memoryUsage);
		rsc.setDisk(diskUsage);
		
		return rsc;
	}
}
