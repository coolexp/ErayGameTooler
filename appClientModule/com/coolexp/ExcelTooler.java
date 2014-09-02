package com.coolexp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import org.apache.commons.io.FilenameUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.coolexp.utils.ExcelUtil;
import com.coolexp.utils.FreeMarkerUtils;
import com.coolexp.vo.ConfigVO;
import com.coolexp.vo.ErayBean;
import com.coolexp.vo.ErayClassBean;
import com.coolexp.vo.InputArgsVO;
import com.coolexp.vo.ObjKeyVO;
import com.google.gson.Gson;

public class ExcelTooler {
	private static ExcelTooler _instance;
	private InputArgsVO inputVO;
	private String keySheetsName = "SheetsName";
	private String[] rootNames = { "root", "data" };
	private String outputfile = "ErayClientData.xml";
	private Element root;
	private Document doc;
	private  Map<String, Object> mainMap = new HashMap<String, Object>();
	private int DATAVO_INDEX = 0;
	public static ExcelTooler getInstance(){
		if(_instance==null){
			_instance = new ExcelTooler();
		}
		return _instance;
	}
	public void parse(InputArgsVO iao) throws Exception{
		DATAVO_INDEX = 0;
		long s = System.currentTimeMillis();
		inputVO = iao;
		start();
		System.out.println("build is ok   Spend time mills:  " + (System.currentTimeMillis() - s));
		System.out.println("PackExcelSuccess");
		System.exit(0);
	}
	private void start() throws Exception{
		root = new Element(rootNames[0]);
		doc = new Document(root);
		ArrayList<Object> jsonSheetList = new ArrayList<Object>();
		mainMap.put("DataTable", jsonSheetList);
		List<String> list = getAllFiles(inputVO.dataPath);
		ArrayList<ErayClassBean> fieldList = new ArrayList<ErayClassBean>();
		for (String s : list) {
			if (FilenameUtils.isExtension(s, "xls")) {
				System.out.println("file:" + s);
				this.tranformExcel(s,fieldList,jsonSheetList);
			}
		}
		if(inputVO.outPutClass){
			FreeMarkerUtils.createDataVOHandler(fieldList, inputVO);
		}
		Format format = Format.getCompactFormat(); 
		format.setIndent("    ");
		format.setLineSeparator("\n");
		this.checkDirExit(inputVO.outPutPath+"\\"+ConfigVO.getDataPath());
		XMLOutputter xmlout = new XMLOutputter(format);
		try {
			xmlout.output(doc, new FileOutputStream(inputVO.outPutPath+"\\"+ConfigVO.getDataPath()+"\\"+outputfile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Gson gson = new Gson();
			String jsonString = gson.toJson(mainMap);
			Writer writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(inputVO.outPutPath+"\\"+ConfigVO.getDataPath()+"\\ErayClientData.json"), "UTF-8"));  
			writer.write(jsonString);  
			writer.close();  
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			
		}
	}
	public void checkDirExit(String path){
		File datOut = new File(path);
		if(!datOut.exists()){
			datOut.mkdir();
		}
	}
	private void tranformExcel(String configExcelPath,ArrayList<ErayClassBean> fieldList,ArrayList<Object> jsonSheetList) throws Exception{
		String[] sheetNames = null;
		try {
			sheetNames = ExcelUtil.getSheetName(configExcelPath);
		} catch (Exception e) {
			System.out.println("GetSheetsFaild");
			e.printStackTrace();
			System.exit(0);
		}
		String sheetName = null;
		Map<String,ObjKeyVO> objKeyMap = ExcelUtil.getKeysList(configExcelPath,this.keySheetsName);
		try {
			
			for (int i = 0; i < sheetNames.length; i++) {
				sheetName = sheetNames[i];
				if(sheetName.equals(keySheetsName)){
					continue;
				}
				
				this.tranformSheet(configExcelPath, sheetName,objKeyMap,fieldList,jsonSheetList);
			}
			
		} catch (Exception e) {
			System.out.println(String.format("读取配置文件失败，请检查!\n路径：%s\n页名:%s",configExcelPath, sheetName));
			e.printStackTrace();
			System.exit(0);
		}
	}
	private void tranformSheet(String path, String sheetName,Map<String,ObjKeyVO> objKeyMap,ArrayList<ErayClassBean> fieldList,ArrayList<Object> jsonSheetList) throws Exception {
		String[][] data = ExcelUtil.getSheetData(path, sheetName);
		ObjKeyVO ovo = objKeyMap.get(sheetName);
		if(ovo==null){
			return;
		}
		if (data.length == 0) {
			return;
		}
		String xlsName = FilenameUtils.getBaseName(path);
		String className = xlsName + "_"+sheetName;
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		Map<String, Object> SheetMap = new HashMap<String, Object>();
		ArrayList<Object> itemList = new ArrayList<Object>();
		SheetMap.put(className, itemList);
		jsonSheetList.add(SheetMap);
		//Start Create class bean
		ErayClassBean b= new ErayClassBean();
		b.classAttr = className;
		b.classLowerCaseAttr = className.toLowerCase();
		b.type = className;
		DATAVO_INDEX++;
		b.num = DATAVO_INDEX;
		ArrayList<ErayBean> erayBeanList = this.createErayBeanList(data);
		b.fieldList = erayBeanList;
		fieldList.add(b);
		//End Create class bean
		Element subroot = new Element(className);
//		Map<String, Object> subMap= new HashMap<String, Object>();
		String[] assistColumn = data[3];
		if(inputVO.outPutClass){
			FreeMarkerUtils.createClassHandler(className,inputVO,erayBeanList);
		}
		boolean isAssist = false;
		for (int j = 1; j < assistColumn.length; j++) {
			if (assistColumn[j].equals("1") || assistColumn[j].equals("3")) {
				isAssist = true;
				break;
			}
		}
		if (!isAssist) {
			return;
		}
		Element sheetRoot = new Element(className);
		for (int i = 5; i < data.length; i++) {
			Map<String, String> ssubMap = new HashMap<String, String>();
			Element ssubroot = new Element(rootNames[1]);
			Element singleSheetEle = new Element(rootNames[1]);
			String id = data[i][0];
			if(!id.equals("")){
				ssubroot.setAttribute(data[0][0].trim(), data[i][0]);
				singleSheetEle.setAttribute(data[0][0].trim(), data[i][0]);
				ssubMap.put(data[0][0].trim(), data[i][0]);
				for (int j = 1; j < assistColumn.length; j++) {
					if (assistColumn[j].equals("1") || assistColumn[j].equals("3")) {
						ssubroot.setAttribute(data[0][j].trim(), data[i][j]);
						ssubMap.put(data[0][j].trim(), data[i][j]);
					}
					
				}
				subroot.addContent(ssubroot);
				sheetRoot.addContent(singleSheetEle);
//				subMap.put(id, ssubMap);
				itemList.add(ssubMap);
			}
		}
		root.addContent(subroot);
//		mainMap.put(className, subMap);
	}
	private ArrayList<ErayBean> createErayBeanList(String[][] data){
		ArrayList<ErayBean> fieldList = new ArrayList<ErayBean>();
		String [] d = data[0];
		String [] dd = data[3];
		for(int k=0;k<d.length;k++){
			if(d[k].equals("")){
				continue;
			}
			if(dd[k].equals("2")){
				continue;
			}
			ErayBean b = new ErayBean();
			for(int i=0;i<3;i++){
				switch(i){
				case 0:
					b.attName = FreeMarkerUtils.toUpperCaseId(data[i][k]).trim();
					b.attLowerName = b.attName.toLowerCase();
					break;
				case 1:
					b.type = FreeMarkerUtils.lowerCaseFirst(data[i][k]).trim();
					b.smallCaseType = (String)data[i][k].toLowerCase().trim();
					break;
				case 2:
					b.commentStr = data[i][k];
					break;
				}
			}
			b.num = k+1;
			fieldList.add(b);
		}
		return fieldList;
	}
	public List<String> getAllFiles(String absDir) {
		List<String> files = new ArrayList<String>();

		File fileDir = new File(absDir);
		String[] list = fileDir.list();
		for (String s : list) {
			String name = absDir + "/" + s;
			File ins = new File(name);
			if (ins.isFile()) {
				files.add(name);
			} else {
				files.addAll(getAllFiles(name));
			}
		}

		return files;
	}
}
