package javache.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionImpl implements HttpSession {

    private Map<String, Map<String, Object>> allSessions;

    public HttpSessionImpl() {
        this.allSessions = new HashMap<>();
    }

    @Override
    public void setSessionData(String sessionId,
                               Map<String, Object> dataMap) {
        if (!this.allSessions.containsKey(sessionId)) {
            this.allSessions.put(sessionId, dataMap);
        } else {
            for (Map.Entry<String, Object> kvp : dataMap.entrySet()) {
                this.allSessions.get(sessionId)
                        .put(kvp.getKey(), kvp.getValue());
            }
        }
    }

    @Override
    public Map<String, Object> getSessionData(String sessionId) {
        return this.allSessions.get(sessionId);
    }
}
