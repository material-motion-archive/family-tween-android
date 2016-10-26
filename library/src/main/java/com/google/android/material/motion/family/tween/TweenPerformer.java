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

import android.animation.PropertyValuesHolder;
import com.google.android.material.motion.runtime.Performer;
import com.google.android.material.motion.runtime.Performer.ContinuousPerformance;
import com.google.android.material.motion.runtime.Plan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;

/**
 * A {@link Performer} for tween animations. Uses the {@link Animator} API to fulfil tweens.
 */
public class TweenPerformer extends Performer implements ContinuousPerformance {

  private IsActiveTokenGenerator isActiveTokenGenerator;

  @Override
  public void setIsActiveTokenGenerator(IsActiveTokenGenerator isActiveTokenGenerator) {
    this.isActiveTokenGenerator = isActiveTokenGenerator;
  }

  @Override
  public void addPlan(Plan plan) {
    if (plan instanceof Tween) {
      addTween((Tween) plan);
    } else {
      throw new IllegalArgumentException("Plan type not supported for " + plan);
    }
  }

  private void addTween(Tween plan) {
    Object target = getTarget();

    ObjectAnimator animator =
      ObjectAnimator.ofPropertyValuesHolder(target, createPropertyValuesHolder(plan));
    animator.setStartDelay(plan.delay);
    animator.setDuration(plan.duration);
    if (plan.interpolator != null) {
      animator.setInterpolator(plan.interpolator);
    }
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

  private PropertyValuesHolder createPropertyValuesHolder(Tween plan) {
    if (plan.from == null) {
      return PropertyValuesHolder
        .ofObject(plan.property.property, plan.property.evaluator, plan.to);
    } else {
      return PropertyValuesHolder
        .ofObject(plan.property.property, plan.property.evaluator, plan.from, plan.to);
    }
  }
}
