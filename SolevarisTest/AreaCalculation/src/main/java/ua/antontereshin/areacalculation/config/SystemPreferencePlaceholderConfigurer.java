package ua.antontereshin.areacalculation.config;

import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;

/**
 * Created by Anton on 05.11.2015.
 */
public class SystemPreferencePlaceholderConfigurer extends PreferencesPlaceholderConfigurer {

    public SystemPreferencePlaceholderConfigurer() {
        setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
    }

    public void setSystemProperties(Map<String,String> properties){
        properties.entrySet().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }
    
}
