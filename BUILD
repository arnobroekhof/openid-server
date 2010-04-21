We use Maven(http://maven.apache.org/) to manage JOS.

Before build:
	Download JavaScript Calendar
	The calendar is for persona modification page.
	Download from http://www.dynarch.com/projects/calendar/old/ ,
	unpack to directory jos-webapp/src/main/webapp/resources/jscalendar/ .

I. Build
	mvn package

Then you'll get a WAR file jos-webapp/target/jos-webapp-*.war,
You can deploy it into your servlet container,
such as Apache Tomcat(http://tomcat.apache.org/).

II. Clean
	mvn clean


III. Release
	mvn assembly:assembly

Then you'll get packages target/jos-*-project.*.
