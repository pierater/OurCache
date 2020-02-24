package boot;


import configuration.Configuration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("Boot")
public class Boot {

    private static final Logger LOGGER = Logger.getLogger(Boot.class.getName());

    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
            XMLConfiguration appConfig = context.getBean("config", XMLConfiguration.class);
            Boot booter = context.getBean("boot", Boot.class);
            booter.run(context, appConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void run(ApplicationContext context,
                    XMLConfiguration appConfig) throws Exception {
        final String appName = "[" + appConfig.getString("AppName.name") + "]";

        LOGGER.info(appName + " Booting...");
        // TODO: Implement boot
        LOGGER.info(appName + " Booted!");

        while(true) {
            LOGGER.info(appName + " Alive!");
            Thread.sleep(5000);
        }
    }
}
