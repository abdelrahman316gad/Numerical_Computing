package com.example.system.of.linear.equations.Nonlinear;

public class Nonlinear {

    public void tryy(){

        try {
            String f = "f(x)= x^2+x+3" ;
            Bisection b = new Bisection() ;

            System.out.println("this the try" + b.bisections(f) );
            System.out.println("has" + b.HasSolution());
            System.out.println(b.getSteps().toString() );

        }catch (Exception e){
            System.out.println("error");

        }

    }
}
