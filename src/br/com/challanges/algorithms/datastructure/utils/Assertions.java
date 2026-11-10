package br.com.challanges.algorithms.datastructure.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Assertions {
    public static void assertEquals(Object a, Object b) {
        if (!a.equals(b)) {
            throw new RuntimeException("O valor experado=" + a + ". Valor atual=" + b);
        }
    }

    public static <T> void assertEquals(Collection<T> a, Collection<T> b) {
        String A = stringfy(a);
        String B = stringfy(b);
        if (!A.equals(B)) {
            throw new RuntimeException("As listas nao sao iguais.\nValores esperado:%s\nValor atual:%s".formatted(A, B));
        }
    }

    public static <T extends Throwable> void assertThrows(Class<T> expectedClassException, Runnable executable) {
        Throwable error = null;
        try {
            executable.run();
        } catch (Throwable e) {
            error = e;
        }
        if (error != null) {
            assertEquals(expectedClassException, error.getClass());
        } else {
            assertFail();
        }
    }

    public static void assertFail() {
        throw new RuntimeException("Esse trecho do codigo nao deveria ser atingido na execucao do assert");
    }

    private static boolean bothArrays(Object a, Object b) {
        return a != null && b != null
                && a.getClass().isArray()
                && b.getClass().isArray();
    }

    @Deprecated
    public static <T> void assertEquals(T[] a, T[] b) {
        if (a.length != b.length) {
            throw new RuntimeException("As listas devem ter o mesmo tamanho.\n Valores esperado:%s\nValor atual:%s".formatted(a, b));
        }
        for (int j = 0; j < a.length; j++) {
            if (bothArrays(a[j], b[j])) {
                assertArrayEquals(a[j], b[j]);
            } else if (!a[j].equals(b[j])) {
                throw new RuntimeException("Elementos do indice=" + j + " nao são iguais. Valor1=" + a[j] + " e valor2=" + b[j]);
            }
        }
    }

    private static String stringfy(Collection<?> c) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        int i = 0;
        for (Object o : c) {
            i++;
            if (o instanceof Collection<?>) {
                s.append(stringfy((Collection<?>) o));
            } else {
                s.append(o);
            }
            if (i < c.size()) {
                s.append(", ");
            }
        }
        return s.append("]").toString();
    }

    private static void assertArrayEquals(Object a, Object b) {
        switch (a) {
            case int[] aa when b instanceof int[] bb -> assertEquals(aa, bb);
            case long[] aa when b instanceof long[] bb -> assertEquals(aa, bb);
            case double[] aa when b instanceof double[] bb -> assertEquals(aa, bb);
            case byte[] aa when b instanceof byte[] bb -> assertEquals(aa, bb);
            case Object[] aa when b instanceof Object[] bb -> assertEquals(aa, bb);
            case null, default -> failArray(a, b);
        }
    }

    private static void failArray(Object a, Object b) {
        throw new AssertionError(
                "Arrays differ: " + Arrays.deepToString(new Object[]{a})
                        + " vs " + Arrays.deepToString(new Object[]{b})
        );
    }

    public static void assertEquals(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new RuntimeException("As listas devem ter o mesmo tamanho");
        }
        for (int j = 0; j < a.length; j++) {
            if (a[j] != b[j]) {
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
