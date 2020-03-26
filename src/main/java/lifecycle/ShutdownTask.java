package lifecycle;

import java.util.logging.Logger;

public abstract class ShutdownTask extends RunnableTask {

    private static Logger shutdownTaskLogger = Logger.getLogger(ShutdownTask.class.getName());

    public ShutdownTask(String taskId) {
        super("ShutdownTask:" + taskId, shutdownTaskLogger);
    }

    public void onShuttingDown() {
        shutdownTaskLogger.info("Notifying " + taskId + " that shutdown tasks have started");
    }

    @Override
    public abstract void runTask();
}
