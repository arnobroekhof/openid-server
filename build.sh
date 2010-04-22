#!/bin/sh
baseDirForScriptSelf=$(cd "$(dirname "$0")"; pwd)
jscalendar="$baseDirForScriptSelf/jos-webapp/src/main/webapp/resources/jscalendar"
if [ ! -d "$jscalendar" ]; then
		echo jscalendar is not found, download from http://www.dynarch.com/projects/calendar/old/ and unpack to directory jos-webapp/src/main/webapp/resources/jscalendar/ first.
		exit 1
fi
if [ ! -f "$jscalendar/calendar_stripped.js" ]; then
		echo File jos-webapp/src/main/webapp/resources/jscalendar/calendar_stripped.js is not found, do not make any subdirectory while unpacking the jscalendar-1.0.zip.
		exit 2
fi

mvn clean package assembly:assembly
