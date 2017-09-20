# Place marbles jar dependencies here.

Need to automate this for both python and java. This can be done using a github
personal access token. The `build.gradle` has the beginnings of a script that will
interface with github API's but it doesn't work yet.

We could use [jitpack](https://jitpack.io/) but that only works for java - we would
still have a problem with python wheels. My current solution is to download manually.

**Do not build dependent jars locally. Always download the tagged release and place
here.**

I found [this solution](https://github.com/gruntwork-io/fetch) by gruntwork-io but 
have not had time to investigate further.

Also found [this link](https://gist.github.com/illepic/32b8ad914f1dc80446c7e81c3be4e286).

*PWG*