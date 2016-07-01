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
import com.google.android.material.motion.runtime.Performer.DelegatedPerformance;
import com.google.android.material.motion.runtime.Performer.PlanPerformance;
import com.google.android.material.motion.runtime.Plan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;

/**
 * A {@link Performer} for tween animations. Delegates execution to the {@link Animator} API.
 */
public final class TweenPerformer extends Performer
    implements PlanPerformance, DelegatedPerformance {
  // TODO: Figure out how to declare explicit duration.
  private static final long TOTAL_DURATION = 2000L;

  private DelegatedPerformanceCallback callback;

  @Override
  public void setDelegatedPerformanceCallback(DelegatedPerformanceCallback callback) {
    this.callback = callback;
  }

  @Override
  public void addPlan(Plan plan) {
    Object target = getTarget();
    final TweenPlan tweenPlan = (TweenPlan) plan;

    Animator animator =
        ObjectAnimator.ofPropertyValuesHolder(target, tweenPlan.createPropertyValuesHolder());
    animator.setStartDelay(tweenPlan.segment.getStartDelay(TOTAL_DURATION));
    animator.setDuration(tweenPlan.segment.getDuration(TOTAL_DURATION));
    animator.setInterpolator(tweenPlan.easingCurve);
    animator.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationStart(Animator animation) {
            callback.onDelegatedPerformanceStart(
                TweenPerformer.this, String.valueOf(tweenPlan.hashCode()));
          }

          @Override
          public void onAnimationEnd(Animator animation) {
            callback.onDelegatedPerformanceEnd(
                TweenPerformer.this, String.valueOf(tweenPlan.hashCode()));
          }
        });
    animator.start();
  }
}
