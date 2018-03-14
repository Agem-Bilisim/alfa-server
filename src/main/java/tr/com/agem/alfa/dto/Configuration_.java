
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Configuration_ implements Serializable
{

    private String driverversion;
    private String firmware;
    private String autonegotiation;
    private String link;
    private String speed;
    private String port;
    private String driver;
    private String multicast;
    private String ip;
    private String broadcast;
    private String latency;
    private String duplex;
    private final static long serialVersionUID = 3858963017731806340L;

    public String getDriverversion() {
        return driverversion;
    }

    public void setDriverversion(String driverversion) {
        this.driverversion = driverversion;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getAutonegotiation() {
        return autonegotiation;
    }

    public void setAutonegotiation(String autonegotiation) {
        this.autonegotiation = autonegotiation;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getMulticast() {
        return multicast;
    }

    public void setMulticast(String multicast) {
        this.multicast = multicast;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String getDuplex() {
        return duplex;
    }

    public void setDuplex(String duplex) {
        this.duplex = duplex;
    }

}
