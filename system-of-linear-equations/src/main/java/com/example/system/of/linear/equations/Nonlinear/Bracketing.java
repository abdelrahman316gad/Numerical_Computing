package com.example.system.of.linear.equations.Nonlinear;

import org.mariuszgromada.math.mxparser.* ;

import java.util.HashMap;
import java.util.LinkedList;

public class Bracketing {

    double es = 0.00001 ;
    int noFigures = 0 ;
    int maxIterations = 50 ;
    LinkedList<HashMap<String,Double>> steps ;
    boolean hasSolution ;

    public void setEs(double es) {this.es = es;}
    public void setNoFigures(int noFigures) {this.noFigures = noFigures;}
    public void setMaxIterations(int maxIterations) {this.maxIterations = maxIterations;}

    public boolean HasSolution(){
        return hasSolution ;
    }

    public LinkedList getSteps(){
        return steps ;
    }

    double fx(String function, double x){
        Function f = new Function(function) ;
        Argument x0 = new Argument("x = 1") ;
        x0.setArgumentValue(x);
        double fx = round(new Expression("f(x)",f, x0).calculate()) ;
        return fx ;
    }


    public double bisections(String function){
        steps = new LinkedList<HashMap<String,Double>>() ;
        hasSolution = true ;

        double[] xlxu = xlxu(function) ;
        if(!hasSolution){
            return -1 ;
        }
        double xl = xlxu[0] ;
        double xu = xlxu[1] ;


        double xr=0 ;
        double ea ;
        for(int i=0 ; i< maxIterations ; i++){
            xr = (xu+xl) /2.0 ;
            System.out.println(xr);
            ea = round(Math.abs(xu-xl)/xl) ;

            double fl = fx(function,xl) ;
            double fr = fx(function,xr);

            update(xl, xu, xr, fr, ea);

            if(fl*fr <0 ){ // test
                xu = xr ;
            }else {
                xl = xr ;
            }

            if(Math.abs(fr) <es || ea<es){
                System.out.print("f(xr)=");
                System.out.println(fr);
                System.out.print("ea=");
                System.out.println(ea);

                hasSolution = true ;
                break;
            }
        }
        return xr ;

    }
    void update(double xl,double xu,double xr,double fr,double ea){
        HashMap<String ,Double> step = new HashMap<String ,Double>() ;
        step.put("xl",xl) ;
        step.put("xu",xu) ;
        step.put("xr",xr) ;
        step.put("f(xr)",fr) ;
        step.put("ea",ea) ;
        steps.add(step) ;
    }


    double round(double x){
        if(noFigures ==0){
            return x ;
        }

        if(Math.abs(x)< es){
            return 0 ;
        }
        int shift = (int)Math.ceil(Math.log10(x)) - noFigures;
        x = x/ Math.pow(10, shift) ;
        x = Math.round(x) ;
        x = x*  Math.pow(10, shift) ;
        x = Double.parseDouble( String.format("%.10g%n",x) ) ;
        return x ;
    }

    double[] xlxu(String function){

        double x0 = 0 ;
        double delta = 10 ;
        double x1 = delta ;
        double fx0 = fx(function,x0) ;
        double fx1 = fx(function,x1) ;
        hasSolution = false ;
        if(Double.isNaN(fx0) && Double.isNaN(fx1)){
            return new double[]{0,0} ;
        }

        for(int i=0 ; i<10000 ; i++){
            // for positive
            fx0 = fx(function,x0) ;
            fx1 = fx(function,x1) ;
            
            if(fx0*fx1 < 0){
                hasSolution = true ;
                break;
            }
            // for negative
            fx0 = fx(function,x0*-1) ;
            fx1 = fx(function,x1*-1) ;
            if(fx0*fx1 < 0){
                double temp = x0 ;
                x0 = -1*x1 ;
                x1 = -1*temp ;
                hasSolution = true ;
                break;
            }

            x0 = x1 ;
            x1 += delta ;
        }
        delta = 1 ;
        x1 = x0 + delta ;

        for(int i=0 ; i<10000 ; i++){
            // for positive
            fx0 = fx(function,x0) ;
            fx1 = fx(function,x1) ;
            if(fx0*fx1 < 0){
                break;
            }
            x0 = x1 ;
            x1 += delta ;
        }

        return new double[]{x0,x1} ;


    }

}
