import lifecycle.LifeCycle;
import lifecycle.ShutdownTask;
import lifecycle.StartupTask;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Main class for starting OurCache
 * Can handle lifecycle events such as defined in
 */
public class OurCacheServer implements LifeCycle {

    private final Set<StartupTask> startupTasks;
    private final Set<ShutdownTask> shutdownTasks;
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(OurCacheServer.class.getName());

    public OurCacheServer(Set<StartupTask> startupTasks,
                          Set<ShutdownTask> shutdownTasks,
                          ExecutorService executorService) {
        this.startupTasks = startupTasks;
        this.shutdownTasks = shutdownTasks;
        this.executorService = executorService;
    }

    @Override
    public void onPowerUp() {
        logger.info("Starting startup tasks");
        startupTasks.forEach(
                task -> this.executorService.submit(task)
        );
    }

    @Override
    public void onShuttingDown() {
        logger.info("Warning shutdown tasks of shutdown sequence");
        shutdownTasks.forEach(
                task -> task.onShuttingDown()
        );
    }

    @Override
    public void onShutDown() {
        logger.info("Performing actual shutdown");
        final long starttime = System.currentTimeMillis();
        shutdownTasks.forEach(
                task -> this.executorService.submit(task)
        );
        try {
            this.executorService.awaitTermination(999, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.severe("Failed to shutdown the server after " + (System.currentTimeMillis() - starttime) + " ms");
            return;
        }
        logger.info("Finished server shutdown in " + (System.currentTimeMillis() - starttime) + " ms");
    }
}
