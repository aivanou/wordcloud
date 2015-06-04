package org.wordcloud.protocol;


import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

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
}
