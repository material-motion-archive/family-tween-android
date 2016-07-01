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

package com.google.android.material.motion.family.tween.sample;

import com.google.android.material.motion.expression.Term;
import com.google.android.material.motion.family.tween.TimingSegment;
import com.google.android.material.motion.family.tween.TweenLanguage;
import com.google.android.material.motion.family.tween.TweenTerm.FloatTweenTerm;
import com.google.android.material.motion.runtime.Plan;
import com.google.android.material.motion.runtime.Scheduler;
import com.google.android.material.motion.runtime.Transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Sample {@link AppCompatActivity}.
 */
public class MainActivity extends AppCompatActivity {

  private final Scheduler scheduler = new Scheduler();

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

    resetDemo(demo1);
    resetDemo(demo2);
    resetDemo(demo3);
    resetDemo(demo4);
    resetDemo(demo5);

    TweenLanguage exp1 = new TweenLanguage();
    FloatTweenTerm<?> exp2 = exp1.scale(0f, 1f);
    FloatTweenTerm<?> exp3 = exp2.from(.5f);
    FloatTweenTerm<?> exp4 = exp2.from(.75f);
    FloatTweenTerm<?> exp5 = exp3.and.moveYOutBy(200f).during(TimingSegment.SECOND_HALF);

    // Can't call plans() on exp1 since it's not a Term.
    // executeDemo(exp1, demo1); // nothing
    executeDemo(exp2, demo2); // scale in from 0f
    executeDemo(exp3, demo3); // scale in from .5f
    executeDemo(exp4, demo4); // scale in from .75f
    executeDemo(exp5, demo5); // scale in from .5f, move out by 200f during 2nd half
  }

  private void resetDemo(View demo) {
    demo.setScaleX(1f);
    demo.setScaleY(1f);
    demo.setTranslationY(0f);
  }

  private void executeDemo(Term term, View demo) {
    Transaction transaction = new Transaction();

    Plan[] plans = term.plans();
    for (Plan plan : plans) {
      transaction.addPlan(plan, demo);
    }

    scheduler.commitTransaction(transaction);
  }
}
