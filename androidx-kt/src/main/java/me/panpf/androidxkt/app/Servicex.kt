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

@file:Suppress("NOTHING_TO_INLINE")

package me.panpf.androidxkt.app

import android.app.Service
import android.content.Context
import android.os.Bundle
import me.panpf.androidx.app.Servicex

/**
 * Return true if the service specifying Class is running
 */
inline fun Context.isServiceRunning(serviceClass: Class<out Service>): Boolean = Servicex.isRunning(this, serviceClass)

/**
 * Start Service
 */
inline fun Context.startService(serviceClass: Class<out Service>, extras: Bundle?) = Servicex.start(this, serviceClass, extras)

/**
 * Start Service
 */
inline fun Context.startService(serviceClass: Class<out Service>) = Servicex.start(this, serviceClass)

/**
 * Start the service if the specified Class is not running.
 */
inline fun Context.tryStartService(serviceClass: Class<out Service>, extras: Bundle?): Boolean = Servicex.tryStart(this, serviceClass, extras)

/**
 * Start the service if the specified Class is not running.
 */
inline fun Context.tryStartService(serviceClass: Class<out Service>): Boolean = Servicex.tryStart(this, serviceClass)

/**
 * Stop Service
 */
inline fun Context.stopService(serviceClass: Class<out Service>) = Servicex.stop(this, serviceClass)

/**
 * Stop it if the Service for the specified Class is running
 */
inline fun Context.tryStopService(serviceClass: Class<out Service>): Boolean = Servicex.tryStop(this, serviceClass)