# **PPC Assignment 1 Guidelines**

## **Objective**

In this assignment we will tackle a common challenge in bioinformatics: detect and count number of DNA patterns in large DNA sequences.

A DNA sequence can be seen as an array of characters, in which only 'a', 't', 'c' and 'g' are valid. A DNA pattern is also a sequence, much smaller in size.

A practical example: Having the (uncommonly small, but useful for a demo) sequence "cgttttt" DNA sequence. If we are looking for the patterns "cgt" and "ttt", we will have the following matches:

- "cgt" found at index 0
- "ttt" found at index 2
- "ttt" found at index 3
- "ttt" found at index 4

Because we are looking for the number of matches, the actual answer should be [1,3] in the order in which the patterns appear in the original list.

## **Report**

Your report should answer the following questions:

- **How did you parallelize the problem?**

    **R**: Como a resolução do problema consistia em averiguar o número de occorências de um padrão numa sequência, a divisão do array de carateres (que representa a sequência) em partições não era fiável, já que os carateres estão relacionados entre si. Por isso, a solução encontrada consistiu em criar tantas threads quanto o número de padrões existentes, em que cada uma, de forma paralela, calculava o número de ocorrências para cada padrão.

- **Did you have concurrency issues? Why or why not?**

- **How was the speed-up with 8 cores? And with 64 cores?**

- **What was the occupancy with 8 cores? And with 64 cores?**
