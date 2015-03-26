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

import org.hibernate.cfg.reveng.ReverseEngineeringRuntimeInfo;
import org.hibernate.cfg.reveng.dialect.JDBCMetaDataDialect;
import org.hibernate.cfg.reveng.dialect.ResultSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Andy Gumbrecht (c)
 */
@SuppressWarnings({"unchecked"})
public class PostgreSQLMetaDataDialect extends JDBCMetaDataDialect {

    private static final Logger log = LoggerFactory.getLogger(PostgreSQLMetaDataDialect.class);

    ReverseEngineeringRuntimeInfo info;

    public void configure(final ReverseEngineeringRuntimeInfo info) {
        this.info = info;
    }

    private Integer getDataType(final Map map, int type) {

        log.debug("Type(" + type + ") : " + map.toString());

        switch (type) {
            case (Types.BIGINT):

                if (map.get("TYPE_NAME").toString().equalsIgnoreCase("oid")) {
                    type = Types.BLOB;
                }

                break;
            case (Types.NVARCHAR):
                type = Types.VARCHAR;
                break;
            case (Types.BINARY):
            case (Types.LONGVARBINARY):
            case (Types.VARBINARY):
                type = Types.BLOB;
                break;
            case (Types.NUMERIC): {
                type = Types.DOUBLE;
                break;
            }
        }

        return type;
    }

    @Override
    public Iterator getColumns(final String catalog, final String schema, final String table, final String column) {
        try {
            log.debug("getColumns(" + catalog + "." + schema + "." + table + "." + column + ")");
            final ResultSet tableRs = this.getMetaData().getColumns(catalog, schema, table, column);

            return new ResultSetIterator(tableRs, PostgreSQLMetaDataDialect.this.getSQLExceptionConverter()) {

                Map element = new HashMap();

                @Override
                protected Object convertRow(final ResultSet rs) throws SQLException {
                    this.element.clear();
                    PostgreSQLMetaDataDialect.this.putTablePart(this.element, rs);

                    this.element.put("TYPE_NAME", rs.getString("TYPE_NAME"));
                    this.element.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
                    this.element.put("NULLABLE", rs.getInt("NULLABLE"));
                    this.element.put("COLUMN_SIZE", rs.getInt("COLUMN_SIZE"));
                    this.element.put("DECIMAL_DIGITS", rs.getInt("DECIMAL_DIGITS"));
                    this.element.put("REMARKS", rs.getString("REMARKS"));
                    this.element.put("COLUMN_DEF", rs.getString("COLUMN_DEF"));
                    this.element.put("TABLE_CAT", rs.getString("TABLE_CAT"));

                    this.element.put("DATA_TYPE", PostgreSQLMetaDataDialect.this.getDataType(this.element, rs.getInt("DATA_TYPE")));

                    return this.element;
                }

                @Override
                protected Throwable handleSQLException(final SQLException e) {
                    throw PostgreSQLMetaDataDialect.this.getSQLExceptionConverter().convert(e, "Error while reading column meta data for " + table, null);
                }
            };
        } catch (final SQLException e) {
            throw this.getSQLExceptionConverter().convert(e, "Error while reading column meta data for " + table, null);
        }
    }
}
