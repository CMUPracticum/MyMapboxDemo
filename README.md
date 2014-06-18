# MyMapboxDemo
Instead of using Gradle or Maven, this application imports the libraries in .jar files manually. Libraries used and their links are:

1. Mapbox Android SDK - [0.2.3](http://search.maven.org/remotecontent?filepath=com/mapbox/mapboxsdk/mapbox-android-sdk/0.2.3/mapbox-android-sdk-0.2.3.jar)
2. Android Support V4 - 19.1 (Located in Android SDK)
3. OkHttp - [1.3.0](http://search.maven.org/remotecontent?filepath=com/squareup/okhttp/okhttp/1.3.0/okhttp-1.3.0-jar-with-dependencies.jar)
4. NineOldAndroids - [2.4.0](http://search.maven.org/remotecontent?filepath=com/nineoldandroids/library/2.4.0/library-2.4.0.jar)
5. DiskLRUCache - [2.0.1](http://search.maven.org/remotecontent?filepath=com/jakewharton/disklrucache/2.0.1/disklrucache-2.0.1.jar)
6. Guava - [16.0.1](http://search.maven.org/remotecontent?filepath=com/google/guava/guava/16.0.1/guava-16.0.1.jar)

##Configuration
1. Clone the project from repository
    - $ git clone https://github.com/CMUPracticum/MyMapboxDemo.git
2. Import the project to Eclipse
3. Set up build path
    - Project>Properties>Java Build Path>Libraries>Add JARs
    - Include .jar files from /libs/ (*Optional: mapbox-android-sdk-0.2.3-javadoc.jar and mapbox-android-sdk-0.2.3-sources.jar should be included as source attachment and Javadoc location of mapbox-android-sdk-0.2.3.jar*)
4. Clean and build project
5. Please note that the tiles should be manually copied to sdcard now (this will be fixed).
    - $ cd ~/<Android SDK Path>/platform-tools
    - $ sudo ./adb push ~/<MyMapboxDemo Path>/assets/Test01.mbtiles /mnt/sdcard/trailscribe/

Then you should be able to run the application on devices.

======

Ching-Lun Lin, clallenlin@gmail.com
