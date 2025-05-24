package br.com.sorriso.domain.stockTransaction;

public enum TransactionCategory {
    APPOINTMENT,         // Uso em Consulta/Atendimento
    INVENTORY_ADJUSTMENT,// Ajuste de estoque
    PURCHASE,            // Compra de novos itens para o estoque
    RETURN,              // Devolução de itens ao estoque
    DISPOSAL,            // Descarte de itens danificados ou sem utilidade
    TRANSFER,            // Transferência de itens entre clinicas ou unidades
    WARRANTY_REPLACEMENT // Substituição de itens sob garantia
}
