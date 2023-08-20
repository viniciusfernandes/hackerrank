package br.com.challanges.algorithms.datastructure;

import java.util.Date;
import java.util.HashMap;

public class HashTableTest {
    public static void main(String[] args) {
        var total = 80000;
        var map = new HashMap<KeyValue, String>();
        var init = new Date().getTime();
        for (int i = 0; i < total; i++) {
            var v = "1000000" + i;
            map.put(new KeyValue(i, v), v);
        }
        System.out.println((new Date().getTime() - init) / 1000d);
        init = new Date().getTime();
        for (int i = 0; i < total; i++) {
            map.get(i);
        }
        System.out.println((new Date().getTime() - init) / 1000d);
    }

    private static class KeyValue {
        int key;
        String value;

        public KeyValue(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return 1;
            //return  value.hashCode();
        }
    }
}
