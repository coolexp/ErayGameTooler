package ${PackageName};
message ${ClassName}VO
{
<#list FieldList as field>
	/**
	 * ${field.commentStr}
	 */		
	optional ${field.type} ${field.attName}= ${field.num};
</#list>
}