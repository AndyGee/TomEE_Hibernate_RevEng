<#if pojo.needsEqualsHashCode() && !clazz.superclass?exists>   public boolean equals(final Object other) {
if ( (this == other ) ) return true;
if ( (other == null ) ) return false;
if ( !(other instanceof ${pojo.getDeclarationName()}) ) return false;
final ${pojo.getDeclarationName()} castOther = ( ${pojo.getDeclarationName()} ) other;

return ${pojo.generateEquals()};
}

public int hashCode() {
int result = 17;

    <#foreach property in pojo.getAllPropertiesIterator()>         ${pojo.generateHashCode(property, "result", "this", jdk5)}
    </#foreach>         return result;
}
</#if>