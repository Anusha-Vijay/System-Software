#!/usr/bin/env python
import sys
import logging
import uuid
import signal

import mesos.interface
from mesos.interface import Scheduler, mesos_pb2
import mesos.native
from mesos.native import MesosSchedulerDriver
import time

logging.basicConfig(level=logging.INFO)
TASK_CPUS = 0.1
TASK_MEM = 256


def new_task(offer, name, cmd):
    task = mesos_pb2.TaskInfo()
    task.task_id.value = str(uuid.uuid4())
    task.slave_id.value = offer.slave_id.value
    task.name = name
    task.command.value = cmd

    cpus = task.resources.add()
    cpus.name = "cpus"
    cpus.type = mesos_pb2.Value.SCALAR
    cpus.scalar.value = TASK_CPUS

    mem = task.resources.add()
    mem.name = "mem"
    mem.type = mesos_pb2.Value.SCALAR
    mem.scalar.value = TASK_MEM

    return task


def new_docker_task(offer, name, cmd):
    task = new_task(offer, name, cmd)

    container = mesos_pb2.ContainerInfo()
    container.type = 1

    docker = mesos_pb2.ContainerInfo.DockerInfo()
    docker.image = "busybox"
    docker.network = 3
    docker.force_pull_image = True

    container.docker.MergeFrom(docker)
    task.container.MergeFrom(container)

    return task


#def max_tasks_to_run_with_offer( offer):
#    logging.info("CPUs: %s MEM: %s",
#                 offer.resources[0].scalar.value,
#                 offer.resources[1].scalar.value)

#    cpu_tasks = int(offer.resources[0].scalar.value/TASK_CPUS)
#    mem_tasks = int(offer.resources[1].scalar.value/TASK_MEM)

#   return cpu_tasks if cpu_tasks <= mem_tasks else mem_tasks


def shutdown(signal, frame):
    logging.info("Shutdown signal")
    driver.stop()
    time.sleep(5)
    sys.exit(0)

def reviveTask(self,driver,update):
    if update.status == TASK_KILLED:
        driver.requestResources(self,requests)
        driver.acceptOffers(self,offer)
        tasks=[]
        task=new_docker_task(offer,"Hello Docker", "echo helloworld")
        driver.launchTasks(offer.id,tasks)


def start_docker(self,driver,offers):
    logging.info("In the start_docker function")
    tasks=[]
    task=new_docker_task(offer,"Hello Docker","echo hello world")
    logging.info("Start %s tasks", self.runningTasks)
    logging.info("Added task %s "
                             "using offer %s.",
                             task.task_id.value,
                             offer.id.value)
    driver.launchTasks(offer.id,tasks)
    return status
            

    
def shutdown(signal, frame):
    logging.info("Shutdown signal")
    driver.stop()
    time.sleep(5)
    sys.exit(0)

def launchFramework():
    framework = mesos_pb2.FrameworkInfo()
    framework.user = ""  # Have Mesos fill in the current user.
    framework.name = "hello-world"
    dockerScheduler = dockerScheduler()
    driver = MesosSchedulerDriver(
        dockerScheduler,
        framework,
        "zk://localhost:2181/mesos"  # assumes running on the master
    )
    driver.start()

    

def startdocker():
    launchFramework()
    if status.update == TASK_RUNNING
    return true 
    else 
    return false

class dockerScheduler(Scheduler):
    def __init__(self):
        self.runningTasks = 0

    def registered(self, driver, framework_id, master_info):
        logging.info("Registered with framework id: %s on: %s",
                     framework_id, master_info.hostname)

    def resourceOffers(self, driver, offers):
        logging.info("Recieved resource offers: %s",
                     [o.id.value for o in offers])
        start_docker(self,driver,offers)
        
    
    def statusUpdate(self, driver, update):
        logging.info("Task %s is in state %s" %
                     (update.task_id.value,
                      mesos_pb2.TaskState.Name(update.state)))

        if update.state == mesos_pb2.TASK_RUNNING:
            self.runningTasks += 1
            logging.info("Running tasks: %s", self.runningTasks)
            return

        if update.state != mesos_pb2.TASK_RUNNING or\
           update.state != mesos_pb2.TASK_STARTING or\
           update.state != mesos_pb2.TASK_STAGING:
            self.runningTasks -= 1
            logging.info("Running tasks: %s", self.runningTasks)
             reviveTask(self,driver,update)

if __name__ == '__main__':
    doc1 = startdocker()
    print doc1
    logging.info("Listening for Ctrl-C")
    signal.signal(signal.SIGINT, shutdown)
    while True:
        time.sleep(5)
    sys.exit(0)

    
