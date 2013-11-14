package com.dotcms.peopleman.viewtools;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.web.servlet.DispatcherServlet;

import com.dotmarketing.filters.CMSFilter;
import com.dotmarketing.osgi.GenericBundleActivator;

public class Activator extends GenericBundleActivator {

    private DispatcherServlet dispatcherServlet;
    private ExtHttpService httpService;
	
    @Override
    public void start ( BundleContext bundleContext ) throws Exception {

        //Initializing services...
        initializeServices( bundleContext );

        //Registering the ViewTool service
        registerViewToolService( bundleContext, new PersonToolInfo() );
        
        ServiceReference sRef = bundleContext.getServiceReference( ExtHttpService.class.getName() );
        if ( sRef != null ) {

            //Publish bundle services
            publishBundleServices( bundleContext );

            httpService = (ExtHttpService) bundleContext.getService( sRef );
            try {
                dispatcherServlet = new DispatcherServlet();
                dispatcherServlet.setContextConfigLocation( "spring/example-servlet.xml" );
                httpService.registerServlet( "/spring", dispatcherServlet, null, null );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            CMSFilter.addExclude( "/app/spring" );
        }
    }

    @Override
    public void stop ( BundleContext bundleContext ) throws Exception {
        unregisterViewToolServices();

        //Unregister the servlet
        if ( httpService != null && dispatcherServlet != null ) {
            httpService.unregisterServlet( dispatcherServlet );
        }

        CMSFilter.removeExclude( "/app/spring" );

        //Unpublish bundle services
        unpublishBundleServices();
    }

}