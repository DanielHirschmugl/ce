package jku.ce;

import java.net.http.HttpClient;

public class ClientApplication {
    public static void main(String[] args) {
        final HttpClient client = HttpClient.newHttpClient();
    }
}