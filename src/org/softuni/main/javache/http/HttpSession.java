package javache.http;

import java.util.Map;

public interface HttpSession {

    void setSessionData(String sessionId, Map<String, Object> dataMap);

    Map<String, Object> getSessionData(String sessionId);

}
