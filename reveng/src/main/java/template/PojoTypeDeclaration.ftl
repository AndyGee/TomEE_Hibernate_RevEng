/**
* Author: Andy Gumbrecht (c)
*/
<#include "Ejb3TypeDeclaration.ftl"/>/*@EntityListeners(com.tomitribe.spectrum.storage.dao.v1.EntityListener.class)*/
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()} ${tomitribe.getExtendsDeclaration(pojo)} ${tomitribe.getImplementsDeclaration(pojo)}