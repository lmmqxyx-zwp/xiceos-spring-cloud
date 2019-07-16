package top.by.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.by.order.entity.Item;
import top.by.order.properties.OrderProperties;
import top.by.order.service.ItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    // Spring框架对RESTful方式的http请求做了封装，来简化操作
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    OrderProperties orderProperties;

    public Item queryItemById(Long id) {

        // 该方法走eureka注册中心调用(去注册中心根据item-service查找服务，这种方式必须先开启负载均衡@LoadBalanced)
        String eurekaClientForItemServiceUrl = "http://item-service/item/{id}";

        Item item = restTemplate.getForEntity(eurekaClientForItemServiceUrl, Item.class, id).getBody();

        logger.info("Eureka：{} -> {}", eurekaClientForItemServiceUrl, item);

// 使用了eureka后不能使用下面的调用方式了，不然会报错
//        Item item = restTemplate.getForObject(orderProperties.getItem().getUrl() + id, Item.class);

//        java.lang.IllegalStateException: No instances available for 127.0.0.1
//        at org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient.execute(RibbonLoadBalancerClient.java:89) ~[spring-cloud-netflix-ribbon-2.0.1.RELEASE.jar:2.0.1.RELEASE]
//        at org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor.intercept(LoadBalancerInterceptor.java:55) ~[spring-cloud-commons-2.0.1.RELEASE.jar:2.0.1.RELEASE]
//        at org.springframework.http.client.InterceptingClientHttpRequest$InterceptingRequestExecution.execute(InterceptingClientHttpRequest.java:92) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.http.client.InterceptingClientHttpRequest.executeInternal(InterceptingClientHttpRequest.java:76) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.http.client.AbstractBufferingClientHttpRequest.executeInternal(AbstractBufferingClientHttpRequest.java:48) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.http.client.AbstractClientHttpRequest.execute(AbstractClientHttpRequest.java:53) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:735) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:670) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.client.RestTemplate.getForObject(RestTemplate.java:311) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at top.by.order.service.impl.ItemServiceImpl.queryItemById(ItemServiceImpl.java:32) ~[classes/:na]
//        at top.by.order.service.impl.OrderServiceImpl.queryOrderById(OrderServiceImpl.java:56) ~[classes/:na]
//        at top.by.order.controller.OrderController.queryOrderById(OrderController.java:18) ~[classes/:na]
//        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_201]
//        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_201]
//        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_201]
//        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_201]
//        at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:189) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1038) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:897) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at javax.servlet.http.HttpServlet.service(HttpServlet.java:634) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882) ~[spring-webmvc-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at javax.servlet.http.HttpServlet.service(HttpServlet.java:741) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.6.RELEASE.jar:5.1.6.RELEASE]
//        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:200) ~[tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:834) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1415) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_201]
//        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_201]
//        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.17.jar:9.0.17]
//        at java.lang.Thread.run(Thread.java:748) [na:1.8.0_201]

        // --------------------------------------------->
        // return item;
        // <---------------------------------------------

        // 添加了Ribbon负载均衡器的做法
        // LoadBalancerInterceptor RibbonLoadBalancerClient (debugger)
        // 在item-service的application.properties中定义过
        String serviceId = "item-service";
        List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            return null;
        }

        // 为了演示，获取第一个实例
        ServiceInstance instance = instances.get(0);

        String url = instance.getHost() + ":" + instance.getPort();

        // 在测试的时候需要启动多个 item-service
        // Error：No instances available for 127.0.0.1
        // 需要在item-service的application.properties中把自己的IP注册到eureka中
        // 即：
        // # 将自己的IP注册到eureka服务中
        // eureka.instance.prefer-ip-address=true
        // eureka.instance.ip-address=127.0.0.1
        // # 指定实例id(在eureka的管理界面显示)
        // eureka.instance.instance-id= ${spring.application.name}###${server.port}
        return this.restTemplate.getForObject("http://" + url + "/item/" + id, Item.class);
    }

}
