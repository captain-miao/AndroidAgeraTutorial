package com.github.captain_miao.agera.tutorial.model;

import java.util.List;

/**
 * @author YanLu
 * @since 16/5/20
 */
public class ApiResult<T> extends BaseModel {
    public boolean error;
    public List<T> results;
}
