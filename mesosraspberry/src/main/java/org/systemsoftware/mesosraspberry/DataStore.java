package org.systemsoftware.mesosraspberry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anusha vijay
 */
public class DataStore {
    String mesosHome;
    String mesosIP;
    Boolean masterStarted;
    Boolean slaveStarted;
    Boolean dockerStarted;
    List<String> nodesList = new ArrayList<String>();
    Process masterProcess;
    Process slaveProcess;
    Process dockerProcess;


    public void setMasterProcess(Process masterProcess) {
        this.masterProcess = masterProcess;
    }

    public void setMesosIP(String mesosIP){ this.mesosIP=mesosIP.substring(0,mesosIP.indexOf(":"));}

    public String getMesosIP(){ return mesosIP;}

    public void setSlaveProcess(Process slaveProcess) {
        this.slaveProcess = slaveProcess;
    }

    public Process getMasterProcess() {
        return masterProcess;
    }

    public Process getSlaveProcess() { return slaveProcess; }

    public Process getDockerProcess() { return dockerProcess; }

    public void setDockerProcess(Process dockerProcess) {this.dockerProcess = dockerProcess;}

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

    public Boolean getSlaveStarted() { return slaveStarted; }

    public Boolean getDockerStarted() { return dockerStarted; }

    public void setMesosHome(String mesosHome) {
        this.mesosHome = mesosHome;
    }

    public void setMasterStarted(Boolean masterStarted) {
        this.masterStarted = masterStarted;
    }

    public void setSlaveStarted(Boolean slaveStarted) { this.slaveStarted = slaveStarted;}

    public void setDockerStarted(Boolean dockerStarted) {this.dockerStarted = dockerStarted;}
}
