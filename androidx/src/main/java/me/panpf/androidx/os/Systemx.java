/*
 * Copyright (C) 2017 Peng fei Pan <sky@panpf.me>
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

package me.panpf.androidx.os;

import android.support.annotation.AnyThread;

import java.io.File;

public class Systemx {
    /**
     * Return true if ROOT is already
     */
    @AnyThread
    public static boolean isRooted() {
        return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists()
                || new File("/sbin/su").exists() || new File("/su").exists();
    }
}