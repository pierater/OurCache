package lifecycle;

import java.util.logging.Logger;

public abstract class StartupTask extends RunnableTask {

    final static Logger startupTaskLogger = Logger.getLogger(StartupTask.class.getName());

    public StartupTask(String taskId) {
        super("StartupTask:"+ taskId, startupTaskLogger);
    }

    @Override
    public abstract void runTask();
}
