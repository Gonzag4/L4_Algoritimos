package com.application;

import java.io.*;

// Classe que representa um nó da árvore AVL
class No {
    int chave;          // valor armazenado no nó
    No esquerda;        // ponteiro para o filho esquerdo
    No direita;         // ponteiro para o filho direito
    int altura;         // altura do nó (necessária para o cálculo do balanceamento)

    public No(int chave) {
        this.chave = chave;
        this.esquerda = null;   // inicialmente não há filho à esquerda
        this.direita = null;    // inicialmente não há filho à direita
        this.altura = 1;        // um novo nó tem altura 1
    }
}

class ArvoreAVL {
    private No raiz;                // raiz da árvore AVL
    private PrintWriter saida;      // escritor para saída em arquivo
    private boolean precisaRotacao; // indica se houve desequilíbrio
    private int noResponsavel;      // armazena o nó que causou o desbalanceamento

    public ArvoreAVL(PrintWriter saida) {
        this.raiz = null;
        this.saida = saida;
    }

    // Retorna a altura de um nó; se for nulo retorna 0
    private int obterAltura(No no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    // Calcula o fator de balanceamento (altura direita - altura esquerda)
    private int calcularFatorBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        return obterAltura(no.direita) - obterAltura(no.esquerda);
    }

    // Atualiza a altura do nó com base em seus filhos
    private void atualizarAltura(No no) {
        if (no != null) {
            int alturaEsq = obterAltura(no.esquerda);
            int alturaDir = obterAltura(no.direita);
            no.altura = 1 + (alturaEsq > alturaDir ? alturaEsq : alturaDir);
        }
    }

    // Rotação simples à direita (caso LL)
    private No rotacaoDireita(No y) {
        No x = y.esquerda;      // filho esquerdo
        No T2 = x.direita;      // subárvore direita de x

        // Realiza rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza alturas dos nós envolvidos
        atualizarAltura(y);
        atualizarAltura(x);

        return x; // novo topo da subárvore
    }

    // Rotação simples à esquerda (caso RR)
    private No rotacaoEsquerda(No x) {
        No y = x.direita;       // filho direito
        No T2 = y.esquerda;     // subárvore esquerda de y

        // Realiza rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza alturas
        atualizarAltura(x);
        atualizarAltura(y);

        return y; // novo topo da subárvore
    }

    // Impressão em ordem (esquerda - raiz - direita)
    // Mostra também o fator de balanceamento entre parênteses
    private void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            int fb = calcularFatorBalanceamento(no);
            String sinal = "";
            if (fb > 0) {
                sinal = "+";
            }
            saida.print(no.chave + "(" + sinal + fb + ") ");
            imprimirEmOrdem(no.direita);
        }
    }

    // Altura total da árvore (altura da raiz)
    private int obterAlturaTotal() {
        return obterAltura(raiz);
    }

    // Insere um valor e registra mensagens de balanceamento
    public void inserir(int chave) {
        precisaRotacao = false;   // reset
        noResponsavel = -1;       // reset
        raiz = inserirRec(raiz, chave);

        // Se não houve rotação, árvore já estava balanceada
        if (!precisaRotacao) {
            saida.println("arvore ja balanceada.");
        }

        // Imprime estrutura atual
        imprimirEmOrdem(raiz);
        saida.println();
        saida.println(obterAlturaTotal());
    }

    // Inserção recursiva padrão de árvore AVL
    private No inserirRec(No no, int chave) {
        if (no == null) {
            return new No(chave);
        }

        // Caminha na árvore conforme a chave
        if (chave < no.chave) {
            no.esquerda = inserirRec(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = inserirRec(no.direita, chave);
        } else {
            return no; // chave duplicada (não insere)
        }

        // Atualiza altura após inserção
        atualizarAltura(no);

        // Calcula fator de balanceamento
        int fb = calcularFatorBalanceamento(no);

        // Detecta o primeiro nó desbalanceado
        if (fb < -1 || fb > 1) {
            if (!precisaRotacao) {
                precisaRotacao = true;
                noResponsavel = no.chave;
                saida.println("no responsavel: " + noResponsavel);
                imprimirEmOrdem(raiz);
                saida.println();
            }
        }

        // --- CASOS DE ROTAÇÃO ---

        // Caso LL (rotação simples à direita)
        if (fb < -1 && calcularFatorBalanceamento(no.esquerda) <= 0) {
            if (noResponsavel == no.chave) {
                saida.println("rotacao direita.");
            }
            return rotacaoDireita(no);
        }

        // Caso RR (rotação simples à esquerda)
        if (fb > 1 && calcularFatorBalanceamento(no.direita) >= 0) {
            if (noResponsavel == no.chave) {
                saida.println("rotacao esquerda.");
            }
            return rotacaoEsquerda(no);
        }

        // Caso LR (rotação dupla esquerda-direita)
        if (fb < -1 && calcularFatorBalanceamento(no.esquerda) > 0) {
            if (noResponsavel == no.chave) {
                saida.println("rotacao direita dupla.");
            }
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RL (rotação dupla direita-esquerda)
        if (fb > 1 && calcularFatorBalanceamento(no.direita) < 0) {
            if (noResponsavel == no.chave) {
                saida.println("rotacao esquerda dupla.");
            }
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no; // retorna nó atualizado
    }
}

public class L4Q1 {
    public static void main(String[] args) {
        try {
            // Arquivo de entrada
            BufferedReader entrada = new BufferedReader(new FileReader("L4Q1_in.txt"));
            // Arquivo de saída
            PrintWriter saida = new PrintWriter(new FileWriter("L4Q1_out.txt"));

            String linha;
            boolean primeiroConjunto = true;

            // Lê cada linha de valores
            while ((linha = entrada.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue; // ignora linhas em branco
                }

                if (!primeiroConjunto) {
                    saida.println(); // separa conjuntos
                }
                primeiroConjunto = false;

                String[] valores = linha.split("\\s+"); // separa por espaço
                ArvoreAVL arvore = new ArvoreAVL(saida); // cria nova árvore para o conjunto

                // Insere cada valor da linha
                for (int i = 0; i < valores.length; i++) {
                    int valor = Integer.parseInt(valores[i]);
                    arvore.inserir(valor);
                }
            }

            saida.println();
            saida.println();

            entrada.close();
            saida.close();

            System.out.println("Arquivo de saida gerado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }
}
