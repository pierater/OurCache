package lifecycle;

import java.util.logging.Logger;

/**
 * A task that can be run within an{@link java.util.concurrent.Executor}
 *
 * Subclasses of this may be startupTasks or shutdownTasks
 */
public abstract class RunnableTask implements Runnable {

    final String taskId;
    private final Logger logger;

    /**
     * Construct a new RunnableTask
     * @param taskId unique ID to identify this tasks progress in logs
     * @param logger parent logger to log with
     */
    public RunnableTask(String taskId, Logger logger) {
        this.taskId = taskId;
        this.logger = logger;
    }

    /**
     * Implement this to perform work to be executed as part of this {@link RunnableTask}
     */
    abstract void runTask();

    @Override
    public void run() {
        final long startTime = System.currentTimeMillis();
        logger.info("Starting task [" + taskId + "]");
        runTask();
        logger.info("Finished task [" + taskId + "] in " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
