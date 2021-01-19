package client.api;

import java.io.Serializable;

public class Request implements Serializable {
    private final String content;

    public Request(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
