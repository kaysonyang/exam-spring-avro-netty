package com.bestna39.exam.springavronetty.service;

import com.bestna39.exam.springavronetty.protocol.Calculator;
import com.bestna39.exam.springavronetty.protocol.CalculatorAdd;
import com.bestna39.exam.springavronetty.protocol.CalculatorSub;
import org.apache.avro.AvroRemoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorImpl implements Calculator {

    @Autowired
    private CalculatorAdd calculatorAdd;

    @Autowired
    private CalculatorSub calculatorSub;

    @Override
    public double add(double x, double y) throws AvroRemoteException {
        return calculatorAdd.add(x, y);
    }

    @Override
    public double subtract(double x, double y) throws AvroRemoteException {
        return calculatorSub.subtract(x, y);
    }
}
