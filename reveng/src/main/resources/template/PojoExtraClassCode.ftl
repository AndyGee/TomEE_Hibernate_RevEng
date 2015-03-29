<#if pojo.hasMetaAttribute("class-code")>
${pojo.getExtraClassCode()}
</#if>
@Override
public int hashCode() {
return (int)this.id;
}

@Override
public boolean equals(final Object o) {
if (this == o) return true;
if (null == o || getClass() != o.getClass()) return false;

final ${pojo.getDeclarationName()} that = (${pojo.getDeclarationName()}) o;
return this.id == that.id;
}