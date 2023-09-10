package com.zpf.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.doubleconverter.DoubleStringConverter;
import com.alibaba.excel.converters.string.StringNumberConverter;

public class FillWithAnnotationData {
    private String name;
    @NumberFormat(",##0.00")
    @ExcelProperty(converter = StringNumberConverter.class)
    private String money;
    @NumberFormat(",##0.00")
    @ExcelProperty(converter = DoubleStringConverter.class)
    private Double number;
}