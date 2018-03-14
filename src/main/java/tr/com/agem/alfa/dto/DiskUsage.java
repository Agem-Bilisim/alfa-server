
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class DiskUsage implements Serializable
{

    private Integer total;
    private Integer used;
    private Float percent;
    private Integer free;
    private final static long serialVersionUID = 663976439206667215L;

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

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

}
