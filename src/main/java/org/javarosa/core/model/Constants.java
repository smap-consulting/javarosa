/*
 * Copyright (C) 2009 JavaRosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javarosa.core.model;

/**
 * Constants shared throughout classes in the containing package.
 * <p/>
 * Where possible use {@link DataType} instead of the DATATYPE_*
 * “int enum” fields.
 */
public class Constants {

    public static final String EMPTY_STRING = "";

    public static final int NO_SELECTION = -1;

    /** ID not set to a value */
    public static final int NULL_ID = -1;

    /** Connection type not specified */
    public static final int CONNECTION_NONE = 0;

    /** Infrared connection */
    public static final int CONNECTION_INFRARED = 1;

    /** Bluetooth connection */
    public static final int CONNECTION_BLUETOOTH = 2;

    /** Data cable connection. Can be USB or Serial. */
    public static final int CONNECTION_CABLE = 3;

    /** Over The Air or HTTP Connection */
    public static final int CONNECTION_OTA = 4;

    public static final int DATATYPE_UNSUPPORTED    = -1;
    public static final int DATATYPE_NULL           = 0;
    public static final int DATATYPE_TEXT           = 1;
    public static final int DATATYPE_INTEGER        = 2;
    public static final int DATATYPE_DECIMAL        = 3;
    public static final int DATATYPE_DATE           = 4;
    public static final int DATATYPE_TIME           = 5;
    public static final int DATATYPE_DATE_TIME      = 6;
    public static final int DATATYPE_CHOICE         = 7;
    /** A list of items used for selecting multiple answers or ordering them */
    public static final int DATATYPE_MULTIPLE_ITEMS = 8;
    /** The same as {@link Constants#DATATYPE_MULTIPLE_ITEMS} (for backwards compatibility) */
    public static final int DATATYPE_CHOICE_LIST    = 8;
    public static final int DATATYPE_BOOLEAN        = 9;
    public static final int DATATYPE_GEOPOINT       = 10;
    public static final int DATATYPE_BARCODE        = 11;
    public static final int DATATYPE_BINARY         = 12;
    public static final int DATATYPE_LONG           = 13;
    public static final int DATATYPE_GEOSHAPE       = 14;
    public static final int DATATYPE_GEOTRACE       = 15;
    public static final int DATATYPE_GEOCOMPOUND       = 16; // smap

    public static final int CONTROL_UNTYPED         = -1;
    public static final int CONTROL_INPUT           = 1;
    public static final int CONTROL_SELECT_ONE      = 2;
    public static final int CONTROL_SELECT_MULTI    = 3;
    public static final int CONTROL_TEXTAREA        = 4;
    public static final int CONTROL_SECRET          = 5;
    public static final int CONTROL_RANGE           = 6;
    public static final int CONTROL_UPLOAD          = 7;
    public static final int CONTROL_SUBMIT          = 8;
    public static final int CONTROL_TRIGGER         = 9;
    public static final int CONTROL_IMAGE_CHOOSE    = 10;
    public static final int CONTROL_LABEL           = 11;
    public static final int CONTROL_AUDIO_CAPTURE   = 12;
    public static final int CONTROL_VIDEO_CAPTURE   = 13;
    public static final int CONTROL_OSM_CAPTURE     = 14;
    /** generic upload */
    public static final int CONTROL_FILE_CAPTURE    = 15;
    public static final int CONTROL_RANK            = 16;

    /* constants for xform tags */
    public static final String XFTAG_UPLOAD = "upload";
}
