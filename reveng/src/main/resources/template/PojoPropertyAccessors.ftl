<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
    <#if pojo.getMetaAttribAsBool(property, "gen-property", true)><#if tomitribe.isNotHiddenAccessor(property)>
        <#if pojo.hasFieldJavaDoc(property)>
        /**
        * ${pojo.getFieldJavaDoc(property, 4)}
        */
        </#if>
        <#include "GetPropertyAnnotation.ftl"/>
    ${pojo.getPropertyGetModifiers(property)} ${tomitribe.getJavaTypeName(pojo,property, jdk5)} ${pojo.getGetterSignature(property)}() {
    return this.${property.name};
    }

    ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(final ${tomitribe.getJavaTypeName(pojo,property, jdk5)} ${property.name}) {
    this.${property.name} = ${property.name};
    }
    </#if></#if>
</#foreach>
