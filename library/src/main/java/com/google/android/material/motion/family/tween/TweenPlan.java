/*
 * Copyright (C) 2016 The Material Motion Authors. All Rights Reserved.
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

import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.Plan;

import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.util.Property;

/**
 * A {@link Plan} for tween animations.
 */
public abstract class TweenPlan<T> extends Plan {

  public Property<?, T> property;
  public TimingSegment segment;
  public TimeInterpolator easingCurve;

  /**
   * Creates a {@link PropertyValuesHolder} for this {@link Plan}. This allows subclasses to
   * optimize the PropertyValuesHolder for primitive valued tween animations.
   *
   * @return A PropertyValuesHolder for this Plan.
   */
  abstract PropertyValuesHolder createPropertyValuesHolder();

  @Override
  public Class<? extends Performer> getPerformerClass() {
    return TweenPerformer.class;
  }

  /**
   * A {@link TweenPlan} for float valued tween animations. This is an optimization to avoid
   * autoboxing.
   */
  public static final class FloatTweenPlan extends TweenPlan<Float> {

    float[] values;

    @Override
    PropertyValuesHolder createPropertyValuesHolder() {
      return PropertyValuesHolder.ofFloat(property, values);
    }
  }

  /**
   * An {@link TweenPlan} for generic {@link Object} valued tween animations.
   */
  public static final class ObjectTweenPlan extends TweenPlan {

    TypeEvaluator evaluator;
    Object[] values;

    @Override
    PropertyValuesHolder createPropertyValuesHolder() {
      return PropertyValuesHolder.ofObject(property, evaluator, values);
    }
  }
}
