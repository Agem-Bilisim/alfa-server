
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class SwapMemory implements Serializable
{

    private Integer free;
    private Integer total;
    private Integer sout;
    private Integer used;
    private Integer sin;
    private Float percent;
    private final static long serialVersionUID = 3199783620401023769L;

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

    public Integer getSout() {
        return sout;
    }

    public void setSout(Integer sout) {
        this.sout = sout;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getSin() {
        return sin;
    }

    public void setSin(Integer sin) {
        this.sin = sin;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

}
