#include "DataFileWrite.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/io/zero_copy_stream_impl.h>
#include <google/protobuf/io/zero_copy_stream.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/stubs/common.h>
#include <google/protobuf/message_lite.h>
#include <google/protobuf/message.h>
using namespace ERAY_PROTOBUF;
using namespace rapidjson;
using namespace std;
static DataFileWrite* dataInstance = NULL;
const char* DATAFILENAME = "DataVO.dat";
${ClassName}* ${ClassName}::getInstance(){
	if(!dataInstance)
	{
		dataInstance = new ${ClassName}();
	}
	return dataInstance;
}
void DataFileWrite::readJsonFile() {
	FILE *fp = fopen(filename_ = "data/ErayClientData.json", "rb");
	fseek(fp, 0, SEEK_END);
	length_ = (size_t)ftell(fp);
	fseek(fp, 0, SEEK_SET);
	json_ = (char*)malloc(length_ + 1);
	size_t readLength = fread(json_, 1, length_, fp);
	json_[readLength] = '\0';
	fclose(fp);
}
void DataFileWrite::parseToPB(){
	readJsonFile();
	Document doc;
	doc.Parse<kParseDefaultFlags>(json_);
	Value &jsonArray = doc["DataTable"];
	if(jsonArray.IsArray()){
		std::fstream output(DATAFILENAME, std::ios::out | std::ios::trunc | std::ios::binary); 
		output.clear();
		vo = new DataVO();
		for(int i=0;i<jsonArray.Size();++i)
		{
			Value &tableArray = jsonArray[i];
			nextExplain(tableArray);
		}
		string vosString = "";
		if (!vo->SerializePartialToString(&vosString))
		{
			std::cerr<<"Failed to write msg."<<std::endl;
			return;
		}
		else
		{
			output.write(vosString.c_str(),vosString.size());
		}
		output.close();
		std::cerr<<"SuccessDat"<<std::endl;
	}
	//StringBuffer buffer;
    //Writer<StringBuffer> writer(buffer);
    //doc.Accept(writer);
	//std::cout << buffer.GetString() << std::endl;
	free(json_);
    json_ = 0;
}
${ClassName}::${ClassName}(){
	
}

${ClassName}::~${ClassName}(){

}
void ${ClassName}::nextExplain(rapidjson::Value& arg){
	<#list FieldList as field>
	if(arg.HasMember("${field.classAttr}"))
	{
		Value &tempArray = arg["${field.classAttr}"];
		if(tempArray.IsArray())
		{
			for(int j=0;j<tempArray.Size();++j)
			{
				writeBinary${field.classAttr}VO(tempArray[j],vo);
			}
		}
	}
	</#list>
}
<#list FieldList as field>
void ${ClassName}::writeBinary${field.classAttr}VO(rapidjson::Value& arg,${PackageName}::DataVO *vos){
		${field.classAttr}VO* vo = vos->add_${field.classLowerCaseAttr}_datas();
	<#list field.fieldList as itemNField>
		<#if itemNField.type == "string" >
		vo->set_${itemNField.attLowerName}(arg["${itemNField.attName}"].GetString());
		<#elseif itemNField.type == "int32">
		vo->set_${itemNField.attLowerName}(atoi(arg["${itemNField.attName}"].GetString()));
		<#elseif itemNField.type == "int64">
		vo->set_${itemNField.attLowerName}(_atoi64(arg["${itemNField.attName}"].GetString()));
		</#if>
	</#list>
}
</#list>