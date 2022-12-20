package com.example.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GreetingServiceAsync {

    void greetServer(String textToServer, AsyncCallback<String> asyncCallback);

}
