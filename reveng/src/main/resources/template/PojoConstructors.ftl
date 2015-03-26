<#--  /** default constructor */ -->
public ${pojo.getDeclarationName()}() {
}

<#if pojo.needsMinimalConstructor()>    <#-- /** minimal constructor */ -->
public ${pojo.getDeclarationName()}(${tomitribe.getParameters(c2j, pojo, jdk5, false)}) {
    <#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassMinimalConstructor().isEmpty()>
    super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassMinimalConstructor())});
    </#if>
    <#foreach field in pojo.getPropertiesForMinimalConstructor()><#if tomitribe.isNotHiddenParameter(field)>
    this.${field.name} = ${field.name};
    </#if></#foreach>
}
</#if>
<#if pojo.needsFullConstructor()>
<#-- /** full constructor */ -->
public ${pojo.getDeclarationName()}(${tomitribe.getParameters(c2j, pojo, jdk5, true)}) {
    <#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassFullConstructor().isEmpty()>
    super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassFullConstructor())});
    </#if>
    <#foreach field in pojo.getPropertiesForFullConstructor()><#if tomitribe.isNotHiddenParameter(field)>
    this.${field.name} = ${field.name};
    </#if></#foreach>
}
</#if>
