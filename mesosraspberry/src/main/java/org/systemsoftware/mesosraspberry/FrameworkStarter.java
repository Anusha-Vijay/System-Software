package org.systemsoftware.mesosraspberry;

import org.apache.mesos.Protos.FrameworkInfo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by anusha vijay
 */
public class FrameworkStarter {

    final public static DataStore ds = new DataStore();

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Provide 2 args : IP:Port dockerImageName MesosHome nodeList");
            System.exit(1);
        }

        int timeOut = 0;

        ds.setMesosHome(args[2]);
        ds.setMesosIP(args[0]);

        // creating a framework object
        FrameworkInfo.Builder mesosFramework = FrameworkInfo.newBuilder()
                .setName("MesosOnRaspberry")
                .setUser("")
                .setFailoverTimeout(timeOut);

        // Not sure of this
        if (System.getenv("MESOS_CHECKPOINT") != null) {
            System.out.println("Enabling checkpoint for the framework");
            mesosFramework.setCheckpoint(true);
        }

        String dockerName = args[1];

        // Create a scheduler object
        DockerPIScheduler dockerScheduler = new DockerPIScheduler(dockerName);

        mesosFramework.setPrincipal("docker-pi-java");
//        MesosSchedulerDriver driver =new MesosSchedulerDriver(dockerScheduler, mesosFramework.build(), args[0]);

//        int status = driver.run() == Protos.Status.DRIVER_STOPPED ? 0 : 1;

        // Ensure that the driver process terminates.
//        driver.stop();

//        System.exit(status);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // create a server object associate with a port
        System.out.println("Starting server on port 8081");
        Server appServer = new Server(8081);
        appServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                Launcher.class.getCanonicalName());

        try {
            appServer.start();
            appServer.join();
        } finally {
            appServer.destroy();
            if(ds.getSlaveProcess() != null)
                ds.getSlaveProcess().destroy();
            if(ds.getMasterProcess() != null)
                ds.getMasterProcess().destroy();
        }

    }
}
