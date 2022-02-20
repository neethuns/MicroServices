package com.maveric.customerAcc.demo.feign;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HystrixFallBackFactory implements FallbackFactory<AccountFeign> {
    @Override
    public AccountFeign create(Throwable cause) {

       return id -> { System.out.println("fallback; reason was: " + cause.getMessage());
            return null;
        };
    }

}

