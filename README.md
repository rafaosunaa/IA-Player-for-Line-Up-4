# Line Up 4 - AI Player (Connect 4)

## Description

This repository contains the implementation of an artificial intelligence for the game Line Up 4 (Connect 4), developed in Java. The AI uses the Minimax algorithm with alpha-beta pruning to make strategic decisions. The project also includes an analysis of the implementation and results, which can be found in the attached PDF file.

## Project Features

-   **Minimax algorithm with alpha-beta pruning**: Optimization in the decision-making process.
-   **Evaluation functions**: Calculation of the heuristic value of the board, state copying, and verification of winning conditions.
-   **Decision tree generation**: The AI builds a tree to explore possible moves and select the optimal one.
-   **Difficulty levels**: The game can be played at different difficulty levels.

## Main Functions

-   **InorderTraversal**: Traverses the decision tree to evaluate possible moves.
-   **CopyBoard**: Creates a copy of the current state of the board.
-   **CalculateValue**: Computes the heuristic value of a node by evaluating rows, columns, and diagonals.
-   **Check**: Verifies if there is a winning or defensive move on the board.
-   **PlayTurn**: Executes the AI's move and returns the selected column.

## Execution

To run the project:

1.  Clone this repository:
   
    `git clone <repository-URL>` 
    
2.  Open the project in your preferred IDE.
3.  Compile and run the main file to start the game.

## Analysis and Results

A thorough analysis of the AI's performance has been conducted, with details available in the attached PDF file.


# Line Up 4 - AI Player (Conecta 4)

## Descripción

Este repositorio contiene la implementación de una inteligencia artificial para el juego Line Up 4 (Conecta 4), desarrollada en Java. La IA utiliza el algoritmo Minimax con poda alfa-beta para tomar decisiones estratégicas. El proyecto también incluye un análisis sobre la implementación y resultados, que puede consultarse en el archivo PDF adjunto.

## Características del proyecto

-   **Algoritmo Minimax con poda alfa-beta**: Optimización en el proceso de toma de decisiones.
-   **Funciones de evaluación**: Cálculo del valor heurístico del tablero, copia de estados y verificación de condiciones de victoria.
-   **Generación de árboles de decisión**: La IA construye un árbol para explorar movimientos posibles y seleccionar el óptimo.
-   **Niveles de dificultad**: El juego puede ejecutarse en diferentes niveles de dificultad.

## Funciones principales

-   **Recorrer/Inorden**: Recorre el árbol de decisiones para evaluar posibles movimientos.
-   **CopiaTablero**: Realiza una copia del estado actual del tablero.
-   **CalculaValor**: Calcula el valor heurístico de un nodo, evaluando filas, columnas y diagonales.
-   **Comprueba**: Verifica si existe una jugada ganadora o defensiva en el tablero.
-   **TurnoJugada**: Ejecuta el movimiento de la IA y devuelve la columna seleccionada.

## Ejecución

Para ejecutar el proyecto:

1.  Clona este repositorio:   
    `git clone <URL-del-repositorio>` 
    
2.  Abre el proyecto en tu IDE preferido.
3.  Compila y ejecuta el archivo principal para iniciar el juego.

## Análisis y Resultados

Se ha realizado un análisis exhaustivo del rendimiento de la IA, cuyos detalles se encuentran en el archivo PDF adjunto.
