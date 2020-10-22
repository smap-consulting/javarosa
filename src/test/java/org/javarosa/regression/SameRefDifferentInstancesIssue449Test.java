/*
 * Copyright 2020 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javarosa.regression;

import static org.hamcrest.Matchers.is;
import static org.javarosa.core.test.AnswerDataMatchers.stringAnswer;
import static org.javarosa.core.util.BindBuilderXFormsElement.bind;
import static org.javarosa.core.util.XFormsElement.body;
import static org.javarosa.core.util.XFormsElement.head;
import static org.javarosa.core.util.XFormsElement.html;
import static org.javarosa.core.util.XFormsElement.input;
import static org.javarosa.core.util.XFormsElement.mainInstance;
import static org.javarosa.core.util.XFormsElement.model;
import static org.javarosa.core.util.XFormsElement.t;
import static org.javarosa.core.util.XFormsElement.title;
import static org.javarosa.test.utils.ResourcePathHelper.r;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.javarosa.core.reference.ReferenceManagerTestUtils;
import org.javarosa.core.test.Scenario;
import org.javarosa.core.util.externalizable.DeserializationException;
import org.junit.Test;

public class SameRefDifferentInstancesIssue449Test {
    @Test
    public void formWithSameRefInDifferentInstances_isSuccessfullyDeserialized() throws IOException, DeserializationException {
        Path formFile = r("issue_449.xml");
        ReferenceManagerTestUtils.setUpSimpleReferenceManager(formFile.getParent(), "file");
        Scenario scenario = Scenario.init(formFile);

        scenario.answer("/data/new-part", "c");
        assertThat(scenario.answerOf("/data/aggregated"), is(stringAnswer("a b c")));

        Scenario deserialized = scenario.serializeAndDeserializeForm();
        assertThat(deserialized.answerOf("/data/new-part[0]"), is(stringAnswer("c")));
        assertThat(deserialized.answerOf("/data/aggregated[0]"), is(stringAnswer("a b c")));

        deserialized.answer("/data/new-part", "c2");
        assertThat(deserialized.answerOf("/data/aggregated[0]"), is(stringAnswer("a b c2")));
    }

    @Test
    public void constraintsAreCorrectlyApplied_afterDeserialization() throws IOException, DeserializationException {
        Scenario scenario = Scenario.init("Tree reference deserialization", html(
            head(
                title("Tree reference deserialization"),
                model(
                    mainInstance(t("data id=\"treeref-deserialization\"",
                        t("a", "not ok"),
                        t("b")
                    )),
                    bind("/data/a").type("string"),
                    bind("/data/b").type("string").constraint(". != /data/a")
                )
            ),
            body(
                input("/data/b")
            )));

        scenario.next();
        scenario.answer("ok");
        MatcherAssert.assertThat(scenario.answerOf("/data/b[0]"), CoreMatchers.is(stringAnswer("ok")));

        scenario.answer("not ok");
        MatcherAssert.assertThat(scenario.answerOf("/data/b[0]"), CoreMatchers.is(stringAnswer("ok")));

        Scenario deserialized = scenario.serializeAndDeserializeForm();

        deserialized.next();
        deserialized.answer("ok");
        MatcherAssert.assertThat(deserialized.answerOf("/data/b[0]"), CoreMatchers.is(stringAnswer("ok")));

        deserialized.answer("not ok");
        MatcherAssert.assertThat(deserialized.answerOf("/data/b[0]"), CoreMatchers.is(stringAnswer("ok")));
    }
}
