[![License](https://img.shields.io/badge/license-GPL-blue)](http://www.gnu.org/licenses/gpl-2.0.html)
# check-in
Copyright (c) 2023 Scalable Solutions SRL   
author: Marius Gligor   
contact: marius.gligor@gmail.com   

_Abstract:_     
Sometime, we need to protect access to a class method in such a way that only calls from specific callers are allowed.

_Solution:_   
One solution to implement that is to annotate the method with an _@AllowedCallers_ annotation which provide a list of allowed callers.
Using an _AspectJ_ pointcut and advice we'll intercept the calls to the methods annotated with _@AllowedCallers_.
On _@Before_ handler we'll get the allowed callers from annotation, extract the caller from the current _StackTrace_ and check if the caller is in the allowed callers list.
If it is not, just raise an _CallerNotAllowedException_ exception.
For more details please see the provided example.   

In order to build and run the example use maven commands as follows:
```shell
$ mvn clean install
$ mvn exec:java
```