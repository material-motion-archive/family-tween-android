/*
 * Copyright 2016-present The Material Motion Authors. All Rights Reserved.
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

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;
import android.graphics.drawable.ColorDrawable;
import android.util.Property;
import android.view.View;

/**
 * Defines the properties that can be animated with {@link Tween}.
 */
public class TweenProperty<T, V> {

  private static final TypeEvaluator<Number> NO_OP = new FloatEvaluator();

  public static final TweenProperty<View, Float> ALPHA =
    new TweenProperty<>(View.ALPHA, NO_OP);
  public static final TweenProperty<View, Float> TRANSLATION_X =
    new TweenProperty<>(View.TRANSLATION_X, NO_OP);
  public static final TweenProperty<View, Float> TRANSLATION_Y =
    new TweenProperty<>(View.TRANSLATION_Y, NO_OP);
  public static final TweenProperty<View, Float> TRANSLATION_Z =
    new TweenProperty<>(View.TRANSLATION_Z, NO_OP);
  public static final TweenProperty<View, Float> X =
    new TweenProperty<>(View.X, NO_OP);
  public static final TweenProperty<View, Float> Y =
    new TweenProperty<>(View.Y, NO_OP);
  public static final TweenProperty<View, Float> Z =
    new TweenProperty<>(View.Z, NO_OP);
  public static final TweenProperty<View, Float> ROTATION =
    new TweenProperty<>(View.ROTATION, NO_OP);
  public static final TweenProperty<View, Float> ROTATION_X =
    new TweenProperty<>(View.ROTATION_X, NO_OP);
  public static final TweenProperty<View, Float> ROTATION_Y =
    new TweenProperty<>(View.ROTATION_Y, NO_OP);
  public static final TweenProperty<View, Float> SCALE_X =
    new TweenProperty<>(View.SCALE_X, NO_OP);
  public static final TweenProperty<View, Float> SCALE_Y =
    new TweenProperty<>(View.SCALE_Y, NO_OP);
  public static final TweenProperty<View, Float> SCALE =
    new TweenProperty<>(new CombinedProperty<>(View.SCALE_X, View.SCALE_Y), NO_OP);
  public static final TweenProperty<View, Integer> BACKGROUND_COLOR =
    new TweenProperty<>(new BackgroundColorProperty(), new ArgbEvaluator());

  final TypeEvaluator<? super V> evaluator;
  final Property<T, V> property;

  public TweenProperty(Property<T, V> property, TypeEvaluator<? super V> evaluator) {
    this.property = property;
    this.evaluator = evaluator;
  }

  /**
   * A utility class to treat multiple properties as one.
   */
  private static class CombinedProperty<T, V> extends Property<T, V> {

    private final Property<T, V>[] properties;

    @SafeVarargs
    private CombinedProperty(Property<T, V>... properties) {
      super(
        properties[0].getType(),
        "combined " + properties[0].getName() + "+" + properties.length);
      this.properties = properties;
    }

    @Override
    public V get(T object) {
      return properties[0].get(object);
    }

    @Override
    public void set(T object, V value) {
      for (Property<T, V> property : properties) {
        property.set(object, value);
      }
    }
  }

  /**
   * A Property for a view's background color.
   */
  private static class BackgroundColorProperty extends Property<View, Integer> {

    public BackgroundColorProperty() {
      super(Integer.class, "background color");
    }

    @Override
    public Integer get(View object) {
      return ((ColorDrawable) object.getBackground()).getColor();
    }

    @Override
    public void set(View object, Integer value) {
      object.setBackgroundColor(value);
    }
  }
}
