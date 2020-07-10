package com.company.springboot.config;



import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticsearchConfiguration {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    public String clusterNodes;

    @Value("${spring.data.elasticsearch.cluster-name}")
    public String getClusterName;

    private RestHighLevelClient restHighLevelClient;

    @Bean
    public RestHighLevelClient client(){
        try {

            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("localhost", 9200, "http"),
                            new HttpHost("localhost", 9201, "http")));

        }catch (Exception e){
            e.printStackTrace();
        }
        return restHighLevelClient;
    }
}
