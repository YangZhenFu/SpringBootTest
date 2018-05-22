package com.stylefeng.guns.core.beetl;

import com.stylefeng.guns.core.beetl.function.AirLedFunction;
import com.stylefeng.guns.core.beetl.function.AirSensorFunction;
import com.stylefeng.guns.core.beetl.function.AirStationFunction;
import com.stylefeng.guns.core.beetl.function.AreaFunction;
import com.stylefeng.guns.core.beetl.function.DeptFunction;
import com.stylefeng.guns.core.beetl.function.StrutilFunction;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

/**
 * beetl拓展配置,绑定一些工具类,方便在模板中直接调用
 *
 * @author stylefeng
 * @Date 2018/2/22 21:03
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
        groupTemplate.registerFunctionPackage("strutils", StrutilFunction.class);
        groupTemplate.registerFunctionPackage("sysArea", AreaFunction.class);
        groupTemplate.registerFunctionPackage("sysDept", DeptFunction.class);
        groupTemplate.registerFunctionPackage("airStation", AirStationFunction.class);
        groupTemplate.registerFunctionPackage("airSensor", AirSensorFunction.class);
        groupTemplate.registerFunctionPackage("airLed", AirLedFunction.class);
    }
}
