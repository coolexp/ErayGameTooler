package com.coolexp.vo;

public class ConfigVO {
	private static String FTL_FOLDER = "\\ftls\\";
	public static String getProtoTemplate(){
		return FTL_FOLDER+"Proto.ftl";
	}
	public static String getDataVOProtoTemplate(){
		return FTL_FOLDER+"DataVO.ftl";
	}
	public static String getBatchBatTemplate(){
		return FTL_FOLDER+"BatchClass.ftl";
	}
	public static String getDataVOWriteHeadTemplate(){
		return FTL_FOLDER+"DataVOWriteHead.ftl";
	}
	public static String getDataVOWriteCPPTemplate(){
		return FTL_FOLDER+"DataVOWriteCPP.ftl";
	}
	public static String getIncludeVOTemplate(){
		return FTL_FOLDER+"IncludeVO.ftl";
	}
	public static String getAntBuildTemplate(){
		return FTL_FOLDER+"Build.ftl";
	}
	public static String getDataPath(){
		return "data";
	}
	public static String getClassPath(){
		return "class";
	}
}
