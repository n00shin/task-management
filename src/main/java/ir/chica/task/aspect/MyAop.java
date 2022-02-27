package ir.chica.task.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Aspect
@Configuration
public class MyAop {
    @Autowired
   private final DbAccess db;
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MyAop.class);

    public MyAop(DbAccess db) {
        this.db = db;
    }

    @Around("@annotation(ir.chica.task.aspect.MyTransactional)")
    public Object transactionSQL(ProceedingJoinPoint pjp) throws Throwable {


        Object output = null;

        try {
            db.beginTransaction();
            output = pjp.proceed();
            db.commitTransaction();
        } catch (Exception ex) {
            db.rollBackTransaction();
            LOG.info("exception occured inside transactional method");
            ex.printStackTrace();

        }

        return output;
    }

}