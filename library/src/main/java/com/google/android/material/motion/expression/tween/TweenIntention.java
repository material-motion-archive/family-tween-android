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

package com.google.android.material.motion.expression.tween;

import com.google.android.material.motion.expression.Intention;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.util.Property;

/**
 * An {@link Intention} for tween animations. Includes all the necessary information to create an
 * {@link Animator}.
 */
public abstract class TweenIntention<T> implements Intention {

  public Property<?, T> property;
  public TimingSegment segment;
  public TimeInterpolator easingCurve;

  /**
   * Creates a {@link PropertyValuesHolder} for this {@link Intention}. This allows subclasses to
   * optimize the PropertyValuesHolder for primitive valued tween animations.
   *
   * @return A PropertyValuesHolder for this Intention.
   */
  protected abstract PropertyValuesHolder createPropertyValuesHolder();

  /**
   * Creates an {@link Animator} from this {@link Intention}.
   *
   * @param duration The total duration of the Animator.
   * @return An Animator for this Intention.
   */
  public final Animator createAnimator(long duration) {
    ObjectAnimator animator = new ObjectAnimator();
    animator.setValues(createPropertyValuesHolder());
    animator.setStartDelay(segment.getStartDelay(duration));
    animator.setDuration(segment.getDuration(duration));
    animator.setInterpolator(easingCurve);
    return animator;
  }

  /**
   * A {@link TweenIntention} for float valued tween animations. This is an optimization to avoid
   * autoboxing.
   */
  public static class FloatTweenIntention extends TweenIntention<Float> {

    float[] values;

    @Override
    protected PropertyValuesHolder createPropertyValuesHolder() {
      return PropertyValuesHolder.ofFloat(property, values);
    }
  }

  /**
   * An {@link TweenIntention} for generic {@link Object} valued tween animations.
   */
  public static class ObjectTweenIntention extends TweenIntention {

    TypeEvaluator evaluator;
    Object[] values;

    @Override
    protected PropertyValuesHolder createPropertyValuesHolder() {
      return PropertyValuesHolder.ofObject(property, evaluator, values);
    }
  }
}
