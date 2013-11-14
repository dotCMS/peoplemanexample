package com.dotcms.peopleman.viewtools;

import org.apache.velocity.tools.view.context.ViewContext;
import org.apache.velocity.tools.view.servlet.ServletToolInfo;

public class PersonToolInfo extends ServletToolInfo {

    @Override
    public String getKey () {
        return "persontool";
    }

    @Override
    public String getScope () {
        return ViewContext.APPLICATION;
    }

    @Override
    public String getClassname () {
        return PersonTool.class.getName();
    }

    @Override
    public Object getInstance ( Object initData ) {

    	PersonTool viewTool = new PersonTool();
        viewTool.init( initData );

        setScope( ViewContext.APPLICATION );

        return viewTool;
    }

}