package webserver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private final String name;
    private final String value;
    private final Map<String, String> attributes = new HashMap<>();
    private static final String PATH = "Path";
    private static final String DOMAIN = "Domain";
    private static final String MAX_AGE = "Max-Age";

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie setAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public Cookie setPath(String pathValue) {
        setAttribute(PATH, pathValue);
        return this;
    }

    public Cookie setDomain(String domainValue) {
        setAttribute(DOMAIN, domainValue);
        return this;
    }

    public Cookie setMaxAge(int maxAge) {
        setAttribute(MAX_AGE, Integer.toString(maxAge));
        return this;
    }

    public Map<String, String> getCookies() {
        return Collections.unmodifiableMap(attributes);
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getAttributeValue(String key) {
        return getCookies().get(key);
    }
}
