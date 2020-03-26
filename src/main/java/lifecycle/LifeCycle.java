package lifecycle;

/**
 * Anything implementing this interface can adhere to lifecycle events
 */
public interface LifeCycle {

    /**
     * Called immediately to initialize any long running startup processes
     */
    void onPowerUp();

    /**
     * Called 15seconds before server is shutdown
     * Used to signal any long running or last second operations that need to happen before total shutdown
     *
     * default noop
     */
    default void onShuttingDown() {};

    /**
     * Used as a last call to notify that the server is shutting down.
     * There is no timing guarantee for when the server shuts down after this is called
     *
     * default noop
     */
    default void onShutDown() {};
}
