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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.Plan;

/**
 * Interpolate an object's {@link TweenProperty} from one value to another.
 */
public class ObjectTween<T, V> extends Plan<T> {

  /**
   * The property whose value will be tweened.
   */
  public TweenProperty<? super T, V> property;

  /**
   * The duration of the animation in milliseconds.
   */
  public long duration;
  /**
   * The start delay of the animation in milliseconds.
   */
  public long delay;

  /**
   * An array of objects providing the value of the animation for each keyframe.
   */
  public V[] values;

  /**
   * An optional array that defines the pacing of the animation. Each offset corresponds to its
   * identically-indexed value in the {@link #values} array. Each offset is a floating point
   * number in the range of [0,1] that defines the fraction of the {@link #duration} at which the
   * corresponding value should apply. If null, each value is assumed to be evenly spaced.
   */
  @Nullable
  public float[] offsets;

  /**
   * An optional array that defines the timing functions to be used between any two values. If
   * {@link #values} is of length n, then this should be of length n-1. If null, each timing
   * function is assumed to be linear.
   * <p>
   * These timing functions composes with the {@link #timingFunction overall timing function}.
   */
  @Nullable
  public TimeInterpolator[] interTimingFunctions;

  /**
   * The overall timing function to apply to the animation. If null, the overall timing function
   * is assumed to be {@link AccelerateDecelerateInterpolator}.
   * <p>
   * This timing function composes with the keyframe {@link #interTimingFunctions}.
   */
  @Nullable
  public TimeInterpolator timingFunction;

  /**
   * Initializes an ObjectTween plan for the given property with the values as the keyframes.
   * <p>
   * If {@code values.length == 1}, the sole value will be treated as the final value. The initial
   * value will be calculated from the target.
   */
  @SafeVarargs
  protected ObjectTween(TweenProperty<? super T, V> property, long duration, @NonNull V... values) {
    this.property = property;
    this.duration = duration;
    this.values = values;
  }

  @Override
  protected Class<? extends Performer<T>> getPerformerClass() {
    return (Class<? extends Performer<T>>) new TweenPerformer<T>().getClass();
  }
}
