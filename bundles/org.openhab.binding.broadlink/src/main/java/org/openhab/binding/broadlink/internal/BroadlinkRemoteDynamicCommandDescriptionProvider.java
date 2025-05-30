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
package org.openhab.binding.broadlink.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.thing.ThingRegistry;
import org.openhab.core.thing.binding.BaseDynamicCommandDescriptionProvider;
import org.openhab.core.thing.i18n.ChannelTypeI18nLocalizationService;
import org.openhab.core.thing.link.ItemChannelLinkRegistry;
import org.openhab.core.thing.type.DynamicCommandDescriptionProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link BroadlinkRemoteDynamicCommandDescriptionProvider} allows learnt remote codes
 * (from the mapping file) to be selected from the openHAB UI without needing to be
 * triggered from a rule etc.
 *
 * @author John Marshall - Initial contribution
 */
@Component(service = { DynamicCommandDescriptionProvider.class,
        BroadlinkRemoteDynamicCommandDescriptionProvider.class })
@NonNullByDefault
public class BroadlinkRemoteDynamicCommandDescriptionProvider extends BaseDynamicCommandDescriptionProvider {

    @Activate
    public BroadlinkRemoteDynamicCommandDescriptionProvider(final @Reference EventPublisher eventPublisher,
            final @Reference ItemChannelLinkRegistry itemChannelLinkRegistry,
            final @Reference ChannelTypeI18nLocalizationService channelTypeI18nLocalizationService,
            final @Reference ThingRegistry thingRegistry) {
        this.eventPublisher = eventPublisher;
        this.itemChannelLinkRegistry = itemChannelLinkRegistry;
        this.channelTypeI18nLocalizationService = channelTypeI18nLocalizationService;
    }
}
