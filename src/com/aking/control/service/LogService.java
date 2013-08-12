package com.aking.control.service;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.LogDao;
import com.aking.model.constant.LogType;
import com.aking.model.system.Log;

@Component
@Aspect
public class LogService extends BaseService<Log, String> {
	@Autowired
	private LogDao logDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return logDao;
	}

	// @Before("execution(* com.aking.control.service.*.*(..))")
	public void saveLogBefore(JoinPoint joinPoint) {
		try {
			Log log = new Log();
			log.setDateTime((new Date()).toString());
			log.setLogType(LogType.INFO);
			log.setDescription(joinPoint.getSignature().getName() + " started!");
			logDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @After("execution(* com.aking.control.service.*.*(..))")
	public void saveLogAfter(JoinPoint joinPoint) {
		Log log = new Log();
		log.setDateTime((new Date()).toString());
		log.setLogType(LogType.INFO);
		log.setDescription(joinPoint.getSignature().getName() + " finished!");
		logDao.save(log);
	}

	// @AfterThrowing(pointcut = "execution(* com.aking.control.service.*.*(..))", throwing = "error")
	public void saveLogAfterThrowing(JoinPoint joinPoint,
			Throwable error) {
		Log log = new Log();
		log.setDateTime((new Date()).toString());
		log.setLogType(LogType.ERROR);
		log.setDescription(joinPoint.getSignature().getName() + " error! " + error.getMessage());
		logDao.save(log);
	}

}
