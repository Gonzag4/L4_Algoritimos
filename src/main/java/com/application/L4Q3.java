import java.io.*;

// Classe que representa um elemento dentro do MinHeap.
// Cada elemento possui um vértice e o peso associado (utilizado para Prim).
class ElementoHeap {
    int vertice;
    int peso;

    public ElementoHeap(int vertice, int peso) {
        this.vertice = vertice;
        this.peso = peso;
    }
}

// Estrutura de MinHeap (árvore binária) para armazenar pares (vértice, peso).
// OBS IMPORTANTE: Esta MinHeap NÃO é genérica — está fixada para ElementoHeap.
// Porém, funciona perfeitamente para o algoritmo de Prim.
class MinHeap {
    private ElementoHeap[] heap;   // Array interno da heap
    private int[] posicao;         // Armazena a posição atual de cada vértice dentro da heap
    private int tamanho;           // Quantidade atual de elementos
    private int capacidade;        // Tamanho máximo da heap

    public MinHeap(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.heap = new ElementoHeap[capacidade];
        this.posicao = new int[capacidade];

        // Inicializa todas posições como "não incluído"
        for (int i = 0; i < capacidade; i++) {
            posicao[i] = -1;
        }
    }

    // Troca dois elementos e atualiza suas posições na tabela de índice
    private void trocar(int i, int j) {
        posicao[heap[i].vertice] = j;
        posicao[heap[j].vertice] = i;

        ElementoHeap temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Move o elemento para cima enquanto o heap estiver desbalanceado
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

    // Move o elemento para baixo garantindo a propriedade do MinHeap
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

    // Insere um novo par (vértice, peso) na MinHeap
    public void inserir(int vertice, int peso) {
        ElementoHeap elemento = new ElementoHeap(vertice, peso);
        heap[tamanho] = elemento;
        posicao[vertice] = tamanho;
        tamanho++;
        heapifyParaCima(tamanho - 1);
    }

    // Extrai o elemento com menor peso da heap (a raiz)
    public ElementoHeap extrairMinimo() {
        if (tamanho == 0) {
            return null;
        }

        ElementoHeap minimo = heap[0];
        posicao[minimo.vertice] = -1;

        tamanho--;
        if (tamanho > 0) {
            heap[0] = heap[tamanho];
            posicao[heap[0].vertice] = 0;
            heapifyParaBaixo(0);
        }

        return minimo;
    }

    // Diminui o peso associado a um vértice dentro da heap
    public void diminuirChave(int vertice, int novoPeso) {
        int idx = posicao[vertice];
        if (idx == -1) {
            return; // O vértice já foi removido
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
    private int[][] grafo;     // Matriz de adjacência do grafo
    private int numVertices;   // Quantidade de vértices

    public Prim(int[][] grafo, int numVertices) {
        this.grafo = grafo;
        this.numVertices = numVertices;
    }

    public void executar() {

        // Vetor de pais que compõem a MST
        int[] pai = new int[numVertices];

        // Chave (peso mínimo para incluir o vértice na MST)
        int[] chave = new int[numVertices];

        // Marca quais vértices já foram incluídos na árvore MST
        boolean[] incluido = new boolean[numVertices];

        // Inicializa todas as chaves como infinito
        for (int i = 0; i < numVertices; i++) {
            chave[i] = Integer.MAX_VALUE;
            incluido[i] = false;
            pai[i] = -1;
        }

        MinHeap heap = new MinHeap(numVertices);

        // Começa pelo vértice 0
        chave[0] = 0;
        heap.inserir(0, 0);

        // Insere os outros vértices com peso infinito
        for (int i = 1; i < numVertices; i++) {
            heap.inserir(i, Integer.MAX_VALUE);
        }

        // Executa Prim utilizando heap
        while (!heap.estaVazio()) {
            ElementoHeap elemento = heap.extrairMinimo();
            int u = elemento.vertice;
            incluido[u] = true;

            for (int v = 0; v < numVertices; v++) {

                // PROBLEMA IMPORTANTE:
                // Se o peso for 0, o algoritmo interpreta como "SEM ARESTA".
                // Isso significa que um grafo com peso 0 NÃO é suportado.
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

class L4Q2 {
    public static void main(String[] args) {
        try {
            BufferedReader entrada = new BufferedReader(new FileReader("L4Q2_in.txt"));

            // Lê a primeira linha para saber quantos vértices terá o grafo
            String primeiraLinha = entrada.readLine();
            int n = Integer.parseInt(primeiraLinha.trim());

            int[][] grafo = new int[n][n];

            // Lê a matriz de adjacência completa
            for (int i = 0; i < n; i++) {
                String linha = entrada.readLine();
                String[] valores = linha.trim().split("\\s+");

                for (int j = 0; j < n; j++) {
                    grafo[i][j] = Integer.parseInt(valores[j]);
                }
            }

            entrada.close();

            // Executa Prim usando o grafo carregado
            Prim prim = new Prim(grafo, n);
            prim.executar();

        } catch (IOException e) {
            System.out.println("Erro ao processar arquivo: " + e.getMessage());
        }
    }
}
