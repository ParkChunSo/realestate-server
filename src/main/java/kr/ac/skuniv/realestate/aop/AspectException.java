package kr.ac.skuniv.realestate.aop;

import kr.ac.skuniv.realestate.exception.UserDefineException;
import kr.ac.skuniv.realestate.utill.ExcelConverterUtill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 해야할일 : Advice
 * 어디에 적용할 것인가 : 포인트컷
 */

@Aspect
@Component
public class AspectException {
    private Logger logger = LoggerFactory.getLogger(AspectException.class);
    private HashMap<String, String> regionCodeHashmap;

    public void setRegionCodeHashmap(HashMap<String, String> regionCodeHashmap) {
        this.regionCodeHashmap = regionCodeHashmap;
    }

    @Pointcut("execution(* kr.ac.skuniv.realestate.service.ConditionService.convertRegionToDto(..))")
    public void convertRegionToDto() {
    }

    @Pointcut("execution(* kr.ac.skuniv.realestate.service.ConditionService.getGraphDtoByRegionDto(..))")
    public void getGraphDtoByRegionDto() {
    }

    @Pointcut("execution(* kr.ac.skuniv.realestate.service.ConditionService.mergeObjectsToGraphDtos(..))")
    public void mergeObjectsToGraphDtos() {
    }

    @Pointcut("execution(* kr.ac.skuniv.realestate.service.ConditionService.convertObjectsToGraphDtos(..))")
    public void convertObjectsToGraphDtos() {
    }

    @Pointcut("execution(* kr.ac.skuniv.realestate.service.ConditionService.convertGraphTmpDtosToGraphDtos(..))")
    public void convertGraphTmpDtosToGraphDtos() {
    }

//    @Around("execution(* kr.ac.skuniv.realestate.service.ConditionService.*(..))")
//    @Around("@annotation(AspectExceptionAnnotation)")
    @Around("getGraphDtoByRegionDto() || mergeObjectsToGraphDtos() || convertObjectsToGraphDtos() || convertGraphTmpDtosToGraphDtos()")
    private Object aroundException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = new Object();
        try {
            result = proceedingJoinPoint.proceed();//TARGET 메소드
        } catch (Exception e) {
            String exceptionMethod = e.getStackTrace()[0].getMethodName();

            if (exceptionMethod.contains("getGraphDtoByRegionDto")) {
                throw new UserDefineException("데이터베이스에서 가져오는 과정에서 오류", e.toString(), exceptionMethod);
            } else if (exceptionMethod.equals("mergeObjectsToGraphDtos")) {
                throw new UserDefineException("GraphDto 병합 과정에서 오류", e.toString(), exceptionMethod);
            } else if (exceptionMethod.equals("convertObjectsToGraphDtos")) {
                throw new UserDefineException("Objects -> GraphDto 변환과정에서 오류", e.toString(), exceptionMethod);
            } else if (exceptionMethod.equals("convertGraphTmpDtosToGraphDtos")) {
                throw new UserDefineException("GraphTmpDto -> GraphDto 변환과정에서 오류", e.toString(), exceptionMethod);
            }
        }
        return result;
    }

    @Around("convertRegionToDto()")
    private Object aroundConvertRegionToDtoException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        String region = null;
        Object[] paramValues = proceedingJoinPoint.getArgs();//본래 메소드가 받은 매개변수
//        HashMap<String, String> regionCodeMap = excelConverterUtill.getRegionCodeMap();

        if (paramValues.length == 1) {
            region = paramValues[0].toString();
        } else if (paramValues.length == 2) {
            region = paramValues[0].toString() + paramValues[1].toString();
        } else if (paramValues.length == 3) {
            region = paramValues[0].toString() + paramValues[1].toString() + paramValues[2].toString();
        }

        if (!regionCodeHashmap.containsKey(region)) {
            throw new UserDefineException("찾을 수 없는 URL 파라미터");
        }

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw new UserDefineException("찾을수 없는 URL 파라미터", e.toString(), e.getStackTrace()[0].getMethodName());
        }

        return result;
    }
}
