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

import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

import java.util.Map;

/**
 * Author: Andy Gumbrecht (c)
 * The Strategy is designed to generate code that is consistent and correct.
 */
public class DefaultStrategy extends AbstractStrategy {

    public DefaultStrategy(final ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    @Override
    public String tableToClassName(final TableIdentifier tableIdentifier) {
        String name = super.tableToClassName(tableIdentifier);

        if (GLOBAL.contains(name)) {
            name = (NS_GLOBAL + name);
        } else if (RECIPE.contains(name)) {
            name = (NS_RECIPE + name);
        } else if (LIBRARY.contains(name)) {
            name = (NS_LIBRARY + name);
        } else if (RESULT.contains(name)) {
            name = (NS_RESULT + name);
        } else if (TREE.contains(name)) {
            name = (NS_TREE + name);
        } else if (OCR.contains(name)) {
            name = (NS_OCR + name);
        } else if (USER.contains(name)) {
            name = (NS_USER + name);
        } else if (MACHINE.contains(name)) {
            name = (NS_MACHINE + name);
        } else if (SERVER.contains(name)) {
            name = (NS_SERVER + name);
        } else if (COLLECTION.contains(name)) {
            name = (NS_COLLECTION + name);
        } else if (MODEL.contains(name)) {
            name = (NS_MODEL + name);
        } else if (CAD.contains(name)) {
            name = (NS_CAD + name);
        } else if (REPORT.contains(name)) {
            name = (NS_REPORT + name);
        } else {
            //throw new IllegalArgumentException("Strategy.tableToClassName - Unknown table name: " + name);
            name = "com.tomitribe.reveng.dao." + name;
        }

        name += "Entity";

        return name;
    }

    @Override
    public Map tableToMetaAttributes(final TableIdentifier tableIdentifier) {
        final Map m = super.tableToMetaAttributes(tableIdentifier);

        if (null != m) {
            for (final Object key : m.keySet()) {
                log.info("tableToMetaAttributes: " + key + " - " + m.get(key));
            }
        }

        return m;
    }

    @Override
    public Map columnToMetaAttributes(final TableIdentifier tableIdentifier, final String s) {
        final Map m = super.columnToMetaAttributes(tableIdentifier, s);

        if (null != m) {
            for (final Object key : m.keySet()) {
                log.info("columnToMetaAttributes: " + tableIdentifier.getName() + " - " + key + " - " + m.get(key));
            }
        }

        return m;
    }
}
