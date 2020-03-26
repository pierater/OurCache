package boot;

import org.apache.commons.configuration2.XMLConfiguration;

/**
 * Contains various params for booting this server
 */
public class BootConfig {

    private final String serverName;

    public BootConfig(XMLConfiguration configuration) {
        this.serverName = configuration.getString("AppName.name");
    }

    public String getServerName() {
        return this.serverName;
    }
}
