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

import com.google.android.material.motion.expression.Initializer;
import com.google.android.material.motion.expression.Initializer.SimpleInitializer;
import com.google.android.material.motion.expression.Intention;
import com.google.android.material.motion.expression.Modifier.SimpleModifier;
import com.google.android.material.motion.expression.Term;
import com.google.android.material.motion.expression.Work;
import com.google.android.material.motion.expression.tween.TweenIntention.FloatTweenIntention;
import com.google.android.material.motion.expression.tween.TweenIntention.ObjectTweenIntention;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.support.annotation.Keep;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * An abstract {@link Term} for tween animations.
 */
public abstract class TweenTerm<T extends TweenTerm<T>> extends Term<T, TweenLanguage> {

  private TweenTerm(
    TweenLanguage language, final Initializer initializer, Intention... intentions) {
    super(
      language,
      new SimpleInitializer(initializer) {

        @Override
        protected void initialize(Intention intention) {
          TweenIntention<?> i = (TweenIntention<?>) intention;
          i.segment = TimingSegment.COMPLETE;
          i.easingCurve = new AccelerateDecelerateInterpolator();
        }
      },
      intentions);
  }

  @Keep
  private TweenTerm(TweenLanguage language, final Work work) {
    super(language, work);
  }

  public T during(final TimingSegment segment) {
    return modify(
      new SimpleModifier() {
        @Override
        public void modify(Intention intention) {
          TweenIntention<?> i = (TweenIntention<?>) intention;
          i.segment = segment;
        }
      });
  }

  public T withEasingCurve(final TimeInterpolator easingCurve) {
    return modify(
      new SimpleModifier() {
        @Override
        public void modify(Intention intention) {
          TweenIntention<?> i = (TweenIntention<?>) intention;
          i.easingCurve = easingCurve;
        }
      });
  }

  /**
   * A {@link TweenTerm} for float valued tween animations. This is an optimization to avoid
   * autoboxing.
   */
  public static final class FloatTweenTerm<T extends FloatTweenTerm<T>> extends TweenTerm<T> {

    FloatTweenTerm(
      TweenLanguage language, final Property<?, Float> property, final float... values) {
      super(
        language,
        new SimpleInitializer(null) {
          @Override
          protected void initialize(Intention intention) {
            FloatTweenIntention i = (FloatTweenIntention) intention;
            i.property = property;
            i.values = values;
          }
        },
        new FloatTweenIntention());
    }

    @Keep
    private FloatTweenTerm(TweenLanguage language, Work work) {
      super(language, work);
    }

    public T from(final float value) {
      return modify(
        new SimpleModifier() {
          @Override
          public void modify(Intention intention) {
            FloatTweenIntention i = (FloatTweenIntention) intention;
            if (i.values.length == 1) {
              i.values = new float[]{value, i.values[0]};
            } else {
              i.values[0] = value;
            }
          }
        });
    }

    public T to(final float value) {
      return modify(
        new SimpleModifier() {
          @Override
          public void modify(Intention intention) {
            FloatTweenIntention i = (FloatTweenIntention) intention;
            i.values[i.values.length - 1] = value;
          }
        });
    }
  }

  /**
   * An abstract {@link TweenTerm} for generic {@link Object} valued tween animations.
   */
  public static abstract class ObjectTweenTerm<T extends ObjectTweenTerm<T, V>, V>
    extends TweenTerm<T> {

    @SafeVarargs
    ObjectTweenTerm(
      TweenLanguage builder,
      final Property<?, V> property,
      final TypeEvaluator<V> evaluator,
      final V... values) {
      super(
        builder,
        new SimpleInitializer(null) {
          @Override
          protected void initialize(Intention intention) {
            ObjectTweenIntention i = (ObjectTweenIntention) intention;
            i.property = property;
            i.evaluator = evaluator;
            i.values = values;
          }
        },
        new ObjectTweenIntention());
    }

    @Keep
    private ObjectTweenTerm(TweenLanguage language, Work work) {
      super(language, work);
    }

    public T from(final V value) {
      return modify(
        new SimpleModifier() {
          @Override
          public void modify(Intention intention) {
            ObjectTweenIntention i = (ObjectTweenIntention) intention;
            if (i.values.length == 1) {
              i.values = new Object[]{value, i.values[0]};
            } else {
              i.values[0] = value;
            }
          }
        });
    }

    public T to(final V value) {
      return modify(
        new SimpleModifier() {
          @Override
          public void modify(Intention intention) {
            ObjectTweenIntention i = (ObjectTweenIntention) intention;
            i.values[i.values.length - 1] = value;
          }
        });
    }
  }
}
