#ifndef WRITE_CLASS_H
#define WRITE_CLASS_H
#include <iostream>
#include <sstream>
#include <fstream>
#include "DataVO.pb.h"
#include "IncludeVO.h"
#include "rapidjson/document.h"
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"
class ${ClassName}{
private:
	const char* filename_;
	char *json_;
	size_t length_;
	ERAY_PROTOBUF::DataVO *vo;
	void readJsonFile();
public:
	void nextExplain(rapidjson::Value& arg);
	void parseToPB();
	DataFileWrite();
	~DataFileWrite();
	static DataFileWrite* getInstance();
	<#list FieldList as field>
	void writeBinary${field.classAttr}VO(rapidjson::Value& arg,${PackageName}::DataVO *vos);
	</#list>
};
#endif