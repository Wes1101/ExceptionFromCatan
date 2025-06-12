package de.dhbw.mapping;

import de.dhbw.dto.NetworkPayload;

public interface PayloadMapper <CLASS, PAYLOAD extends NetworkPayload> {
    PAYLOAD toPayload(CLASS networkPayload);
    CLASS fromPayload(PAYLOAD payload);
}
