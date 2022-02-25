***How did you parallelize the problem?***

Como a resolução do problema consistia em averiguar o número de occorências de um padrão numa sequência, a divisão do array de carateres (que representa a sequência) em partições não era fiável, já que os carateres estão relacionados entre si. Por isso, a solução encontrada consistiu em criar tantas threads quanto o número de padrões existentes, em que cada uma, de forma paralela, calculava o número de ocorrências para cada padrão.

***How was the speed-up with 8 cores?***

Sendo que *speed-up = tempo de execução do algoritmo sequencial / tempo de execução do algoritmo paralelo*, para os valores obtidos:

*speed-up* = 585500 / 9585600 = X0.061

Notas: Os valores utilizados no cálculo do *speed-up* são aproximações dos valores que foram calculados após múltiplas execuções do programa. Não foi feito o teste para 64 cores dada a especificação da máquina utilizada.