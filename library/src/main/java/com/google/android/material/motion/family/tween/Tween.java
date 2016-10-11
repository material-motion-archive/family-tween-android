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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.Plan;

import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.util.Property;

/**
 * Interpolate a {@link TweenProperty} from one value to another.
 */
public class Tween<V> extends Plan {

  /**
   * The property whose value will be tweened.
   */
  public TweenProperty<V> property;
  /**
   * The initial value of the tween.
   *
   * If null, the tween will infer the start value from the target's current value.
   */
  @Nullable
  public V from;
  /**
   * The final value of the tween.
   */
  public V to;

  /**
   * The duration of the animation in milliseconds.
   */
  public long duration;
  /**
   * The start delay of the animation in milliseconds.
   */
  public long delay;
  /**
   * The timing function to apply to the animation.
   *
   * If null, a default {@link AccelerateDecelerateInterpolator} will apply.
   */
  @Nullable
  public TimeInterpolator interpolator;

  /**
   * Initializes a Tween plan for the given property to the given final value. The initial value is
   * calculated from the target.
   */
  public Tween(TweenProperty<V> property, long duration, @NonNull V to) {
    this.property = property;
    this.duration = duration;
    this.to = to;
  }

  /**
   * Initializes a Tween plan for the given property from the given initial value to the given final
   * value.
   */
  public Tween(TweenProperty<V> property, long duration, @NonNull V from, @NonNull V to) {
    this.property = property;
    this.duration = duration;
    this.from = from;
    this.to = to;
  }

  @Override
  public Class<? extends Performer> getPerformerClass() {
    return TweenPerformer.class;
  }
}
