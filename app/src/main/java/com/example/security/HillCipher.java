package com.example.security;

import android.os.Handler;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.Arrays;

public class HillCipher {

    private static final int MODULUS = 256;


    public static boolean verifierKey(String key) {
        return Math.sqrt(key.length()) % 1 == 0;
    }

    public static int[][] stringToSquareMatrix(String num1,String num2,String num3,String num4 ) {

        int[][] matrix = new int[2][2];
        matrix[0][0] = Integer.valueOf(num1);
        matrix[0][1] = Integer.valueOf(num2);
        matrix[1][0] = Integer.valueOf(num3);
        matrix[1][1] = Integer.valueOf(num4);

        return matrix;
    }

    public static String encrypt(String plaintext, int[][] keyMatrix) {
        StringBuilder res = new StringBuilder();


        // Padding if needed
        if (plaintext.length() % 2 != 0) {
            plaintext += 'X';
        }

        for (int i = 0; i < plaintext.length(); i += 2) {

            char currentChar1 = plaintext.charAt(i);
            char currentChar2 = plaintext.charAt(i + 1);

            int[][] matrix = new int[2][1];



            int intValue1 = (int) currentChar1;

            int intValue2 = (int) currentChar2;

            matrix[0][0] = intValue1  ;
            matrix[1][0] = intValue2  ;

                int[][] encryptedMatrix = multiplierMatrices(keyMatrix, matrix);

            System.out.println(encryptedMatrix[0][0]);
            System.out.println(encryptedMatrix[1][0]);




                int a = encryptedMatrix[0][0] % MODULUS  ;

                int b = encryptedMatrix[1][0] % MODULUS  ;


                char f = (char)(a);
                char v = (char)(b);
                  res.append(f );
                  res.append(v);
        }

        return res.toString();
    }



    public  static  String Decrypt(String ciphertext , int[][]keymatriuce){
        StringBuilder res = new StringBuilder();


        int[][] inverse = MatriceMod26Inverse.inverseMatriceMod26(keymatriuce);



        if (inverse != null){
            for (int i = 0; i < ciphertext.length(); i += 2) {

                char currentChar1 = ciphertext.charAt(i);
                char currentChar2 = ciphertext.charAt(i + 1);

                int[][] matrix = new int[2][1];

                int intValue1 = (int) currentChar1;

                int intValue2 = (int) currentChar2;

                matrix[0][0] = intValue1;
                matrix[1][0] = intValue2;

                if (matrix[0][0] == -1 || matrix[1][0] == -1) {

                    res.append(currentChar1);
                    res.append(currentChar2);
                } else {


                    int[][] decmat = multiplierMatrices(inverse, matrix);


                    int a = decmat[0][0] % MODULUS  ;

                    int b = decmat[1][0] % MODULUS  ;


                    char f = (char)(a) ;
                    char v = (char)(b) ;
                    res.append(f);
                    res.append(v);





                }
            }



            return res.toString();
        }else {
            return  "";
        }





    }



    public  static  Boolean CalcDetermina (int[][] key){
        int a  = key[0][0];
        int b  = key[0][1];
        int c  = key[1][0];
        int d  = key[1][1];
        int res = (a*d) - (b*c);
        if (res == 0 ){
            return  false;
        }else {
            return  true ;
        }
    }

    public static int crypter(char caractere) {
        if (caractere >= 'A' && caractere <= 'Z') {
            return caractere - 'A';
        } else if (caractere >= 'a' && caractere <= 'z') {
            return caractere - 'a' + 26;
        } else if (caractere >= '0' && caractere <= '9') {
            return caractere - '0' + 52;
        } else if (caractere >= '\u0020' && caractere <= '\u007E') {

            return caractere - '\u0020' + 62;
        } else if (caractere >= '\u0080' && caractere <= '\u07FF') {

            return caractere - '\u0080' + 128 + 62 + 95;
        } else if (caractere >= '\u0800' && caractere <= '\uFFFF') {

            return caractere - '\u0800' + 2048 + 62 + 95 + 1920;
        } else {

            return -1;
        }
    }


    public static int[][] multiplierMatrices(int[][] matrice1, int[][] matrice2) {
        int ligneMat1 = matrice1.length;
        int colMat1 = matrice1[0].length;
        int colMat2 = matrice2[0].length;

        int[][] resultat = new int[ligneMat1][colMat2];

        for (int i = 0; i < ligneMat1; i++) {
            for (int j = 0; j < colMat2; j++) {
                for (int k = 0; k < colMat1; k++) {
                    resultat[i][j] += matrice1[i][k] * matrice2[k][j];
                }
            }
        }

        return resultat;
    }




}