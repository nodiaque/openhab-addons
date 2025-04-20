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

import static org.openhab.binding.tapocontrol.internal.constants.TapoComConstants.*;
import static org.openhab.binding.tapocontrol.internal.constants.TapoThingConstants.*;
import static org.openhab.binding.tapocontrol.internal.helpers.utils.TypeUtils.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.tapocontrol.internal.TapoStateDescriptionProvider;
import org.openhab.binding.tapocontrol.internal.devices.wifi.TapoBaseDeviceHandler;
import org.openhab.binding.tapocontrol.internal.helpers.TapoErrorHandler;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.unit.Units;
import org.openhab.core.thing.ChannelGroupUID;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TAPO Smart-Plug-Device.
 *
 * @author Christian Wild - Initial contribution
 */
@NonNullByDefault
public class TapoSwitchHandler extends TapoBaseDeviceHandler {
    private final Logger logger = LoggerFactory.getLogger(TapoSwitchHandler.class);
    private TapoSwitchData switchData = new TapoSwitchData();
    private final TapoStateDescriptionProvider stateDescriptionProvider;

    /**
     * Constructor
     *
     * @param thing Thing object representing device
     */
    public TapoSwitchHandler(Thing thing, TapoStateDescriptionProvider stateDescriptionProvider) {
        super(thing);
        this.stateDescriptionProvider = stateDescriptionProvider;
    }

    @Override
    public void initialize() {
        super.initialize();
        ThingTypeUID typeUID = thing.getThingTypeUID();
    }

    /**
     * Function called by {@link org.openhab.binding.tapocontrol.internal.api.TapoDeviceConnector} if new data were
     * received
     *
     * @param queryCommand command where new data belong to
     */
    @Override
    public void newDataResult(String queryCommand) {
        super.newDataResult(queryCommand);
        if (DEVICE_CMD_GETINFO.equals(queryCommand)) {
            switchData = connector.getResponseData(TapoSwitchData.class);
            updateChannels(switchData);
        }
    }

    /*****************************
     * HANDLE COMMANDS
     *****************************/

    /**
     * handle command sent to device
     *
     * @param channelUID channelUID command is sent to
     * @param command command to be sent
     */
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        String channel = channelUID.getIdWithoutGroup();
        if (command instanceof RefreshType) {
            queryDeviceData();
        } else {
            switch (channel) {
                case CHANNEL_OUTPUT:
                    handleOnOffCommand(command);
                    break;
                default:
                    logger.warn("({}) command type '{}' not supported for channel '{}'", uid, command,
                            channelUID.getId());
            }
        }
    }

    private void handleOnOffCommand(Command command) {
        switchOnOff(command == OnOffType.ON ? Boolean.TRUE : Boolean.FALSE);
    }



    /*****************************
     * SEND COMMANDS
     *****************************/

    /**
     * Switch device On or Off
     *
     * @param on if true device will switch on. Otherwise switch off
     */
    protected void switchOnOff(boolean on) {
        switchData.switchOnOff(on);
        connector.sendCommandAndQuery(switchData, switchData.supportsMultiRequest());
    }


    /*****************************
     * UPDATE CHANNELS
     *****************************/

    protected void updateChannels(TaposwitchData deviceData) {
        updateState(getChannelID(CHANNEL_GROUP_ACTUATOR, CHANNEL_OUTPUT), getOnOffType(deviceData.isOn()));
        updateState(getChannelID(CHANNEL_GROUP_DEVICE, CHANNEL_WIFI_STRENGTH),
                getDecimalType(deviceData.getSignalLevel()));
        updateState(getChannelID(CHANNEL_GROUP_DEVICE, CHANNEL_ONTIME),
                getTimeType(deviceData.getOnTime(), Units.SECOND));
        updateState(getChannelID(CHANNEL_GROUP_DEVICE, CHANNEL_OVERHEAT), getOnOffType(deviceData.isOverheated()));
    }
}