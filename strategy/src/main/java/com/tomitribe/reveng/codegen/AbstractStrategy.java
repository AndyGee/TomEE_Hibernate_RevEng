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

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Andy Gumbrecht (c)
 */
public class AbstractStrategy extends DelegatingReverseEngineeringStrategy {

    protected static final Logger log = LoggerFactory.getLogger(AbstractStrategy.class);

    protected static final String NS_GLOBAL = "com.orprovision.spectrum.storage.dao.v1.";
    protected static final String NS_RECIPE = "com.orprovision.spectrum.storage.dao.v1.recipe.";
    protected static final String NS_LIBRARY = "com.orprovision.spectrum.storage.dao.v1.library.";
    protected static final String NS_RESULT = "com.orprovision.spectrum.storage.dao.v1.result.";
    protected static final String NS_TREE = "com.orprovision.spectrum.storage.dao.v1.tree.";
    protected static final String NS_OCR = "com.orprovision.spectrum.storage.dao.v1.ocr.";
    protected static final String NS_USER = "com.orprovision.spectrum.storage.dao.v1.user.";
    protected static final String NS_MACHINE = "com.orprovision.spectrum.storage.dao.v1.machine.";
    protected static final String NS_SERVER = "com.orprovision.spectrum.storage.dao.v1.server.";
    protected static final String NS_COLLECTION = "com.orprovision.spectrum.storage.dao.v1.collection.";
    protected static final String NS_MODEL = "com.orprovision.spectrum.storage.dao.v1.model.";
    protected static final String NS_CAD = "com.orprovision.spectrum.storage.dao.v1.cad.";
    protected static final String NS_REPORT = "com.orprovision.spectrum.storage.dao.v1.report.";

    public static final Set<String> GLOBAL = new HashSet<String>();
    public static final Set<String> RECIPE = new HashSet<String>();
    public static final Set<String> LIBRARY = new HashSet<String>();
    public static final Set<String> RESULT = new HashSet<String>();
    public static final Set<String> TREE = new HashSet<String>();
    public static final Set<String> OCR = new HashSet<String>();
    public static final Set<String> USER = new HashSet<String>();
    public static final Set<String> MACHINE = new HashSet<String>();
    public static final Set<String> SERVER = new HashSet<String>();
    public static final Set<String> COLLECTION = new HashSet<String>();
    public static final Set<String> MODEL = new HashSet<String>();
    public static final Set<String> CAD = new HashSet<String>();
    public static final Set<String> REPORT = new HashSet<String>();

    static {
        GLOBAL.add("Comment");
        GLOBAL.add("Uncommitted");
        GLOBAL.add("Property");
        GLOBAL.add("PropertyValue");
        GLOBAL.add("PropertyValueLob");
        GLOBAL.add("Iolock");
        GLOBAL.add("Contextlock");

        RECIPE.add("Recipe");
        RECIPE.add("RecipeProperty");
        RECIPE.add("RecipeBackgroundFullLob");
        RECIPE.add("RecipeBackgroundLob");
        RECIPE.add("RecipeBackgroundPropsLob");
        RECIPE.add("RecipeBackgroundBareLob");
        RECIPE.add("RecipeCadLob");
        RECIPE.add("RecipeCadParseLob");
        RECIPE.add("RecipeCadGeoLob");
        RECIPE.add("RecipeHistoryLob");
        RECIPE.add("RecipeThumbnailLob");
        RECIPE.add("RecipeVersion");
        RECIPE.add("RecipeVersionProperty");
        RECIPE.add("RecipeVersionLob");
        RECIPE.add("Frame");
        RECIPE.add("FrameComponent");
        RECIPE.add("Board");
        RECIPE.add("BoardComponent");
        RECIPE.add("BoardMini");
        RECIPE.add("BoardMiniComponent");
        RECIPE.add("Component");
        RECIPE.add("ComponentLegacy");
        RECIPE.add("ComponentProperty");

        LIBRARY.add("Pack");
        LIBRARY.add("PackProperty");
        LIBRARY.add("PackRecipeLob");
        LIBRARY.add("PackThumbnailLob");
        LIBRARY.add("LibComponent");
        LIBRARY.add("LibComponentArray");
        LIBRARY.add("LibComponentProperty");
        LIBRARY.add("LibComponentImageLob");
        LIBRARY.add("LibComponentThumbnailLob");

        MACHINE.add("Machine");
        MACHINE.add("MachineProperty");
        MACHINE.add("MachineLine");
        MACHINE.add("MachineStage");
        MACHINE.add("MachineType");

        RESULT.add("TestResult");
        RESULT.add("TestResultLegacy");
        RESULT.add("TestResultProperty");
        RESULT.add("TestResultBarcode");
        RESULT.add("TestResultFrame");
        RESULT.add("TestItem");
        RESULT.add("TestItemChild");
        RESULT.add("TestItemProperty");
        RESULT.add("TestItemLob");
        RESULT.add("TestItemImage");
        RESULT.add("TestItemImageProperty");
        RESULT.add("TestItemImageLob");
        RESULT.add("TestItemImagePropsLob");
        RESULT.add("TestItemDefect");
        RESULT.add("TestItemResult");
        RESULT.add("TestItemChildResult");

        TREE.add("TreeNode");
        TREE.add("TreeNodeProperty");

        OCR.add("OcrFont");
        OCR.add("OcrFontProperty");
        OCR.add("OcrFontLob");
        OCR.add("OcrFontCompareLob");

        USER.add("AppUser");
        USER.add("AppUserProperty");
        USER.add("AppUserRole");
        USER.add("AppRole");

        SERVER.add("Preference");
        SERVER.add("PreferenceProperty");

        COLLECTION.add("Collection");
        COLLECTION.add("CollectionProperty");
        COLLECTION.add("CollectionItem");
        COLLECTION.add("CollectionVersion");

        MODEL.add("ModelTraining");
        MODEL.add("ModelTrainingProperty");
        MODEL.add("ModelTrainingVersion");
        MODEL.add("ModelTrainingVersionLob");
        MODEL.add("ModelTrainingVersionFilterLob");
        MODEL.add("ModelTrainingVersionPatternLob");

        CAD.add("Cad");
        CAD.add("CadProperty");
        CAD.add("CadLob");

        REPORT.add("Report");
        REPORT.add("ReportProperty");
        REPORT.add("ReportLob");
        REPORT.add("ReportItem");
        REPORT.add("ReportItemLob");
    }

    public AbstractStrategy(final ReverseEngineeringStrategy delegate) {
        super(delegate);
    }
}
