package de.dhbw.mapping;

import de.dhbw.dto.INetServerAddressPayload;

import java.net.InetSocketAddress;

/**
 * Mapper class responsible for converting between {@link InetSocketAddress} and
 * its serializable DTO representation {@link INetServerAddressPayload}.
 * <p>
 * This is typically used when transferring socket address data over the network,
 * such as in multiplayer setup screens or during client-server discovery.
 * </p>
 */
public class INetServerAddressMapper implements PayloadMapper<InetSocketAddress, INetServerAddressPayload> {

    /**
     * Converts a {@link InetSocketAddress} into a serializable payload.
     *
     * @param i the socket address to convert
     * @return an {@link INetServerAddressPayload} containing the IP and port
     */
    @Override
    public INetServerAddressPayload toPayload(InetSocketAddress i) {
        return new INetServerAddressPayload(i.getPort(), i.getAddress().getHostAddress());
    }

    /**
     * Reconstructs a {@link InetSocketAddress} from its payload representation.
     *
     * @param i the payload containing IP and port
     * @return a new {@link InetSocketAddress} constructed from the payload
     */
    @Override
    public InetSocketAddress fromPayload(INetServerAddressPayload i) {
        return new InetSocketAddress(i.getIP(), i.getPORT());
    }
}
