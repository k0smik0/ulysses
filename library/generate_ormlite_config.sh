#!/bin/bash

java -cp $(echo $(find libs -iname *jar -exec echo "{}:" \;)$ANDROID_SDK/platforms/android-22/android.jar:bin/classes | sed 's/ //g') net.iubris.ulysses.engine.persist.sql.ormlite.DatabaseConfigUtil
