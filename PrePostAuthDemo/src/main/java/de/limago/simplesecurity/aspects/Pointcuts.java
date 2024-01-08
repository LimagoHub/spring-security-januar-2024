package de.limago.simplesecurity.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    //@Pointcut("@within(de.limago.simplesecurity.aspects.annotations.RunAsRole)")
    @Pointcut("execution(* *(..)) && @annotation(de.limago.simplesecurity.aspects.annotations.RunAsRole)")
    public void runAsRoleMethods(){}
}
