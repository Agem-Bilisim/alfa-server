
package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Memory implements Serializable
{

    private VirtualMemory virtualMemory;
    private SwapMemory swapMemory;
    private List<Device___> devices = null;
    private final static long serialVersionUID = -1401881594673409436L;

    public VirtualMemory getVirtualMemory() {
        return virtualMemory;
    }

    public void setVirtualMemory(VirtualMemory virtualMemory) {
        this.virtualMemory = virtualMemory;
    }

    public SwapMemory getSwapMemory() {
        return swapMemory;
    }

    public void setSwapMemory(SwapMemory swapMemory) {
        this.swapMemory = swapMemory;
    }

    public List<Device___> getDevices() {
        return devices;
    }

    public void setDevices(List<Device___> devices) {
        this.devices = devices;
    }

}
