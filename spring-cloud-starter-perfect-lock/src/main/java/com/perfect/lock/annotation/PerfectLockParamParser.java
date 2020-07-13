package com.perfect.lock.annotation;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

public class PerfectLockParamParser {
    private static Logger log = LoggerFactory.getLogger(PerfectLockParamParser.class);
    private final Expression expression;
    private final String[] paraNameArr;
    private final PerfectLock annotation;

    public PerfectLockParamParser(MethodSignature signature) {
        log.info("create PerfectLockParamParser: {}", signature);
        Method method = signature.getMethod();
        annotation = method.getAnnotation(PerfectLock.class);

        ExpressionParser parser = new SpelExpressionParser();
        expression = parser.parseExpression(annotation.name());

        paraNameArr = signature.getParameterNames();
    }

    public PerfectLockParam parse(Object[] args) {
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return new PerfectLockParam(expression.getValue(context, String.class), annotation.maxWait(), annotation.maxHold());
    }
}
