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

import com.google.android.material.motion.expression.Language;
import com.google.android.material.motion.expression.Term;
import com.google.android.material.motion.family.tween.TweenTerm.FloatTweenTerm;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * A {@link Language} for tween animations. This Language provides basic tween property {@link Term
 * Terms}.
 */
public final class TweenLanguage extends Language<TweenLanguage> {

  public TweenLanguage() {
    super();
  }

  @Keep
  private TweenLanguage(@NonNull Term<?, TweenLanguage> previousTerm) {
    super(previousTerm);
  }

  public FloatTweenTerm<?> fadeIn() {
    return fade(0f, 1f);
  }

  public FloatTweenTerm<?> fadeOut() {
    return fade(1f, 0f);
  }

  public FloatTweenTerm<?> fade(float left, float right) {
    return new FloatTweenTerm<>(this, View.ALPHA, left, right);
  }

  public FloatTweenTerm<?> moveXInBy(float value) {
    return moveXBy(value, 0f);
  }

  public FloatTweenTerm<?> moveXOutBy(float value) {
    return moveXBy(0f, value);
  }

  public FloatTweenTerm<?> moveXBy(float left, float right) {
    return new FloatTweenTerm<>(this, View.TRANSLATION_X, left, right);
  }

  public FloatTweenTerm<?> moveYInBy(float value) {
    return moveYBy(value, 0f);
  }

  public FloatTweenTerm<?> moveYOutBy(float value) {
    return moveYBy(0f, value);
  }

  public FloatTweenTerm<?> moveYBy(float left, float right) {
    return new FloatTweenTerm<>(this, View.TRANSLATION_Y, left, right);
  }

  public FloatTweenTerm<?> scaleInFrom(float value) {
    return scale(value, 1f);
  }

  public FloatTweenTerm<?> scaleOutTo(float value) {
    return scale(1f, value);
  }

  public FloatTweenTerm<?> scale(float left, float right) {
    return new FloatTweenTerm<>(this, Utils.SCALE, left, right);
  }
}
