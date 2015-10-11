package com.borneo.framework.common.cxf.web.service.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UTPasswordServerCallBack implements CallbackHandler {
    private final Logger log = LoggerFactory.getLogger(UTPasswordServerCallBack.class);

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        pc.setPassword("keypass");
        log.info("Server Identifier=" + pc.getIdentifier());
        log.info("Server Password=" + pc.getPassword());
    }
}