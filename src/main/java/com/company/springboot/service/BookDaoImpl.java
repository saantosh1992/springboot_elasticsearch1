package com.company.springboot.service;

import com.company.springboot.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookDaoImpl implements BookDao {

    private final String INDEX = "book_data";
    private final String TYPE = "books";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> getBookById(String id) {

        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try{
            getResponse = restHighLevelClient.get(getRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  getResponse.getSourceAsMap();
    }

    @Override
    public Book insertBook(Book book) {

        book.setId(UUID.randomUUID().toString());
       Map dataMap =  objectMapper.convertValue(book, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getId()).source(dataMap);

        try{
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
        }catch (ElasticsearchException | IOException ese){
            ese.printStackTrace();
        }
        return book;
    }

    @Override
    public Map<String, Object> updateBookById(Book book, String id) {

        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true);
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Unable to update book");

        try {
            String bookJson = objectMapper.writeValueAsString(book);
            updateRequest.doc(bookJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);

            response = updateResponse.getGetResult().sourceAsMap();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void deleteBookById(String id) {

        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);

        try {
            restHighLevelClient.delete(deleteRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
