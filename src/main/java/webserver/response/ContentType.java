package webserver.response;

public enum ContentType {
    HTML("html", "text/html;charset=utf-8"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    SVG("svg", "image/svg+xml");


    private final String typeExtension;

    private final String mimeName;

    ContentType(String typeExtension, String mimeName) {
        this.typeExtension = typeExtension;
        this.mimeName = mimeName;
    }

    public static ContentType findContentType(String requestURI) {
        for (ContentType contentType : values()) {
            if (requestURI.endsWith(contentType.getTypeExtension())) {
                return contentType;
            }
        }
        return HTML;
    }

    public String getTypeExtension() {
        return typeExtension;
    }

    public String getMimeName() {
        return mimeName;
    }
}