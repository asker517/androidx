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

package me.panpf.androidx.net;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import me.panpf.javax.lang.Classx;
import me.panpf.javax.lang.Stringx;
import me.panpf.javax.util.Premisex;

@SuppressWarnings("WeakerAccess")
public class Networkx {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkState getState(Context context) {
        return NetworkState.get(context);
    }

    /**
     * Get the status of Wi-Fi
     *
     * @return The value is one of WIFI_STATE_ENABLED, WIFI_STATE_ENABLING, WIFI_STATE_DISABLED, WIFI_STATE_DISABLING, WIFI_STATE_UNKNOWN in WifiManager.
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static int getWifiState(@NonNull Context context) {
        WifiManager manager = ((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        if (manager == null) throw new IllegalStateException("WifiManager not found");
        return manager.getWifiState();
    }

    /**
     * Return true if Wi-Fi is turned on
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static boolean isWifiEnabled(@NonNull Context context) {
        int state = getWifiState(context);
        return state == WifiManager.WIFI_STATE_ENABLED || state == WifiManager.WIFI_STATE_ENABLING;
    }

    /**
     * Turn Wi-Fi on or off
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE})
    public static boolean setWifiEnabled(@NonNull Context context, boolean enable) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager == null) throw new IllegalStateException("WifiManager not found");
        return manager.setWifiEnabled(enable);
    }

    /**
     * Return true if mobile network is turned on
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileEnabled(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) throw new IllegalStateException("ConnectivityManager not found");
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Turn mobile network on or off
     */
    @RequiresPermission(Manifest.permission.CHANGE_NETWORK_STATE)
    public static boolean setMobileEnabled(Context context, boolean enabled) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) throw new IllegalStateException("ConnectivityManager not found");
        try {
            // todo 测试是否兼容 android 9
            Classx.callMethod(manager, "setMobileDataEnabled", enabled);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get local IP address
     */
    @NotNull
    public static String getLocalIpAddress(@NonNull String defaultIpAddress) {
        //noinspection ResultOfMethodCallIgnored
        Premisex.requireNonNull(defaultIpAddress, "param defaultIpAddress is not safe");
        String ipAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress != null && Stringx.isSafe(ipAddress) ? ipAddress : defaultIpAddress;
    }

    /**
     * Get local IP address
     */
    @Nullable
    public static String getLocalIpAddress() {
        String ipAddress = getLocalIpAddress("missing");
        return !"missing".equals(ipAddress) ? ipAddress : null;
    }

    /**
     * Get local IPV4 address
     */
    @NonNull
    public static String getLocalIpV4Address(@NonNull String defaultIpAddress) {
        //noinspection ResultOfMethodCallIgnored
        Premisex.requireNonNull(defaultIpAddress, "param defaultIpAddress is not safe");
        String ipAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        ipAddress = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress != null && Stringx.isSafe(ipAddress) ? ipAddress : defaultIpAddress;
    }

    /**
     * Get local IPV4 address
     */
    @Nullable
    public static String getLocalIpV4Address() {
        String ipAddress = getLocalIpV4Address("missing");
        return !"missing".equals(ipAddress) ? ipAddress : null;
    }
}
