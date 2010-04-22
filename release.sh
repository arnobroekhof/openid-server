#!/bin/sh
baseDirForScriptSelf=$(cd "$(dirname "$0")"; pwd)

$baseDirForScriptSelf/build.sh

DIST_DIR=$baseDirForScriptSelf/target/dist

mkdir $DIST_DIR
cp jos-webapp/target/jos-webapp-*.war $DIST_DIR
cp target/jos-*-project.* $DIST_DIR
for f in $DIST_DIR/*
do
	md5 $f > $f.md5
	gpg -a -o $f.asc --detach-sign $f
done
