<#if ejb3?if_exists>
    <#if pojo.isComponent()>
    @${pojo.importType("javax.persistence.Embeddable")}
    <#else>
    @${pojo.importType("javax.persistence.Entity")}
    @${pojo.importType("javax.persistence.Table")}(name = "${clazz.table.name}"<#if clazz.table.schema?exists>,schema="${clazz.table.schema}"</#if><#if clazz.table.catalog?exists>,catalog="${clazz.table.catalog}"</#if><#assign uniqueConstraint=pojo.generateAnnTableUniqueConstraint()><#if uniqueConstraint?has_content>, uniqueConstraints = ${uniqueConstraint}</#if>)
    ${tomitribe.SQLCheck(clazz.table.name)}
    //@org.hibernate.annotations.Entity(dynamicUpdate = true)
    </#if>
</#if>