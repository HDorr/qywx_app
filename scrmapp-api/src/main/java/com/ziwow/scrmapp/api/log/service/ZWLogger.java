package com.ziwow.scrmapp.api.log.service;

import org.apache.log4j.Logger;

public class ZWLogger {
	private static Logger convLogger = Logger.getLogger("ZWConvLogger");
	private static Logger eventLogger = Logger.getLogger("ZWEventLogger");

	public static void appendZwConvLog(LogLine logLine) {
		convLogger.info(logLine.getMessage());
	}
	
	public static void appendZwEventLog(LogLine logLine) {
		eventLogger.info(logLine.getMessage());
	}
}