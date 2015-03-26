<#if ejb3>
    <#if pojo.hasIdentifierProperty()>
        <#if property.equals(clazz.identifierProperty)>${tomitribe.generateAnnIdGenerator(pojo)}</#if>
    </#if>
    <#if c2h.isOneToOne(property)>
    ${pojo.generateOneToOneAnnotation(property, cfg)}
    <#elseif c2h.isManyToOne(property)>
    ${tomitribe.generateManyToOneAnnotation(pojo, property, clazz.table.name)}
    <#--TODO support optional and targetEntity-->
    ${pojo.generateJoinColumnsAnnotation(property, cfg)}
    <#elseif c2h.isCollection(property)>
    ${pojo.generateCollectionAnnotation(property, cfg)}
    <#else>${tomitribe.parseAnnotation(pojo, property, clazz.identifierProperty)}${tomitribe.generateBasicAnnotation(pojo,property)}${tomitribe.generateAnnColumnAnnotation(pojo,property)}${tomitribe.annotate(pojo, property, clazz, c2h)}
    </#if></#if>