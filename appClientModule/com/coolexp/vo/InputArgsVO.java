package com.coolexp.vo;

public class InputArgsVO {
	public boolean outPutData = false;
	public boolean outPutClass = false;
	public String dataPath = "";
	public String outPutPath = "";
	public String packageString = "";
	public String classFolder = "";
	public static InputArgsVO parse(String[]  args){
		InputArgsVO iao = new InputArgsVO();
		boolean outPutData = true;
		boolean outPutClass = true;
		String dataPath = "D:\\data";
		String outPutPath = "D:\\outputdata";
		String classPath = "D:\\classfolder";
		String packageString = "ERAY_PROTOBUF";
		System.out.println("argslength"+args.length);
		if(args.length!=0){
			outPutData = args[0].equals("1") ? true : false;
			outPutClass = args[1].equals("1") ? true : false;
			dataPath = args[2];
			outPutPath = args[3];
			packageString = args[4];
			classPath = args[5];
		}
		iao.outPutData = outPutData;
		iao.outPutClass = outPutClass;
		iao.dataPath = dataPath;
		iao.outPutPath = outPutPath;
		iao.packageString = packageString;
		iao.classFolder = classPath;
		System.out.println(iao.dataPath+"_"+iao.outPutPath+"_"+iao.classFolder+"_"+iao.packageString);
		return iao;
	}
}
