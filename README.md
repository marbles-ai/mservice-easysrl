# An EasySRL gRPC service.

## Overview

- `easysrl`: modified build tree for easysrl.
- `src/main/proto`: [submodule](https://github.com/marbles-ai/marbles-proto) containing protobuf files. 
   See its README for help on setting up your protobuf and grpc environment.
  

## Large File Support

The model file ares are too large to store directly on github. We use the
[git-lfs extension](https://git-lfs.github.com/) to accommodate large files. 
After you have installed the dependencies you will need to setup large
file support as described below.

Install the git-lfs extension then run:

```
git lfs install
```

Home brew also recommends running `git lfs install --system` however this is only
required if you want lfs support in XCode.  We don't need it for this project so
it's up to you.

The `.gitattributes` file in the main directory contains the list of extensions
we use to indicate large files. To see the list run:

```
git lfs track
```

## Building and Testing

The build will untar grammar models so all you need to do is run:
```
gradle build
```

The tests take about 1 minute to complete. If you want to avoid running the tests run:
```
gradle build -x test
```

## Running the EasySRL service

You can start the service in a local shell or run it as a daemon in the background.
To run locally just execute `./daemons/easysrl` from the main project folder.  When
you run as a daemon a time-stamped log file is stored at ./daemons/log.

To start EasySRL as a daemon run `./scripts/start_server.sh easysrl`.

To stop the EasySRL daemon run `./scripts/stop_server.sh easysrl`.
  
To view the list of running daemons run `./scripts/stop_server.sh`.

## Modifications to Original EasySRL

I have directly integrated the gRPC daemon into the EasySRL project and added a 
`--daemonize` command line option. The daemon code is located at
[edu.uw.easysrl.main.CcgServiceHandler](easysrl/src/edu/uw/easysrl/main/CcgServiceHandler.java).

It is important to build a jar with all dependencies. The [capsule](http://www.capsule.io/)
gradle plugin is used for this purpose.  The jar file is tagged with a `capsule` suffix.
