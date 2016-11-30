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

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TweenTests {

  @Before
  public void setUp() {
  }

  @Test
  public void cloneHasEqualProperties() {
    Tween<Float> tween = new Tween<>(TweenProperty.ALPHA, 300, 0f, 1f);
    Tween clone = (Tween) tween.clone();

    assertThat(clone.property).isEqualTo(tween.property);
    assertThat(clone.values).isEqualTo(tween.values);
    assertThat(clone.duration).isEqualTo(tween.duration);
    assertThat(clone.delay).isEqualTo(tween.delay);
    assertThat(clone.timingFunction).isEqualTo(tween.timingFunction);
  }
}