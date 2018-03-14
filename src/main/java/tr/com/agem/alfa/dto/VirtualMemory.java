
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class VirtualMemory implements Serializable
{

    private Integer free;
    private Integer total;
    private Integer used;
    private Integer cached;
    private Integer shared;
    private Float percent;
    private Integer available;
    private Integer active;
    private Integer inactive;
    private Integer buffers;
    private final static long serialVersionUID = -8674429435213577156L;

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public Integer getShared() {
        return shared;
    }

    public void setShared(Integer shared) {
        this.shared = shared;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getInactive() {
        return inactive;
    }

    public void setInactive(Integer inactive) {
        this.inactive = inactive;
    }

    public Integer getBuffers() {
        return buffers;
    }

    public void setBuffers(Integer buffers) {
        this.buffers = buffers;
    }

}
