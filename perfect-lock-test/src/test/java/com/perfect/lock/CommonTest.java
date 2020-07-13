package com.perfect.lock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CommonTest {

    @Test
    public void commonTest() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'mylock'");
        Object value = expression.getValue();
        Assertions.assertEquals("mylock",value);

        Expression expression1 = parser.parseExpression("'mylock:' + #orderNo");

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("orderNo", 2L);
        String value1 = expression1.getValue(context, String.class);
        Assertions.assertEquals("mylock:2",value1);
    }
}
