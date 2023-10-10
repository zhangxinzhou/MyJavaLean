package com.example.demo.multitransaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 多数据源事务切面
 * ※采用Around似乎不行※
 */
@Component
@Aspect
public class MultiTransactionAop {
    /**
     * 线程本地变量：为什么使用栈？※为了达到后进先出的效果※
     */
    private static final ThreadLocal<Stack<Map<DataSourceTransactionManager, TransactionStatus>>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 用于获取事务管理器
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 事务声明
     */
    private DefaultTransactionDefinition def = new DefaultTransactionDefinition();

    {
        // 非只读模式
        def.setReadOnly(false);
        // 事务隔离级别：采用数据库的
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(com.example.demo.multitransaction.MultiDataSourceTransactional)")
    public void pointcut() {
    }

    /**
     * 声明事务
     *
     * @param transactional 注解
     */
    @Before("pointcut() && @annotation(transactional)")
    public void before(MultiDataSourceTransactional transactional) {
        // 根据设置的事务名称按顺序声明，并放到ThreadLocal里
        String[] transactionManagerNames = transactional.transactionManagers();
        Stack<Map<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        for (String transactionManagerName : transactionManagerNames) {
            DataSourceTransactionManager transactionManager = applicationContext.getBean(transactionManagerName, DataSourceTransactionManager.class);
            TransactionStatus transactionStatus = transactionManager.getTransaction(def);
            Map<DataSourceTransactionManager, TransactionStatus> transactionMap = new HashMap<>();
            transactionMap.put(transactionManager, transactionStatus);
            pairStack.push(transactionMap);
        }
        THREAD_LOCAL.set(pairStack);
    }

    /**
     * 提交事务
     */
    @AfterReturning("pointcut()")
    public void afterReturning() {
        // ※栈顶弹出（后进先出）
        Stack<Map<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Map<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.forEach((key, value) -> key.commit(value));
        }
        THREAD_LOCAL.remove();
    }

    /**
     * 回滚事务
     */
    @AfterThrowing(value = "pointcut()")
    public void afterThrowing() {
        // ※栈顶弹出（后进先出）
        Stack<Map<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Map<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.forEach((key, value) -> key.rollback(value));
        }
        THREAD_LOCAL.remove();
    }
}