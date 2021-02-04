package com.example.syshology.jpa.exception;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 3:59
 * Project : IntelliJ IDEA
 */
public class NotEnoughStockException extends Exception{
    public NotEnoughStockException(String message){
        super(message);
    }
}
