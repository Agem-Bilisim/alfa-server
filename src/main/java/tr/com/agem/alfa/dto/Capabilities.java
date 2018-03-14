
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Capabilities implements Serializable
{

    private String removable;
    private String partitionedGpt;
    private String gpt100;
    private String partitioned;
    private final static long serialVersionUID = -8710401330192635165L;

    public String getRemovable() {
        return removable;
    }

    public void setRemovable(String removable) {
        this.removable = removable;
    }

    public String getPartitionedGpt() {
        return partitionedGpt;
    }

    public void setPartitionedGpt(String partitionedGpt) {
        this.partitionedGpt = partitionedGpt;
    }

    public String getGpt100() {
        return gpt100;
    }

    public void setGpt100(String gpt100) {
        this.gpt100 = gpt100;
    }

    public String getPartitioned() {
        return partitioned;
    }

    public void setPartitioned(String partitioned) {
        this.partitioned = partitioned;
    }

}
