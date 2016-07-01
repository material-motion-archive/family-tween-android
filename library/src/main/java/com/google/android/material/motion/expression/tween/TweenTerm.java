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
import com.google.android.material.motion.expression.Modifier.SimpleModifier;
import com.google.android.material.motion.expression.Term;
import com.google.android.material.motion.expression.Work;
import com.google.android.material.motion.expression.tween.TweenPlan.FloatTweenPlan;
import com.google.android.material.motion.expression.tween.TweenPlan.ObjectTweenPlan;
import com.google.android.material.motion.runtime.Plan;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.support.annotation.Keep;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * An abstract {@link Term} for tween animations.
 */
public abstract class TweenTerm<T extends TweenTerm<?>> extends Term<T, TweenLanguage> {

  private TweenTerm(TweenLanguage language, final Initializer initializer, Plan... plans) {
    super(
        language,
        new SimpleInitializer(initializer) {

          @Override
          protected void initialize(Plan plan) {
            TweenPlan<?> i = (TweenPlan<?>) plan;
            i.segment = TimingSegment.COMPLETE;
            i.easingCurve = new AccelerateDecelerateInterpolator();
          }
        },
        plans);
  }

  @Keep
  private TweenTerm(TweenLanguage language, final Work work) {
    super(language, work);
  }

  public T during(final TimingSegment segment) {
    return modify(
        new SimpleModifier() {
          @Override
          public void modify(Plan plan) {
            TweenPlan<?> i = (TweenPlan<?>) plan;
            i.segment = segment;
          }
        });
  }

  public T withEasingCurve(final TimeInterpolator easingCurve) {
    return modify(
        new SimpleModifier() {
          @Override
          public void modify(Plan plan) {
            TweenPlan<?> i = (TweenPlan<?>) plan;
            i.easingCurve = easingCurve;
          }
        });
  }

  /**
   * A {@link TweenTerm} for float valued tween animations. This is an optimization to avoid
   * autoboxing.
   */
  public static final class FloatTweenTerm<T extends FloatTweenTerm<?>> extends TweenTerm<T> {

    FloatTweenTerm(
        TweenLanguage language, final Property<?, Float> property, final float... values) {
      super(
          language,
          new SimpleInitializer(null) {
            @Override
            protected void initialize(Plan plan) {
              FloatTweenPlan i = (FloatTweenPlan) plan;
              i.property = property;
              i.values = values;
            }
          },
          new FloatTweenPlan());
    }

    @Keep
    private FloatTweenTerm(TweenLanguage language, Work work) {
      super(language, work);
    }

    public T from(final float value) {
      return modify(
          new SimpleModifier() {
            @Override
            public void modify(Plan plan) {
              FloatTweenPlan i = (FloatTweenPlan) plan;
              if (i.values.length == 1) {
                i.values = new float[] {value, i.values[0]};
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
            public void modify(Plan plan) {
              FloatTweenPlan i = (FloatTweenPlan) plan;
              i.values[i.values.length - 1] = value;
            }
          });
    }
  }

  /**
   * An abstract {@link TweenTerm} for generic {@link Object} valued tween animations.
   */
  public static abstract class ObjectTweenTerm<T extends ObjectTweenTerm<?, ?>, V>
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
            protected void initialize(Plan plan) {
              ObjectTweenPlan i = (ObjectTweenPlan) plan;
              i.property = property;
              i.evaluator = evaluator;
              i.values = values;
            }
          },
          new ObjectTweenPlan());
    }

    @Keep
    private ObjectTweenTerm(TweenLanguage language, Work work) {
      super(language, work);
    }

    public T from(final V value) {
      return modify(
          new SimpleModifier() {
            @Override
            public void modify(Plan plan) {
              ObjectTweenPlan i = (ObjectTweenPlan) plan;
              if (i.values.length == 1) {
                i.values = new Object[] {value, i.values[0]};
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
            public void modify(Plan plan) {
              ObjectTweenPlan i = (ObjectTweenPlan) plan;
              i.values[i.values.length - 1] = value;
            }
          });
    }
  }
}
