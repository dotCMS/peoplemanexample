package com.dotcms.peopleman.viewtools;

import org.apache.velocity.tools.view.context.ViewContext;
import org.apache.velocity.tools.view.servlet.ServletToolInfo;

public class UserToolInfo extends ServletToolInfo {

    @Override
    public String getKey () {
        return "usertool";
    }

    @Override
    public String getScope () {
        return ViewContext.APPLICATION;
    }

    @Override
    public String getClassname () {
        return UserTool.class.getName();
    }

    @Override
    public Object getInstance ( Object initData ) {

        UserTool viewTool = new UserTool();
        viewTool.init( initData );

        setScope( ViewContext.APPLICATION );

        return viewTool;
    }

}