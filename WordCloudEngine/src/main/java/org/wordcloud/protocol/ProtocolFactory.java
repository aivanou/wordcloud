package org.wordcloud.protocol;


import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates a map of protocols and invokes corresponding one using protocol name
 * Currently supports: Rss link search and twitter hashtag
 * The appropriate protocols are described in wordcloud.xml spring configuation file in resources folder
 */
public class ProtocolFactory {

    private final Map<String, Protocol> protocols;

    public ProtocolFactory(Map<String, Protocol> prots) {
        this.protocols = new HashMap<>();
        for (String name : prots.keySet()) {
            protocols.put(name, prots.get(name));
        }
    }

    public Reader search(String query, String protocolName) throws ProtocolException {
        if (!protocols.containsKey(protocolName))
            throw new ProtocolException("cannot recognize: " + protocolName);
        Protocol prot = protocols.get(protocolName);
        return prot.search(query);
    }

    public void asyncSearch(String query, String protocolName, ProtocolCallback callback) {
        if (!protocols.containsKey(protocolName)) {
            callback.onFailure(new ProtocolException("cannot recognize: " + protocolName));
        }
        Protocol prot = protocols.get(protocolName);
        prot.asyncSearch(query, callback);
    }

    public void stop() {
        for (Protocol protocol : protocols.values()) {
            protocol.stop();
        }
    }
}
