We use Maven(http://maven.apache.org/) to manage JOS.


I. Before build
0. As we are using openid4java-0.9.5-SNAPSHOT now, you should checkout and
   install openid4java from its svn manually:
	svn checkout http://openid4java.googlecode.com/svn/trunk/ openid4java-read-only
	cd openid4java-read-only/maven2
	mvn install

1. Change current directory to jos-webapp/src/main/webapp/WEB-INF/config/:
	cd jos-webapp/src/main/webapp/WEB-INF/config/

2. Copy jdbc.dist.properties to jdbc.properties:
	cp jdbc.dist.properties jdbc.properties

3. Copy applicationContext-mail.dist.xml to applicationContext-mail.xml
	cp applicationContext-mai.dist.xml applicationContext-mail.xml


II. Build
	mvn package

Then you will get a war file in directory "jos-webapp/target/jos-webapp-*.war,
You can deploy it into your servlet container,
such as Apache Tomcat(http://tomcat.apache.org/).

III. Clean
	mvn clean


IV. Release
	mvn assembly:assembly

Then you'll get packages target/jos-*-project.*.
