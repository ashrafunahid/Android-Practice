package com.ashrafunahid.customvpn.View.Model;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private String country;
    private String flagUrl;
    private String ipAddress;
    private int ipPort;
    private int pings;

    public ServerInfo() {
    }

    public ServerInfo(String country, String flagUrl, String ipAddress, int ipPort, int pings) {
        this.country = country;
        this.flagUrl = flagUrl;
        this.ipAddress = ipAddress;
        this.ipPort = ipPort;
        this.pings = pings;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIpPort() {
        return ipPort;
    }

    public void setIpPort(int ipPort) {
        this.ipPort = ipPort;
    }

    public int getPings() {
        return pings;
    }

    public void setPings(int pings) {
        this.pings = pings;
    }
}
