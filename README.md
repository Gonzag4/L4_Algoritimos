# L4_Algoritimos
Implementação manual de Árvores AVL e algoritmos em Grafos (Prim e Dijkstra), desenvolvida para a disciplina de Algoritmos e Estruturas de Dados da UFRPE, seguindo rigorosamente as regras da lista de exercícios, sem uso de estruturas prontas ou métodos otimizados da linguagem.



# 📘 Lista de Exercícios 04 – Árvores AVL e Grafos

## Universidade Federal Rural de Pernambuco – UFRPE  
**Área:** Informática  
**Disciplina:** Algoritmos e Estruturas de Dados  
**Curso:** Bacharelado em Ciência da Computação  


---

## 👤 Autor
**Luiz Gonzaga**  
Graduando em Ciência da Computação – UFRPE  

---

## 🎯 Objetivo do Trabalho

Este trabalho tem como objetivo aplicar de forma prática e rigorosa os conceitos avançados de **Estruturas de Dados**, com foco na implementação manual de **Árvores AVL** e **Algoritmos em Grafos**, conforme o conteúdo estudado na disciplina.

Todas as estruturas e algoritmos foram desenvolvidos **sem o uso de estruturas prontas ou métodos otimizados da linguagem**, respeitando integralmente as regras estabelecidas na Lista de Exercícios 04.

---

## 📌 Conformidade com as Regras da Disciplina

O desenvolvimento do trabalho atende rigorosamente às exigências da disciplina, destacando-se:

- Não utilização de estruturas de dados prontas (`List`, `Vector`, `Heap` nativo, etc.)
- Implementação manual de todas as estruturas de dados
- Não utilização de funções, métodos ou comandos otimizados da linguagem
- Uso exclusivo de:
  - Variáveis e tipos primitivos
  - Estruturas condicionais
  - Estruturas de repetição
  - Sub-rotinas (funções/métodos)
  - Estruturas homogêneas (arrays estáticos)
  - Estruturas heterogêneas (classes/structs)
- Uso de **alocação dinâmica** apenas para a Árvore AVL
- Uso de **alocação sequencial (arrays estáticos)** para:
  - Listas de Prioridade
  - Grafos (matriz de adjacência)
- Implementação obrigatória de **Lista de Prioridade** como estrutura auxiliar nos algoritmos de grafos

---

## 📂 Estrutura dos Arquivos

Os arquivos seguem o padrão exigido pela disciplina:

Lista04/
├── L4Q1.<extensão>
├── L4Q2.<extensão>
├── L4Q3.<extensão>
└── README.md


Cada arquivo contém todas as estruturas e procedimentos necessários para a resolução completa da respectiva questão.

---

## 🧠 Questões Implementadas

---

### 🔹 Questão 1 – Árvore AVL Dinâmica

Nesta questão foi implementada uma **Árvore AVL binária dinâmica**, capaz de manter-se balanceada após a inserção de `n` nós, conforme os conjuntos de dados fornecidos por arquivo de entrada.

#### Funcionalidades implementadas:

- Inserção de nós respeitando as propriedades de uma Árvore Binária de Busca
- Cálculo da altura dos nós
- Cálculo do fator de balanceamento:
  

fb(v) = h(v.dir) - h(v.esq)


- Identificação de infrações ao balanceamento
- Execução automática das rotações necessárias:
- Rotação simples à direita
- Rotação simples à esquerda
- Rotação dupla à direita
- Rotação dupla à esquerda

#### Entrada e Saída

- **Entrada:**  
Arquivo `.txt` contendo múltiplos conjuntos de dados, onde cada linha representa uma sequência de chaves a serem inseridas na árvore.

- **Saída:**  
Para cada inserção, o programa exibe:
- Mensagem indicando se a árvore permaneceu balanceada ou qual rotação foi necessária
- Nó responsável pela infração (quando houver)
- Impressão da árvore em ordem, com os fatores de balanceamento
- Altura total da árvore após cada inserção

O formato da saída segue rigorosamente o padrão especificado no enunciado da questão.

---

### 🔹 Questão 2 – Algoritmo de Prim em Grafos

Nesta questão foi implementado o **Algoritmo de Prim**, utilizado para encontrar a **Árvore Geradora Mínima** de um grafo ponderado.

#### Características da implementação:

- Representação do grafo por **matriz de adjacência**
- Uso obrigatório de **Lista de Prioridade** implementada manualmente
- Estrutura de dados alocada de forma sequencial (arrays estáticos)
- Leitura dos dados a partir de arquivo `.txt`

---

### 🔹 Questão 3 – Algoritmo de Dijkstra em Grafos

Nesta questão foi implementado o **Algoritmo de Dijkstra**, utilizado para encontrar o **menor caminho a partir de um vértice origem** em um grafo ponderado.

#### Características da implementação:

- Suporte a grafos direcionados
- Representação do grafo por **matriz de adjacência**
- Uso obrigatório de **Lista de Prioridade** (Heap implementado pelo aluno)
- Estruturas auxiliares implementadas manualmente
- Leitura dos dados a partir de arquivo `.txt`

---

## 📥 Formato dos Arquivos de Entrada (L4Q2 e L4Q3)

- Primeira linha: número `n` de vértices do grafo
- Próximas `n` linhas: matriz `n x n`, onde:
- A posição `i x j` indica a existência (ou não) de aresta
- O valor representa o peso da aresta
- Para a questão L4Q3, a matriz **não é necessariamente simétrica**, pois o grafo pode ser direcionado

---

## ✅ Considerações Finais

A Lista de Exercícios 04 possibilitou o aprofundamento prático em:

- Balanceamento automático de árvores (AVL)
- Manipulação de fatores de balanceamento e rotações
- Implementação de algoritmos clássicos em grafos
- Uso de listas de prioridade como estrutura auxiliar fundamental
- Leitura e escrita de dados via arquivos

Todo o código foi desenvolvido de forma autoral, seguindo estritamente as regras da disciplina e refletindo o aprendizado obtido ao longo do curso.
