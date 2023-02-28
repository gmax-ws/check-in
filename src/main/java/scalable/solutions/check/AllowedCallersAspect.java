/*
 * check-in
 * Copyright (c) 2023 Scalable Solutions SRL
 *
 * Author: Marius Gligor <marius.gligor@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111, USA.
 */
package scalable.solutions.check;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.String.format;

@Aspect
public class AllowedCallersAspect {

    private static final Logger logger = LoggerFactory.getLogger(AllowedCallersAspect.class);

    private AllowedCallersAspect() {
    }

    @Before("@annotation(scalable.solutions.check.AllowedCallers) && call(* *(..))")
    public void check(JoinPoint jp) {
        // get caller
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String caller = caller(stackTrace, 1);

        // get allowed callers from AllowedCallers annotation
        Method method = method(jp);
        logger.debug("Method call: {}", method);
        AllowedCallers callers = method.getAnnotation(AllowedCallers.class);

        // check caller
        if (!Arrays.asList(callers.value()).contains(caller)) {
            String message = format("Caller %s is not allowed", caller);
            throw new CallerNotAllowedException(message);
        }

        logger.debug("Caller: {} is allowed", caller);
    }

    private Method method(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getMethod();
    }

    private String caller(StackTraceElement[] stackTrace, int index) {
        String className = stackTrace[index].getClassName();
        String methodName = stackTrace[index].getMethodName();
        return format("%s.%s", className, methodName);
    }
}
