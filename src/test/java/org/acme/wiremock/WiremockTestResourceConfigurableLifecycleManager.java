package org.acme.wiremock;

import java.lang.annotation.*;

import io.quarkus.test.common.*;

public class WiremockTestResourceConfigurableLifecycleManager
extends WiremockTestResource
implements QuarkusTestResourceConfigurableLifecycleManager {

    @Override
    public void init(Annotation annotation) {
        //manage eventually new annotations
        QuarkusTestResourceConfigurableLifecycleManager.super.init(annotation);
    }
}
