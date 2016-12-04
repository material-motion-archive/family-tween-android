/*
 * Copyright 2016-present The Material Motion Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.material.motion.family.tween;

import android.animation.TypeEvaluator;
import android.util.Property;

import com.google.android.material.motion.runtime.MotionRuntime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ObjectTweenTests {

  private MotionRuntime runtime;

  @Before
  public void setUp() {
    runtime = new MotionRuntime();
  }

  @Test
  public void tweenArbitraryObjectProperty() {
    Data data = new Data("hi");

    ObjectTween<Object, String> tween = new ObjectTween<>(new TextProperty(), 300, "bye");
    runtime.addPlan(tween, data);

    assertThat(data.text).isEqualTo("bye");
  }

  @Test
  public void tweenArbitraryDataProperty() {
    Data data = new Data("hi");

    ObjectTween<Data, String> tween = new ObjectTween<>(new TextProperty(), 300, "bye");
    runtime.addPlan(tween, data);

    assertThat(data.text).isEqualTo("bye");
  }

  private static class Data {
    public String text;

    public Data(String text) {
      this.text = text;
    }
  }

  private static class TextProperty extends TweenProperty<Object, String> {

    public TextProperty() {
      super(new Property<Object, String>(String.class, "text") {

        @Override
        public String get(Object object) {
          return ((Data) object).text;
        }

        @Override
        public void set(Object object, String value) {
          ((Data) object).text = value;
        }
      }, new TypeEvaluator<String>() {

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
          return endValue;
        }
      });
    }
  }
}
