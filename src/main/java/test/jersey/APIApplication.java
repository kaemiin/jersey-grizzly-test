package test.jersey;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import test.jersey.service.DraftService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kaemiin on 2016/3/11.
 */
@ApplicationPath("/*")
public class APIApplication extends ResourceConfig {

    public APIApplication() {

        final Set<Class<?>> classes = new HashSet<>();

        classes.add(DraftService.class);

        this.registerClasses(classes);

        this.register(APIFeature.class);

        this.register(MyExceptionMapper.class);
    }
}

class APIFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {

        if (!context.getConfiguration().isRegistered(APIResolverBinder.class)) {
            context.register(new APIResolverBinder());
        }

        return true;
    }
}

class APIResolverBinder extends AbstractBinder {

    @Override
    protected void configure() {

        //TODO
//        bind(new JSONBuilder(new GsonImpl()))
//                .to(JSONBuilder.class);
//
//        bindAsContract(RedisIAuthProvider.class)
//                .in(Singleton.class);
    }
}