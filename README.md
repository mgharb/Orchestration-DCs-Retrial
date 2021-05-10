# Orchestration-DCs-Retrial
Simulator of resource orchestration strategies with retrials for latency-sensitive network slicing over distributed DCs

The simulator provides a set of orchestration strategies with a retrial mechanism. The aim is to minimize the latency or the blocking probability. of network slicing requests. The simulator operates over a flexi-grid network.

The topology is composed of 12 DCs.

The main class is Simulator/Run.java

Before starting the simulator, some parameters have to be fixed: number of iterations, number of requests, number of frequency slots per request, etc.
