package de.dhbw.mapping;

import de.dhbw.dto.INetServerAddressPayload;

import java.net.InetSocketAddress;

public class INetServerAddressMapper implements PayloadMapper<InetSocketAddress, INetServerAddressPayload> {
    /**
     * @param i
     * @return
     */
    @Override
    public INetServerAddressPayload toPayload(InetSocketAddress i) {
        return new INetServerAddressPayload(i.getPort(), i.getAddress().getHostAddress());
    }

    /**
     * @param i
     * @return
     */
    @Override
    public InetSocketAddress fromPayload(INetServerAddressPayload i) {
        return new InetSocketAddress(i.getIP(), i.getPORT());
    }
}
