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

package me.panpf.androidxkt.view

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation

/*
 * View animation related extension methods or properties
 */

/**
 * 默认动画持续时间
 */
const val DEFAULT_ANIMATION_DURATION: Long = 400

/**
 * 透明度动画
 *
 * @param fromAlpha         开始透明度
 * @param toAlpha           结束透明度
 * @param durationMillis    持续时间，毫秒
 * @param isBanClick        在执行动画的过程中是否禁止点击
 * @param listener 动画监听器
 */
fun View.animationAlpha(fromAlpha: Float, toAlpha: Float, durationMillis: Long = DEFAULT_ANIMATION_DURATION,
                        isBanClick: Boolean = false, listener: AnimationListener? = null) {
    this.clearAnimation()
    val isClickable = this.isClickable

    this.startAnimation(AlphaAnimation(fromAlpha, toAlpha).apply {
        duration = durationMillis
        setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (isClickable && isBanClick) this@animationAlpha.isClickable = false
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                listener?.onAnimationRepeat(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                if (isClickable && isBanClick) this@animationAlpha.isClickable = true
                listener?.onAnimationEnd(animation)
            }
        })
    })
}

/**
 * 移动动画
 *
 * @param fromXDelta     X轴开始坐标
 * @param toXDelta       X轴结束坐标
 * @param fromYDelta     Y轴开始坐标
 * @param toYDelta       Y轴结束坐标
 * @param cycles         重复
 * @param durationMillis 持续时间
 * @param isBanClick     在执行动画的过程中是否禁止点击
 * @param listener       动画监听器
 */
fun View.animationTranslate(fromXDelta: Float, toXDelta: Float, fromYDelta: Float, toYDelta: Float, cycles: Float,
                            durationMillis: Long = DEFAULT_ANIMATION_DURATION,
                            isBanClick: Boolean = false, listener: AnimationListener? = null) {
    this.clearAnimation()
    val isClickable = this.isClickable

    this.startAnimation(TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta).apply {
        duration = durationMillis
        if (cycles > 0.0f) interpolator = CycleInterpolator(cycles)
        setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (isClickable && isBanClick) this@animationTranslate.isClickable = false
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                listener?.onAnimationRepeat(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                if (isClickable && isBanClick) this@animationTranslate.isClickable = true
                listener?.onAnimationEnd(animation)
            }
        })
    })
}

/**
 * 视图左右摇晃
 *
 * @param extent         摇晃幅度
 * @param cycles         重复次数
 * @param durationMillis 持续时间
 * @param isBanClick     在执行动画的过程中是否禁止点击
 * @param listener       动画监听器
 */
fun View.shake(extent: Float = 10.0f, cycles: Float = 7f, durationMillis: Long = 700,
               isBanClick: Boolean = false, listener: AnimationListener? = null) {
    animationTranslate(0.0f, extent, 0.0f, 0.0f, cycles, durationMillis, isBanClick, listener)
}

/**
 * 视图上下震动
 *
 * @param extent         震动幅度
 * @param cycles         重复次数
 * @param durationMillis 持续时间
 * @param isBanClick     在执行动画的过程中是否禁止点击
 * @param listener       动画监听器
 */
fun View.shock(extent: Float = 10.0f, cycles: Float = 7f, durationMillis: Long = 700,
               isBanClick: Boolean = false, listener: AnimationListener? = null) {
    animationTranslate(0.0f, 0.0f, 0.0f, extent, cycles, durationMillis, isBanClick, listener)
}

/**
 * 执行资源中定义的动画
 * @param animationId   动画资源 ID
 * @param listener      动画监听器
 */
fun View.startAnimationFromRes(animationId: Int, isBanClick: Boolean = false, listener: AnimationListener? = null) {
    this.clearAnimation()
    val isClickable = this.isClickable

    startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, animationId).apply {
        setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (isClickable && isBanClick) this@startAnimationFromRes.isClickable = false
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                listener?.onAnimationRepeat(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                if (isClickable && isBanClick) this@startAnimationFromRes.isClickable = true
                listener?.onAnimationEnd(animation)
            }
        })
    })
}

/**
 * 使用透明度渐变动画隐藏 View，结束时 visibility 设为 View.INVISIBLE
 *
 * @param durationMillis    持续时间，毫秒
 * @param isBanClick        在执行动画的过程中是否禁止点击
 * @param listener          动画监听器
 */
fun View.invisibleByAnimationAlpha(durationMillis: Long = DEFAULT_ANIMATION_DURATION,
                                   isBanClick: Boolean = false, listener: AnimationListener? = null) {
    if (this.visibility == View.INVISIBLE) return
    animationAlpha(1.0f, 0.0f, durationMillis, isBanClick, object : AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            listener?.onAnimationStart(animation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            listener?.onAnimationRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animation?) {
            this@invisibleByAnimationAlpha.visibility = View.INVISIBLE
            listener?.onAnimationEnd(animation)
        }
    })
}

/**
 * 使用透明度渐变动画隐藏 View，结束时 visibility 设为 View.GONE
 *
 * @param durationMillis    持续时间，毫秒
 * @param isBanClick        在执行动画的过程中是否禁止点击
 * @param listener          动画监听器
 */
fun View.goneByAnimationAlpha(durationMillis: Long = DEFAULT_ANIMATION_DURATION,
                              isBanClick: Boolean = false, listener: AnimationListener? = null) {
    if (this.visibility == View.GONE) return
    animationAlpha(1.0f, 0.0f, durationMillis, isBanClick, object : AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            listener?.onAnimationStart(animation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            listener?.onAnimationRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animation?) {
            this@goneByAnimationAlpha.visibility = View.GONE
            listener?.onAnimationEnd(animation)
        }
    })
}

/**
 * 使用透明度渐变动画显示 View
 *
 * @param durationMillis    持续时间，毫秒
 * @param isBanClick        在执行动画的过程中是否禁止点击
 * @param listener          动画监听器
 */
fun View.visibleByAnimationAlpha(durationMillis: Long = DEFAULT_ANIMATION_DURATION,
                                 isBanClick: Boolean = false, listener: AnimationListener? = null) {
    if (this.visibility == View.VISIBLE) return
    this.visibility = View.VISIBLE
    animationAlpha(0.0f, 1.0f, durationMillis, isBanClick, listener)
}
