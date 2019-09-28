package org.apache.dubbo.demo.consumer.local.stub;

import org.apache.dubbo.demo.DemoService;

public class DemoServiceStub implements DemoService {

    private DemoService demoService;

    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public String sayHello(String name) {
        System.err.println("demo service stub");
        return demoService.sayHello(name);
    }
}
