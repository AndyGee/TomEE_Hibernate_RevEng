<#if pojo.hasMetaAttribute("class-code")>
${pojo.getExtraClassCode()}
</#if>
@Override
public int hashCode() {
return (null != this.id ? this.id.hashCode() : super.hashCode());
}

@Override
public boolean equals(final Object o) {
if (this == o) return true;
if (null == o || getClass() != o.getClass()) return false;

final ${pojo.getDeclarationName()} that = (${pojo.getDeclarationName()}) o;
return !(null != this.id ? !this.id.equals(that.id) : null != that.id);
}