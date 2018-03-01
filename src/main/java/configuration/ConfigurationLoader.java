package configuration;


import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

public class ConfigurationLoader {

    public final static String EMAIL_CONFIG_FILE = "email_config.properties";
    //final static Logger log = LogManager.getLogger(ConfigurationLoader.class);

    public ConfigurationLoader() {
        String rawPath = new File(EMAIL_CONFIG_FILE).toURI().getRawPath();
        ConfigFactory.setProperty("emailconfig", rawPath);
    }

    public Configuration get() {
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
