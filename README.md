README
------

PeopleMan OSGI plugin
---------------------------------

This plugin shows a user how to build a complete Spring web app and velocity viewtool that
can be and push/launched via Push Publishing.  The plugin creates a new structure, 
called "People" that can be used to store extra/custom information regarding 
any dotcms user.  It will also build a folder under /peopleman with pages and the .vtl 
files needed to demonstrate the app.

This plugin is intended to be demonstrated on the dotcms starter site.




## Running
1.  From the Dynamic Plugin scree, add "com.dotmarketing.portlets.languagesmanager.business" to your OSGi exports
2.  From the "Push Publishing" Screen, upload the bundle under ./bundle/peopleman-bundle.tar.gz
3.  From the Dynamic Plugin scree, make sure the OSGi plugin has started
4.  Start a new/anonymous browser session and hit http://{yourserver}/peopleman/ from the from end. This should show you a list of dotcms users.
5.  Make sure your exports look like this
com.dotmarketing.business.web,
com.dotmarketing.business,
com.dotmarketing.cache,
com.dotmarketing.exception,
com.dotmarketing.util,
com.dotmarketing.portlets.structure.factories,
com.dotmarketing.portlets.structure.model,
com.dotmarketing.portlets.contentlet.business,
com.dotmarketing.portlets.contentlet.model,
org.apache.commons.httpclient,
org.apache.commons.lang,
com.dotmarketing.osgi,
org.codehaus.jackson.map,
javax.xml.parsers,
javax.inject.Qualifier,
org.osgi.framework,
org.osgi.service.packageadmin,
org.osgi.service.startlevel,
org.osgi.service.url,
org.osgi.util.tracker,
org.osgi.service.http,
org.apache.velocity,
org.apache.velocity.tools,
org.apache.velocity.tools.view,
org.apache.velocity.tools.view.context,
org.apache.velocity.tools.view.servlet,
org.apache.velocity.tools.view.tools,
org.springframework.core;version=3.1,
org.springframework.asm;version=3.1,
org.springframework.context.support;version=3.1,
org.springframework.web.bind.annotation;version=3.1,
org.springframework.web.context;version=3.1,
org.springframework.web.context.support;version=3.1,
org.springframework.web.servlet.config.annotation;version=3.1,
org.springframework.web.servlet.handler;version=3.1,
org.springframework.web.servlet.mvc;version=3.1,
org.springframework.osgi.service.importer;version=3.1,
org.springframework.osgi.service.importer.support;version=3.1,
org.springframework.web.servlet.mvc.annotation;version=3.1,
org.springframework.aop;version=3.1,
org.springframework.beans;version=3.1,
org.springframework.beans.factory;version=3.1,
org.springframework.core.io;version=3.1,
org.aopalliance.aop,
org.aopalliance.intercept,
org.springframework.beans.factory.config;version=3.1,
org.springframework.context.annotation;version=3.1,
org.springframework.osgi.web.context.support;version=3.1,
org.springframework.stereotype;version=3.1,
org.springframework.ui;version=3.1,
org.springframework.context.annotation;version=3.1,
org.springframework.web.servlet;version=3.1,
org.springframework.web.servlet.view;version=3.1,
org.springframework.beans.factory.annotation;version=3.1,
com.dotmarketing.portlets.workflows.actionlet,
com.dotmarketing.portlets.workflows.model,
com.dotmarketing.portlets.workflows.business,
com.dotmarketing.filters,
com.dotcms.spring.web,
javax.servlet.resources,
javax.servlet;javax.servlet.http;version=2.5,
org.quartz,
com.dotmarketing.quartz.ScheduledTask,
com.dotmarketing.quartz,
edu.emory.mathcs.backport.java.util.concurrent,
edu.emory.mathcs.backport.java.util.concurrent.locks,
edu.emory.mathcs.backport.java.util.concurrent.atomic,
org.quartz.Job,
org.quartz.JobExecutionContext,
org.quartz.JobExecutionException,
com.liferay.portal.ejb,
com.liferay.util,
com.liferay.portal.model,
org.apache.struts.config,
org.apache.struts.action,
org.apache.struts.config.impl,
org.tuckey.web.filters.urlrewrite

