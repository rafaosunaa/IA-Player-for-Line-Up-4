/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta4;

import java.awt.Color;
import java.util.ArrayList;


/**
 *
 * @author José María Serrano
 * @version 1.5 Departamento de Informática. Universidad de Jáen

 Inteligencia Artificial. 2º Curso. Grado en Ingeniería Informática

 Clase IAPlayer para representar al jugador CPU que usa una técnica de IA

 Esta clase es la que tenemos que implementar y completar
 *
 */


public class IAPlayer extends Player {
    
    public class Nodo{
        
        private final Grid nodo;
        private double valor;
        private double alfa;
        private double beta;
        private final ArrayList<Nodo> hijos;
        
        public Nodo(Grid nodo, int valor){
            this.nodo = nodo;
            this.valor = valor;
            alfa = Double.NEGATIVE_INFINITY;
            beta = Double.POSITIVE_INFINITY;
            hijos = new ArrayList<>();
        }
        /**
         * @brief Calcula el hijo de la columna indicada(si se puede insertar)
         * @param nivel Nivel actual para calcular jugador
         * @param col Columana en la que se insertaria la ficha
         * @return La x donde se inserta la ficha
         */
        public int calculaPosiblesMov(int nivel,int col){
            
            int x = -2;
            if(!this.nodo.fullColumn(col)){//Si no esta llena la columna

                Grid nueva = copiaTablero(nodo);//Crea un tablero copia del pasado
                

                if((nivel + 1) % 2 == 0){//Calcula el jugador y introduce la ficha
                   x = nueva.setButton(col, Conecta4.PLAYER1);
                }else{
                   x = nueva.setButton(col, Conecta4.PLAYER2);
                }
                Nodo aux = new Nodo(nueva,0);

                this.hijos.add(aux);//La mete en el array de hijos

            }
            
            return x;//Devuelve x
        }
        /**
         * @brief Funcion que copia una Grid en otra
         * @param tablero Grid a copiar
         * @return Copia de la pasada por parametro
         */
        private Grid copiaTablero(Grid tablero){
            Grid nueva = new Grid(tablero.getFilas(), tablero.getColumnas(),"assets/player1.png", "assets/player2.png");
            Conecta4 a = null;
            for(int i = 0; i < nueva.getFilas(); i++){
                for(int j = 0; j < nueva.getColumnas(); j++){
                    nueva.initialize(i, j, a, Color.BLACK);
                }
            }
            int[][] arr = tablero.toArray();

            for(int j = arr.length-1; j >= 0; j--){
                for(int k = 0; k < arr[j].length; k++){
                    if(arr[j][k] == -1){
                        nueva.setButton(k, Conecta4.PLAYER2);
                    }else if(arr[j][k] == 1){
                        nueva.setButton(k, Conecta4.PLAYER1);
                    }
                    
                }
            }
            
            return nueva;
        }

        public Grid getNodo() {
            return nodo;
        }

        public double getValor() {
            return valor;
        }

        public ArrayList<Nodo> getHijos() {
            return hijos;
        }

        public double getAlfa() {
            return alfa;
        }

        public double getBeta() {
            return beta;
        }
        
        
        /**
         * @brief LLama a la funcion inorden con la raiz
         * @param conecta Numero de fichas para ganar
         * @param maxNivel Nivel maximo a la que baja
         */
        public void recorrer(int conecta,int maxNivel){
            int[][] aux = this.nodo.toArray();
            int col = 0;
            for(int i = 0; i < this.nodo.getColumnas(); i++){//Calcula donde se inserto la primera moneda
                if(aux[this.nodo.getFilas()-1][i] != 0){
                    col = i;
                    break;
                }
            }
            
            inOrden(this,0,this.nodo.getFilas()-1,col,conecta,maxNivel);//LLama a inorden
        }
        /**
         * @brief Llama recursivamente para calcular hijos e hijos de cada uno
         * @param nodo Nodo actual
         * @param nivel Nivel actual para calcular jugador
         * @param x Donde se introdució la ultima ficha
         * @param y Donde se introdució la ultima ficha
         * @param conecta Numero de fichas para hacer linea
         * @param maxNivel Nivel maximo al que baja
         */
        private void inOrden(Nodo nodo, int nivel,int x, int y,int conecta, int maxNivel){
            
            if(nivel < maxNivel){
                if(nodo.nodo.checkWin(x, y, conecta) == 0){//Comprueba si no es ganador el tablero 
                    if(nodo.hijos.isEmpty()){//Si no tiene hijos 
                        int actual = 0;
                        boolean cogido = false;
                        int pos;
                        for(int i = 0; i < this.nodo.getColumnas(); i++){//Para cada columna
                            
                            pos = nodo.calculaPosiblesMov(nivel,i);//Calcula los hijos de la columna actual y devuelve la x

                            if((!nodo.getHijos().isEmpty()) && pos != -2){//Si tiene hijos y la x(pos) tiene,si no esta llena la columna 
                                double a =  nodo.alfa;
                                nodo.hijos.get(actual).alfa = a;//Asignamos alfa al siguiente hijo
                                double b = nodo.beta;
                                nodo.hijos.get(actual).beta = b;//Asignamos Beta al siguiente hijo
                                inOrden(nodo.hijos.get(actual), nivel+1,pos,i,conecta,maxNivel);//Se llama al hijo generado
                                
                                if(nivel % 2 == 0){//Si es nivel Max
                                    if(cogido == false){//Si no ha cogido nada
                                        double v = nodo.hijos.get(actual).valor;//Actualiza valor con el primero
                                        nodo.valor = v;
                                        cogido = true;
                                        if(nodo.valor >= nodo.beta){//Comprueba si poda o no
                                            break;
                                        }
                                        if(nodo.valor > nodo.alfa){//Actualiza alfa si es necesario
                                            double z = nodo.valor;
                                            nodo.alfa = z;
                                        }

                                        
                                    }else{
                                        if(nodo.hijos.get(actual).valor > nodo.valor){//Si ya tiene valor comprara con el nuevo
                                            double v = nodo.hijos.get(actual).valor;
                                            nodo.valor = v;
                                            if(nodo.valor >= nodo.beta){
                                                break;
                                            }
                                            if(nodo.valor > nodo.alfa){
                                                double z = nodo.valor;
                                                nodo.alfa = z;
                                            }
                                            

                                            
                                        }
                                    }
                                }else{//Si es nivel Min
                                    if(cogido == false){
                                        double v = nodo.hijos.get(actual).valor;
                                        nodo.valor = v;
                                        cogido = true;
                                        if(nodo.valor <= nodo.alfa){
                                                break;
                                        }
                                        if(nodo.valor < nodo.beta){
                                            double z = nodo.valor;
                                            nodo.beta = z;
                                        }


                                    }else{
                                        if(nodo.hijos.get(actual).valor < nodo.valor){
                                            double v = nodo.hijos.get(actual).valor;
                                            nodo.valor = v;
                                            if(nodo.valor <= nodo.alfa){
                                                break;
                                            }
                                            if(nodo.valor < nodo.beta){
                                                double z = nodo.valor;
                                                nodo.beta = z;
                                            }


                                        }
                                    }
                                }
                                
                                actual++;//Siguiente hijo
                            }

                        }
                        
                        
                    }      
                }else{
                   calculaValor(nodo,conecta);//Si es solucion calcula su heuristica
                }
            }else if(nivel == maxNivel){
                calculaValor(nodo,conecta);//Si es el nodo del nivel maximo restringido calcula heuristica
            }
  
        }
        /***
         * @brief Funcion que calcula el valor de los nodos terminales
         * @param nodo Nodo actual
         * @param conecta Fichas para ganar
         */
        private void calculaValor(Nodo nodo, int conecta){
            if(nodo.getHijos().isEmpty()){
                //Calculo valores
                int cont = 0;
                int actual = 0;
                int enemigo = 0;
              
                int[][] aux = nodo.nodo.toArray();
                do{
                    //Actual para calcular
                    if(cont == 0){
                        actual = -1;
                        enemigo = 1;
                    }else if(cont == 1){
                        actual = 1;
                        enemigo = -1;
                    }

                            
                    for(int i = nodo.nodo.getFilas()-1; i >= 0; i--){//Para cada fila desde el final
                        int nFichasH = 0;
                        int nHayH = 0;
                        int nFichasV = 0;
                        int nHayV = 0;
                        int nFichasDI = 0;
                        int nFichasDD = 0;
                        int nHayDD = 0;
                        int nHayDI = 0;

                        for(int j = 0; j < nodo.nodo.getColumnas(); j++){//Para cada columna
                            //Horizontal
                            if(aux[i][j] == 0){
                                nHayH++;
                            }else if(aux[i][j] == actual){//Calculo del actual en horizontal
                                nHayH++;
                                nFichasH++;
                            }else if(aux[i][j] == enemigo){
                                if(nFichasH >= 1 && nFichasH + nHayH >= conecta){//Si hay solucion calcula el valor
                                    comprueba(nodo, conecta, nFichasH, nHayH, cont);
                                }
                                nHayH = 0;
                                nFichasH = 0;

                            }
                            
                            int c = 0;
                            //Vertical
                            while(i - c >= 0){
                                if(aux[i-c][j] == 0){
                                    nHayV++;
                                }else if(aux[i-c][j] == actual){
                                    nHayV++;
                                    nFichasV++;

                                }else if(aux[i-c][j] == enemigo){
                                    if(nFichasV >= 1 && nFichasV + nHayV >= conecta){
                                        comprueba(nodo, conecta, nFichasV, nHayV, cont);
                                    }
                                    nHayV = 0;
                                    nFichasV = 0;

                                }
                                if(j == 0){//Diagonal derecha
                                    if(i == nodo.nodo.getFilas()-1){
                                        int au = 0;
                                        while(i - c - au >= 0 && j + au < nodo.nodo.getColumnas()){
                                            if(aux[i-c-au][j+au] == 0){
                                                nHayDD++;
                                            }else if(aux[i-c-au][j+au] == actual){
                                                nHayDD++;
                                                nFichasDD++;
                                            }else if(aux[i-c-au][j+au] == enemigo){
                                                if(nFichasDD >= 1 && nFichasDD + nHayDD >= conecta){
                                                    comprueba(nodo, conecta, nFichasDD, nHayDD, cont);
                                                }
                                                nHayDD = 0;
                                                nFichasDD = 0;
                                            } 
                                            au++;
                                        }
                                    }
                                }else{
                                   if(i == nodo.nodo.getFilas()-1){
                                       int au = 0; 
                                       while(i - au >= 0 && j + au < nodo.nodo.getColumnas()){
                                            if(aux[i-au][j+au] == 0){
                                                nHayDD++;
                                            }else if(aux[i-au][j+au] == actual){
                                                nHayDD++;
                                                nFichasDD++;
                                            }else if(aux[i-au][j+au] == enemigo){
                                                if(nFichasDD >= 1 && nFichasDD + nHayDD >= conecta){
                                                    comprueba(nodo, conecta, nFichasDD, nHayDD, cont);
                                                }
                                                nHayDD = 0;
                                                nFichasDD = 0;
                                            } 
                                            au++;
                                        } 
                                    } 
                                }

                                //Comprobacion diagonal derecha
                                comprueba(nodo, conecta, nFichasDD, nHayDD, cont);

                                nFichasDD = 0;
                                nHayDD = 0;

                                if(j == nodo.nodo.getColumnas()-1){//Diagonal Izquierda
                                    if(i == nodo.nodo.getFilas()-1){
                                        int au = 0;
                                        while(i - c - au >= 0 && j - au >= 0){
                                            if(aux[i-c-au][j-au] == 0){
                                                nHayDI++;
                                            }else if(aux[i-c-au][j-au] == actual){
                                                nHayDI++;
                                                nFichasDI++;
                                            }else if(aux[i-c-au][j-au] == enemigo){
                                                if(nFichasDI >= 1 && nFichasDI + nHayDI >= conecta){
                                                    comprueba(nodo, conecta, nFichasDI, nHayDI, cont);
                                                }
                                                nHayDI = 0;
                                                nFichasDI = 0;
                                            } 
                                            au++;
                                        }
                                    }
                                }else{
                                   if(i == nodo.nodo.getFilas()-1){
                                       int au = 0; 
                                       while(i - au >= 0 && j - au >= 0){
                                            if(aux[i-au][j-au] == 0){
                                                nHayDI++;
                                            }else if(aux[i-au][j-au] == actual){
                                                nHayDI++;
                                                nFichasDI++;
                                            }else if(aux[i-au][j-au] == enemigo){
                                                if(nFichasDI >= 1 && nFichasDI + nHayDI >= conecta){
                                                    comprueba(nodo, conecta, nFichasDI, nHayDI, cont);
                                                }
                                                nHayDI = 0;
                                                nFichasDI = 0;
                                            } 
                                            au++;
                                        } 
                                    } 
                                }
                                //Comprobacion diagonal izquierda
                                comprueba(nodo, conecta, nFichasDI, nHayDI, cont);

                                nFichasDI = 0;
                                nHayDI = 0;
                                c++;
                            }
                            //Comprobacion vertical
                            comprueba(nodo, conecta, nFichasV, nHayV, cont);

                            nFichasV = 0;
                            nHayV = 0;
                            
                        }
                        //Comprobacion Horizontal
                        
                        comprueba(nodo, conecta, nFichasH, nHayH, cont);
                        
                        nFichasH = 0;
                        nHayH =0;
                    }
                    
                    cont++;
                }while(cont < 2);
                
            }
        }
        /***
         * @brief Funcion que calcula el valor dados las fichas y las posibilidad 
         * @param nodo Nodo des que calcula
         * @param conecta valor para ganar
         * @param nFichas Fichas que hay en la linea analizada
         * @param nHay Valor fichas o 0 seguidos con los que hay posibilidad de ganar
         * @param cont Jugador calculado
         */
        public void comprueba(Nodo nodo,int conecta,int nFichas,int nHay,int cont){
            
            if(nFichas >= 1){//Si almenos hay una ficha
                if(nHay >= conecta){//Si en total hay las necesarias para ganar
                    if(cont == 0){//Maquina
                        if(nFichas != conecta){//Si No hay las necesaria para ganar
                            nodo.valor = nodo.valor + Math.pow(10, nFichas); //Valor es 10^NFichas
                        }else{
                            nodo.valor = nodo.valor + Double.POSITIVE_INFINITY;//Si hay para ganar Infinito
                        }
                    }else if(cont == 1){//Jugador
                        if(nFichas != conecta){
                            nodo.valor = nodo.valor - Math.pow(10, nFichas);
                        }else{
                            nodo.valor = nodo.valor + Double.NEGATIVE_INFINITY;
                        }
                    }
                }
            }
        }
        
        /**
         * @brief Muestra solo la primera rama
         * @param nivelMax Nivel Maximo al que baja
         */
        public void mostrarRama(int nivelMax){
            mostrarRamaInorden(this,0,nivelMax);
        }
        /**
         * @brief Muestra recursivamente la primera rama
         * @param nodo Nodo actual
         * @param nivel nivel actual
         * @param nivelMax Nivel Maximo al que baja
         */
        private void mostrarRamaInorden(Nodo nodo,int nivel,int nivelMax){
            if(nivel <= nivelMax){
                if(!nodo.getHijos().isEmpty()){
                    mostrarRamaInorden(nodo.hijos.get(0), nivel+1,nivelMax);

                    int[][] aux = nodo.nodo.toArray();
                    boolean datos = false;
                    String sal = "";
                    for(int j = 0; j < nivel; j++){
                        sal = sal + "       ";
                    }
                    for(int i = 0; i < nodo.nodo.getFilas(); i++){
                        if(datos == false){
                            System.out.println(sal + "Nivel: " + nivel + " Valor: " + nodo.valor);
                            datos = true;
                        }
                        String out = "";
                        for(int j = 0; j < nodo.nodo.getColumnas(); j++){
                            out = out + " " + aux[i][j];
                        }

                        System.out.println(sal + out);

                    }
                }else{
                    boolean datos = false;
                    String sal = "";
                    int[][] aux = nodo.nodo.toArray();
                    for(int j = 0; j < nivel; j++){
                            sal = sal + "       ";
                    }
                    for(int i = 0; i < nodo.nodo.getFilas(); i++){
                        if(datos == false){
                            System.out.println(sal + "Nivel: " + nivel + " Valor: " + nodo.valor);
                            datos = true;
                        }
                        String out = "";
                        
                        for(int j = 0; j < nodo.nodo.getColumnas(); j++){
                            out = out + " " + aux[i][j];
                        }

                        System.out.println(sal + out);

                    }
                }
            }
        }
        /***
         * @brief Funcion que pasa la raiz y llama recursivamente a los hijos
         * @param nivelMax Nivel Maximo al que baja
         */
        public void mostrar(int nivelMax){

            mostrarInOrden(this,0,nivelMax);
        }
        /**
         * @brief Funcion recursiva que muestra los hijos y el nodo actual
         * @param nodo Nodo actual
         * @param nivel Nivel actual para mostrar los nodos al nivel
         * @param nivelMax Nivel Maximo al que baja
         */
        private void mostrarInOrden(Nodo nodo, int nivel,int nivelMax){
            if(nivel <= nivelMax){
                if(!nodo.getHijos().isEmpty()){//Si hijos
                    int mitad = 0;
                    boolean hecho = false;
                    if(nodo.hijos.size() > 1){//Si tiene mas de un hijo
                        mitad = nodo.hijos.size()/2;//Calcula mitad para mostrar padre en medio
                        for(int i = 0; i < mitad; i++){
                            mostrarInOrden(nodo.hijos.get(i), nivel+1,nivelMax);//Muestra hijos hasta mitad
                        }
                        hecho = true;
                    }else{//Solo 1 hijo muestra hijo
                        for(int i = 0; i < nodo.hijos.size(); i++){
                            mostrarInOrden(nodo.hijos.get(i), nivel+1,nivelMax);
                        }
                    }


                    //Muestra actual
                    String sal = "";
                    for(int j = 0; j < nivel; j++){
                        sal = sal + "       ";
                    }
                    int[][] aux = nodo.nodo.toArray();
                    boolean datos = false;
                    for(int i = 0; i < nodo.nodo.getFilas(); i++){
                        if(datos == false){
                            System.out.println(sal + "Nivel: " + nivel + " Valor: " + nodo.valor);
                            datos = true;
                        }
                        String out = "";
                        
                        for(int j = 0; j < nodo.nodo.getColumnas(); j++){
                            out = out + " " + aux[i][j];
                        }

                        System.out.println(sal + out);

                    }
                    //Muestra el resto de hijos
                    if(hecho == true){
                        for(int i = mitad; i < nodo.hijos.size() ; i++){
                            mostrarInOrden(nodo.hijos.get(i), nivel+1,nivelMax);
                        }
                        hecho = false;
                    }

                }else{
                    //Si no hijos muestra actual y sale
                    String sal = "";
                    for(int j = 0; j < nivel; j++){
                        sal = sal + "       ";
                    }
                    boolean datos = false;
                    int[][] aux = nodo.nodo.toArray();
                    for(int i = 0; i < nodo.nodo.getFilas(); i++){
                        if(datos == false){
                            System.out.println(sal + "Nivel: " + nivel + " Valor: " + nodo.valor);
                            datos = true;
                        }
                        String out = "";

                        for(int j = 0; j < nodo.nodo.getColumnas(); j++){
                            out = out + " " + aux[i][j];
                        }

                        System.out.println(sal + out);
                    }
                }
            }
        }
    }

    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int turnoJugada(Grid tablero, int conecta) {
        
        int nivelMax = 4;//Funciona como Maximo con el 5
        Nodo nodoRaiz = new Nodo(tablero, 0);
        nodoRaiz.recorrer(conecta,nivelMax);
        //Funciones que muestran arbol(La primera) o Rama 1 (Segunda)
        //nodoRaiz.mostrar(nivelMax);
        nodoRaiz.mostrarRama(nivelMax);
        
        
        // ...
        // Calcular la mejor columna posible donde hacer nuestra turnoJugada
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
        //int columna = getRandomColumn(tablero);
        
        //Calculo del Escogido
        double posicion = nodoRaiz.valor;
        int columna = -1;
        int[][] aux = null;
        int[][] raiz = nodoRaiz.nodo.toArray();
        for(int i = 0; i < nodoRaiz.hijos.size(); i++){
            if(nodoRaiz.hijos.get(i).valor == posicion){
                aux = nodoRaiz.hijos.get(i).nodo.toArray();
                break;
                
            }
        }
        
        if(aux == null){
            aux = nodoRaiz.hijos.get(0).nodo.toArray();
        }
        
        for(int i = 0; i < nodoRaiz.nodo.getColumnas(); i++){
            for(int j = 0; j < nodoRaiz.nodo.getFilas(); j++){
                if(raiz[j][i] != aux[j][i]){
                    columna = i;
                    break;
                }
            }
            if(columna != -1){
                break;
            }
        }
        
        return tablero.checkWin(tablero.setButton(columna, Conecta4.PLAYER2), columna, conecta);

    } // turnoJugada
    
   
} // IAPlayer
