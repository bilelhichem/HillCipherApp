package com.example.security;

import android.os.Handler;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.Arrays;

public class HillCipher {

    private static final int MODULUS = 26;

    public static void afficherMatrice(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }






    public static boolean verifierKey(String key) {
        return Math.sqrt(key.length()) % 1 == 0;
    }

    public static int[][] stringToSquareMatrix(String matrixString) {
        int size = (int) Math.sqrt(matrixString.length());
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = matrixString.charAt(i * size + j) - '0';
            }
        }
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


// Convert char to integer
            int intValue1 = (int) currentChar1;

            int intValue2 = (int) currentChar2;
            matrix[0][0] = intValue1 - 65 ;
            matrix[1][0] = intValue2 - 65 ;

            if (matrix[0][0] == -1 || matrix[1][0] == -1) {

                res.append(currentChar1);
                res.append(currentChar2);
            } else {

                int[][] encryptedMatrix = multiplierMatrices(keyMatrix, matrix);

                int a = encryptedMatrix[0][0] % MODULUS  ;
                int b = encryptedMatrix[1][0] % MODULUS  ;

                char f = (char)(a+65);
                char v = (char)(b+65);
                  res.append(f );
                  res.append(v);



          //       System.out.println( getCharacter(encryptedMatrix[0][0] % MODULUS ));
            //    System.out.println( getCharacter(encryptedMatrix[1][0] % MODULUS ));

            //    res.append(getCharacter(encryptedMatrix[0][0] % MODULUS ) );
              //  res.append(getCharacter(encryptedMatrix[1][0] % MODULUS) );



            }
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
                matrix[0][0] = crypter(currentChar1);
                matrix[1][0] = crypter(currentChar2);

                if (matrix[0][0] == -1 || matrix[1][0] == -1) {

                    res.append(currentChar1);
                    res.append(currentChar2);
                } else {


                    int[][] decmat = multiplierMatrices(inverse, matrix);


                    int a = decmat[0][0] % MODULUS  ;

                    int b = decmat[1][0] % MODULUS  ;


                    char f = (char)(a+65) ;
                    char v = (char)(b+65) ;
                    res.append(f);
                    res.append(v);



                    //       System.out.println( getCharacter(encryptedMatrix[0][0] % MODULUS ));
                    //    System.out.println( getCharacter(encryptedMatrix[1][0] % MODULUS ));

                    //    res.append(getCharacter(encryptedMatrix[0][0] % MODULUS ) );
                    //  res.append(getCharacter(encryptedMatrix[1][0] % MODULUS) );



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

    public static char getCharacter(int equivalence) {
        if (equivalence >= 0 && equivalence < 26) {

            return (char)('A' + equivalence);
        } else if (equivalence >= 26 && equivalence < 52) {

            return (char)('a' + equivalence - 26);
        } else if (equivalence >= 52 && equivalence < 62) {

            return (char)('0' + equivalence - 52);
        } else if (equivalence >= 62 && equivalence < 94) {

            return (char)('!' + equivalence - 62);
        } else if (equivalence >= 94 && equivalence < 127) {

            return (char)('~' + equivalence - 94);
        } else if (equivalence >= 127 && equivalence < 55296) {

            return (char)(127 + equivalence - 127);
        } else if (equivalence >= 55296 && equivalence < 57344) {

            return '?';
        } else if (equivalence >= 57344 && equivalence < 65536) {

            return (char)(57344 + equivalence - 57344);
        } else if (equivalence >= 65536 && equivalence < 1114112) {

            return (char)(65536 + equivalence - 65536);
        } else {
            return '?';
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

    public static int[][] inverserMatrice(int[][] matrice) {
        int determinant = matrice[0][0] * matrice[1][1] - matrice[0][1] * matrice[1][0];

        // Vérification de la non-singularité de la matrice
        if (determinant == 0) {
            System.out.println("La matrice est singulière. L'inverse n'existe pas.");
            return null;
        }

        // Calcul de l'inverse
        int[][] inverse = new int[2][2];
        inverse[0][0] = matrice[1][1] / determinant;
        inverse[0][1] = -matrice[0][1] / determinant;
        inverse[1][0] = -matrice[1][0] / determinant;
        inverse[1][1] = matrice[0][0] / determinant;

        return inverse;
    }


}