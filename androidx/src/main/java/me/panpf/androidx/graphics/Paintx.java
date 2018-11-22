/*
 * Copyright (C) 2018 Peng fei Pan <panpfpanpf@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.panpf.androidx.graphics;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.support.annotation.NonNull;

public class Paintx {

    private Paintx() {
    }

    /**
     * 获取给定文字的宽度
     *
     * @param text     要计算的文字
     * @param textSize 文字大小
     * @return 给定文字的宽度
     */
    public static float getTextWidth(@NonNull String text, float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    /**
     * 获取当给定的文字使用给定的画笔绘制时的宽度
     *
     * @param paint 指定的画笔
     * @param text  指定的文字
     * @return 当给定的文字使用给定的画笔绘制时的宽度
     */
    public static float getTextWidth(@NonNull String text, @NonNull Paint paint) {
        return paint.measureText(text);
    }

    /**
     * 获取给定画笔的文字高度
     *
     * @param paint 给定的画笔
     * @return 文字的高度
     */
    public static float getTextHeightCompact(@NonNull Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 获取给定尺寸的文字的高度
     *
     * @param textSize 给定尺寸
     * @return 文字的高度
     */
    public static float getTextHeightCompact(float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return getTextHeightCompact(paint);
    }

    /**
     * 获取给定画笔的文字高度
     *
     * @param paint 给定的画笔
     * @return 文字的高度
     */
    public static float getTextHeight(@NonNull Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }

    /**
     * 获取给定尺寸的文字的高度
     *
     * @param textSize 给定尺寸
     * @return 文字的高度
     */
    public static float getTextHeight(float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return getTextHeight(paint);
    }

    /**
     * 获取给定文字的宽度
     *
     * @param text     要计算的文字
     * @param textSize 文字大小
     * @return 文字的宽度
     */
    public static int getTextWidthByBounds(@NonNull String text, float textSize) {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 获取给定文字的高度
     *
     * @param text     要计算的文字
     * @param textSize 文字大小
     * @return 文字的高度
     */
    public static int getTextHeightByBounds(@NonNull String text, float textSize) {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /**
     * 获取指定画笔的文字离顶部的基准距离
     *
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getTextLeading(@NonNull Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    /**
     * 获取绘制文字时垂直居中的的基线位置，使用 bottom 和 top 计算
     * @param rectTop 绘制面板的顶部位置
     * @param rectBottom 绘制面板的底部位置
     */
    public static float getDrawTextVerticalCenterBaseLine(@NonNull Paint paint, float rectTop, float rectBottom) {
        return (rectBottom + rectTop - paint.getFontMetrics().bottom - paint.getFontMetrics().top) / 2;
    }

    /**
     * 获取绘制文字时垂直居中的的基线位置，使用 descent 和 ascent 计算
     * @param rectTop 绘制面板的顶部位置
     * @param rectBottom 绘制面板的底部位置
     */
    public static float getDrawTextVerticalCenterBaseLineCompact(@NonNull Paint paint, float rectTop, float rectBottom) {
        return (rectBottom + rectTop - paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2;
    }
}
