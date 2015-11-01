package name.christianson.mike;

import name.christianson.mike.resource.TimeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class TimeApplication extends Application<TimezoneConfiguration> {

    @Override
    public void initialize(Bootstrap<TimezoneConfiguration> timezoneConfigurationBootstrap) {
    }

    @Override
    public void run(TimezoneConfiguration config, Environment environment) {
        String defaultTimezone = config.getDefaultTimezone();
        System.out.println("ggg");
        TimeResource timeResource = new TimeResource();

        // Add resources to Dropwizard
        environment.jersey().register( timeResource );
    }

	public static void main(String[] args) throws Exception {
        new TimeApplication().run(args);
    }

}