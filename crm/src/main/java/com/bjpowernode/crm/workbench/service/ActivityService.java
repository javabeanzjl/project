package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);

    int queryCountOfActivityByCondition(Map<String, Object> map);
}
