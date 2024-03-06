package com.ashrafunahid.customvpn.View.Model;

public class Servers {
    private String ServerCountry;
    private String FlagUrl;
    private String Ovpn;
    private String OvpnUserName;
    private String OvpnPassword;

    public Servers() {
    }

    public Servers(String serverCountry, String flagUrl, String ovpn, String ovpnUserName, String ovpnPassword) {
        ServerCountry = serverCountry;
        FlagUrl = flagUrl;
        Ovpn = ovpn;
        OvpnUserName = ovpnUserName;
        OvpnPassword = ovpnPassword;
    }

    public String getServerCountry() {
        return ServerCountry;
    }

    public void setServerCountry(String serverCountry) {
        ServerCountry = serverCountry;
    }

    public String getFlagUrl() {
        return FlagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        FlagUrl = flagUrl;
    }

    public String getOvpn() {
        return Ovpn;
    }

    public void setOvpn(String ovpn) {
        Ovpn = ovpn;
    }

    public String getOvpnUserName() {
        return OvpnUserName;
    }

    public void setOvpnUserName(String ovpnUserName) {
        OvpnUserName = ovpnUserName;
    }

    public String getOvpnPassword() {
        return OvpnPassword;
    }

    public void setOvpnPassword(String ovpnPassword) {
        OvpnPassword = ovpnPassword;
    }
}
