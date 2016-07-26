package org.systemsoftware.mesosraspberry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anusha vijay
 */
public class DataStore {
    String mesosHome;
    Boolean masterStarted;
    Boolean slaveStarted;
    List<String> nodesList = new ArrayList<String>();
    Process masterProcess;
    Process slaveProcess;

    public void setMasterProcess(Process masterProcess) {
        this.masterProcess = masterProcess;
    }

    public void setSlaveProcess(Process slaveProcess) {
        this.slaveProcess = slaveProcess;
    }

    public Process getMasterProcess() {
        return masterProcess;
    }

    public Process getSlaveProcess() {
        return slaveProcess;
    }

    public void setNodesList(List<String> nodesList) {
        this.nodesList = nodesList;
    }

    public List<String> getNodesList() {
        return nodesList;
    }

    public String getMesosHome() {
        return mesosHome;
    }

    public Boolean getMasterStarted() {
        return masterStarted;
    }

    public Boolean getSlaveStarted() {
        return slaveStarted;
    }

    public void setMesosHome(String mesosHome) {
        this.mesosHome = mesosHome;
    }

    public void setMasterStarted(Boolean masterStarted) {
        this.masterStarted = masterStarted;
    }

    public void setSlaveStarted(Boolean slaveStarted) {
        this.slaveStarted = slaveStarted;
    }
}
