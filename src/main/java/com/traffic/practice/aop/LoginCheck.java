package com.traffic.practice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *
 *  Weaving (위빙)
 * Weaving은 AOP에서 핵심적인 개념으로, 어떤 시점에서 특정 코드에 **Advice(부가적인 기능)**를 적용(Weaving)할 것인지에 대한 과정.
 * Spring에서는 Aspect와 Advice를 핵심 로직에 결합하는 작업을 Weaving이라고 한다..
 *
 * 컴파일 시점(Compile-time Weaving): 컴파일 시점에서 AOP 관련 로직을 코드에 삽입.
 * 로드 시점(Class-load-time Weaving): 클래스 로딩 시점에서 AOP 로직을 삽입.
 * 런타임 시점(Run-time Weaving): 런타임 동안 객체 생성 시점에 AOP 로직을 삽입.
 *
 *
 *
**/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {
    // @LoginCheck 어노테이션을 메소드 위에 붙여주기만 하면 적용되도록 함
    public static enum UserType {
        USER, ADMIN
    }

    UserType type();

}
