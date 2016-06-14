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

package com.google.android.material.motion.expression.tween.sample;

import com.google.android.material.motion.expression.Expression;
import com.google.android.material.motion.expression.Intention;
import com.google.android.material.motion.expression.tween.TimingSegment;
import com.google.android.material.motion.expression.tween.TweenIntention;
import com.google.android.material.motion.expression.tween.TweenLanguage;
import com.google.android.material.motion.expression.tween.TweenTerm.FloatTweenTerm;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Sample {@link AppCompatActivity}.
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main_activity);

    findViewById(R.id.start)
        .setOnClickListener(
            new OnClickListener() {
              @Override
              public void onClick(View v) {
                runDemos();
              }
            });

    runDemos();
  }

  private void runDemos() {
    tweenDemo();
  }

  private void tweenDemo() {
    View demo1 = findViewById(R.id.demo1);
    View demo2 = findViewById(R.id.demo2);
    View demo3 = findViewById(R.id.demo3);
    View demo4 = findViewById(R.id.demo4);
    View demo5 = findViewById(R.id.demo5);

    TweenLanguage exp1 = new TweenLanguage();
    FloatTweenTerm<?> exp2 = exp1.scale(0f, 1f);
    FloatTweenTerm<?> exp3 = exp2.from(.5f);
    FloatTweenTerm<?> exp4 = exp2.from(.75f);
    FloatTweenTerm<?> exp5 = exp3.and.moveYOutBy(200f).during(TimingSegment.SECOND_HALF);

    executeDemo(exp1, demo1); // nothing
    executeDemo(exp2, demo2); // scale in from 0f
    executeDemo(exp3, demo3); // scale in from .5f
    executeDemo(exp4, demo4); // scale in from .75f
    executeDemo(exp5, demo5); // scale in from .5f, move out by 200f during 2nd half
  }

  private void executeDemo(Expression expression, View demo) {
    Intention[] intentions = expression.intentions();
    for (Intention i : intentions) {
      TweenIntention<?> intention = (TweenIntention<?>) i;
      Animator animator = intention.createAnimator(2000L);
      animator.setTarget(demo);
      animator.start();
    }
  }
}
