/*
 * Copyright (C) 2014 JavaRosa
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
 *
 * Created by Smap
 */

package org.javarosa.core.model.data;

import org.javarosa.core.util.externalizable.ExtUtil;
import org.javarosa.core.util.externalizable.PrototypeFactory;
import org.javarosa.xpath.IExprDataType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A response to a question requesting an GeoCompound Value.
 *
 * @author neilpenman@smap.com.au
 *
 */
public class GeoCompoundData implements IAnswerData, IExprDataType {

    /**
     * GeoCompoundData is managed as a String
     *
     * @author neilpenman@smap.com.au
     *
     */


    private String theData = "";


    /**
     * Empty Constructor, necessary for dynamic construction during
     * deserialization. Shouldn't be used otherwise.
     */
    public GeoCompoundData() {

    }

    /**
     * Copy constructor (deep)
     *
     * @param data
     */
    public GeoCompoundData(GeoCompoundData data) {
        theData = data.theData;
    }

    public GeoCompoundData(String data) {
        theData = data;
    }

    @Override
    public IAnswerData clone() {
        return new GeoCompoundData(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.javarosa.core.model.data.IAnswerData#getDisplayText()
     */
    @Override
    public String getDisplayText() {
        return theData;
    }


    @Override
    public Object getValue() {
        return theData;
    }


    @Override
    public void setValue(Object o) {
        if (o == null) {
            throw new NullPointerException("Attempt to set an IAnswerData class to null.");
        }
        theData = (String)o;
    }


    @Override
    public void readExternal(DataInputStream in, PrototypeFactory pf) throws IOException {
        theData = ExtUtil.readString(in);
    }


    @Override
    public void writeExternal(DataOutputStream out) throws IOException {
        ExtUtil.writeString(out, theData);
    }


    @Override
    public UncastData uncast() {
        return new UncastData(getDisplayText());
    }

    @Override
    public GeoCompoundData cast(UncastData data) throws IllegalArgumentException {
        return new GeoCompoundData(data.value);
    }


    @Override
    public Boolean toBoolean() {
        // return whether or not any Geopoints have been set
        return !(theData == null || theData.length() == 0);
    }

    @Override
    public Double toNumeric() {
        return 0.0;
    }

    @Override
    public String toString() {
        return getDisplayText();
    }
}
