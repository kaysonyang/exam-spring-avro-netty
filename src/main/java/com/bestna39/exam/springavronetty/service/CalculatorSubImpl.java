package com.bestna39.exam.springavronetty.service;

import com.bestna39.exam.springavronetty.protocol.CalculatorSub;
import org.apache.avro.AvroRemoteException;
import org.springframework.stereotype.Service;

@Service
public class CalculatorSubImpl implements CalculatorSub {
    @Override
    public double subtract(double x, double y) throws AvroRemoteException {
        return x - y;
    }
}
