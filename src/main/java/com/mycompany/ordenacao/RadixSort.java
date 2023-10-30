package com.mycompany.ordenacao;

import java.util.ArrayList;
import java.util.List;

public class RadixSort {

public static void radixSort(List<ArquivoOrdenacao> lista, String campo) {
    if (lista == null || lista.isEmpty()) {
        return;
    }

    // Encontrar o valor máximo no campo especificado
    int maxCampo = Integer.MIN_VALUE;
    for (ArquivoOrdenacao obj : lista) {
        int valorCampo = 0;
        if (campo.equals("arq")) {
            valorCampo = obj.getArq();
        } else if (campo.equals("ordem")) {
            valorCampo = obj.getOrdem();
        } // Adicione mais casos para outros campos, se necessário

        maxCampo = Math.max(maxCampo, valorCampo);
    }

    // Realizar o Radix Sort usando contagem de dígitos
    for (int exp = 1; maxCampo / exp > 0; exp *= 10) {
        countingSort(lista, exp, campo);
    }
}

private static void countingSort(List<ArquivoOrdenacao> lista, int exp, String campo) {
    int n = lista.size();
    int[] output = new int[n];
    int[] count = new int[10];

    // Inicializar o array de contagem
    for (int i = 0; i < 10; i++) {
        count[i] = 0;
    }

    // Contar a ocorrência de cada dígito no campo especificado
    for (ArquivoOrdenacao obj : lista) {
        int valorCampo = 0;
        if (campo.equals("arq")) {
            valorCampo = obj.getArq();
        } else if (campo.equals("ordem")) {
            valorCampo = obj.getOrdem();
        } // Adicione mais casos para outros campos, se necessário

        count[(valorCampo / exp) % 10]++;
    }

    // Calcular a posição correta de cada elemento no array de saída
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    // Construir o array de saída
    for (int i = n - 1; i >= 0; i--) {
        ArquivoOrdenacao obj = lista.get(i);
        int valorCampo = 0;
        if (campo.equals("arq")) {
            valorCampo = obj.getArq();
        } else if (campo.equals("ordem")) {
            valorCampo = obj.getOrdem();
        } // Adicione mais casos para outros campos, se necessário

        output[count[(valorCampo / exp) % 10] - 1] = valorCampo;
        count[(valorCampo / exp) % 10]--;
    }

    // Atualizar a lista com os elementos ordenados
    for (int i = 0; i < n; i++) {
        ArquivoOrdenacao obj = lista.get(i);
        int valorCampo = output[i];
        if (campo.equals("arq")) {
            obj.setArq(valorCampo);
        } else if (campo.equals("ordem")) {
            obj.setOrdem(valorCampo);
        } // Adicione mais casos para outros campos, se necessário
    }
}

}
