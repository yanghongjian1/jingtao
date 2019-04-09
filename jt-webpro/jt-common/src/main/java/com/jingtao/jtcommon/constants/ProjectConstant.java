package com.jingtao.jtcommon.constants;

/**
 * Created by zhangwangwang on 2017/10/31.
 */
public class ProjectConstant {
    public static final String BASE_PACKAGE = "com.jingtao.*.*.api";//项目基础包名称，根据自己公司的项目修改

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE =   "com.jingtao.jtcommon.core.Mapper";//Mapper插件基础接口的完全限定名
}
