package com.dmarco.seckill.exception;

import com.dmarco.seckill.result.CodeMsg;

/**
 * @author Dmarco
 */
public class GlobalException  extends  RuntimeException{

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm=cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
