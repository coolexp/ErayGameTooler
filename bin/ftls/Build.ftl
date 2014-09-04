<?xml version="1.0"?>
<project name="solveFiles">
       <target name="copyFiles">
      	 <copy todir="${TPath}">
      	 	<fileset dir="${SPath}">
				<#list FieldList as field>
				<include name="${field.classAttr}VO.pb.cc" />
				</#list>
				<#list FieldList as field>
				<include name="${field.classAttr}VO.pb.h" />
				</#list>
				<include name="DataVO.pb.cc" />
				<include name="DataVO.pb.h" />
				<include name="IncludeVO.h" />
          	</fileset>
       </copy>
       </target>
       <target name="sayReady" depends="copyFiles">
	       	<delete includeEmptyDirs="true">
	       		<fileset dir="${SPath}">
					<#list FieldList as field>
					<include name="${field.classAttr}VO.pb.cc" />
					</#list>
					<#list FieldList as field>
					<include name="${field.classAttr}VO.pb.h" />
					</#list>
					<include name="DataVO.pb.cc" />
					<include name="DataVO.pb.h" />
					<include name="IncludeVO.h" />
					<include name="DataFileWrite.h" />
					<include name="DataFileWrite.cpp" />
					<include name="DataVO.bat" />
	       		</fileset>
	       	</delete>
			<delete dir="data"/>
			<delete dir="proto"/>
       </target>
</project>