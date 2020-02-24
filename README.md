# OurCache

The goal for OurCache is to be a distributed cache.

Given a cluster of any number of nodes, OurCache operates similar to a Map<String, Object> interface.

Nodes in the cluster can be grouped for cache distribution. ie

If you have a cluster of 10, and you want to logically split it into 2 groups.

Group A: 5 members
Group B: 5 members

Now when any node receives a PUT operation, the VALUE placed
will first be broadcast to a specific group based on the KEY provided.


Any node, either in group A or B can retrieve that VALUE given a KEY.
Although there is a strong benefit for a member of the same group as the VALUE to perform the GET operation as it will already be in its local JVM memory.
If a member from a different group attempts to perform a GET operation, it must first request it from any member of the specified group, making the latency much longer than a local group.




# Install

To install simply run:

mvn clean install

In the future this project is planned to be dockerized. 
This is to be able to have multiple nodes running locally at once
