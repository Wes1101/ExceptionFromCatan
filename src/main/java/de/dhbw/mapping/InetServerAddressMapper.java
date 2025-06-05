package de.dhbw.mapping;

import de.dhbw.dto.INetSocketAddressPayload;

import java.net.InetSocketAddress;

public class InetServerAddressMapper implements PayloadMapper<InetSocketAddress, INetSocketAddressPayload> {
    /**
     * @param i
     * @return
     */
    @Override
    public INetSocketAddressPayload toPayload(InetSocketAddress i) {
        return new INetSocketAddressPayload(i.getPort(), i.getAddress().getHostAddress());
    }

    /**
     * @param i
     * @return
     */
    @Override
    public InetSocketAddress fromPayload(INetSocketAddressPayload i) {
        return new InetSocketAddress(i.getIP(), i.getPORT());
    }
}
