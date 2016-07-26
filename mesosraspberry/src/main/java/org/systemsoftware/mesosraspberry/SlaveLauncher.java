package org.systemsoftware.mesosraspberry;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by anusha vijay
 */

@Path("/launchMesos")
public class SlaveLauncher {

    String slaveLaunchCommand;
    String masterLaunchCommand;
    String masterLaunchCommand2;

    public SlaveLauncher() {
        this.slaveLaunchCommand = FrameworkStarter.ds.getMesosHome() + "/bin/mesos-slave.sh";
        this.masterLaunchCommand = FrameworkStarter.ds.getMesosHome() + "/bin/mesos-master.sh";
        this.masterLaunchCommand2 = "/opt/mapr/mesos-services/mesos-services-0.1/bin/mesos-master.sh start";
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
            System.out.println("launching a mesos slave");
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
            Process p = Runtime.getRuntime().exec(masterLaunchCommand2);
            FrameworkStarter.ds.setMasterProcess(p);
            FrameworkStarter.ds.setMasterStarted(true);
            System.out.println("launching a mesos master");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
