package com.vajda.spockExamples.interactions;

import java.util.ArrayList;
import java.util.List;

public class DataOperations {
    
    private final SqlExecutor sqlExecutor;
    
    public DataOperations(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public List<List<Integer>> getData(String sql, int value) {
        List<Object[]> rawData = sqlExecutor.execute(sql);
        return removeValuesLowerThan(rawData, value);
    }
    
    private List<List<Integer>> removeValuesLowerThan(List<Object[]> data, int value) {
        List<List<Integer>> transformedList = transformData(data);
        for (List<Integer> li : transformedList) {
            for (int i = 0; i < li.size(); i++) {
                Integer item = li.get(i);
                if (item < value) {
                    li.remove(i);
                    i--;
                }
            }
        }
        return transformedList;
    }
    
    public List<List<Integer>> transformData(List<Object[]> data) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (Object[] oa : data) {
            List<Integer> li = new ArrayList<Integer>();
            for (Object o : oa) {
                li.add((Integer) o);
            }
            result.add(li);
        }
        return result;
    }
}
