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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;

import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.PerformerFeatures.ContinuousPerforming;
import com.google.android.material.motion.runtime.PlanFeatures.BasePlan;

/**
 * A {@link Performer} for tween animations. Uses the {@link Animator} API to fulfil tweens.
 */
public class TweenPerformer extends Performer implements ContinuousPerforming {

  private IsActiveTokenGenerator isActiveTokenGenerator;

  @Override
  public void setIsActiveTokenGenerator(IsActiveTokenGenerator isActiveTokenGenerator) {
    this.isActiveTokenGenerator = isActiveTokenGenerator;
  }

  @Override
  public void addPlan(BasePlan plan) {
    if (plan instanceof Tween) {
      addTween((Tween) plan);
    } else {
      throw new IllegalArgumentException("Plan type not supported for " + plan);
    }
  }

  private void addTween(Tween plan) {
    if (!validate(plan)) {
      throw new IllegalArgumentException("Plan failed validation: " + plan);
    }

    ObjectAnimator animator =
      ObjectAnimator.ofPropertyValuesHolder(getTarget(), createPropertyValuesHolder(plan));
    animator.setStartDelay(plan.delay);
    animator.setDuration(plan.duration);
    if (plan.timingFunction != null) {
      animator.setInterpolator(plan.timingFunction);
    }
    animator.setEvaluator(plan.property.evaluator);

    animator.addListener(
      new AnimatorListenerAdapter() {

        private IsActiveToken token;

        @Override
        public void onAnimationStart(Animator animation) {
          token = isActiveTokenGenerator.generate();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
          token.terminate();
        }
      });
    animator.start();
  }

  private boolean validate(Tween plan) {
    if (plan.values.length == 0) {
      return false;
    }

    if (plan.offsets != null && plan.offsets.length != plan.values.length) {
      return false;
    }

    if (plan.interTimingFunctions != null
      && plan.interTimingFunctions.length != plan.values.length - 1) {
      return false;
    }

    return true;
  }

  private PropertyValuesHolder createPropertyValuesHolder(Tween plan) {
    // Create keyframes.
    Keyframe[] keyframes;
    if (plan.values.length == 1) {
      keyframes = new Keyframe[2];

      keyframes[0] = Keyframe.ofObject(0f);
      keyframes[1] = Keyframe.ofObject(1f, plan.values[0]);
    } else {
      keyframes = new Keyframe[plan.values.length];

      for (int i = 0, count = plan.values.length; i < count; i++) {
        Object value = plan.values[i];
        float offset = plan.offsets != null ? plan.offsets[i] : 0f;
        TimeInterpolator interTimingFunction = plan.interTimingFunctions != null && i > 0
          ? plan.interTimingFunctions[i - 1] : null;

        keyframes[i] = Keyframe.ofObject(offset, value);
        keyframes[i].setInterpolator(interTimingFunction);
      }
    }

    // Space keyframes dynamically.
    if (plan.offsets == null) {
      for (int i = 0, count = keyframes.length; i < count; i++) {
        keyframes[i].setFraction((float) i / count - 1);
      }
    }

    return PropertyValuesHolder.ofKeyframe(plan.property.property, keyframes);
  }
}
