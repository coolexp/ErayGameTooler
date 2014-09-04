package ${PackageName};
<#list FieldList as field>	
	import "${field.classAttr}VO.proto";
</#list>
message ${ClassName}
{
<#list FieldList as field>	
	repeated ${field.type}VO ${field.classAttr}_datas= ${field.num};
</#list>
}