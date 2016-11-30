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

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;

import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.PerformerFeatures.ContinuousPerforming.IsActiveToken;
import com.google.android.material.motion.runtime.PerformerFeatures.ContinuousPerforming.IsActiveTokenGenerator;
import com.google.android.material.motion.runtime.Plan;
import com.google.android.material.motion.runtime.MotionRuntime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TweenPerformerTests {

  private MotionRuntime runtime;
  private View target;

  @Before
  public void setUp() {
    runtime = new MotionRuntime();
    Context context = Robolectric.setupActivity(Activity.class);
    target = new View(context);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addUnsupportedPlanThrowsException() {
    TweenPerformer performer = createTweenPerformer();
    performer.addPlan(new UnsupportedPlan());
  }

  @Test
  public void tweenChangesTargetValue() {
    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 1f, 0f);
    runtime.addPlan(fadeOut, target);

    assertThat(target.getAlpha()).isWithin(0).of(0f);
  }

  @Test
  public void tweenCanHaveImplicitFrom() {
    target.setAlpha(1f);

    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 0f);
    runtime.addPlan(fadeOut, target);

    // Unfortunately it's difficult to test that the implicit from is honored, because animations
    // are run synchronously in unit tests.
    assertThat(target.getAlpha()).isWithin(0).of(0f);
  }

  @Test
  public void interpolatorDoesNotCrash() {
    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 0f);
    fadeOut.timingFunction = new AccelerateInterpolator();

    runtime.addPlan(fadeOut, target);

    // Unfortunately it's difficult to test that the interpolator is honored, because animations
    // are run synchronously in unit tests.
    // Best we can do right now is ensure it does not crash.
  }

  @Test
  public void keyframeApiChangesTargetValue() {
    target.setAlpha(1f);

    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 1f, .5f, 0f);
    fadeOut.offsets = new float[]{0f, .25f, 1f};
    fadeOut.interTimingFunctions = new TimeInterpolator[]{
      new AccelerateInterpolator(), new AnticipateInterpolator()
    };

    runtime.addPlan(fadeOut, target);

    // Unfortunately animations are run synchronously in unit tests.
    assertThat(target.getAlpha()).isWithin(0).of(0f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidValuesLength() {
    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300);

    runtime.addPlan(fadeOut, target);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidOffsetsLength() {
    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 1f, .5f, 0f);
    fadeOut.offsets = new float[2];

    runtime.addPlan(fadeOut, target);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterTimingFunctionsLength() {
    Tween<Float> fadeOut = new Tween<>(TweenProperty.ALPHA, 300, 1f, .5f, 0f);
    fadeOut.interTimingFunctions = new TimeInterpolator[3];

    runtime.addPlan(fadeOut, target);
  }

  /**
   * Creates and initializes a TweenPerformer manually.
   */
  private TweenPerformer createTweenPerformer() {
    TweenPerformer performer = new TweenPerformer();
    performer.initialize(target);
    performer.setIsActiveTokenGenerator(new IsActiveTokenGenerator() {
      @Override
      public IsActiveToken generate() {
        return new IsActiveToken() {
          @Override
          public void terminate() {
            // No-op.
          }
        };
      }
    });
    return performer;
  }

  private static class UnsupportedPlan extends Plan {

    @Override
    public Class<? extends Performer> getPerformerClass() {
      return TweenPerformer.class;
    }
  }
}
