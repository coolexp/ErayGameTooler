package com.coolexp.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.coolexp.ExcelTooler;
import com.coolexp.vo.ConfigVO;
import com.coolexp.vo.ErayBean;
import com.coolexp.vo.ErayClassBean;
import com.coolexp.vo.InputArgsVO;



import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtils {
	public static void createClassHandler(String ClassName,InputArgsVO inputVO,ArrayList<ErayBean> fieldList) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				template = freemarkerCfg.getTemplate(ConfigVO.getProtoTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\proto\\";
					ExcelTooler.getInstance().checkDirExit(packageFolder);
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+"VO.proto"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	public static void createDataVOHandler(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				String ClassName = "DataVO";
				template = freemarkerCfg.getTemplate(ConfigVO.getDataVOProtoTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\proto\\";
					ExcelTooler.getInstance().checkDirExit(inputVO.outPutPath+"\\proto");
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+".proto"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		createBatchBatHandler(fieldList,inputVO);
		createWriteHeadVO(fieldList,inputVO);
		createWriteCPPVO(fieldList,inputVO);
		createIncludeVO(fieldList, inputVO);
		createBuildXML(fieldList, inputVO);
	}
	private static void createBatchBatHandler(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				String ClassName = "DataVO";
				template = freemarkerCfg.getTemplate(ConfigVO.getBatchBatTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				root.put("BasePath", inputVO.outPutPath);
				root.put("ClassPath", inputVO.outPutPath);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\";
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+".bat"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	private static void createBuildXML(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				template = freemarkerCfg.getTemplate(ConfigVO.getAntBuildTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("SPath", inputVO.outPutPath);
				root.put("TPath", inputVO.classFolder);
				root.put("FieldList", fieldList);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\";
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+"build.xml"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	private static void createWriteHeadVO(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				String ClassName = "DataFileWrite";
				template = freemarkerCfg.getTemplate(ConfigVO.getDataVOWriteHeadTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				root.put("BasePath", inputVO.outPutPath);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\";
					ExcelTooler.getInstance().checkDirExit(packageFolder);
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+".h"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	private static void createWriteCPPVO(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				String ClassName = "DataFileWrite";
				template = freemarkerCfg.getTemplate(ConfigVO.getDataVOWriteCPPTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				root.put("BasePath", inputVO.outPutPath);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\";
					ExcelTooler.getInstance().checkDirExit(packageFolder);
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+".cpp"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	private static void createIncludeVO(ArrayList<ErayClassBean> fieldList,InputArgsVO inputVO) throws IOException{
		try{
			Configuration freemarkerCfg = FreemarkerConfiguration.getConfiguation();
			freemarkerCfg.setDirectoryForTemplateLoading(new File("./"));
			freemarkerCfg.setEncoding(Locale.getDefault(), "gb2312");
			Template template;
			try{
				String ClassName = "IncludeVO";
				template = freemarkerCfg.getTemplate(ConfigVO.getIncludeVOTemplate(),Locale.ENGLISH);
				template.setEncoding("UTF-8");
				HashMap<Object, Object> root = new HashMap<Object, Object>();
				root.put("ClassName", ClassName);
				root.put("FieldList", fieldList);
				root.put("PackageName", inputVO.packageString);
				root.put("BasePath", inputVO.outPutPath);
				Writer writer;
				try{
					String packageFolder = inputVO.outPutPath+"\\";
					ExcelTooler.getInstance().checkDirExit(packageFolder);
					File asDic = new File(packageFolder);
					if(!asDic.exists()){
						asDic.mkdirs();
					}
					writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packageFolder+ClassName+".h"), "UTF-8"));
					template.process(root, writer);
					writer.flush();
					writer.close();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.print(e.toString());
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	public static String lowerCaseFirst(String str){
		if(str.equals("string")||str.equals("String")){
	        str  = str.substring(0,1).toLowerCase()+str.substring(1);
		}
		if(str.equals("long")){
			str = "int64";
		}
		if(str.equals("int")){
			str = "int32";
		}
        return str;
	}
//	private static String toLowerCaseId(String str){
//		if(str.equals("ID")||str.equals("Id")||str.equals("iD")){
//			str = str.toLowerCase();
//		}
//		return str;
//	}
	public static String toUpperCaseId(String str){
//		if(str.equals("ID")||str.equals("Id")||str.equals("iD")){
//			str = str.toUpperCase();
//		}
		return str;
	}
	
}
