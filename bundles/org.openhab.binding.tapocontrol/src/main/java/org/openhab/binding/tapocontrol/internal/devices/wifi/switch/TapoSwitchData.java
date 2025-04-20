/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.tapocontrol.internal.devices.wifi.switch;

import static org.openhab.binding.tapocontrol.internal.TapoControlHandlerFactory.GSON;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.tapocontrol.internal.devices.dto.TapoBaseDeviceData;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.library.types.PercentType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Tapo-Device Information class
 *
 * @author Christian Wild - Initial contribution
 */
@NonNullByDefault
public class TapoSwitchData extends TapoBaseDeviceData {
    @SerializedName("device_on")
    @Expose(serialize = true, deserialize = true)
    private boolean deviceOn = false;

    @Expose(serialize = false, deserialize = true)
    private long onTime = 0;

    @SerializedName("time_usage_past7")
    @Expose(serialize = false, deserialize = true)
    private long timeUsagePast7 = 0;

    @SerializedName("time_usage_past30")
    @Expose(serialize = false, deserialize = true)
    private long timeUsagePast30 = 0;

    @SerializedName("time_usage_today")
    @Expose(serialize = false, deserialize = true)
    private long timeUsageToday = 0;


    /***********************************
     *
     * SET VALUES
     *
     ************************************/

    public void switchOn() {
        deviceOn = true;
    }

    public void switchOff() {
        deviceOn = false;
    }

    public void switchOnOff(boolean on) {
        deviceOn = on;
    }


    /***********************************
     *
     * GET VALUES
     *
     ************************************/
   

    public boolean isOff() {
        return !deviceOn;
    }

    public boolean isOn() {
        return deviceOn;
    }

    public long getOnTime() {
        return onTime;
    }

    public long getTimeUsagePast7() {
        return timeUsagePast7;
    }

    public long getTimeUsagePast30() {
        return timeUsagePast30;
    }

    public long getTimeUsagePastToday() {
        return timeUsageToday;
    }

    public boolean supportsMultiRequest() {
        return !getHardwareVersion().startsWith("1");
    }

    @Override
    public String toString() {
        return toJson();
    }

    public String toJson() {
        return GSON.toJson(this);
    }
}