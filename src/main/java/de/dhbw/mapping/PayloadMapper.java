package de.dhbw.mapping;

import de.dhbw.dto.NetworkPayload;

/**
 * Generic interface for mapping between application-level objects and their network-transferable
 * payload representations.
 *
 * <p>Implementations of this interface are responsible for converting back and forth between an
 * internal Java object ({@code CLASS}) and a serializable DTO that extends {@link NetworkPayload}.</p>
 *
 * @param <CLASS>   the type of the internal object being mapped (e.g. InetSocketAddress)
 * @param <PAYLOAD> the network-safe representation, must extend {@link NetworkPayload}
 */
public interface PayloadMapper<CLASS, PAYLOAD extends NetworkPayload> {

    /**
     * Converts an internal Java object to its network payload representation.
     *
     * @param networkPayload the object to convert
     * @return a serializable payload object
     */
    PAYLOAD toPayload(CLASS networkPayload);

    /**
     * Converts a network payload back into its original internal object form.
     *
     * @param payload the serialized payload to convert
     * @return the original internal object
     */
    CLASS fromPayload(PAYLOAD payload);
}
