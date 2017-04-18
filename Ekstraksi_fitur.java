/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EKSTRAKSI_FITUR;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 *
 * @author user
 */
public class Ekstraksi_fitur {
    
    BufferedImage  image;
    int width, height;
    int r1, r2, g1, g2, b1, b2;
    double w, h, size;
    
    double[][] r;        
    double[][] g;        
    double[][] b; 
    
    double mean_r, mean_g, mean_b;
    double sttd_r, sttd_g, sttd_b;
    
    double[][] glcm_0_r   = new double[256][256];
    double[][] glcm_45_r  = new double[256][256];
    double[][] glcm_90_r  = new double[256][256];
    double[][] glcm_135_r = new double[256][256];
    
    double[][] glcm_0_g   = new double[256][256];
    double[][] glcm_45_g  = new double[256][256];
    double[][] glcm_90_g  = new double[256][256];
    double[][] glcm_135_g = new double[256][256];
    
    double[][] glcm_0_b   = new double[256][256];
    double[][] glcm_45_b  = new double[256][256];
    double[][] glcm_90_b  = new double[256][256];
    double[][] glcm_135_b = new double[256][256];
       
    double sum_0_r = 0, sum_45_r = 0, sum_90_r = 0, sum_135_r = 0;
    double sum_0_g = 0, sum_45_g = 0, sum_90_g = 0, sum_135_g = 0;
    double sum_0_b = 0, sum_45_b = 0, sum_90_b = 0, sum_135_b = 0;
    
    double asm_0_r = 0, asm_45_r = 0, asm_90_r = 0, asm_135_r = 0;
    double asm_0_g = 0, asm_45_g = 0, asm_90_g = 0, asm_135_g = 0;
    double asm_0_b = 0, asm_45_b = 0, asm_90_b = 0, asm_135_b = 0;
    
    double con_0_r = 0, con_45_r = 0, con_90_r = 0, con_135_r = 0;
    double con_0_g = 0, con_45_g = 0, con_90_g = 0, con_135_g = 0;
    double con_0_b = 0, con_45_b = 0, con_90_b = 0, con_135_b = 0;
    
    double idm_0_r = 0, idm_45_r = 0, idm_90_r = 0, idm_135_r = 0;
    double idm_0_g = 0, idm_45_g = 0, idm_90_g = 0, idm_135_g = 0;
    double idm_0_b = 0, idm_45_b = 0, idm_90_b = 0, idm_135_b = 0;
    
    double entropi_0_r = 0, entropi_45_r = 0, entropi_90_r = 0, entropi_135_r = 0;
    double entropi_0_g = 0, entropi_45_g = 0, entropi_90_g = 0, entropi_135_g = 0;
    double entropi_0_b = 0, entropi_45_b = 0, entropi_90_b = 0, entropi_135_b = 0;
    
    double korelasi_0_r = 0, korelasi_45_r = 0, korelasi_90_r = 0, korelasi_135_r = 0;
    double korelasi_0_g = 0, korelasi_45_g = 0, korelasi_90_g = 0, korelasi_135_g = 0;
    double korelasi_0_b = 0, korelasi_45_b = 0, korelasi_90_b = 0, korelasi_135_b = 0;
    
    double[] array_fitur = new double[66];
    int count_f = 0;
    
    double[][] normalized_fitur;
    
    public Ekstraksi_fitur(String path) throws IOException{
        
        File input = new File(path);
        image = ImageIO.read(input);
        
        width = image.getWidth();
        height = image.getHeight();
        
        w = (double) width;
        h = (double) height;
        
        size = w * h;
        
        this.r = new double[height][width];        
        this.g = new double[height][width];        
        this.b = new double[height][width];        
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                
                Color c = new Color(image.getRGB(j, i));
                
                r[i][j] = c.getRed();
                g[i][j] = c.getGreen();
                b[i][j] = c.getBlue();
            }
        }
    }
    
    public void color_moments(){

        double sumR = 0;
        double sumG = 0;
        double sumB = 0;
         
        for(int i=0; i<r.length; i++){
            for(int j=0; j<r[i].length; j++){
                                   
               sumR = sumR + r[i][j];
               sumG = sumG + g[i][j];               
               sumB = sumB + b[i][j];
            }
        }
        
        mean_r = sumR / size;
        mean_g = sumG / size;
        mean_b = sumB / size;
                
        for(int i=0; i<r.length; i++){
            for(int j=0; j<r[i].length; j++){
                
               sttd_r += Math.pow(r[i][j] - mean_r, 2);
               sttd_g += Math.pow(g[i][j] - mean_g, 2);
               sttd_b += Math.pow(b[i][j] - mean_b, 2);
            }
        }
        
        sttd_r = Math.sqrt(sttd_r / size);
        sttd_g = Math.sqrt(sttd_g / size);
        sttd_b = Math.sqrt(sttd_b / size);
        
        array_fitur[count_f] = mean_r;  
        count_f++;
        array_fitur[count_f] = mean_g;
        count_f++;
        array_fitur[count_f] = mean_b;
        count_f++;
        
        array_fitur[count_f] = sttd_r;
        count_f++;
        array_fitur[count_f] = sttd_g;
        count_f++;
        array_fitur[count_f] = sttd_b;
        count_f++;
    }
     
    public void set_matrix_glcm(){
        
        for(int i = 1; i < (height-1); i++){
            for(int j = 1; j < (width-1); j++){
                
                //sudut 0
                Color a = new Color(image.getRGB(j, i));
                Color b = new Color(image.getRGB((j+1), i));
                
                r1 = a.getRed();
                r2 = b.getRed();
                
                glcm_0_r[r1][r2] = glcm_0_r[r1][r2] +1;
                glcm_0_r[r2][r1] = glcm_0_r[r2][r1] +1;
                
                g1 = a.getGreen();
                g2 = b.getGreen();
                
                glcm_0_g[g1][g2] = glcm_0_g[g1][g2] +1;
                glcm_0_g[g2][g1] = glcm_0_g[g2][g1] +1;
                
                b1 = a.getBlue();
                b2 = b.getBlue();
                
                glcm_0_b[b1][b2] = glcm_0_b[b1][b2] +1;
                glcm_0_b[b2][b1] = glcm_0_b[b2][b1] +1;
                
                //sudut 45
                Color c = new Color(image.getRGB(j, i));
                Color d = new Color(image.getRGB((j+1), (i-1)));
                
                r1 = c.getRed();
                r2 = d.getRed();
                
                glcm_45_r[r1][r2] = glcm_45_r[r1][r2] +1;
                glcm_45_r[r2][r1] = glcm_45_r[r2][r1] +1;
                
                g1 = c.getGreen();
                g2 = d.getGreen();
                
                glcm_45_g[g1][g2] = glcm_45_g[g1][g2] +1;
                glcm_45_g[g2][g1] = glcm_45_g[g2][g1] +1;
                
                b1 = c.getBlue();
                b2 = d.getBlue();
                
                glcm_45_b[b1][b2] = glcm_45_b[b1][b2] +1;
                glcm_45_b[b2][b1] = glcm_45_b[b2][b1] +1;
                
                //sudut 90
                Color e = new Color(image.getRGB(j, i));
                Color f = new Color(image.getRGB((j), (i-1)));
                
                r1 = e.getRed();
                r2 = f.getRed();
                
                glcm_90_r[r1][r2] = glcm_90_r[r1][r2] +1;
                glcm_90_r[r2][r1] = glcm_90_r[r2][r1] +1;
                
                g1 = e.getGreen();
                g2 = f.getGreen();
                
                glcm_90_g[g1][g2] = glcm_90_g[g1][g2] +1;
                glcm_90_g[g2][g1] = glcm_90_g[g2][g1] +1;
                
                b1 = e.getBlue();
                b2 = f.getBlue();
                
                glcm_90_b[b1][b2] = glcm_90_b[b1][b2] +1;
                glcm_90_b[b2][b1] = glcm_90_b[b2][b1] +1;
                
                //sudut 135
                Color g = new Color(image.getRGB(j, i));
                Color h = new Color(image.getRGB((j-1), (i-1)));
                
                r1 = g.getRed();
                r2 = h.getRed();
                
                glcm_135_r[r1][r2] = glcm_135_r[r1][r2] +1;
                glcm_135_r[r2][r1] = glcm_135_r[r2][r1] +1;
                
                g1 = g.getGreen();
                g2 = h.getGreen();
                
                glcm_135_g[g1][g2] = glcm_135_g[g1][g2] +1;
                glcm_135_g[g2][g1] = glcm_135_g[g2][g1] +1;
                
                b1 = g.getBlue();
                b2 = h.getBlue();
                
                glcm_135_b[b1][b2] = glcm_135_b[b1][b2] +1;
                glcm_135_b[b2][b1] = glcm_135_b[b2][b1] +1;
                
            }
        }
    }
        
    public void normalized_matrix_glcm(){
        
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                sum_0_r   = sum_0_r + glcm_0_r[i][j];
                sum_45_r  = sum_45_r + glcm_45_r[i][j];
                sum_90_r  = sum_90_r + glcm_90_r[i][j];
                sum_135_r = sum_135_r + glcm_135_r[i][j];
                
                sum_0_g   = sum_0_g + glcm_0_g[i][j];
                sum_45_g  = sum_45_g + glcm_45_g[i][j];
                sum_90_g  = sum_90_g + glcm_90_g[i][j];
                sum_135_g = sum_135_g + glcm_135_g[i][j];
                
                sum_0_b   = sum_0_b + glcm_0_b[i][j];
                sum_45_b  = sum_45_b + glcm_45_b[i][j];
                sum_90_b  = sum_90_b + glcm_90_b[i][j];
                sum_135_b = sum_135_b + glcm_135_b[i][j];
            }
        }
        
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                glcm_0_r[i][j]   = glcm_0_r[i][j] / sum_0_r;
                glcm_45_r[i][j]  = glcm_45_r[i][j] / sum_45_r;
                glcm_90_r[i][j]  = glcm_90_r[i][j] / sum_90_r;
                glcm_135_r[i][j] = glcm_135_r[i][j] / sum_135_r;
                
                glcm_0_g[i][j]   = glcm_0_g[i][j] / sum_0_g;
                glcm_45_g[i][j]  = glcm_45_g[i][j] / sum_45_g;
                glcm_90_g[i][j]  = glcm_90_g[i][j] / sum_90_g;
                glcm_135_g[i][j] = glcm_135_g[i][j] / sum_135_g;
                
                glcm_0_b[i][j]   = glcm_0_b[i][j] / sum_0_b;
                glcm_45_b[i][j]  = glcm_45_b[i][j] / sum_45_b;
                glcm_90_b[i][j]  = glcm_90_b[i][j] / sum_90_b;
                glcm_135_b[i][j] = glcm_135_b[i][j] / sum_135_b;
            }
        }
    }
    
    public void calculate_ASM(){
        
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                asm_0_r   = asm_0_r + Math.pow(glcm_0_r[i][j],2);
                asm_45_r  = asm_45_r + Math.pow(glcm_45_r[i][j],2);
                asm_90_r  = asm_90_r + Math.pow(glcm_90_r[i][j],2);
                asm_135_r = asm_135_r + Math.pow(glcm_135_r[i][j],2);
                
                asm_0_g   = asm_0_g + Math.pow(glcm_0_g[i][j],2);
                asm_45_g  = asm_45_g + Math.pow(glcm_45_g[i][j],2);
                asm_90_g  = asm_90_g + Math.pow(glcm_90_g[i][j],2);
                asm_135_g = asm_135_g + Math.pow(glcm_135_g[i][j],2);
                
                asm_0_b   = asm_0_b + Math.pow(glcm_0_b[i][j],2);
                asm_45_b  = asm_45_b + Math.pow(glcm_45_b[i][j],2);
                asm_90_b  = asm_90_b + Math.pow(glcm_90_b[i][j],2);
                asm_135_b = asm_135_b + Math.pow(glcm_135_b[i][j],2);
            }
        }
        
        array_fitur[count_f] = asm_0_r;
        count_f++;
        array_fitur[count_f] = asm_0_g;
        count_f++;
        array_fitur[count_f] = asm_0_b;
        count_f++;
        
        array_fitur[count_f] = asm_45_r;
        count_f++;
        array_fitur[count_f] = asm_45_g;
        count_f++;
        array_fitur[count_f] = asm_45_b;
        count_f++;
                
        array_fitur[count_f] = asm_90_r;
        count_f++;
        array_fitur[count_f] = asm_90_g;
        count_f++;
        array_fitur[count_f] = asm_90_b;
        count_f++;
                
        array_fitur[count_f] = asm_135_r;
        count_f++;
        array_fitur[count_f] = asm_135_g;
        count_f++;
        array_fitur[count_f] = asm_135_b;
        count_f++;       
        
    }
    
    public void calculate_Contrast(){
               
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                double ii = (double) i;
                double jj = (double) j;
                double n = ii-jj;

                con_0_r   = con_0_r + ((Math.pow(n, 2))*glcm_0_r[i][j]);
                con_45_r  = con_45_r + ((Math.pow(n, 2))*glcm_45_r[i][j]);
                con_90_r  = con_90_r + ((Math.pow(n, 2))*glcm_90_r[i][j]);
                con_135_r = con_135_r + ((Math.pow(n, 2))*glcm_135_r[i][j]);
                
                con_0_g   = con_0_g + ((Math.pow(n, 2))*glcm_0_g[i][j]);
                con_45_g  = con_45_g + ((Math.pow(n, 2))*glcm_45_g[i][j]);
                con_90_g  = con_90_g + ((Math.pow(n, 2))*glcm_90_g[i][j]);
                con_135_g = con_135_g + ((Math.pow(n, 2))*glcm_135_g[i][j]);
                
                con_0_b   = con_0_b + ((Math.pow(n, 2))*glcm_0_b[i][j]);
                con_45_b  = con_45_b + ((Math.pow(n, 2))*glcm_45_b[i][j]);
                con_90_b  = con_90_b + ((Math.pow(n, 2))*glcm_90_b[i][j]);
                con_135_b = con_135_b + ((Math.pow(n, 2))*glcm_135_b[i][j]);
            }
        }
        
        array_fitur[count_f] = con_0_r;
        count_f++;
        array_fitur[count_f] = con_0_g;
        count_f++;
        array_fitur[count_f] = con_0_b;
        count_f++;
        
        array_fitur[count_f] = con_45_r;
        count_f++;
        array_fitur[count_f] = con_45_g;
        count_f++;
        array_fitur[count_f] = con_45_b;
        count_f++;
        
        array_fitur[count_f] = con_90_r;
        count_f++;
        array_fitur[count_f] = con_90_g;
        count_f++;
        array_fitur[count_f] = con_90_b;
        count_f++;
        
        array_fitur[count_f] = con_135_r;
        count_f++;
        array_fitur[count_f] = con_135_g;
        count_f++;
        array_fitur[count_f] = con_135_b;
        count_f++;
    }
    
    public void calculate_IDM(){
        
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                double ii = (double) i;
                double jj = (double) j;
                double n = ii-jj;

                idm_0_r   = idm_0_r + (glcm_0_r[i][j]/(1+(Math.pow(n, 2))));
                idm_45_r  = idm_45_r + (glcm_45_r[i][j]/(1+(Math.pow(n, 2))));
                idm_90_r  = idm_90_r + (glcm_90_r[i][j]/(1+(Math.pow(n, 2))));
                idm_135_r = idm_135_r + (glcm_135_r[i][j]/(1+(Math.pow(n, 2))));
                
                idm_0_g   = idm_0_g + (glcm_0_g[i][j]/(1+(Math.pow(n, 2))));
                idm_45_g  = idm_45_g + (glcm_45_g[i][j]/(1+(Math.pow(n, 2))));
                idm_90_g  = idm_90_g + (glcm_90_g[i][j]/(1+(Math.pow(n, 2))));
                idm_135_g = idm_135_g + (glcm_135_g[i][j]/(1+(Math.pow(n, 2))));
                
                idm_0_b   = idm_0_b + (glcm_0_b[i][j]/(1+(Math.pow(n, 2))));
                idm_45_b  = idm_45_b + (glcm_45_b[i][j]/(1+(Math.pow(n, 2))));
                idm_90_b  = idm_90_b + (glcm_90_b[i][j]/(1+(Math.pow(n, 2))));
                idm_135_b = idm_135_b + (glcm_135_b[i][j]/(1+(Math.pow(n, 2))));
            }
        }
        
        array_fitur[count_f] = idm_0_r;
        count_f++;
        array_fitur[count_f] = idm_0_g;
        count_f++;
        array_fitur[count_f] = idm_0_b;
        count_f++;
        
        array_fitur[count_f] = idm_45_r;
        count_f++;
        array_fitur[count_f] = idm_45_g;
        count_f++;
        array_fitur[count_f] = idm_45_b;
        count_f++;
        
        array_fitur[count_f] = idm_90_r;
        count_f++;
        array_fitur[count_f] = idm_90_g;
        count_f++;
        array_fitur[count_f] = idm_90_b;
        count_f++;
        
        array_fitur[count_f] = idm_135_r;
        count_f++;
        array_fitur[count_f] = idm_135_g;
        count_f++;
        array_fitur[count_f] = idm_135_b;
        count_f++;
        
    }
    
    public void calculate_Entrophy(){
        
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                
                if(glcm_0_r[i][j] != 0){
                    
                    entropi_0_r   = entropi_0_r - (glcm_0_r[i][j]*(Math.log10(glcm_0_r[i][j])));
                }
                else{
                    
                }
                
                if(glcm_45_r[i][j] != 0){
                    entropi_45_r  = entropi_45_r - (glcm_45_r[i][j]*(Math.log10(glcm_45_r[i][j])));
                }
                else{
                    
                }
                
                if(glcm_90_r[i][j] != 0){
                    entropi_90_r  = entropi_90_r - (glcm_90_r[i][j]*(Math.log10(glcm_90_r[i][j])));
                }
                else{
                    
                }
                
                if(glcm_135_r[i][j] != 0){
                    entropi_135_r = entropi_135_r - (glcm_135_r[i][j]*(Math.log10(glcm_135_r[i][j])));
                }
                else{
                    
                }
                
                if(glcm_0_g[i][j] != 0){
                    entropi_0_g   = entropi_0_g - (glcm_0_g[i][j]*(Math.log10(glcm_0_g[i][j])));
                }
                else{
                    
                }
                
                if(glcm_45_g[i][j] != 0){
                    entropi_45_g  = entropi_45_g - (glcm_45_g[i][j]*(Math.log10(glcm_45_g[i][j])));
                }
                else{
                    
                }
                
                if(glcm_90_g[i][j] != 0){
                    entropi_90_g  = entropi_90_g - (glcm_90_g[i][j]*(Math.log10(glcm_90_g[i][j])));
                }
                else{
                    
                }
                
                if(glcm_135_g[i][j] != 0){
                    entropi_135_g = entropi_135_g - (glcm_135_g[i][j]*(Math.log10(glcm_135_g[i][j])));
                }
                else{
                    
                }
                
                if(glcm_0_b[i][j] != 0){
                    entropi_0_b   = entropi_0_b - (glcm_0_b[i][j]*(Math.log10(glcm_0_b[i][j])));
                }
                else{
                    
                }
                
                if(glcm_45_b[i][j] != 0){
                    entropi_45_b  = entropi_45_b - (glcm_45_b[i][j]*(Math.log10(glcm_45_b[i][j])));
                }
                else{
                    
                }
                
                if(glcm_90_b[i][j] != 0){
                    entropi_90_b  = entropi_90_b - (glcm_90_b[i][j]*(Math.log10(glcm_90_b[i][j])));
                }
                else{
                    
                }
                
                if(glcm_135_b[i][j] != 0){
                    entropi_135_b = entropi_135_b - (glcm_135_b[i][j]*(Math.log10(glcm_135_b[i][j])));
                }
                else{
                    
                }
            }
        }
        
        array_fitur[count_f] = entropi_0_r;
        count_f++;
        array_fitur[count_f] = entropi_0_g;
        count_f++;
        array_fitur[count_f] = entropi_0_b;
        count_f++;
        
        array_fitur[count_f] = entropi_45_r;
        count_f++;
        array_fitur[count_f] = entropi_45_g;
        count_f++;
        array_fitur[count_f] = entropi_45_b;
        count_f++;
        
        array_fitur[count_f] = entropi_90_r;
        count_f++;
        array_fitur[count_f] = entropi_90_g;
        count_f++;
        array_fitur[count_f] = entropi_90_b;
        count_f++;
        
        array_fitur[count_f] = entropi_135_r;
        count_f++;
        array_fitur[count_f] = entropi_135_g;
        count_f++;
        array_fitur[count_f] = entropi_135_b;
        count_f++;
    }
    
    public void calculate_Correlation(){
        //kovarian i
        double kovarian_0_r_i = 0, kovarian_45_r_i = 0, kovarian_90_r_i = 0, kovarian_135_r_i = 0;
        double kovarian_0_g_i = 0, kovarian_45_g_i = 0, kovarian_90_g_i = 0, kovarian_135_g_i = 0;
        double kovarian_0_b_i = 0, kovarian_45_b_i = 0, kovarian_90_b_i = 0, kovarian_135_b_i = 0;
        
        //kovarian j
        double kovarian_0_r_j = 0, kovarian_45_r_j = 0, kovarian_90_r_j = 0, kovarian_135_r_j = 0;
        double kovarian_0_g_j = 0, kovarian_45_g_j = 0, kovarian_90_g_j = 0, kovarian_135_g_j = 0;
        double kovarian_0_b_j = 0, kovarian_45_b_j = 0, kovarian_90_b_j = 0, kovarian_135_b_j = 0;
        
        //menghitung kovarian
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                
                //kovarian i
                kovarian_0_r_i = kovarian_0_r_i + (i * glcm_0_r[i][j]);
                kovarian_45_r_i = kovarian_45_r_i + (i * glcm_45_r[i][j]);
                kovarian_90_r_i = kovarian_90_r_i + (i * glcm_90_r[i][j]);
                kovarian_135_r_i = kovarian_135_r_i + (i * glcm_135_r[i][j]);
                
                kovarian_0_g_i = kovarian_0_g_i + (i * glcm_0_g[i][j]);
                kovarian_45_g_i = kovarian_45_g_i + (i * glcm_45_g[i][j]);
                kovarian_90_g_i = kovarian_90_g_i + (i * glcm_90_g[i][j]);
                kovarian_135_g_i = kovarian_135_g_i + (i * glcm_135_g[i][j]);
                
                kovarian_0_b_i = kovarian_0_b_i + (i * glcm_0_b[i][j]);
                kovarian_45_b_i = kovarian_45_b_i + (i * glcm_45_b[i][j]);
                kovarian_90_b_i = kovarian_90_b_i + (i * glcm_90_b[i][j]);
                kovarian_135_b_i = kovarian_135_b_i + (i * glcm_135_b[i][j]);
                
                //kovarian j
                kovarian_0_r_j = kovarian_0_r_j + (j * glcm_0_r[i][j]);
                kovarian_45_r_j = kovarian_45_r_j + (j * glcm_45_r[i][j]);
                kovarian_90_r_j = kovarian_90_r_j + (j * glcm_90_r[i][j]);
                kovarian_135_r_j = kovarian_135_r_j + (j * glcm_135_r[i][j]);
                
                kovarian_0_g_j = kovarian_0_g_j + (j * glcm_0_g[i][j]);
                kovarian_45_g_j = kovarian_45_g_j + (j * glcm_45_g[i][j]);
                kovarian_90_g_j = kovarian_90_g_j + (j * glcm_90_g[i][j]);
                kovarian_135_g_j = kovarian_135_g_j + (j * glcm_135_g[i][j]);
                
                kovarian_0_b_j = kovarian_0_b_j + (j * glcm_0_b[i][j]);
                kovarian_45_b_j = kovarian_45_b_j + (j * glcm_45_b[i][j]);
                kovarian_90_b_j = kovarian_90_b_j + (j * glcm_90_b[i][j]);
                kovarian_135_b_j = kovarian_135_b_j + (j * glcm_135_b[i][j]);
            }
        }
        
        //standar deviasi (std) i
        double std_0_r_i = 0, std_45_r_i = 0, std_90_r_i = 0, std_135_r_i = 0;
        double std_0_g_i = 0, std_45_g_i = 0, std_90_g_i = 0, std_135_g_i = 0;
        double std_0_b_i = 0, std_45_b_i = 0, std_90_b_i = 0, std_135_b_i = 0;
        
        //standar deviasi (std) j
        double std_0_r_j = 0, std_45_r_j = 0, std_90_r_j = 0, std_135_r_j = 0;
        double std_0_g_j = 0, std_45_g_j = 0, std_90_g_j = 0, std_135_g_j = 0;
        double std_0_b_j = 0, std_45_b_j = 0, std_90_b_j = 0, std_135_b_j = 0;
        
        //menghitung standar deviasi
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                
                //std i
                std_0_r_i = std_0_r_i + (Math.pow((i - kovarian_0_r_i), 2) * glcm_0_r[i][j]);
                std_45_r_i = std_45_r_i + (Math.pow((i - kovarian_45_r_i), 2) * glcm_45_r[i][j]);
                std_90_r_i = std_90_r_i + (Math.pow((i - kovarian_90_r_i), 2) * glcm_90_r[i][j]);
                std_135_r_i = std_135_r_i + (Math.pow((i - kovarian_135_r_i), 2) * glcm_135_r[i][j]);
                
                std_0_g_i = std_0_g_i + (Math.pow((i - kovarian_0_g_i), 2) * glcm_0_g[i][j]);
                std_45_g_i = std_45_g_i + (Math.pow((i - kovarian_45_g_i), 2) * glcm_45_g[i][j]);
                std_90_g_i = std_90_g_i + (Math.pow((i - kovarian_90_g_i), 2) * glcm_90_g[i][j]);
                std_135_g_i = std_135_g_i + (Math.pow((i - kovarian_135_g_i), 2) * glcm_135_g[i][j]);
                
                std_0_b_i = std_0_b_i + (Math.pow((i - kovarian_0_b_i), 2) * glcm_0_b[i][j]);
                std_45_b_i = std_45_b_i + (Math.pow((i - kovarian_45_b_i), 2) * glcm_45_b[i][j]);
                std_90_b_i = std_90_b_i + (Math.pow((i - kovarian_90_b_i), 2) * glcm_90_b[i][j]);
                std_135_b_i = std_135_b_i + (Math.pow((i - kovarian_135_b_i), 2) * glcm_135_b[i][j]);
                
                //std j
                std_0_r_j = std_0_r_j + (Math.pow((j - kovarian_0_r_j), 2) * glcm_0_r[i][j]);
                std_45_r_j = std_45_r_j + (Math.pow((j - kovarian_45_r_j), 2) * glcm_45_r[i][j]);
                std_90_r_j = std_90_r_j + (Math.pow((j - kovarian_90_r_j), 2) * glcm_90_r[i][j]);
                std_135_r_j = std_135_r_j + (Math.pow((j - kovarian_135_r_j), 2) * glcm_135_r[i][j]);
                
                std_0_g_j = std_0_g_j + (Math.pow((j - kovarian_0_g_j), 2) * glcm_0_g[i][j]);
                std_45_g_j = std_45_g_j + (Math.pow((j - kovarian_45_g_j), 2) * glcm_45_g[i][j]);
                std_90_g_j = std_90_g_j + (Math.pow((j - kovarian_90_g_j), 2) * glcm_90_g[i][j]);
                std_135_g_j = std_135_g_j + (Math.pow((j - kovarian_135_g_j), 2) * glcm_135_g[i][j]);
                
                std_0_b_j = std_0_b_j + (Math.pow((j - kovarian_0_b_j), 2) * glcm_0_b[i][j]);
                std_45_b_j = std_45_b_j + (Math.pow((j - kovarian_45_b_j), 2) * glcm_45_b[i][j]);
                std_90_b_j = std_90_b_j + (Math.pow((j - kovarian_90_b_j), 2) * glcm_90_b[i][j]);
                std_135_b_j = std_135_b_j + (Math.pow((j - kovarian_135_b_j), 2) * glcm_135_b[i][j]);
            }
        }
        
        //menghitung korelasi
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                
                korelasi_0_r   = korelasi_0_r + ((((i*j)*glcm_0_r[i][j]) - (kovarian_0_r_i*kovarian_0_r_j))/(std_0_r_i*std_0_r_j));
                korelasi_45_r  = korelasi_45_r + ((((i*j)*glcm_45_r[i][j]) - (kovarian_45_r_i*kovarian_45_r_j))/(std_45_r_i*std_45_r_j));
                korelasi_90_r  = korelasi_90_r + ((((i*j)*glcm_90_r[i][j]) - (kovarian_90_r_i*kovarian_90_r_j))/(std_90_r_i*std_90_r_j));
                korelasi_135_r = korelasi_135_r + ((((i*j)*glcm_135_r[i][j]) - (kovarian_135_r_i*kovarian_135_r_j))/(std_135_r_i*std_135_r_j));
                
                korelasi_0_g   = korelasi_0_g + ((((i*j)*glcm_0_g[i][j]) - (kovarian_0_g_i*kovarian_0_g_j))/(std_0_g_i*std_0_g_j));
                korelasi_45_g  = korelasi_45_g + ((((i*j)*glcm_45_g[i][j]) - (kovarian_45_g_i*kovarian_45_g_j))/(std_45_g_i*std_45_g_j));
                korelasi_90_g  = korelasi_90_g + ((((i*j)*glcm_90_g[i][j]) - (kovarian_90_g_i*kovarian_90_g_j))/(std_90_g_i*std_90_g_j));
                korelasi_135_g = korelasi_135_g + ((((i*j)*glcm_135_g[i][j]) - (kovarian_135_g_i*kovarian_135_g_j))/(std_135_g_i*std_135_g_j));
                                
                korelasi_0_b   = korelasi_0_b + ((((i*j)*glcm_0_b[i][j]) - (kovarian_0_b_i*kovarian_0_b_j))/(std_0_b_i*std_0_b_j));
                korelasi_45_b  = korelasi_45_b + ((((i*j)*glcm_45_b[i][j]) - (kovarian_45_b_i*kovarian_45_b_j))/(std_45_b_i*std_45_b_j));
                korelasi_90_b  = korelasi_90_b + ((((i*j)*glcm_90_b[i][j]) - (kovarian_90_b_i*kovarian_90_b_j))/(std_90_b_i*std_90_b_j));
                korelasi_135_b = korelasi_135_b + ((((i*j)*glcm_135_b[i][j]) - (kovarian_135_b_i*kovarian_135_b_j))/(std_135_b_i*std_135_b_j));
                
            }
        }
        
        array_fitur[count_f] = korelasi_0_r;
        count_f++;
        array_fitur[count_f] = korelasi_0_g;
        count_f++;
        array_fitur[count_f] = korelasi_0_b;
        count_f++;
        
        array_fitur[count_f] = korelasi_45_r;
        count_f++;
        array_fitur[count_f] = korelasi_45_g;
        count_f++;
        array_fitur[count_f] = korelasi_45_b;
        count_f++;
        
        array_fitur[count_f] = korelasi_90_r;
        count_f++;
        array_fitur[count_f] = korelasi_90_g;
        count_f++;
        array_fitur[count_f] = korelasi_90_b;
        count_f++;
        
        array_fitur[count_f] = korelasi_135_r;
        count_f++;
        array_fitur[count_f] = korelasi_135_g;
        count_f++;
        array_fitur[count_f] = korelasi_135_b;
        count_f++;
    }
    
    public double[] array_fitur(){
        
        return array_fitur;
    }
    
    public void print_matrix(){
        
        for(int i = 0; i < 256; i++){
            System.out.printf("| \t");
            for(int j = 0; j < 256; j++){
                System.out.printf(glcm_0_r[i][j] + "\t | \t");
            }
            System.out.println("");
        }
    }
    
}

