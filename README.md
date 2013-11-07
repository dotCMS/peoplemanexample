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
