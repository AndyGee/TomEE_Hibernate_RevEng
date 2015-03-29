/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tomitribe.reveng.codegen;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Selectable;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.Value;
import org.hibernate.tool.hbm2x.Cfg2HbmTool;
import org.hibernate.tool.hbm2x.Cfg2JavaTool;
import org.hibernate.tool.hbm2x.pojo.BasicPOJOClass;
import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.ImportContext;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

/**
 * Author: Andy Gumbrecht (c)
 */
@SuppressWarnings({ "UseOfSystemOutOrSystemErr", "UnusedDeclaration" })
public class FreemarkerObject {

	public FreemarkerObject() {
		System.out.println("FreemarkerObject initialized");
	}

	public String getDate() {
		return DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
	}

	public String generateAnnIdGenerator(final POJOClass pojo) {
		return trim(pojo.generateAnnIdGenerator());
	}

	private static String trim(String s) {

		if (null != s) {
			s = s.trim();
			s = s.replace("\r\n", "");
			s = s.replace("\r", "");
			s = s.replace("\n", "");
		}

		return s;
	}

	public String parseAnnotation(final POJOClass pojo, final Property p, final Property id) {

		// if (p.equals(id)) {
		// // This is the 'id'
		// return "@Override\n";
		// }
		//
		// if ("version".equals(p.getName())) {
		// return "@Override\n";
		// }
		//
		// if ("data".equals(p.getName())) {
		// // return "@Lob\n@Type(type = \"org.hibernate.type.BinaryType\")";
		// return "@Lob\n@Basic(fetch=FetchType.LAZY)\n";
		// }

		return "@Basic";
	}

	public String generateImports(final POJOClass pojo) {
		return pojo.generateImports()
				+ "import org.hibernate.annotations.Type;\nimport org.hibernate.annotations.SQLInsert;\nimport javax.persistence.*;\nimport org.hibernate.annotations.ForeignKey;\nimport org.hibernate.annotations.Index;\n";
	}

	public String getExtendsDeclaration(final POJOClass pojo) {
		String s = pojo.getExtendsDeclaration();

		if (null == s) {
			s = "";
		}

		// if (s.length() < 1) {
		// s = "extends com.tomitribe.reveng.dao.AbstractEntity";
		// }

		return s;
	}

	public String getImplementsDeclaration(final POJOClass pojo) {
		String implementz = pojo.getImplementsDeclaration();

		if (null == implementz) {
			implementz = "";
		}

		// if (SomeMap.contains(pojo.getDeclarationName().replace("Entity",
		// ""))) {
		//
		// if (implementz.length() > 0) {
		// implementz += ",";
		// } else {
		// implementz += " implements ";
		// }
		//
		// implementz += "some.Interface";
		// }

		// if (implementz.length() > 0) {
		// implementz += ",";
		// } else {
		// implementz += " implements ";
		// }
		//
		// implementz += "com.tomitribe.reveng.dao.BasicEntity";

		return implementz;
	}

	public String generateBasicAnnotation(final POJOClass pojo, final Property p) {

		final String s = trim(pojo.generateBasicAnnotation(p));

		if ("id".equals(p.getName())) {

		}

		return s;

	}

	public String generateAnnColumnAnnotation(final POJOClass pojo, final Property p) {
		String s = pojo.generateAnnColumnAnnotation(p);

		// if (!p.isComposite() && p.getColumnSpan() == 1) {
		// Selectable selectable = (Selectable) p.getColumnIterator().next();
		//
		// if (!selectable.isFormula()) {
		// Column column = (Column) selectable;
		//
		// if ("body_size_x".equals(column.getName())) {
		// log.info(column.toString());
		// log.info(column.getLength());
		// log.info(column.getSqlTypeCode());
		// }
		//
		//
		// }
		// }

		s = trim(s);

		if ("data".equals(p.getName()) && !s.contains(", length=")) {

			s = s.substring(0, (s.lastIndexOf(')')));
			// s += ", length=Integer.MAX_VALUE)";
			s += ")";

		} else if ("id".equals(p.getName())) {

		} else {

			s = ("\n" + s);
		}

		if ("storestamp".equals(p.getName())) {
			s = s.replace(", length=29", ", insertable = false, updatable = false, columnDefinition=\"timestamp not null default current_timestamp\"");
		}

		// Never have this on a Column - always in annotation @UniqueConstraint
		s = s.replace(", unique=true", "");

		// if (s.indexOf("\"version\"") > -1) {
		// s = s.replace("\"version\"", "\"`version`\"");
		// }
		//
		// if (s.indexOf("\"valid\"") > -1) {
		// s = s.replace("\"valid\"", "\"`valid`\"");
		// }
		//
		// if (s.indexOf("\"type\"") > -1) {
		// s = s.replace("\"type\"", "\"`type`\"");
		// }
		//
		System.out.println("Column: " + s);

		return s;
	}

	public String generateManyToOneAnnotation(final BasicPOJOClass pojo, final Property p, String table) {

		// @ForeignKey(name="FK_recipe_tree_node")
		final Value value = p.getValue();
		final int span;
		final Iterator columnIterator;
		if (value != null && value instanceof Collection) {
			final Collection collection = (Collection) value;
			span = collection.getKey().getColumnSpan();
			columnIterator = collection.getKey().getColumnIterator();
		} else {
			span = p.getColumnSpan();
			columnIterator = p.getColumnIterator();
		}
		final Selectable selectable = (Selectable) columnIterator.next();
		final Column column = (Column) selectable;
		table += "_" + column.getName(); // id_tree_node
		table = "FK_" + table.replace("_id_", "_");

		String s = EntityPOJOClass.class.cast(pojo).generateManyToOneAnnotation(p);
		s += "\n@ForeignKey(name=\"" + table + "\")";

		// log.info(s);

		return s;
	}

	public String getJavaTypeName(final BasicPOJOClass pojo, final Property field, final boolean jdk5) {
		String s = "";

		s = pojo.getJavaTypeName(field, jdk5);

		s = s.trim();

		if (s.equalsIgnoreCase("Blob")) {
			System.out.println("" + s);
		}

		// if (s.equalsIgnoreCase("Integer")) {
		// s = "int";
		// } else if (s.equalsIgnoreCase("Long")) {
		// s = "long";
		// }

		return s;
	}

	public String setVariable(final BasicPOJOClass pojo, final Property field, final boolean jdk5) {

		String s = "";

		if (pojo.hasFieldInitializor(field, jdk5)) {
			s += " = " + pojo.getFieldInitialization(field, jdk5);
		} else {
			if ("boolean".equals(pojo.getJavaTypeName(field, jdk5))) {
				s += " = false";
			}
		}

		return s;
	}

	public String getEntityName(final BasicPOJOClass pojo) {
		String pkg = (pojo.getPackageName().replace("com.tomitribe.reveng.dao", ""));

		if (pkg.startsWith(".")) {
			pkg = pkg.substring(1);
		}

		if (pkg.length() > 0) {
			pkg += ".";
		}

		return (pkg + (pojo.getDeclarationName().replace("Entity", "")));
	}

	public String getParameters(final Cfg2JavaTool c2j, final BasicPOJOClass pojo, final boolean jdk5, final boolean full) {
		//
		// final List fullConstructor =
		// pojo.getPropertyClosureForFullConstructor();
		// final List minimalConstructor =
		// pojo.getPropertyClosureForMinimalConstructor();
		//
		// final List fullConstructorParsed = new ArrayList();
		// final List minimalConstructorParsed = new ArrayList();
		//
		// for (final Object o : fullConstructor) {
		// final org.hibernate.mapping.Property p = (Property) o;
		//
		// if (!"storestamp".equals(p.getName())) {
		// fullConstructorParsed.add(p);
		// break;
		// }
		// }
		//
		// for (final Object o : minimalConstructor) {
		// final org.hibernate.mapping.Property p = (Property) o;
		//
		// if (!"storestamp".equals(p.getName())) {
		// minimalConstructorParsed.add(p);
		// break;
		// }
		// }

		final String cp = this.asParameterList(c2j, (full ? pojo.getPropertyClosureForFullConstructor() : pojo.getPropertyClosureForMinimalConstructor()), jdk5, pojo);
		return cp.replace(", final Date storestamp", "");
	}

	public String asParameterList(final Cfg2JavaTool c2j, final Iterator fields, final boolean useGenerics, final ImportContext ic) {
		final StringBuilder sb = new StringBuilder();
		while (fields.hasNext()) {
			final Property field = (Property) fields.next();
			sb.append("final ");
			sb.append(c2j.getJavaTypeName(field, useGenerics, ic)).append(" ").append(field.getName());
			if (fields.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	public String asParameterList(final Cfg2JavaTool c2j, final java.util.List fields, final boolean useGenerics, final ImportContext ic) {
		return this.asParameterList(c2j, fields.iterator(), useGenerics, ic);
	}

	public String annotate(final BasicPOJOClass pojo, final Object pObj, final Object rootObj, final Object toolObj) {
		// public String annotate(final Object pojo, final Property p, final
		// RootClass root, final Cfg2HbmTool tool) {

		if (BasicPOJOClass.class.isInstance(pojo)) {

		} else {
			// System.out.println(ob.class.getName());
		}

		RootClass root = null;
		try {
			root = (RootClass) rootObj;
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return "";
		}

		final Property p = (Property) pObj;
		final Cfg2HbmTool tool = (Cfg2HbmTool) toolObj;

		final Table table = root.getTable();

		Iterator it = table.getColumnIterator();

		Column column = null;
		Column c;
		String name;
		while (it.hasNext()) {
			c = (Column) it.next();

			name = c.getName().replace("_", "").toLowerCase();

			if (name.equals(p.getName().toLowerCase())) {
				column = c;
				break;
			}
		}

		if (null != column) {

			System.out.print("FreemarkerObject.annotate: " + table.getName() + " - " + p.getName() + " - ");

			it = table.getIndexIterator();

			org.hibernate.mapping.Index index;
			while (it.hasNext()) {

				final Object next = it.next();

				if (org.hibernate.mapping.Index.class.isInstance(next)) {

					index = org.hibernate.mapping.Index.class.cast(next);

					if (index.containsColumn(column)) {
						System.out.print(index.getName());

						return String.format("\n@Index(name = \"%1$s\", columnNames = {\"%2$s\"})", index.getName().toLowerCase(), column.getName());
					}
				}
			}

			System.out.println();
		} else {
			System.out.println("FreemarkerObject.annotate: " + table.getName() + " - " + p.getName() + " - " + p.getNodeName() + " - " + p.getPropertyAccessorName());
		}

		return "";
	}

	public String SQLCheck(final String table) {

		if ("test_item".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item (angle, id_component, description, designation, failed, node, nr, nr_verified, occurred, ocr, passed, pin, pos_valid, pos_x, pos_y, pos_z, qref, reference_id, repaired, size_x, size_y, sort, subtype, id_test_item, id_test_result, type, valid, version, id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\")";
		} else if ("test_item_result".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_result (failed, failed_verified, node, nr, nr_verified, occurred, repaired, sort, subtype, id_test_item, type, valid, version, id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)\")";
		} else if ("test_item_child".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_child (failed, node, nr, nr_verified, occurred, ocr, passed, pin, qref, repaired, sort, subtype, id_test_item, type, valid, version, id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\")";
		} else if ("test_item_child_result".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_child_result (failed, failed_verified, node, nr, nr_verified, occurred, repaired, sort, subtype, id_test_item_child, type, valid, version, id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)\")";
		} else if ("test_item_image".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_image (angle, name, size_x, size_y, sort, id_test_item, tooltip, version, view_id, x, y, id) values (?,?,?,?,?,?,?,?,?,?,?,?)\")";
		} else if ("test_item_image_lob".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_image_lob (data, hash, mime_type, id_test_item_image, uri, version, id) values (?,?,?,?,?,?,?)\")";
		} else if ("test_item_image_property".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_image_property (id_comment, id_property, id_property_value, id_test_item_image, version, id) values (?,?,?,?,?,?)\")";
		} else if ("test_item_image_props_lob".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_image_props_lob (data, hash, mime_type, id_test_item_image, uri, version, id) values (?,?,?,?,?,?,?)\")";
		} else if ("test_item_lob".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_lob (data, hash, mime_type, id_test_item, uri, version, id) values (?,?,?,?,?,?,?)\")";
		} else if ("test_item_property".equals(table.toLowerCase())) {
			return "@SQLInsert(sql = \"insert into test_item_property (id_comment, id_property, id_property_value, id_test_item, version, id) values (?,?,?,?,?,?)\")";
		}

		return "";
	}

	public boolean isNotHiddenField(final Property property) {

		// System.out.println("property = " + property.getName());

		return true;// !"storestamp".equals(property.getName());
	}

	public boolean isNotHiddenAccessor(final Property property) {

		// System.out.println("property = " + property.getName());

		return true;// !"storestamp".equals(property.getName());
	}

	public boolean isNotHiddenParameter(final Property property) {

		// System.out.println("property = " + property.getName());

		return !"storestamp".equals(property.getName());
	}
}
