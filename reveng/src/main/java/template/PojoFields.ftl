<#-- // Fields -->private static final long serialVersionUID = 1L;

<#foreach field in pojo.getAllPropertiesIterator()><#if pojo.getMetaAttribAsBool(field, "gen-property", true)> <#if pojo.hasMetaAttribute(field, "field-description")>    /**
${pojo.getFieldJavaDoc(field, 0)}
*/
</#if><#if tomitribe.isNotHiddenField(field)>    ${pojo.getFieldModifiers(field)} ${tomitribe.getJavaTypeName(pojo,field, jdk5)} ${field.name}${tomitribe.setVariable(pojo,field, jdk5)};
</#if></#if>
</#foreach>
