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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.android.material.motion.family.tween.Tween;
import com.google.android.material.motion.family.tween.TweenProperty;
import com.google.android.material.motion.runtime.Scheduler;
import com.google.android.material.motion.runtime.Transaction;

/**
 * Material Motion Tween Family sample Activity.
 */
public class MainActivity extends AppCompatActivity {

  private final Scheduler scheduler = new Scheduler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main_activity);

    View content = findViewById(android.R.id.content);
    final View target = findViewById(R.id.target);

    content.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent event) {
        Tween tween = new Tween<>(TweenProperty.SCALE, 300, 1f);

        switch (event.getActionMasked()) {
          case MotionEvent.ACTION_DOWN:
            tween.to = .5f;
            break;
          case MotionEvent.ACTION_UP:
            tween.to = 1f;
            break;
          default:
            return false;
        }

        Transaction transaction = new Transaction();
        transaction.addPlan(tween, target);
        scheduler.commitTransaction(transaction);

        return true;
      }
    });
  }
}
