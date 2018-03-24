package com.kiafarhang.distillr;

import com.kiafarhang.distillr.yelp.YelpAPIRequest;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            String response = YelpAPIRequest.fetchYelpData();
            System.out.println(response);
        } catch (IOException exception) {
            System.out.println(exception);
        }
        // try {
        //     File file = new File("yelp-key.txt");
        //     Scanner input = new Scanner(file, "UTF-8");
        //     while (input.hasNextLine()) {
        //         System.out.println(input.nextLine());
        //     }
        // } catch (FileNotFoundException exception) {
        //     System.out.println(exception);
        // }
    }
}
