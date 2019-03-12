package com.java.test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * date:2019-01-20
 * 19:42
 * description:mongoTest
 * author:潘全科
 */
public class mongoTest {
    //往k10库中students插入一条数据
    //1.连接库
    private MongoClient mongoClient=null;
    private  MongoCollection<Document> stuCollection = null;

    @Before
    public void init(){
        //1、MongoClient(host，port)
        mongoClient = new MongoClient("127.0.0.1", 27017);
        //2、获取你要操作的库
        MongoDatabase db = mongoClient.getDatabase("k10");
        //3、找到需要添加数据的集合
        stuCollection = db.getCollection("students");
    }
    /*
    添加多条数据
     */
    @Test
    public void insertone(){
        Map<String,Object> aMap = new HashMap<>();
        aMap.put("stuName","wangermazi");
        Document doc1 = new Document(aMap);
        /*doc1.append("stuName","wangermazi");
        doc1.append("age",18);
        doc1.append("gender","男");*/
        stuCollection.insertOne(doc1);
    }

    /**
     * 往MongoDB中添加多条数据
     */
    @Test
    public void insertMany(){
        Document doc1 = new Document();
        doc1.append("stuName","lisi");
        doc1.append("age",38);
        doc1.append("gender","女");

        Document doc2 = new Document();
        doc2.append("stuName","wangwu");
        doc2.append("age",88);
        doc2.append("gender","男");

        Document doc3 = new Document();
        doc3.append("stuName","wangermazi");
        doc3.append("age",28);
        doc3.append("gender","女");

        stuCollection.insertMany(Arrays.asList(doc1,doc2,doc3));
    }
    @Test
    public void get(){
        FindIterable<Document> docIt = stuCollection.find();
        docIt.skip(0);
        docIt.limit(10);
        MongoCursor<Document> iterator = docIt.iterator();
        iterator.forEachRemaining(temp-> System.out.println(temp));
    }
@Test
/**
 * 查询库中年龄》>=30的
 */
public void selecrt(){
    Bson bson = Filters.gt("age", 28);
    FindIterable<Document> documents = stuCollection.find(bson);
    MongoCursor<Document> iterator = documents.iterator();
    iterator.forEachRemaining(temp-> System.out.println(temp));
}
    //查询k10库中students表中18<age<=88
    @Test
    public void getByCondi2(){
        //Document doc1 = Document.parse("{$and:[{\"age\":{$gt:18}},{\"age\":{$lte:88}}]}");
        Bson bson1 = Filters.gt("age",18);
        Bson bson2 = Filters.lte("age",88);
        Bson bson = Filters.and(bson1,bson2);
        FindIterable<Document> docIterable = stuCollection.find(bson);
        docIterable.iterator().forEachRemaining(temp-> System.out.println(temp));
    }
    @Test
    public void remove(){
        Bson bson1 = Filters.eq("age",18);
        Bson bson2 = Filters.eq("gender","女");
        Bson bson = Filters.and(bson1,bson2);
        DeleteResult deleteResult = stuCollection.deleteMany(bson);
        System.out.println(deleteResult);
    }
    /**
     * 修改多条数据
     */
    @Test
    public void update(){
        Document bson1 = Document.parse("{\"gender\":\"男\"}");
        Document bson2 = Document.parse("{$set:{\"stuName\":\"admin\",\"age\":99}}");
        UpdateResult updateResult = stuCollection.updateMany(bson1, bson2);
        System.out.println(updateResult);
    }

    @After
    public void close(){
        mongoClient.close();
    }
}
