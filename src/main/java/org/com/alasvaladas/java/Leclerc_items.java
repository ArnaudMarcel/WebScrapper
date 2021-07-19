/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.alasvaladas.java;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnaud
*/

public class Leclerc_items {
    
    public static void extractRequestData(javax.websocket.Session session, String request){
        String search_by = request.substring(request.indexOf(":\""), request.lastIndexOf("\",\"")).replace(":\"", "");
        String value = request.substring(request.lastIndexOf(":\""), request.lastIndexOf("\"}")).replace(":\"", "").replace("%", "%25").replace(" ", "%20").replace("'", "%27");
        Search_items(session, search_by, value);
    }
    
    private static void Search_items(javax.websocket.Session session, String search_by, String value) {
        com.machinepublishers.jbrowserdriver.JBrowserDriver driver = new com.machinepublishers.jbrowserdriver.JBrowserDriver(com.machinepublishers.jbrowserdriver.Settings
            .builder().
            userAgent(com.machinepublishers.jbrowserdriver.UserAgent.CHROME).
            timezone(com.machinepublishers.jbrowserdriver.Timezone.EUROPE_ATHENS).build());
        driver.get("https://fd10-courses.leclercdrive.fr/magasin-096401-Pau-Universite/recherche.aspx?TexteRecherche=" + value);
        String loadedPage = driver.getPageSource();
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(loadedPage);
        if("name_product".equals(search_by)){
            ItemsListByName(session, doc, value, driver);
        }
        driver.close();
    }
    
    private static void ItemsListByName(javax.websocket.Session session, org.jsoup.nodes.Document doc, String value, com.machinepublishers.jbrowserdriver.JBrowserDriver driver){
        java.util.Set<String> item_urls = Sought_item_urls(doc);
        System.out.println(item_urls);
        java.util.Set<org.com.alasvaladas.java.Item> user_items = new java.util.HashSet();
        for(String url : item_urls){
            user_items.add(ScrapeItem(url, driver));
        }
        try {
            org.com.alasvaladas.websocket.WebSockets.My_ServerEndpoint.sendItems(session, user_items);
        } catch (IOException ex) {
            Logger.getLogger(Leclerc_items.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //passer cette fonction en async
    private static org.com.alasvaladas.java.Item ScrapeItem(String url, com.machinepublishers.jbrowserdriver.JBrowserDriver driver){
        System.out.println(url);
        org.com.alasvaladas.java.Item my_item = null;
        if(!url.isEmpty()){
            driver.get(url);
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(driver.getPageSource());
            String description = null;
            if(!doc.getElementsByClass("descriptif-produit").isEmpty()){
                description = doc.getElementsByClass("descriptif-produit").first().text();
            }
            String ingrediants = null;
            if(!doc.getElementsByClass("accordeons-detail").isEmpty()){
              if(doc.getElementsByClass("accordeons-detail").first().getElementsByClass("accordeons-detail-paragaphe").hasText()){
                ingrediants = doc.getElementsByClass("accordeons-detail").first().getElementsByClass("accordeons-detail-paragaphe").first().text();  
              }
            }
            my_item = new org.com.alasvaladas.java.Item(doc.getElementsByClass("titre-fiche").first().text(), doc.getElementsByClass("prix-actuel").first().text(), doc.getElementsByClass("vue-detaillee tc-element").last().attr("src"), ingrediants, description);         
        }

        return my_item;
    }
    
    private static java.util.Set Sought_item_urls(org.jsoup.nodes.Document doc){
        org.jsoup.select.Elements links = doc.getElementsByTag("form");
        org.jsoup.nodes.Element elt = links.first();
        org.com.alasvaladas.java.Find_url_item item_urls = new org.com.alasvaladas.java.Find_url_item(elt.getElementsByTag("script").last().toString());
        return item_urls.get_all_url();
    }
    
}
