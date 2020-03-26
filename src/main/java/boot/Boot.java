package boot;


import configuration.Configuration;
import lifecycle.RunnableTask;
import lifecycle.ShutdownTask;
import lifecycle.StartupTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Boot {

    private static final Logger LOGGER = Logger.getLogger(Boot.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.info("Loading spring beans");
            ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
            BootConfig bootConfig = context.getBean("bootConfig", BootConfig.class);
            Boot booter = context.getBean("boot", Boot.class);
            booter.run(context, bootConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void run(ApplicationContext context,
                    BootConfig config) throws Exception {
        final long starttime = System.currentTimeMillis();
        final String serverName = "[" + config.getServerName() + "]";
        try {
            LOGGER.info(serverName + " Booting startup tasks...");
            executeTasks(StartupTask.class, context, "StartupTask");
            LOGGER.info(serverName + " Completed Boot in: [" + (System.currentTimeMillis() - starttime) + "] ms");

            while(true) {
                LOGGER.info(serverName + " Alive!");
                Thread.sleep(5000);
            }
        } finally {
            long shutdownTime = System.currentTimeMillis();
            LOGGER.info("Shutting down...");
            executeTasks(ShutdownTask.class, context, "ShutdownTask");
            LOGGER.info("Shutdown completed in: [" + (System.currentTimeMillis() - shutdownTime) + "] ms");
            LOGGER.info("Total up time: [" + (System.currentTimeMillis() - starttime) + "] ms");
        }
    }

    private <T extends RunnableTask> void executeTasks(Class<T> clazz, ApplicationContext context, String taskType) {
        List<CompletableFuture> taskFutures = new ArrayList<>();
        Map<String, T> tasks = context.getBeansOfType(clazz);
        for(Map.Entry<String, T> taskBean : tasks.entrySet()) {
            final String taskName = taskBean.getKey();
            final T task = taskBean.getValue();
            taskFutures.add(CompletableFuture.runAsync(task)
                    .thenAccept(t -> {
                        LOGGER.info(taskType + ": [" + taskName + "] completed");
                    }
            ));
        }
        // Block on startup
        // TODO: this might need a timeout..
        try {
            CompletableFuture.allOf(taskFutures.toArray(CompletableFuture[]::new)).get();
        } catch (CancellationException | ExecutionException e) {
            if(e.getCause() instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            LOGGER.severe("Failed to start server...");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
