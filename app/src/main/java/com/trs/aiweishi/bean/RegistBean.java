package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/6/29.
 */

public class RegistBean extends BaseBean{
 private String serviceName;
 private String result;

 @Override
 public String toString() {
  return "RegistBean{" +
          "serviceName='" + serviceName + '\'' +
          ", desc='" + desc + '\'' +
          ", result='" + result + '\'' +
          '}';
 }
}
