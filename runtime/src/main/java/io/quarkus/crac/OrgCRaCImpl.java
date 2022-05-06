package io.quarkus.crac;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class OrgCRaCImpl extends CRaCImpl {
    /**
     * The weakMap is aligns lifespans of objects.
     * Once the Resource become unreachable, the o.c.Resource will become unreachable as well.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static final WeakHashMap<Resource, org.crac.Resource> weakMap = new WeakHashMap<>();

    private static class ResourceImpl extends WeakReference<Resource> implements org.crac.Resource {
        Resource strongRef;

        public ResourceImpl(Resource referent) {
            super(referent);
        }

        @Override
        public void beforeCheckpoint(org.crac.Context<? extends org.crac.Resource> context) throws Exception {
            Resource r = get();
            if (r != null) {
                strongRef = r;
                r.beforeCheckpoint();
            }
        }

        @Override
        public void afterRestore(org.crac.Context<? extends org.crac.Resource> context) throws Exception {
            strongRef.afterRestore();
            strongRef = null;
        }
    }

    @Override
    protected void registerImpl(Resource r) {
        ResourceImpl impl = new ResourceImpl(r);
        weakMap.put(r, impl);
        org.crac.Core.getGlobalContext().register(impl);
    }

}
