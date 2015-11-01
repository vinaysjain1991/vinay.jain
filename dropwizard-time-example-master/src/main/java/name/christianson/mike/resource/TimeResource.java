package name.christianson.mike.resource;

import com.google.common.base.Optional;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import name.christianson.mike.model.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Path("/time")
//@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {
	

	@GET
	public Time getTime(@QueryParam("timezone") Optional<String> timezone) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//TimeZone timeZone = TimeZone.getTimeZone(timezone.or(defaultTimezone));
		//formatter.setTimeZone(timeZone);
		String formatted = formatter.format(new Date());
		return new Time(formatted);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrackInJSON() {
		System.out.println("fdfsf");
		//Track track = new Track();
		//track.setTitle("Enter Sandman");
		//track.setSinger("Metallica");

		return "HI";

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createTrackInJSON(@FormParam("param1") String param1 ) {

		//String result = "Track saved : " + track;
		System.out.println(param1);
		return param1+"fdf";

	}
}