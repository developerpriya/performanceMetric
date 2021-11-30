package com.metric.performance.schedule;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import com.metric.performance.model.Webpage;
import com.metric.performance.repository.WebpageRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private WebpageRepository webpageRepository;

    @Scheduled(fixedDelay = 300000, initialDelay = 3000)
    public void fixedDelaySch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Fixed Delay scheduler:: " + strDate);
        try {
            File myObj = new File("src/main/resources/data/metric.json");
            if (myObj.createNewFile()) {
                System.out.println("File created");
            }
            URL url = new URL("https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=https://www.adfolks.com/&category=performance&key=AIzaSyDkkPkogFDrlLj7DAfUg23iHptE89l_mRs&strategy=mobile");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            Scanner sc;
            try {
                sc = new Scanner(url.openStream());
                Writer writer = new FileWriter("src/main/resources/data/metric.json", false);
                while (sc.hasNext()) {
                    writer.write(sc.nextLine());
                }
                writer.close();
            } finally {
                con.disconnect();
            }

            JSONParser parser = new JSONParser();
            Reader reader = new FileReader("src/main/resources/data/metric.json");
            Object object = parser.parse(reader);

            reader.close();
            HashMap<String, Object> map = new HashMap<>();
            //Json-object extraction
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("lighthouseResult");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get("audits");
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("metrics");
            JSONObject jsonObject4 = (JSONObject) jsonObject3.get("details");
            JSONArray jsonArray = (JSONArray) jsonObject4.get("items");
            JSONObject jsonObject5 = (JSONObject) jsonArray.get(0);
            JSONObject json_categories = (JSONObject) jsonObject1.get("categories");
            JSONObject json_performance = (JSONObject) json_categories.get("performance");
            map.put("id", json_performance.get("id"));

            String fetchTime = (String) jsonObject1.get("fetchTime");

            JSONObject json_loadingExperience = (JSONObject) jsonObject.get("loadingExperience");
            map.put("URL", json_loadingExperience.get("initial_url"));
            String url1 = (String) json_loadingExperience.get("initial_url");

            //Calculation of performance
            double score = (double) json_performance.get("score");
            int performance = (int) (score * 100);

            long firstContentfulPaint, speedIndex, largestContentfulPaint, firstMeaningfulPaint, interactive, totalBlockingTime;
            firstContentfulPaint = (long) jsonObject5.get("firstContentfulPaint");
            speedIndex = (long) jsonObject5.get("speedIndex");
            largestContentfulPaint = (long) jsonObject5.get("largestContentfulPaint");
            firstMeaningfulPaint = (long) jsonObject5.get("firstMeaningfulPaint");
            interactive = (long) jsonObject5.get("interactive");
            totalBlockingTime = (long) jsonObject5.get("totalBlockingTime");

            //Calculation of other values
            double firstContentfulPaint1 = (double) firstContentfulPaint / 1000;
            double speedIndex1 = (double) speedIndex / 1000;
            double interactive1 = (double) interactive / 1000;
            double firstMeaningfulPaint1 = (double) firstMeaningfulPaint / 1000;
            double largestContentfulPaint1 = (double) largestContentfulPaint / 1000;
            double totalBlockingTime1 = totalBlockingTime / 1000;

            map.put("performance", performance);
            map.put("firstContentfulPaint", firstContentfulPaint1);
            map.put("speedIndex", speedIndex1);
            map.put("largestContentfulPaint", largestContentfulPaint1);
            map.put("firstMeaningfulPaint", firstMeaningfulPaint1);
            map.put("interactive", interactive1);
            map.put("totalBlockingTime", totalBlockingTime1);


            Webpage w = new Webpage();
            w.setUrl(url1);
            w.setDate(strDate);
            w.setPerformance(performance);
            w.setFirstContentfulPaint(firstContentfulPaint1);
            w.setFirstMeaningfulPaint(firstMeaningfulPaint1);
            w.setLargestContentfulPaint(largestContentfulPaint1);
            w.setSpeedIndex(speedIndex1);
            w.setInteractive(interactive1);
            w.setTotalBlockingTime(totalBlockingTime1);
            webpageRepository.save(w);
            System.out.println("Values inserted");

        } catch (Exception e) {
            System.out.println("Error : " + e);
        }

    }
}




