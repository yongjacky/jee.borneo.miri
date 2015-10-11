/**
 *
 */
package com.borneo.framework.spring.mvc.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

/**
 * @author peter.yuan
 */
public class OptimisticOperationExecutor implements Ordered {

    private static final int DEFAULT_MAX_RETRIES = 2;

    private int maxRetries = DEFAULT_MAX_RETRIES;

    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Object doOptimisticOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        HibernateOptimisticLockingFailureException lockFailureException;

        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (HibernateOptimisticLockingFailureException ex) {
                lockFailureException = ex;
            }
        } while (numAttempts <= this.maxRetries);

        throw lockFailureException;
    }

}
