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

import android.app.Activity;
import android.content.Context;
import android.view.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TweenPropertyTests {

  private View target;

  @Before
  public void setUp() {
    Context context = Robolectric.setupActivity(Activity.class);
    target = new View(context);
  }

  @Test
  public void propertySetsTargetValue() {
    target.setScaleX(0f);

    TweenProperty.SCALE.property.set(target, 0.5f);

    assertThat(target.getScaleX()).isWithin(0).of(0.5f);
  }

  @Test
  public void propertyGetsTargetValue() {
    target.setScaleX(0.5f);

    float scale = TweenProperty.SCALE.property.get(target);

    assertThat(scale).isWithin(0).of(0.5f);
  }
}
