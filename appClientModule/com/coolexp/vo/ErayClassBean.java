package com.coolexp.vo;

import java.util.ArrayList;

public class ErayClassBean {
	public int num;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String classAttr;
	public String getClassAttr() {
		return classAttr;
	}
	public void setClassAttr(String classAttr) {
		this.classAttr = classAttr;
	}
	public ArrayList<ErayBean> fieldList;
	public ArrayList<ErayBean> getFieldList() {
		return fieldList;
	}
	public void setFunctionName(ArrayList<ErayBean> fieldList) {
		this.fieldList = fieldList;
	}
	
	public String classLowerCaseAttr;
	public String getClassLowerCaseAttr() {
		return classLowerCaseAttr;
	}
	public void setClassLowerCaseAttr(String classLowerCaseAttr) {
		this.classLowerCaseAttr = classLowerCaseAttr;
	}
}
