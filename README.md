# camel-webapp

This project is an extension of camel-cxfrs component.

It easy serves static resources (html, javascript, etc) in addition of what camel-cxfrs does

Static resources can be in the jar or externalized in the filesystem.

URI Format
webapp://uneAdresse[?options]


| Option name            | Description                   | Required  | Default value
| ---------------------- | ----------------------------- | --------  | ------------- |
| resourceResolver       | Resolver strategy for loading static resources. Class or service that implements com.iperlon.camel.webapp.ResourceResolver. |  false | com.iperlon.camel.webapp.ClasspathResourceResolver |
| welcome                | Welcome Page                  | false | /static/index.html |
| externalServices       | IDs of JAX-RS annotated beans that aren't executed in the camel route but directly in the java implementation. beanid1,beanid2  | false | |
| rsCacheMaxSize         | Enables in memory cache of static resources. Only resources with a length in bytes less than rsCacheMaxSize are cached. 5242880 for caching  resources that < 5MB | false | - |



