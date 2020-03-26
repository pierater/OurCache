package configuration;

import boot.Boot;
import boot.BootConfig;
import lifecycle.ShutdownTask;
import lifecycle.StartupTask;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@org.springframework.context.annotation.Configuration
@Lazy
public class Configuration {

    @Bean
    public Boot boot() {
        return new Boot();
    }

    @Bean
    public BootConfig bootConfig(@Lazy XMLConfiguration configuration) {
        return new BootConfig(configuration);
    }

    @Bean
    public XMLConfiguration config() {
        Configurations configs = new Configurations();
        try {
            XMLConfiguration appConfig = configs.xml("AppConfig.xml");
            return appConfig;
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public StartupTask discoveryServiceStartupTask() {
        final String discoveryServiceStartupTaskId = "discoveryServiceStartupTask";
        return new StartupTask(discoveryServiceStartupTaskId) {

            @Override
            public void runTask() {
                // TODO: actually start discoveryService
            }
        };
    }

    @Bean
    public ShutdownTask discoveryServiceShutdownTask() {
        final String discoveryServiceShutdownTaskId = "discoveryServiceShutdownTask";
        return new ShutdownTask(discoveryServiceShutdownTaskId) {
           @Override
            public void runTask() {
               // TODO: actually kill discoveryService
           }
        };
    }
}
