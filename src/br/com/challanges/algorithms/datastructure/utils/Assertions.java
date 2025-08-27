package br.com.challanges.algorithms.datastructure.utils;

public class Assertions {
    public static void assertEquals(Object a, Object b) {
        if (!a.equals(b)) {
            throw new RuntimeException("O valor experado=" + a + ". Valor atual=" + b);
        }
    }

    public static <T> void assertEquals(T[] a, T[] b) {
        for (int j = 0; j < a.length; j++) {
            if (!a[j].equals(b[j])) {
                throw new RuntimeException("Elementos do indice=" + j + " nao são iguais. Valor1=" + a[j] + " e valor2=" + b[j]);
            }
        }
    }

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException("O valor experado=true. Valor atual=false");
        }
    }

    public static void assertFalse(boolean b) {
        if (b) {
            throw new RuntimeException("O valor experado=false. Valor atual=true");
        }
    }
}
