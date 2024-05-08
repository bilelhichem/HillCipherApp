package com.example.security;

import android.widget.Toast;

public class MatriceMod26Inverse {

    private static final int MODULUS = 26;
    public static int pgcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static boolean isPGCDOne(int a) {
        return pgcd(a, MODULUS) == 1;
    }

    public static int[][] inverseMatriceMod26(int[][] matrice) {
        int n = matrice.length;
        int[][] adjointe = adjointeMatrice(matrice);
        int determinant = determinant(matrice);
        if (isPGCDOne(determinant)) {
            int inverseDeterminant = trouverInverseMod26(determinant);

            int[][] inverse = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    inverse[i][j] = (adjointe[i][j] * inverseDeterminant) % MODULUS;
                    if (inverse[i][j] < 0) {
                        inverse[i][j] += MODULUS; // Correction : Modulo 26, pas 256
                    }
                }
            }
            return inverse;
        } else {
            return null;
        }
    }


    public static int[][] adjointeMatrice(int[][] matrice) {
        int n = matrice.length;
        int[][] adjointe = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int signe = (int) Math.pow(-1, i + j);
                int mineur = determinant(mineurMatrice(matrice, i, j));
                adjointe[j][i] = signe * mineur;
                if (adjointe[j][i] < 0) {
                    adjointe[j][i] += MODULUS;
                }
            }
        }

        return adjointe;
    }


    public static int[][] mineurMatrice(int[][] matrice, int row, int col) {
        int n = matrice.length;
        int[][] mineur = new int[n - 1][n - 1];
        int r = 0, c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != row && j != col) {
                    mineur[r][c++] = matrice[i][j];
                    if (c == n - 1) {
                        c = 0;
                        r++;
                    }
                }
            }
        }
        return mineur;
    }


    public static int determinant(int[][] matrice) {
        int n = matrice.length;
        if (n == 1) {
            return matrice[0][0];
        }
        if (n == 2) {
            return (matrice[0][0] * matrice[1][1] - matrice[0][1] * matrice[1][0]);
        }
        int determinant = 0;
        for (int j = 0; j < n; j++) {
            determinant += matrice[0][j] * determinant(mineurMatrice(matrice, 0, j)) * (int) Math.pow(-1, j);
        }
        return determinant;
    }

    // Fonction pour trouver l'inverse modulo 26
    public static int trouverInverseMod26(int a) {
        a = a % MODULUS;
        for (int x = 1; x < MODULUS; x++) {
            if ((a * x) % MODULUS == 1) {
                return x;
            }
        }
        return -1;
    }



}
