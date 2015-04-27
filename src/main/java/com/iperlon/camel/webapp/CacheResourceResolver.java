package com.iperlon.camel.webapp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class CacheResourceResolver implements ResourceResolver {

    private ResourceResolver decorated;

    private Map<String, StaticResource> cachedResources = new HashMap<String, StaticResource>();
    private Set<String> excludedCacheResources = new HashSet<String>();

    private int cacheMaxSize;

    public CacheResourceResolver(ResourceResolver decorated, int cacheMaxSize) {
        this.decorated = decorated;
        this.cacheMaxSize = cacheMaxSize;
    }

    @Override
    public StaticResource get(String path) throws Exception {
        //Return non cached resources
        if (excludedCacheResources.contains(path)) {
            return decorated.get(path);
        }
        //Return cached resource
        StaticResource staticResource = cachedResources.get(path);
        if (staticResource != null) {
            return staticResource;
        }
        staticResource = decorated.get(path);
        //if resource is lower than 5Mo with cache it
        if (staticResource != null) {
            if (staticResource.getLength() < cacheMaxSize) {
                synchronized (cachedResources) {
                    if(!cachedResources.containsKey(path)) {
                        cachedResources.put(path, new CachedStaticResource(staticResource));
                    }
                }
            } else {
                excludedCacheResources.add(path);
            }
        }
        return staticResource;
    }
}
