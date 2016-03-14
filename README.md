Test GRIZZLY issue in Jersey
  
Grizzly 2.3.25 issue:

    https://java.net/jira/browse/GRIZZLY-1822

Test Environment:

    Jave 1.8.0_31-b13
    GlassFish 4.1-b13

Local Test Run Jetty:

    gradle jettyrun

Run Test:

    gradle test -i --rerun-tasks
