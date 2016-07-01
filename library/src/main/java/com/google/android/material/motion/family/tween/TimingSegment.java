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

/**
 * A representation of the timing applied to a tween animation.
 */
public class TimingSegment {

  public static final TimingSegment FIRST_QUARTER = new TimingSegment(0.00f, 0.25f);
  public static final TimingSegment SECOND_QUARTER = new TimingSegment(0.25f, 0.50f);
  public static final TimingSegment THIRD_QUARTER = new TimingSegment(0.50f, 0.75f);
  public static final TimingSegment FOURTH_QUARTER = new TimingSegment(0.75f, 1.00f);
  public static final TimingSegment FIRST_HALF = new TimingSegment(0.00f, 0.50f);
  public static final TimingSegment MIDDLE_HALF = new TimingSegment(0.25f, 0.75f);
  public static final TimingSegment SECOND_HALF = new TimingSegment(0.50f, 1.00f);
  public static final TimingSegment FIRST_THREE_QUARTERS = new TimingSegment(0.00f, 0.75f);
  public static final TimingSegment LAST_THREE_QUARTERS = new TimingSegment(0.25f, 1.00f);
  public static final TimingSegment COMPLETE = new TimingSegment(0.00f, 1.00f);

  public final float from;
  public final float to;

  public TimingSegment(float from, float to) {
    this.from = from;
    this.to = to;
  }

  public long getStartDelay(long totalDuration) {
    return (long) (from * totalDuration);
  }

  public long getDuration(long totalDuration) {
    return (long) ((to - from) * totalDuration);
  }
}
