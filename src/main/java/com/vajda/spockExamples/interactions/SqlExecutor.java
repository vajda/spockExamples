package com.vajda.spockExamples.interactions;

import java.util.List;

public interface SqlExecutor {

    List<Object[]> execute(String sql);
}
