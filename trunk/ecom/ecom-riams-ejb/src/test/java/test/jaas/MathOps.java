package test.jaas;

import java.util.*;

public class MathOps {
  // Создает стенографию, чтобы меньше печатать:
  static void prt(String s) {
    System.out.println(s);
  }
  // стенография для печати строки и int:
  static void pInt(String s, int i) {
    prt(s + " = " + i);
  }
  // стенография для печати строки и float:
  static void pFlt(String s, float f) {
    prt(s + " = " + f);
  }
  public static void main(String[] args) {
    // Создает генератор случайных чисел,
    // принимающий текущее время по умолчанию:
    Random rand = new Random();
    int i, j, k;
    // '%' ограничивает максимальное значение величиной 99:
    j = rand.nextInt() % 100;
    k = rand.nextInt() % 100;
    pInt("j",j);  pInt("k",k);
    i = j + k; pInt("j + k", i);
    i = j - k; pInt("j - k", i);
    i = k / j; pInt("k / j", i);
    i = k * j; pInt("k * j", i);
    i = k % j; pInt("k % j", i);
    j %= k; pInt("j %= k", j);
    // Проверка чисел с плавающей точкой:
    float u,v,w;  // Также применима к числам двойной точности
    v = rand.nextFloat();
    w = rand.nextFloat();
    pFlt("v", v); pFlt("w", w);
    u = v + w; pFlt("v + w", u);
    u = v - w; pFlt("v - w", u);
    u = v * w; pFlt("v * w", u);
    u = v / w; pFlt("v / w", u);
    // следующее также работает для
    // char, byte, short, int, long,
    // и double:
    u += v; pFlt("u += v", u);
    u -= v; pFlt("u -= v", u);
    u *= v; pFlt("u *= v", u);
    u /= v; pFlt("u /= v", u);
  }
} ///:~

