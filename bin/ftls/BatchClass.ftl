protoc -I=${BasePath}\proto --cpp_out=${BasePath} <#list FieldList as field>${BasePath}\proto\${field.classAttr}VO.proto </#list>${BasePath}\proto\${ClassName}.proto
g++ ParsePB.cpp DataFileWrite.cpp *.cc libprotobuf.dll -isystem ${ClassPath} -o Generate.exe
Generate.exe
ant sayReady
echo "PackageSuccess"
pause