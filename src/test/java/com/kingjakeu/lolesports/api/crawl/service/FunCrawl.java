package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kingjakeu.lolesports.api.fun.ProducerData;
import com.kingjakeu.lolesports.util.HttpRequester;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FunCrawl {

    @Test
    void test() throws IOException {
        List<String> menuList = this.getMenuList();
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(ProducerData.class)
                .withHeader();

        ObjectWriter writer = csvMapper.writer(csvSchema);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("temp.csv")), "UTF-8");

        for(String menuId: menuList){
            List<ProducerData> producerDataList = new ArrayList<>();
            List<String> articleList = this.getArticleListByMenuId(menuId);
            System.out.println("TOTAL: "+articleList.size());

            for(String articleId : articleList){
                Map<String, String> queryParams = new HashMap<>();
                queryParams.put("useCafeId", "true");
                ProducerData producerData = this.crawlArticle(articleId, queryParams);
                if(!producerData.isEmpty()){
                    producerDataList.add(producerData);
                }
            }
            System.out.println("SUCCESS: "+producerDataList.size());
            writer.writeValues(osw).writeAll(producerDataList);
        }
    }

    private ProducerData crawlArticle(String articleId, Map<String, String> queryParams){
        ProducerData producerData = new ProducerData();
        try{
            String response = HttpRequester.doGetJsonString(
                    "https://apis.naver.com/cafe-web/cafe-articleapi/v2/cafes/25257486/articles/" + articleId,
                    new HashMap<>(),
                    queryParams
            );
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);

            Map<String,Object> result = (Map<String, Object>) responseMap.get("result");
            Map<String,Object> article = (Map<String, Object>) result.get("article");
            String contentHtml = (String) article.get("contentHtml");
            Document document = Jsoup.parse(contentHtml);

            Elements elements = document.getElementsByClass("se-text-paragraph");
            for(Element el : elements){
                String text = el.text();
                if (text.startsWith("· 식품의 유형 -")){
                    producerData.setFoodCategory(text.replace("· 식품의 유형 -", "").trim());
                }else if (text.startsWith("· 상호 -")){
                    producerData.setName(text.replace("· 상호 -", "").trim());
                }else if (text.startsWith("· 대표자 -")){
                    producerData.setPresident(text.replace("· 대표자 -", "").trim());
                }else if (text.startsWith("· 주소 -")){
                    producerData.setAddress(text.replace("· 주소 -", "").trim());
                }else if (text.startsWith("· 연락처 -")){
                    producerData.setContact(text.replace("· 연락처 -", "").trim());
                }else if (text.startsWith("· 사업자 번호 -")){
                    producerData.setProducerNum(text.replace("· 사업자 번호 -", "").trim());
                }else if (text.startsWith("· 통신판매업번호 -")){
                    producerData.setTeleProducerNum(text.replace("· 통신판매업번호 -", "").trim());
                }
            }
        }catch (Exception e){
            // System.out.println("FAIL : " +articleId);
        }
        return producerData;
    }

    private List<String> getMenuList() throws IOException {
        String url = "https://apis.naver.com/cafe-web/cafe2/SideMenuList";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("cafeId", "25257486");
        String response = HttpRequester.doGetJsonString(
                url,
                new HashMap<>(),
                queryParams
        );

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Map<String, Object> message = (Map<String, Object>) responseMap.get("message");
        Map<String, Object> result = (Map<String, Object>) message.get("result");
        List<Map<String,Object>> menuList = (List<Map<String, Object>>) result.get("menus");
        int i = 0;
        List<String> menuIdList = new ArrayList<>();
        for(Map<String, Object> menu : menuList){
            //System.out.println(i +": "+ menu.get("menuId")+": "+ menu.get("menuName"));
            if(i > 61 && i < 75){ // 채소
                menuIdList.add(String.valueOf(menu.get("menuId")));
            }else if(i>75 && i<87){ // 과일
                menuIdList.add(String.valueOf(menu.get("menuId")));
            }
            i += 1;
        }
        return menuIdList;
    }

    private List<String> getArticleListByMenuId(String menuId) throws JsonProcessingException {
        String url = "https://apis.naver.com/cafe-web/cafe2/ArticleList.json";
        List<String> articleIdList = new ArrayList<>();
        Set<String> writerIdSet = new HashSet<>();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("search.clubid", "25257486");
        queryParams.put("search.queryType", "lastArticle");
        queryParams.put("search.menuid", menuId);
        queryParams.put("search.perPage", "50");

        boolean checked = false;
        int i = 1;
        while (!checked){
            queryParams.put("search.page", String.valueOf(i));
            try {

                String response = HttpRequester.doGetJsonString(
                        url,
                        new HashMap<>(),
                        queryParams
                );
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
                Map<String, Object> message = (Map<String, Object>) responseMap.get("message");
                Map<String, Object> result = (Map<String, Object>) message.get("result");
                List<Map<String,Object>> articleList = (List<Map<String, Object>>) result.get("articleList");

                for(Map<String,Object> article : articleList){
                    Date date = new Date((Long) article.get("writeDateTimestamp"));
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if(localDate.getYear() < 2021){
                        checked = true;
                        break;
                    }
                    String writerId = (String) article.get("writerId");
                    if(!writerIdSet.contains(writerId)){
                        writerIdSet.add(writerId);
                        articleIdList.add(String.valueOf(article.get("articleId")));
                    }
                }
                i += 1;
            }catch (Exception e){
                break;
            }
        }
        return articleIdList;
    }
}
