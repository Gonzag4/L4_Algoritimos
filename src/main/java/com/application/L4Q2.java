package com.application;

import java.io.*;

// Classe que representa um elemento dentro da MinHeap.
// Cada elemento guarda um vértice e o peso associado.
// OBS IMPORTANTE: Nesta implementação, "peso" é usado como chave de comparação.
class ElementoHeap {
    int vertice;
    int peso;

    public ElementoHeap(int vertice, int peso) {
        this.vertice = vertice;
        this.peso = peso;
    }
}

// Implementação de uma MinHeap específica para armazenar ElementoHeap.
// OBS IMPORTANTE: Esta heap NÃO é genérica, funciona apenas para ElementoHeap.
// Isso não causa erro no algoritmo de Prim, mas impede reuso da classe para outros tipos.
class MinHeap {
    private ElementoHeap[] heap; // Array que representa a heap
    private int[] posicao;       // Guarda a posição atual de cada vértice dentro da heap
    private int tamanho;         // Número de elementos na heap
    private int capacidade;      // Capacidade máxima

    public MinHeap(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.heap = new ElementoHeap[capacidade];
        this.posicao = new int[capacidade];

        // Inicializa todas posições como -1, indicando que nenhum vértice está na heap
        for (int i = 0; i < capacidade; i++) {
            posicao[i] = -1;
        }
    }

    // Troca dois elementos da heap e atualiza suas posições
    private void trocar(int i, int j) {
        posicao[heap[i].vertice] = j;
        posicao[heap[j].vertice] = i;

        ElementoHeap temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Move o elemento para cima até restaurar a propriedade de MinHeap
    private void heapifyParaCima(int idx) {
        while (idx > 0) {
            int pai = (idx - 1) / 2;
            if (heap[idx].peso < heap[pai].peso) {
                trocar(idx, pai);
                idx = pai;
            } else {
                break;
            }
        }
    }

    // Move o elemento para baixo até restaurar a propriedade de MinHeap
    private void heapifyParaBaixo(int idx) {
        int menor = idx;

        while (true) {
            int esquerda = 2 * idx + 1;
            int direita = 2 * idx + 2;

            if (esquerda < tamanho && heap[esquerda].peso < heap[menor].peso) {
                menor = esquerda;
            }

            if (direita < tamanho && heap[direita].peso < heap[menor].peso) {
                menor = direita;
            }

            if (menor != idx) {
                trocar(idx, menor);
                idx = menor;
            } else {
                break;
            }
        }
    }

    // Insere um novo vértice e peso na heap
    public void inserir(int vertice, int peso) {
        ElementoHeap elemento = new ElementoHeap(vertice, peso);
        heap[tamanho] = elemento;
        posicao[vertice] = tamanho;
        tamanho++;
        heapifyParaCima(tamanho - 1);
    }

    // Extrai o elemento com menor peso (raiz da heap)
    public ElementoHeap extrairMinimo() {
        if (tamanho == 0) {
            return null;
        }

        ElementoHeap minimo = heap[0];
        posicao[minimo.vertice] = -1; // Marca vértice como removido

        tamanho--;
        if (tamanho > 0) {
            heap[0] = heap[tamanho];
            posicao[heap[0].vertice] = 0;
            heapifyParaBaixo(0);
        }

        return minimo;
    }

    // Atualiza o peso de um vértice já existente na heap
    public void diminuirChave(int vertice, int novoPeso) {
        int idx = posicao[vertice];
        if (idx == -1) {
            return;
        }

        heap[idx].peso = novoPeso;
        heapifyParaCima(idx);
    }

    public boolean estaVazio() {
        return tamanho == 0;
    }

    public boolean contem(int vertice) {
        return posicao[vertice] != -1;
    }
}

class Prim {
    private int[][] grafo;     // Matriz de adjacência representando o grafo
    private int numVertices;

    public Prim(int[][] grafo, int numVertices) {
        this.grafo = grafo;
        this.numVertices = numVertices;
    }

    public void executar() {
        int[] pai = new int[numVertices];    // Guarda o pai de cada vértice na MST
        int[] chave = new int[numVertices];  // Menor peso para conectar cada vértice
        boolean[] incluido = new boolean[numVertices]; // Marca vértices já incluídos

        // Inicializa estruturas
        for (int i = 0; i < numVertices; i++) {
            chave[i] = Integer.MAX_VALUE;
            incluido[i] = false;
            pai[i] = -1;
        }

        MinHeap heap = new MinHeap(numVertices);

        // Começa pelo vértice 0
        chave[0] = 0;
        heap.inserir(0, 0);

        // Insere os demais vértices na heap com peso infinito
        for (int i = 1; i < numVertices; i++) {
            heap.inserir(i, Integer.MAX_VALUE);
        }

        // Enquanto houver vértices na heap
        while (!heap.estaVazio()) {
            ElementoHeap elemento = heap.extrairMinimo();
            int u = elemento.vertice;
            incluido[u] = true;

            // Percorre vértices vizinhos
            for (int v = 0; v < numVertices; v++) {

                // AQUI ESTÁ UM PROBLEMA IMPORTANTE:
                // ---------------------------------------------------------------
                // O algoritmo verifica se grafo[u][v] != 0 para considerar a aresta existente.
                // Isso significa que ARESTAS DE PESO 0 NÃO SÃO SUPORTADAS.
                // Pesos 0 são tratados como "sem aresta".
                // ---------------------------------------------------------------
                if (grafo[u][v] != 0 && !incluido[v] && grafo[u][v] < chave[v]) {
                    pai[v] = u;
                    chave[v] = grafo[u][v];
                    heap.diminuirChave(v, chave[v]);
                }
            }
        }

        imprimirResultado(pai, chave);
    }

    private void imprimirResultado(int[] pai, int[] chave) {
        int pesoTotal = 0;

        System.out.println("Arvore Geradora Minima (Algoritmo de Prim):");
        System.out.println("Aresta \t\tPeso");

        for (int i = 1; i < numVertices; i++) {
            System.out.println(pai[i] + " - " + i + "\t\t" + chave[i]);
            pesoTotal = pesoTotal + chave[i];
        }

        System.out.println("\nPeso total da MST: " + pesoTotal);
    }
}

public class L4Q2 {
    public static void main(String[] args) {
        try {
            BufferedReader entrada = new BufferedReader(new FileReader("L4Q2_in.txt"));

            String primeiraLinha = entrada.readLine();
            int n = Integer.parseInt(primeiraLinha.trim());

            int[][] grafo = new int[n][n];

            // Lê matriz de adjacência
            for (int i = 0; i < n; i++) {
                String linha = entrada.readLine();
                String[] valores = linha.trim().split("\\s+");

                for (int j = 0; j < n; j++) {
                    grafo[i][j] = Integer.parseInt(valores[j]);
                }
            }

            entrada.close();

            Prim prim = new Prim(grafo, n);
            prim.executar();

        } catch (IOException e) {
            System.out.println("Erro ao processar arquivo: " + e.getMessage());
        }
    }
}
