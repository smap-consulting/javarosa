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

package org.javarosa.core.model.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javarosa.core.test.AnswerDataMatchers.intAnswer;
import static org.javarosa.core.test.AnswerDataMatchers.stringAnswer;
import static org.javarosa.core.util.BindBuilderXFormsElement.bind;
import static org.javarosa.core.util.XFormsElement.body;
import static org.javarosa.core.util.XFormsElement.head;
import static org.javarosa.core.util.XFormsElement.html;
import static org.javarosa.core.util.XFormsElement.input;
import static org.javarosa.core.util.XFormsElement.mainInstance;
import static org.javarosa.core.util.XFormsElement.model;
import static org.javarosa.core.util.XFormsElement.repeat;
import static org.javarosa.core.util.XFormsElement.setvalue;
import static org.javarosa.core.util.XFormsElement.setvalueLiteral;
import static org.javarosa.core.util.XFormsElement.t;
import static org.javarosa.core.util.XFormsElement.title;

import java.io.IOException;
import org.javarosa.core.test.Scenario;
import org.javarosa.core.util.externalizable.DeserializationException;
import org.junit.Test;

public class SetValueActionTest {
    @Test
    public void when_triggerNodeIsUpdated_targetNodeCalculation_isEvaluated() throws IOException {
        Scenario scenario = Scenario.init("Nested setvalue action", html(
            head(
                title("Nested setvalue action"),
                model(
                    mainInstance(t("data id=\"nested-setvalue\"",
                        t("source"),
                        t("destination")
                    )),
                    bind("/data/source").type("int"),
                    bind("/data/destination").type("int")
                )
            ),
            body(
                input("/data/source",
                    setvalue("xforms-value-changed", "/data/destination", "4*4"))
            )));

        assertThat(scenario.answerOf("/data/destination"), is(nullValue()));

        scenario.next();
        scenario.answer(22);

        assertThat(scenario.answerOf("/data/destination"), is(intAnswer(16)));
    }

    @Test
    public void when_triggerNodeIsUpdatedWithTheSameValue_targetNodeCalculation_isNotEvaluated() throws IOException {
        Scenario scenario = Scenario.init("Nested setvalue action", html(
            head(
                title("Nested setvalue action"),
                model(
                    mainInstance(t("data id=\"nested-setvalue\"",
                        t("source"),
                        t("destination"),
                        t("some-field")
                    )),
                    bind("/data/destination").type("string")
            )),
            body(
                input("/data/source",
                    setvalue("xforms-value-changed", "/data/destination", "concat('foo',/data/some-field)")),
                input("/data/some-field")
            )));

        assertThat(scenario.answerOf("/data/destination"), is(nullValue()));

        scenario.next();
        scenario.answer(22);
        assertThat(scenario.answerOf("/data/destination"), is(stringAnswer("foo")));

        scenario.next();
        scenario.answer("bar");

        scenario.prev();
        scenario.answer(22);
        assertThat(scenario.answerOf("/data/destination"), is(stringAnswer("foo")));

        scenario.answer(23);
        assertThat(scenario.answerOf("/data/destination"), is(stringAnswer("foobar")));
    }

    @Test
    public void setvalue_isSerializedAndDeserialized() throws IOException, DeserializationException {
        Scenario scenario = Scenario.init("Nested setvalue action", html(
            head(
                title("Nested setvalue action"),
                model(
                    mainInstance(t("data id=\"nested-setvalue\"",
                        t("source"),
                        t("destination")
                    )),
                    bind("/data/destination").type("int")
                )
            ),
            body(
                input("/data/source",
                    setvalue("xforms-value-changed", "/data/destination", "4*4"))
            )));

        scenario.serializeAndDeserializeForm();

        assertThat(scenario.answerOf("/data/destination"), is(nullValue()));

        scenario.next();
        scenario.answer(22);

        assertThat(scenario.answerOf("/data/destination"), is(intAnswer(16)));
    }

    @Test
    public void when_triggerNodeIsUpdatedWithinRepeat_targetNodeCalculation_isEvaluated() throws IOException {
        Scenario scenario = Scenario.init("Nested setvalue action with repeats", html(
            head(
                title("Nested setvalue action with repeats"),
                model(
                    mainInstance(t("data id=\"nested-setvalue-repeats\"",
                        repeat("repeat",
                            t("source"),
                            t("destination")
                        )
                    )),
                    bind("/data/repeat/destination").type("int")
                )
            ),
            body(
                repeat("/data/repeat",
                    input("/data/repeat/source",
                        setvalue("xforms-value-changed", "/data/repeat/destination", "4*position(..)"))
                )
            )));

        final int REPEAT_COUNT = 5;

        for (int i = 0; i < REPEAT_COUNT; i++) {
            scenario.createNewRepeat("/data/repeat");
            assertThat(scenario.answerOf("/data/repeat[" + i + "]/destination"), is(nullValue()));
        }

        for (int i = 0; i < REPEAT_COUNT; i++) {
            scenario.answer("/data/repeat[" + i + "]/source", 7);
        }

        for (int i = 0; i < REPEAT_COUNT; i++) {
            assertThat(scenario.answerOf("/data/repeat[" + i + "]/destination"), is(intAnswer(4 * (i + 1))));
        }
    }

    /**
     * Read-only is a display-only concern so it should be possible to use an action to modify the value of a read-only
     * field.
     */
    @Test
    public void setvalue_setsValueOfReadOnlyField() throws IOException {
        Scenario scenario = Scenario.init("Setvalue readonly", html(
            head(
                title("Setvalue readonly"),
                model(
                    mainInstance(t("data id=\"setvalue-readonly\"",
                        t("readonly-field")
                    )),
                    bind("/data/readonly-field").readonly("1").type("int"),
                    setvalue("odk-instance-first-load", "/data/readonly-field", "4*4")
                )
            ),
            body(
                input("/data/readonly-field")
            )));

        assertThat(scenario.answerOf("/data/readonly-field"), is(intAnswer(16)));
    }

    @Test
    public void setvalue_withInnerEmptyString_clearsTarget() throws IOException {
        Scenario scenario = Scenario.init("Setvalue empty string", html(
            head(
                title("Setvalue empty string"),
                model(
                    mainInstance(t("data id=\"setvalue-empty-string\"",
                        t("a-field", "12")
                    )),
                    bind("/data/a-field").type("int"),
                    setvalue("odk-instance-first-load", "/data/a-field")
                )
            ),
            body(
                input("/data/a-field")
            )));

        assertThat(scenario.answerOf("/data/a-field"), is(nullValue()));
    }

    @Test
    public void setvalue_withEmptyStringValue_clearsTarget() throws IOException {
        Scenario scenario = Scenario.init("Setvalue empty string", html(
            head(
                title("Setvalue empty string"),
                model(
                    mainInstance(t("data id=\"setvalue-empty-string\"",
                        t("a-field", "12")
                    )),
                    bind("/data/a-field").type("int"),
                    setvalue("odk-instance-first-load", "/data/a-field", "")
                )
            ),
            body(
                input("/data/a-field")
            )));

        assertThat(scenario.answerOf("/data/a-field"), is(nullValue()));
    }

    @Test
    public void setvalue_setsValueOfMultipleFields() throws IOException {
        Scenario scenario = Scenario.init("Setvalue multiple destinations", html(
            head(
                title("Setvalue multiple destinations"),
                model(
                    mainInstance(t("data id=\"setvalue-multiple\"",
                        t("source"),
                        t("destination1"),
                        t("destination2")
                    )),
                    bind("/data/destination1").type("int"),
                    bind("/data/destination2").type("int")
                )
            ),
            body(
                input("/data/source",
                    setvalueLiteral("xforms-value-changed", "/data/destination1", "7"),
                    setvalueLiteral("xforms-value-changed", "/data/destination2", "11"))
            )));

        scenario.answer("/data/source", "foo");
        assertThat(scenario.answerOf("/data/destination1"), is(intAnswer(7)));
        assertThat(scenario.answerOf("/data/destination2"), is(intAnswer(11)));
    }
}
