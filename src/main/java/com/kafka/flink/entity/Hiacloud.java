package com.kafka.flink.entity;

public class Hiacloud  {
    private String namespace;
    private String internalSeriesId;
    private String regions;
    private String s;
    private String v;
    private String gatewayId;
    private String pointId;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getInternalSeriesId() {
        return internalSeriesId;
    }

    public void setInternalSeriesId(String internalSeriesId) {
        this.internalSeriesId = internalSeriesId;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
}
