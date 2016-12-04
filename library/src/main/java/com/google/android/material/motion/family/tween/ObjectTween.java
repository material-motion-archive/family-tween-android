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
import android.view.View;

import com.google.android.material.motion.runtime.Performer;

/**
 * Interpolate a {@link TweenProperty} on a object from one value to another.
 */
public class ObjectTween<V> extends BaseTween<Object, V> {

  /**
   * Initializes an ObjectTween plan for the given property with the values as the keyframes.
   * <p>
   * If {@code values.length == 1}, the sole value will be treated as the final value. The initial
   * value will be calculated from the target.
   */
  @SafeVarargs
  public ObjectTween(TweenProperty<? super Object, V> property, long duration, @NonNull V... values) {
    super(property, duration, values);
  }

  @Override
  protected Class<? extends Performer<Object>> getPerformerClass() {
    return ObjectTweenPerformer.class;
  }
}
