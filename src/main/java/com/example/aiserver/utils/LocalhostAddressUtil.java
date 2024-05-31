package com.example.aiserver.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalhostAddressUtil {
    public static String getLocalHostAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "127.0.0.1"; // fallback to localhost
        }
    }
}

