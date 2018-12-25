package com.redhat.kubakc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Service
public class MetadataService {

    @Value("${hostname.suffix}")
    private String hostnameSuffix;

    public String getHostname() {
        try {
            return String.format("%s.%s", Inet4Address.getLocalHost().getHostName(), hostnameSuffix);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        try {
            return String.format("%s", Inet4Address.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
