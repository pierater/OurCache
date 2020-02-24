package configuration;

import boot.Boot;
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
}
