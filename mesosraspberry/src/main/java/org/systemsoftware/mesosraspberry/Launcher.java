package org.systemsoftware.mesosraspberry;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by anusha vijay
 *
 sudo ./mesos-master.sh --advertise_ip=54.67.39.153 --work_dir=/var/lib/mesos
 --zk=zk://ec2-54-67-39-153.us-west-1.compute.amazonaws.com:2181/mesos --quorum=1

 sudo ./mesos-slave.sh —-ip=“” —master=172.31.3.24:5050 --work_dir=/var/lib/mesos --containerizers=docker,mesos
 */

@Path("/launchMesos")
public class Launcher {

    String slaveLaunchCommand;
    String masterLaunchCommand;
    String dockerLaunchCommand;

    public Launcher() {
        this.slaveLaunchCommand = FrameworkStarter.ds.getMesosHome() + "build/bin/mesos-slave.sh --master="+FrameworkStarter.ds.getMesosIP()+":5050 --work_dir=/var/lib/mesos --containerizers=docker,mesos";
        this.masterLaunchCommand = FrameworkStarter.ds.getMesosHome() + "build/bin/mesos-master.sh --advertise_ip="+FrameworkStarter.ds.getMesosIP()+"--work_dir=/var/lib/mesos --zk=zk://"+FrameworkStarter.ds.getMesosIP()+":2181/mesos --quorum=1";
        this.dockerLaunchCommand = "";
    }

    @Context
    private DataStore ds;

    @PUT
    @Path("launchSlave")
    @Consumes(MediaType.TEXT_PLAIN)
    public void launchSlave() {
        try {
            System.out.println(slaveLaunchCommand);
            Process p = Runtime.getRuntime().exec(slaveLaunchCommand);
            FrameworkStarter.ds.setSlaveProcess(p);
            FrameworkStarter.ds.setSlaveStarted(true);
            System.out.println("Launching a Mesos Slave");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("launchMaster")
    @Consumes(MediaType.TEXT_PLAIN)
    public void launchMaster() {
        try {
            System.out.println(masterLaunchCommand);
            Process p = Runtime.getRuntime().exec(masterLaunchCommand);
            FrameworkStarter.ds.setMasterProcess(p);
            FrameworkStarter.ds.setMasterStarted(true);
            System.out.println("Launching Mesos Master");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("launchDocker")
    @Consumes(MediaType.TEXT_PLAIN)
    public void launchDocker(){
        try{
            System.out.println("Launching Docker on Mesoscluster:");
            Process p =Runtime.getRuntime().exec("dockerLaunchCommand");
            FrameworkStarter.ds.setDockerProcess(p);
            FrameworkStarter.ds.setDockerStarted(true);
            System.out.println("Launching Docker Process");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

}
