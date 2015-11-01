package name.christianson.mike;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;


public class TimezoneConfiguration extends Configuration {

	@NotEmpty
	@JsonProperty
	private String defaultTimezone;

	public String getDefaultTimezone() {
		return defaultTimezone;
	}
}