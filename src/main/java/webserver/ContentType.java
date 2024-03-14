package webserver;

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


}
