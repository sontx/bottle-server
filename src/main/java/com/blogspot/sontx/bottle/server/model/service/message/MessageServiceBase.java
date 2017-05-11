package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.service.auth.AuthService;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Log4j
abstract class MessageServiceBase {
    @Value("${bottlefs.server.delete}")
    private String bottlefsServer;
    @Value("${bottlefs.admin.id}")
    private String bottlefsAdminId;
    private final AuthService authService;

    private static OkHttpClient client = new OkHttpClient();

    @Autowired
    public MessageServiceBase(AuthService authService) {
        this.authService = authService;
    }

    void deleteMedia(String name) {
        String auth = "Bearer " + authService.getBottlefsAuthToken();

        String url = bottlefsServer + "/" + name;
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_JSON), "");
        Request request = new Request.Builder()
                .url(url)
                .method("DELETE", requestBody)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info("deleted");
            }
        });
    }
}
