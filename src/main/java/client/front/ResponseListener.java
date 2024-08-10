package client.front;

import common.interaction.Response;

public interface ResponseListener {
    void onResponseReceived(Response response);
}
